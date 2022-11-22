//package Unit2;

/**
 * This program generates random sentences every 2 seconds using recursion.
 *
 * @author Ruhan
 */
public class RecursionWithSentences {
    static String[] conjunction  = {"and", "or","but","because"};
    static String[] proper_noun  = {"Fred", "Jane", "Richard Nixon", "Miss America"};
    static String[] common_noun  = {"man", "woman", "fish", "elephant", "unicorn"};
    static String[] determiner  = {"a", "the", "every", "some"};
    static String[] adjective  = {"big", "tiny", "pretty", "bald"};
    static String[] intransitive_verb  = {"runs", "jumps", "talks", "sleeps"};
    static String[] transitive_verb  = {"loves", "hates", "sees", "knows", "looks for", "finds"};

    public static void main(String[] args) {
        while (true) {
            System.out.println("Generated sentence: " + generateSentence());
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
            }
        }
    }

    /**
     * This method returns a sentence that represents a base sentence.
     *
     * @return String
     */
    private static String generateSentence() {
        if (Math.random() < 0.8)
            return randomSimpleSentence();
        else
            return randomItem(conjunction) + " " + generateSentence();
    }

    /**
     * This method returns a sentence that represents the simple sentence.
     *
     * @return String
     */
    private static String randomSimpleSentence() {
        return randomNounPhrase() + " " + randomVerbPhrase();
    }

    /**
     * This method returns a phrase that represents a noun phrase.
     *
     * @return String
     */
    private static String randomNounPhrase() { //<proper_noun> | <determiner> [ <adjective> ]. <common_noun> [ who <verb_phrase> ]
        String phrase = null;

        if (Math.random() > 0.5)
            phrase = randomItem(proper_noun);
        else {
            phrase = randomItem(determiner);

            while (Math.random() > 0.7)
                phrase += " " + randomItem(adjective);

            phrase += " " + randomItem(common_noun);

            while (Math.random() > 0.7)
                phrase += " who " + randomVerbPhrase();
        }
        return phrase;
    }

    /**
     * This method returns a phrase that represents a verb phrase.
     *
     * @return String
     */
    private static String randomVerbPhrase() { //<intransitive_verb> | <transitive_verb> <noun_phrase> | is <adjective> | believes that <simple_sentence>
        return switch ((int) (Math.random() * 4)+1) {
            case 1 -> randomItem(intransitive_verb);
            case 2 -> randomItem(transitive_verb) + " " + randomNounPhrase();
            case 3 -> "is " + randomItem(adjective);
            case 4 -> "believes that " + randomSimpleSentence();
            default -> throw new IndexOutOfBoundsException("Random int in switch is either zero or 5!" );
        };
    }

    /**
     * This method returns a random item from a list of strings.
     *
     * @return String
     */
    static String randomItem(String[] listOfStrings) {
        return listOfStrings[(int)(Math.random()*listOfStrings.length)];
    }
}
