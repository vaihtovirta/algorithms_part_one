import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();

            randomQueue.enqueue(item);
        }

        for (int i = 0; i < n; i++) {
            StdOut.println(randomQueue.dequeue());
        }
    }
}