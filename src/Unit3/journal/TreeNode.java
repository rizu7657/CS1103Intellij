package Unit3.journal;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int item;
    TreeNode left;
    TreeNode right;

    private static TreeNode root;
    private static List<TreeNode> listOfLeaves;

    TreeNode(int item) {
        this.item = item;
    }

    public static void main(String[] args) {

        // Generate random BST
        for (int i = 0; i < 1023; i++) {
            treeInsert(Double.valueOf(Math.random() * 100).intValue());
        }

        // count the leaves
        countLeaves();

    }

    /**
     * Add the item to the binary sort tree to which the global variable
     * "root" refers. (Note that root can’t be passed as a parameter to
     * this routine because the value of root might change, and a change
     * in the value of a formal parameter does not change the actual parameter.)
     */
    private static void treeInsert(int newItem) {
        if (root == null) {
// The tree is empty. Set root to point to a new node containing
// the new item. This becomes the only node in the tree.
            root = new TreeNode(newItem);
            return;
        }
        TreeNode runner; // Runs down the tree to find a place for newItem.
        runner = root; // Start at the root.
        while (true) {
            if (newItem < runner.item) {
// Since the new item is less than the item in runner,
// it belongs in the left subtree of runner. If there
// is an open space at runner.left, add a new node there.
// Otherwise, advance runner down one level to the left.
                if (runner.left == null) {
                    runner.left = new TreeNode(newItem);
                    return; // New item has been added to the tree.
                } else
                    runner = runner.left;
            } else {
// Since the new item is greater than or equal to the item in
// runner, it belongs in the right subtree of runner. If there
// is an open space at runner.right, add a new node there.
// Otherwise, advance runner down one level to the right.
                if (runner.right == null) {
                    runner.right = new TreeNode(newItem);
                    return; // New item has been added to the tree.
                } else
                    runner = runner.right;
            }
        }
    }

    private static List<TreeNode> countLeaves() {


    }
}
