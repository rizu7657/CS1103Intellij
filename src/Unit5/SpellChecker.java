package Unit5;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellChecker {
    public static void main(String[] args) {

        try {
            Scanner file = new Scanner(new File("C:\\Users\\Ruhan\\.pers\\CS1103intellij\\src\\Unit5\\words.txt"));
            HashSet<String> dictionary = new HashSet();

            // Read the words.txt file into a HashSet
            while (file.hasNext()) {
                String tk = file.next();
                dictionary.add(tk.toLowerCase());
            }

            // Check that we have added all the words
            if (dictionary.size() != 72875) {
                System.out.println("Not all the words were captured!");
            }

            // Get file from user
            File fileFromUser = getInputFileNameFromUser();
            Scanner in = (fileFromUser != null ? new Scanner(fileFromUser) : null);
            in.useDelimiter("[^a-zA-Z]+");

            while (in.hasNext()) {
                String tk = in.next().toLowerCase();
                if (!dictionary.contains(tk)) {
                    System.out.println(tk + ": " + corrections(tk, dictionary).toString());
                }
            }

            // Check if word in list is in set provided by user

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    static TreeSet corrections(String badWord, HashSet dictionary) {
        TreeSet<String> corrections = new TreeSet<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        StringBuilder sb = new StringBuilder(badWord);
        int random = (int) (Math.random() * badWord.length());

        //• Delete any one of the letters from the misspelled word.
        corrections.add(sb.deleteCharAt(random).toString());

        //• Change any letter in the misspelled word to any other letter.
        sb = new StringBuilder(badWord);
        corrections.add(sb.replace(random, random, String.valueOf(alphabet[random])).toString());

        //• Insert any letter at any point in the misspelled word.
        sb = new StringBuilder(badWord);
        corrections.add(sb.insert(random, alphabet[random]).toString());

        //• Swap any two neighboring characters in the misspelled word.
        sb = new StringBuilder(badWord);
        char temp;

        if (badWord.length() == 1 ) {

        } else if (random == badWord.length() - 1 && random != 0) {
            temp = badWord.charAt(random - 1);
            sb.replace(random - 1, random - 1, String.valueOf(badWord.charAt(random)));
            sb.replace(random, random, String.valueOf(temp));
        } else {
            temp = badWord.charAt(random + 1);
            sb.replace(random + 1, random + 1, String.valueOf(badWord.charAt(random)));
            sb.replace(random, random, String.valueOf(temp));
        }
        corrections.add(sb.toString());

        //• Insert a space at any point in the misspelled word (and check that both of the words that are produced are in the dictionary)
        sb = new StringBuilder(badWord);
        corrections.add(sb.replace(random, random, " ").toString());

        return corrections;
    }

    /**
     * Lets the user select an input file using a standard file
     * selection dialog box.  If the user cancels the dialog
     * without selecting a file, the return value is null.
     */
    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) return null;
        else return fileDialog.getSelectedFile();
    }
}
