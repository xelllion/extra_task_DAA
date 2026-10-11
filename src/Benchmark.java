
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000};
    private static final int WARMUP = 5;
    private static final int RUNS = 20;
    private static final int SEED = 42;

    private static volatile int sink = 0;

    public static void main(String[] args) throws IOException {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter("results.csv"))) {

            writer.println("Size,Structure,Insert_ms,Search_ms,Delete_ms");

            System.out.println(
                    "Size,Structure,Insert_ms,Search_ms,Delete_ms");

            for (int n : SIZES) {
                int[] data = generateData(n);

                for (int i = 0; i < WARMUP; i++) {
                    measureBST(data);
                    measureHash(data);
                }

                double[] bstTimes = new double[3];
                double[] hashTimes = new double[3];

                for (int i = 0; i < RUNS; i++) {
                    double[] bst = measureBST(data);
                    double[] hash = measureHash(data);

                    for (int j = 0; j < 3; j++) {
                        bstTimes[j] += bst[j];
                        hashTimes[j] += hash[j];
                    }
                }

                printResult(writer, n, "BST", bstTimes);
                printResult(writer, n, "HashTable", hashTimes);
            }
        }

        System.out.println("Results saved to results.csv");
    }

    private static int[] generateData(int n) {
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = i;
        }

        Random random = new Random(SEED);

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }

        return data;
    }

    private static double[] measureBST(int[] data) {
        BST tree = new BST();
        int found = 0;

        long start = System.nanoTime();

        for (int key : data) {
            tree.insert(key);
        }

        long insertTime = System.nanoTime() - start;

        start = System.nanoTime();

        for (int key : data) {
            if (tree.search(key)) {
                found++;
            }
        }

        long searchTime = System.nanoTime() - start;

        if (found != data.length) {
            throw new AssertionError("BST search failed");
        }

        sink = found;

        start = System.nanoTime();

        for (int key : data) {
            tree.delete(key);
        }

        long deleteTime = System.nanoTime() - start;

        if (data.length > 0 && tree.search(data[0])) {
            throw new AssertionError("BST deletion failed");
        }

        return new double[]{
                insertTime / 1_000_000.0,
                searchTime / 1_000_000.0,
                deleteTime / 1_000_000.0
        };
    }

    private static double[] measureHash(int[] data) {
        HashTable table = new HashTable();
        int found = 0;

        long start = System.nanoTime();

        for (int key : data) {
            table.insert(key);
        }

        long insertTime = System.nanoTime() - start;

        start = System.nanoTime();

        for (int key : data) {
            if (table.search(key)) {
                found++;
            }
        }

        long searchTime = System.nanoTime() - start;

        if (found != data.length) {
            throw new AssertionError("Hash search failed");
        }

        sink = found;

        start = System.nanoTime();

        for (int key : data) {
            table.delete(key);
        }

        long deleteTime = System.nanoTime() - start;

        if (data.length > 0 && table.search(data[0])) {
            throw new AssertionError("Hash deletion failed");
        }

        return new double[]{
                insertTime / 1_000_000.0,
                searchTime / 1_000_000.0,
                deleteTime / 1_000_000.0
        };
    }

    private static void printResult(
            PrintWriter writer,
            int n,
            String name,
            double[] times) {

        String result = String.format(
                java.util.Locale.US,
                "%d,%s,%.6f,%.6f,%.6f",
                n,
                name,
                times[0] / RUNS,
                times[1] / RUNS,
                times[2] / RUNS
        );

        System.out.println(result);
        writer.println(result);
    }
}
