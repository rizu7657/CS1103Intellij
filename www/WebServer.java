package Unit7.WebServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WebServer {

    private final static int LISTENING_PORT = 8080;
    private final static String rootDirectory =
            "/Users/ruhan/Documents/projects/CS1103Intellij/www/"; // Dear peer, please change this variable to suite
    // to suite your development environment.

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
        } catch (Exception e) {
            System.out.println("Failed to create listening socket.");
            return;
        }
        System.out.println("Listening on port " + LISTENING_PORT);
        try {
            while (true) {
                Socket connection = serverSocket.accept();
                System.out.println("\nConnection from "
                        + connection.getRemoteSocketAddress());
                ConnectionThread thread = new ConnectionThread(connection);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("Server socket shut down unexpectedly!");
            System.out.println("Error: " + e);
            System.out.println("Exiting.");
        }
    }

    private static void handleConnection(Socket connection) {


        try {
            Scanner in = new Scanner(connection.getInputStream());
            OutputStream out = connection.getOutputStream();
            PrintWriter outpw = new PrintWriter(connection.getOutputStream());


            String request = in.nextLine().trim();
            System.out.println("   " + request);

            // Check request validity
            if (!request.toUpperCase().contains("GET") && !(request.toUpperCase().contains("HTTP/1.1") || request.toUpperCase().contains("HTTP/1.0"))) {
                sendErrorResponse(501, out);
                connection.close();
            }

            // Check request syntax
            if (request.split(" ").length != 3) {
                sendErrorResponse(400, out);
                connection.close();
            }

            // Extract file name and check for downloadable
            String pathToFile = request.split(" ")[1];
            if (pathToFile.equals("/")) {
                File cwd = new File(rootDirectory);
                if (cwd.listFiles() != null && cwd.listFiles().length != 0) {
                    String response = getResponseHeaders(cwd);
                    response += "Content-Type: text/plain\r\n";
                    outpw.println(response);
                    outpw.println("Directory: " + cwd.getName());
                    for (File file : cwd.listFiles()) {
                        if (file.isDirectory())
                            outpw.println("  dir: " + file.getName());
                        outpw.println("  file: " + file.getName());
                    }
                    outpw.flush();
                    connection.close();
                    return;
                } else {
                    outpw.println(getResponseHeaders(cwd));
                    outpw.println("Content-Type: text/plain\r\n");
                    outpw.println("No files to download on Webserver.");
                    outpw.flush();
                    connection.close();
                }
            }
            File file = new File(rootDirectory + pathToFile);

            if (!file.exists()) {
                sendErrorResponse(404, out);
                connection.close();
                return;
            }
            if (file.isDirectory()) {
                sendErrorResponse(403, out);
                connection.close();
                return;
            }
            if (!file.canRead()) {
                sendErrorResponse(403, out);
                connection.close();
                return;
            } else {
                outpw.println(getResponseHeaders(file));
                outpw.flush();
                sendFile(file, out);
            }
        } catch (Exception e) {
            System.out.println("Error while communicating with client: " + e);
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Error closing connection:  " + e);
            }
            System.out.println("Connection closed.");
        }

    }

    private static void sendErrorResponse(int errorCode, OutputStream socketOut) {
        String response = "";
        PrintWriter out = new PrintWriter(socketOut);

        switch (errorCode) {
            case 400 -> {
                response = "\nHTTP/1.1 400 Bad Request\n";
                response += "Connection: close\n";
                response += "Content-Type: text/html\n\n";
                response += """
                        <html><head><title>Error</title></head><body>\n
                        <h2>Error: 400 Bad Request</h2>\n
                        <p>Syntax error.</p>\n
                        </body></html>
                        """;
                out.println(response);
                out.flush();
            }
            case 403 -> {
                response = "\nHTTP/1.1 403 Forbidden\n";
                response += "Connection: close\n";
                response += "Content-Type: text/html\n\n";
                response += """
                        <html><head><title>Error</title></head><body>\n
                        <h2>Error: 403 Forbidden</h2>\n
                        <p>The resource that you requested is a directory.</p>\n
                        </body></html>
                        """;
                out.println(response);
                out.flush();
            }
            case 404 -> {
                response = "\nHTTP/1.1 404 Not Found\n";
                response += "Connection: close\n";
                response += "Content-Type: text/html\n\n";
                response += """
                        <html><head><title>Error</title></head><body>\n
                        <h2>Error: 404 Not Found</h2>\n
                        <p>The resource that you requested does not exist on this server.</p>\n
                        </body></html>
                        """;
                out.println(response);
                out.flush();
            }
            case 500 -> {
                response = "\nHTTP/1.1 500 Internal Server Error\n";
                response += "Connection: close\n";
                response += "Content-Type: text/html\n\n";
                response += """
                        <html><head><title>Error</title></head><body>\n
                        <h2>Error: 500 Internal Server Error</h2>\n
                        <p>The request to the server is incorrect.</p>\n
                        </body></html>
                        """;
                out.println(response);
                out.flush();
            }
            case 501 -> {
                response = "\nHTTP/1.1 501 Not Implemented\n";
                response += "Connection: close\n";
                response += "Content-Type: text/html\n\n";
                response += """
                        <html><head><title>Error</title></head><body>\n
                        <h2>Error: 501 Not Implemented</h2>\n
                        <p>The request not a GET method.</p>\n
                        </body></html>
                        """;
                out.println(response);
                out.flush();
            }
        }
    }

    private static void sendFile(File file, OutputStream socketOut) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        OutputStream out = new BufferedOutputStream(socketOut);
        while (true) {
            int x = in.read(); // read one byte from file
            if (x < 0)
                break; // end of file reached
            out.write(x);  // write the byte to the socket
        }
        out.flush();
    }

    private static String getMimeType(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos < 0)  // no file extension in name
            return "x-application/x-unknown";
        String ext = fileName.substring(pos + 1).toLowerCase();
        if (ext.equals("txt")) return "text/plain";
        else if (ext.equals("html")) return "text/html";
        else if (ext.equals("htm")) return "text/html";
        else if (ext.equals("css")) return "text/css";
        else if (ext.equals("js")) return "text/javascript";
        else if (ext.equals("java")) return "text/x-java";
        else if (ext.equals("jpeg")) return "image/jpeg";
        else if (ext.equals("jpg")) return "image/jpeg";
        else if (ext.equals("png")) return "image/png";
        else if (ext.equals("gif")) return "image/gif";
        else if (ext.equals("ico")) return "image/x-icon";
        else if (ext.equals("class")) return "application/java-vm";
        else if (ext.equals("jar")) return "application/java-archive";
        else if (ext.equals("zip")) return "application/zip";
        else if (ext.equals("xml")) return "application/xml";
        else if (ext.equals("xhtml")) return "application/xhtml+xml";
        else return "x-application/x-unknown";
        // Note:  x-application/x-unknown  is something made up;
        // it will probably make the browser offer to save the file.
    }

    private static String getResponseHeaders(File file) {
        String responseHeader;

        responseHeader = "HTTP/1.1 200 OK\n";
        responseHeader += "Connection: close\n";
        responseHeader += String.format("Content-Length: %d", file.length()) + "\n";

        return responseHeader;
    }

    private static class ConnectionThread extends Thread {
        Socket connection;

        ConnectionThread(Socket connection) {
            this.connection = connection;
        }

        public void run() {
            handleConnection(connection);
        }
    }
}
