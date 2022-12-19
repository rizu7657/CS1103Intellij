package Unit5.PA;

import java.io.*;

import java.util.*;

import javax.swing.JFileChooser;

public class Dictionary {

    public static void main(String[] arg) throws FileNotFoundException {

        try {

// taking a look at the words.txt file.

            Scanner filein = new Scanner(new File("words.txt"));

// new dictionary data structure created

            HashSet<String> hash = new HashSet();

            while (filein.hasNext()) {

                String tk = filein.next();

//Adding words from "words.txt" to the dictionary

                hash.add(tk.toLowerCase());

            }

            Scanner userFile = new Scanner(getInputFileNameFromUser());

            userFile.useDelimiter("[^a-zA-Z]+");

            while (userFile.hasNext()) {

                String two = userFile.next();

                String two1 = two.toLowerCase();

                if (!hash.contains(two1)) {

                    System.out.println(two1 + ":" + corrections(two1, hash));

                }

            }

        } catch (IOException e) {

            System.out.println("File not found - words.txt");

        }

    }

    /**
     * Once the variants have been saved in a TreeSet, the different misspellings of the word are retrieved.
     * <p>
     * Because they are stored in a tree set, the corrections are automatically * printed out in alphabetical order with no repetitions.
     * <p>
     * Some potential corrections that the program might take into account include the following: *
     * <p>
     * Take off any letter from the misspelled word.
     * <p>
     * Change any one of the letters in the misspelled word with any other letter.
     * <p>
     * Anywhere in the erroneous word, change any letter.
     * <p>
     * Any two adjacent letters in the wrong word should be changed.
     * <p>
     * Put a space after any point in the misspelled word (and check that both of the words that are produced are in the dictionary)
     */

    static TreeSet corrections(String badWord, HashSet dictionary) {

        TreeSet<String> tree = new TreeSet<String>();

//Remove any one of the misspelled word's letters, then look it up in the dictionary to see whether it still exists.

        for (int i = 0; i < badWord.length(); i++) {

            String s = badWord.substring(0, i) + badWord.substring(i + 1);

            if (dictionary.contains(s)) {

                tree.add(s);

            }

        }

//If a word is misspelled, replace any letter with any other letter and then look it up in the dictionary.

        for (int i = 0; i < badWord.length(); i++) {

            for (char ch = 'a'; ch <= 'z'; ch++) {

                String s = badWord.substring(0, i) + ch + badWord.substring(i + 1);

                if (dictionary.contains(s)) {

                    tree.add(s);

                }

            }

        }

//If a word is misspelled, add any letter at any location, then see if it appears in the dictionary.

        for

        (int i = 0; i <= badWord.length(); i++) {

            for (char ch = 'a'; ch <= 'z'; ch++) {

                String s = badWord.substring(0, i) + ch + badWord.substring(i);
                if (dictionary.contains(s)) {

                    tree.add(s);

                }

            }

//Make that the two /words that are produced when you insert a space anywhere in the misspelled word are accepted by the dictionary.

            for (int j = 1; j < badWord.length(); i++) {

                String stringInput = badWord.substring(0, j) + " " + badWord.substring(j);

                String tempString = "";

//Tokenize a string and add the result to tempWords

                StringTokenizer tempWords = new StringTokenizer(stringInput);

//Loop through every word in tempWords.

                while (tempWords.hasMoreTokens()) {

//Put every word in a temporary string.

                    String stringWord1 = tempWords.nextToken();

                    String stringWord2 = tempWords.nextToken();

//If there are any temporary terms in the dictionary, check otherwise.

                    if (dictionary.contains(stringWord1) && dictionary.contains(stringWord2)) {

                        tempString = stringWord1 + " " + stringWord2;

                    } else

                        break;

                    tree.add(tempString);

                }

            }

            if (tree.isEmpty()) {

                tree.add("no suggestions");

            }

            return tree;
        }
        return null;
    }

    /**
     * gives the user access to a normal file selection dialog box for use when
     * choosing a source file. If the user closes the browser, the return value is null.
     * without selecting a file, dialog box.
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

}