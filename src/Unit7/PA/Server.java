package Unit7.PA;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Server {

    private static final int LISTENING_PORT = 10094;
    private static final String rootDirectory = "/Users/ruhan/Documents/projects/CS1103Intellij/www/";

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

                System.out.println("\nConnection from " +

                        connection.getRemoteSocketAddress());

                ConnectionThread thread = new

                        ConnectionThread(connection);

                thread.start();

            }

        } catch (Exception e) {

            System.out.println("Server socket shut down unexpectedly!");

            System.out.println("Error: " + e);

            System.out.println("Exiting.");

        }

    }

    private static void handleConnection(Socket connection) throws IOException {

        try {

            OutputStream os = connection.getOutputStream();

            BufferedReader br = new BufferedReader(new

                    InputStreamReader(connection.getInputStream()));

// Display request line.
            String requestLine = br.readLine();
            System.out.println();

            System.out.println(requestLine);

            String filePath = parseRequest(requestLine);

// Get and display the header lines

            String headerLine;

            while ((headerLine = br.readLine()).length() != 0) {

                System.out.println(headerLine);

                File file = getFile(filePath);

                PrintWriter pw = new PrintWriter(os);

                String contentType = getMimeType(file.getName());

                pw.write("HTTP/1.1 200 OK \r\n");

                pw.write("Connection: close \r\n");

                pw.write("Content-Type: " + contentType + " \r\n");

                pw.write("Content-Length: " + file.length() + " \r\n");

                pw.write("\r\n");

                pw.flush();

                sendFile(file, os);

            }

        } catch (HttpErrorException ex) {

            String genericErrorDescription = ex.getHttpErrorCode() + " " +

                    ex.getGenericErrorDescription();

            sendErrorResponse(genericErrorDescription, ex.getMessage(),

                    new

                            DataOutputStream(connection.getOutputStream()));

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        } finally {

            connection.close();

        }

    }

    private static String parseRequest(String requestLine) throws HttpErrorException {

        StringTokenizer tokens = new StringTokenizer(requestLine);

        System.out.println(tokens.countTokens());

        if (tokens.countTokens() > 3)

            throw new HttpErrorException(400, "Malformed Request");

        String method = tokens.nextToken();

        System.out.println(method);

        String path = tokens.nextToken();

        System.out.println(path);

        String protocol = tokens.nextToken();

        System.out.println(protocol);

        if (!method.equals("GET"))

            throw new HttpErrorException(405, "Invalid Request Method");

        if (!protocol.equals("HTTP/1.1") || protocol.equals("HTTP/1.0"))

            throw new HttpErrorException(400, "Invalid Request Protocol");

        if (!isValidPath(path))

            throw new HttpErrorException(400, "Invalid Path");

        return path;

    }

    public static boolean isValidPath(String path) {

        try {

            Paths.get(path);

        } catch (InvalidPathException | NullPointerException ex) {

            return false;

        }

        return true;

    }

    private static File getFile(String pathToFile) throws HttpErrorException {

        if (pathToFile.equals("\\"))

            pathToFile = "index.html.txt";

        File file = new File(rootDirectory + pathToFile);

        if (!file.exists())

            throw new HttpErrorException(404, "The requested resource is not present in this server ");

        if (!file.canRead())

            throw new HttpErrorException(403, "You cannot access this filer");

        if (file.isDirectory())

            return getFile(pathToFile + "index.html");

        return file;

    }

    private static void sendErrorResponse(String genericErrorDescription, String

            detailedErrorMessage,

                                          OutputStream socketOut) throws IOException {

        PrintWriter pw = new PrintWriter(socketOut);

        pw.write("HTTP/1.1 " + genericErrorDescription + " \r\n");

        pw.write("Connection: close \r\n");

        pw.write("Content-Type: text/html \r\n");

        pw.write("\r\n");

        pw.write("<html><head><title>Error</title></head><body> \r\n");

        pw.write("<h2>Error: " + genericErrorDescription + "</h2>\r\n");

        pw.write("<p>" + detailedErrorMessage + "</p>\r\n");

        pw.write("</body></html>\r\n");

        pw.flush();

        pw.close();

    }

    private static void sendFile(File file, OutputStream socketOut) throws IOException {

        InputStream in = new BufferedInputStream(new FileInputStream(file));

        OutputStream out = new BufferedOutputStream(socketOut);

        while (true) {

            int x = in.read(); // read one byte from file

            if (x < 0)

                break; // end of file reached

            out.write(x); // write the byte to the socket

        }

        out.flush();

    }

    private static String getMimeType(String fileName) {

        int pos = fileName.lastIndexOf('.');

        if (pos < 0) // no file extension in name

            return "x-application/x-unknown";

        String ext = fileName.substring(pos + 1).toLowerCase();

        if (ext.equals("txt"))

            return "text/plain";

        else if (ext.equals("html"))

            return "text/html";

        else if (ext.equals("htm"))

            return "text/html";

        else if (ext.equals("css"))

            return "text/css";

        else if (ext.equals("js"))

            return "text/javascript";

        else if (ext.equals("java"))

            return "text/x-java";

        else if (ext.equals("jpeg"))

            return "image/jpeg";

        else if (ext.equals("jpg"))

            return "image/jpeg";

        else if (ext.equals("png"))

            return "image/png";

        else if (ext.equals("gif"))

            return "image/gif";

        else if (ext.equals("ico"))

            return "image/x-icon";

        else if (ext.equals("class"))

            return "application/java-vm";

        else if (ext.equals("jar"))

            return "application/java-archive";

        else if (ext.equals("zip"))

            return "application/zip";

        else if (ext.equals("xml"))

            return "application/xml";

        else if (ext.equals("xhtml"))

            return "application/xhtml+xml";

        else

            return "x-application/x-unknown";

// Note: x-application/x-unknown is something made up;

// it will probably make the browser offer to save the file.

    }

    private static class ConnectionThread extends Thread {

        Socket connection;

        ConnectionThread(Socket connection) {

            this.connection = connection;

        }

        public void run() {

            try {

                handleConnection(connection);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }
}

