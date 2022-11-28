package Unit2.PA;

import java.util.Random;

public class RecursiveSyntax {
    static String[] conj = {" and", " or", " but", " because"};
    static String[] prop = {" Fred", " Jane", " Richard", " Miss"};
    static String[] comm = {" man", " woman", " fish", " elephant", "unicorn"};
    static String[] deter = {" a", " the", " every", " some"};
    static String[] adj = {" big", " tiny", " pretty", " bald"};
    static String[] intra = {" runs", " jumps", " talks", " sleeps"};
    static String[] trans = {" loves", " hates", " sees", " knows", " looks for", " finds"};
    static Random prob = new Random(); // used for Probability
    static String sent;

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++)
            System.out.println(sentence().stripIndent());
    }

    public static String sentence() {

        sent = simpleSentence();

        Random choice = new Random();
        if (choice.nextBoolean())
            sent += conj[choice.nextInt(conj.length)] +

                    sentence();
        return sent;
    }

    public static String simpleSentence() {
        return nounPhrase() + verbPhrase();
    }

    public static String nounPhrase() {
        double y = prob.nextDouble();
        Random choice = new Random();
        if (y <= 0.3) {
            sent = deter[choice.nextInt(deter.length)];
            Random r = new Random();
            while (r.nextInt(10) < 3)
                sent += adj[choice.nextInt(adj.length)];
            sent += comm[choice.nextInt(comm.length)];
            if (r.nextBoolean())
                sent += " who" + verbPhrase();
        } else
            sent = prop[choice.nextInt(prop.length)];
        return sent;
    }

    public static String verbPhrase() {
        double x = prob.nextDouble();
        Random choice = new Random();
        if (x <= 0.1)
            sent = " believes that" + simpleSentence();
        else if (x <= 0.2)
            sent = trans[choice.nextInt(trans.length)] + nounPhrase();
        else if (x <= 0.6)
            sent = intra[choice.nextInt(intra.length)];
        else
            sent = " is" + adj[choice.nextInt(adj.length)];
        return sent;
    }
}
