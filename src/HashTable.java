
public class HashTable {

    private static class Entry {
        int key;
        Entry next;

        Entry(int key, Entry next) {
            this.key = key;
            this.next = next;
        }
    }

    private Entry[] buckets;
    private int size;

    private static final double LOAD_FACTOR = 0.75;

    public HashTable() {
        buckets = new Entry[16];
        size = 0;
    }

    private int hash(int key) {
        int h = key;
        h ^= (h >>> 16);
        h *= 0x7feb352d;
        h ^= (h >>> 15);
        h *= 0x846ca68b;
        h ^= (h >>> 16);

        return h & (buckets.length - 1);
    }

    public void insert(int key) {
        int index = hash(key);
        Entry current = buckets[index];

        while (current != null) {
            if (current.key == key) {
                return;
            }
            current = current.next;
        }

        if ((size + 1) > buckets.length * LOAD_FACTOR) {
            resize();
            index = hash(key);
        }

        buckets[index] = new Entry(key, buckets[index]);
        size++;
    }

    public boolean search(int key) {
        int index = hash(key);
        Entry current = buckets[index];

        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public void delete(int key) {
        int index = hash(key);
        Entry current = buckets[index];
        Entry previous = null;

        while (current != null) {
            if (current.key == key) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return;
            }

            previous = current;
            current = current.next;
        }
    }

    private void resize() {
        Entry[] oldBuckets = buckets;
        buckets = new Entry[oldBuckets.length * 2];

        for (Entry head : oldBuckets) {
            Entry current = head;

            while (current != null) {
                Entry next = current.next;
                int index = hash(current.key);

                current.next = buckets[index];
                buckets[index] = current;

                current = next;
            }
        }
    }
}
