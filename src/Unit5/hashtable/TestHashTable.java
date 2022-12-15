package Unit5.hashtable;

import Unit4.TextIO;

public class TestHashTable {

    public static void main(String[] args) {
        MyHashTable table = new MyHashTable(2);
        String key, value;
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("   1. test put(key,value)");
            System.out.println("   2. test get(key)");
            System.out.println("   3. test containsKey(key)");
            System.out.println("   4. test remove(key)");
            System.out.println("   5. show complete contents of hash table.");
            System.out.println("   6. EXIT");
            System.out.println("Enter your command:   ");
            switch (TextIO.getlnInt()) {
                case 1:
                    System.out.println("\n   Key = ");
                    key = TextIO.getln();
                    System.out.println("   Value = ");
                    value = TextIO.getln();
                    table.put(key, value);
                    break;
                case 2:
                    System.out.println("\n   Key = ");
                    key = TextIO.getln();
                    System.out.println("   Value is " + table.get(key));
                    break;
                case 3:
                    System.out.println("\n   Key = ");
                    key = TextIO.getln();
                    System.out.println("   containsKey(" + key + ") is "
                            + table.containsKey(key));
                    break;
                case 4:
                    System.out.println("\n   Key = ");
                    key = TextIO.getln();
                    table.remove(key);
                    break;
                case 5:
                    table.dump();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("   Illegal command.");
                    break;
            }
        }
    }
}
