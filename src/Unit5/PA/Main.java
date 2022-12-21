package Unit5.PA;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

import javax.swing.JFileChooser;

public class Main {
    static HashSet<String> wordSet = new HashSet<String>();

    public static void main(String[] args) {

        String fileNameWithPath = "/Users/Ruhan/Documents/projects/CS1103Intellij/src/Unit5/PA/words.txt";
        // this function reads the words file and stores the result in hashset
        HashSet<String> wordSet = readDictionary(fileNameWithPath);

        // for sorted order
        TreeSet<String> dictionary = new TreeSet<>();

        // for each loop put words in order, for that use TreeSet
        for (String word : wordSet) {
            dictionary.add(word);
        }

        // check words in file
        checkWordsInFile(dictionary);
    }

    /**
     * method to check words in file
     */
    private static void checkWordsInFile(TreeSet<String> dictionary) {
        File selectedFile = getInputFileNameFromUser();
        System.out.println("Words not there in dictionary: ");
        try {
            Scanner in = new Scanner(selectedFile);
            in.useDelimiter("[^a-zA-Z]+");
            // works till file has more words
            while (in.hasNext()) {
                String tk = in.next();
                if (tk.isBlank() || tk.isEmpty())
                    continue;
                // process token tk, and convert to lowercase
                tk = process(tk);
                // word not there in dictionary
                if (!dictionary.contains(tk)) {
                    System.out.println(tk);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * method to select file using the JFileChooser
     *
     * @return
     */
    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return null;
        else
            return fileDialog.getSelectedFile();
    }

    /**
     * method to read dictionary and populate the dictionary set
     *
     * @param fileNameWithPath
     */
    private static HashSet<String> readDictionary(String fileNameWithPath) {
        HashSet<String> wordSet = new HashSet<String>();
        try {
            Scanner filein = new Scanner(new File(fileNameWithPath));
            // works till file has more words
            while (filein.hasNext()) {
                String tk = filein.next();
                if (tk.isBlank() || tk.isEmpty())
                    continue;
                // process token tk, and convert to lowercase
                tk = process(tk);
                wordSet.add(tk);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return wordSet;
    }

    /**
     * processing words and adding in Hashset
     *
     * @param token
     */
    public static String process(String token) {
        // remove extra spaces
        token = token.trim();
        return token.toLowerCase();
    }
}