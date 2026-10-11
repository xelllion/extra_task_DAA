
public class Tests {

    public static void main(String[] args) {
        testBST();
        testHashTable();
        System.out.println("All tests passed!");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void testBST() {
        BST tree = new BST();

        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int value : values) {
            tree.insert(value);
        }

        check(tree.search(50), "BST root search failed");
        check(tree.search(20), "BST leaf search failed");
        check(!tree.search(99), "BST missing key failed");

        tree.insert(30);

        tree.delete(20);
        check(!tree.search(20), "BST leaf deletion failed");

        tree.delete(30);
        check(!tree.search(30), "BST one-child deletion failed");
        check(tree.search(40), "BST lost child");

        tree.delete(50);
        check(!tree.search(50), "BST two-child deletion failed");

        for (int value : new int[]{40, 60, 70, 80}) {
            check(tree.search(value), "BST lost key " + value);
        }

        tree.delete(999);
        tree.delete(40);
        tree.delete(60);
        tree.delete(70);
        tree.delete(80);

        check(!tree.search(80), "BST final deletion failed");

        System.out.println("BST tests passed");
    }

    private static void testHashTable() {
        HashTable table = new HashTable();

        check(!table.search(10), "Empty table search failed");

        for (int i = 0; i < 1000; i++) {
            table.insert(i);
        }

        table.insert(100);
        table.insert(-50);

        for (int i = 0; i < 1000; i++) {
            check(table.search(i), "Missing hash key " + i);
        }

        check(table.search(-50), "Negative key failed");
        check(!table.search(2000), "Missing key search failed");

        for (int i = 0; i < 1000; i += 2) {
            table.delete(i);
        }

        for (int i = 0; i < 1000; i++) {
            check(table.search(i) == (i % 2 != 0),
                    "Incorrect deletion of " + i);
        }

        table.delete(-50);
        table.delete(9999);

        check(!table.search(-50), "Negative key deletion failed");

        System.out.println("Hash Table tests passed");
    }
}
