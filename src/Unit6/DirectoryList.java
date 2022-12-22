package Unit6;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program lists the files in a directory specified by
 * the user.  The user is asked to type in a directory name.
 * If the name entered by the user is not a directory, a
 * message is printed and the program ends.
 */
public class DirectoryList {


    public static void main(String[] args) {

        String directoryName;  // Directory name entered by the user.
        File directory;        // File object referring to the directory.
        String[] files;        // Array of file names in the directory.
        Scanner scanner;       // For reading a line of input from the user.
        ArrayList<String[]> directoryTree = new ArrayList<>();

        scanner = new Scanner(System.in);  // scanner reads from standard input.

        System.out.print("Enter a directory name: ");
        directoryName = scanner.nextLine().trim();
        directory = new File(directoryName);

        if (!directory.isDirectory()) {
            if (!directory.exists())
                System.out.println("There is no such directory!");
            else
                System.out.println("That file is not a directory.");
        } else {
            listDirectory(directory);
        }

    } // end main()

    private static void listDirectory(File directory) {
        System.out.println("\t" + directory);
        System.out.print("\t");
        String[] files;
        files = directory.list();

        for (int i = 0; i < files.length; i++) {
            File f;
            f = new File(directory, files[i]);

            if (f.isDirectory()) {
                listDirectory(f);

            } else {
                System.out.print("\t\t " + files[i] + "\n");
            }

        }
    }

} // end class DirectoryList
