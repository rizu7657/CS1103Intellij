package Unit5.hashtable;

public class MyHashTable {

    static private class ListNode {
        String key;
        String value;
        ListNode next;
    }

    private ListNode[] table;

    private int count;

    public MyHashTable() {
        table = new ListNode[64];
    }

    public MyHashTable(int initialSize) {
        table = new ListNode[initialSize];
    }

    public void put(String key, String value) {
        int location = hash(key);
        ListNode list = table[location];

        while (list != null) {
            if (list.key.equals(key))
                break;
            list = list.next;
        }

        if (list != null) {
            list.value = value;
        } else {
            ListNode newNode = new ListNode();
            newNode.key = key;
            newNode.value = value;
            newNode.next = table[location];
            table[location] = newNode;
            count++;
        }
    }

    public String get(String key) {
        int location = hash(key);
        ListNode list = table[location];

        while (list != null) {
            if (list.key.equals(key))
                return list.value;
            list = list.next;
        }

        return null;
    }

    public void remove(String key) {
        int location = hash(key);

        if (table[location] == null) {
            return;
        }

        if (table[location].key.equals(key)) {
            table[location] = table[location].next;
            count--;
            return;
        }
        ListNode prev = table[location];

        ListNode curr = prev.next;

        while (curr != null && ! curr.key.equals(key)) {
            curr = curr.next;
            prev = curr;

        }
        if (curr != null) {
            prev.next = curr.next;
            count--;
        }
    }

    public boolean containsKey(String key) {
        int location = hash(key);
        ListNode list = table[location];
        while (list != null) {
            if (list.key.equals(key))
                return true;
            list = list.next;
        }
        return false;
    }

    void dump() {
        System.out.println();
        for (int i = 0; i < table.length; i++) {
            System.out.println(i + ":");
            ListNode list = table[i];
            while (list != null) {
                System.out.println("  (" + list.key + "," + list.value + ")");
                list = list.next;
            }
            System.out.println();
        }
    }

    public int size() {
        return count;
    }

    private int hash(String key) {
        return (Math.abs(key.hashCode())) % table.length;
    }
}
