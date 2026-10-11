
# BST vs Hash Table Performance Analysis

## 1. Objective

The objective of this assignment is to implement and compare
a Binary Search Tree (BST) and a Hash Table in Java.

The comparison focuses on three operations:
insertion, searching, and deletion.

## 2. Implementation

### Binary Search Tree (BST)

The BST is implemented using nodes.
Each node contains an integer key and references
to its left and right children.

The tree follows the BST property:
- Left subtree contains smaller keys.
- Right subtree contains larger keys.
- Duplicate keys are ignored.

This implementation does not use self-balancing.

### Hash Table

The Hash Table uses Separate Chaining with linked lists
to resolve collisions.

Implementation details:
- Initial capacity: 16
- Load factor threshold: 0.75
- Collision handling: Separate Chaining
- Hashing: Bit mixing and masking
- Resizing: Capacity doubles when the threshold is exceeded
- Duplicate keys are ignored

## 3. Time Complexity Analysis

| Operation | BST Best | BST Average | BST Worst | Hash Best | Hash Average | Hash Worst |
|---|---|---|---|---|---|---|
| Insert | O(1) | O(log n) | O(n) | O(1) | O(1) | O(n) |
| Search | O(1) | O(log n) | O(n) | O(1) | O(1) | O(n) |
| Delete | O(1) | O(log n) | O(n) | O(1) | O(1) | O(n) |

### BST Analysis

The performance of a BST depends on its height.

When the tree is relatively balanced, operations
take O(log n) time.

In the worst case, the tree becomes similar to a
linked list, and operations take O(n) time.

### Hash Table Analysis

A Hash Table provides expected O(1) time for
insertion, searching, and deletion when keys
are distributed uniformly.

In the worst case, multiple keys collide in
the same bucket, resulting in O(n) time.

Resizing requires O(n) time for rehashing,
but insertion remains amortized O(1).

## 4. Experimental Setup

Programming language: Java

Dataset sizes:
- 100 elements
- 1,000 elements
- 10,000 elements

Dataset: Unique randomly shuffled integers

Random seed: 67

Measurement method: System.nanoTime()

Warmup iterations: 5

Measured iterations: 20

Reported values: Average total execution time in milliseconds

Both data structures use identical input datasets.


## 5. Experimental Results

| Size | Structure | Insert (ms) | Search (ms) | Delete (ms) |
|---|---|---|---|---|
| 100 | BST | 0.004550 | 0.004880 | 0.004085 |
| 100 | Hash Table | 0.146030 | 0.002865 | 0.002765 |
| 1000 | BST | 0.061395 | 0.092545 | 0.065075 |
| 1000 | Hash Table | 0.165005 | 0.038370 | 0.023020 |
| 10000 | BST | 1.002205 | 0.936235 | 0.830605 |
| 10000 | Hash Table | 0.291450 | 0.077460 | 0.066500 |



## 6. Performance Discussion

The benchmark results show that the Hash Table generally
performed faster than the BST, especially with larger datasets.

For 100 elements, BST insertion was faster than Hash Table
insertion. However, the Hash Table performed better in
searching and deletion.

For 1,000 elements, BST insertion was still faster,
while Hash Table searching and deletion remained faster.

For 10,000 elements, Hash Table was faster in all three
operations.

The BST performance depends on the height of the tree.
Although the input data was shuffled, searching and
updating nodes still require tree traversal.

The Hash Table uses a hash function to calculate the
bucket index directly. Separate Chaining resolves
collisions, while resizing controls the load factor.

Hash Table insertion can also be affected by resizing,
which requires redistributing existing entries.

Execution times may vary due to JVM optimization,
memory allocation, and system conditions.



## 7. Conclusion

The experiment compared BST and Hash Table performance
using 100, 1,000, and 10,000 unique integer keys.

The results demonstrate that Hash Table generally offers
better performance for searching and deletion.

For larger datasets, Hash Table also showed faster
insertion.

BST performance depends on tree height, while Hash Table
performance depends on hash distribution, collisions,
and load factor.

Therefore, Hash Table is generally preferable for fast
key-based operations, while BST is useful when ordered
data and sorted traversal are required.


## 8. How to Run

Compile the Java files:

    javac -d out src/*.java

Run correctness tests:

    java -cp out Tests

Run performance benchmark:

    java -cp out Benchmark

The benchmark saves execution times to results.csv.
