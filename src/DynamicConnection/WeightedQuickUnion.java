package DynamicConnection;


public class WeightedQuickUnion {
    private final int[] tree;
    private final int[] sizes;

    public WeightedQuickUnion(int N) {
        // Initialize with N objects
        this.tree = new int[N];
        this.sizes = new int[N];

        // Initial fill components with value=index
        for (int i = 0; i < this.tree.length; i++) {
            this.tree[i] = i;
            this.sizes[i] = 0;
        }
    }


    /**
     * Return the root in tree for node i
     * Auto path compression to avoid tall tree.
     * Move `i` subtree root to 1 depth below main root.
     *
     * @param i node to search root for
     * @return the root for node i
     */
    private int find(int i) {
        // Go up through the tree until find the `reflexive node` = root
        while (i != this.tree[i]) {
            // Root for `i` will be one degree below main root now.
            this.tree[i] = this.tree[this.tree[i]];
            i = this.tree[i];
        }
        return i;

    }

    /**
     * Check if node p 's root belongs to node q 's root
     * If the value is the same then they are connected
     *
     * @param p index
     * @param q index
     * @return boolean true if connected else false
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Replace one value only in array to connect components
     * With p and q 's root corresponding [0,1] => [1,1] p is now sub-tree of q's root
     *
     * @param p index
     * @param q index
     */
    public void union(int p, int q) {
        // root for index p
        int pRoot = find(p);
        // root for index q
        int qRoot = find(q);
        // Same root? Do nothing!
        if (pRoot == qRoot) return;

        weightedUnion(pRoot, qRoot);


    }

    /**
     * Check for size in two trees.
     * Add the smaller tree below the big one.
     *
     * @param pRoot root for p
     * @param qRoot root for q
     */

    void weightedUnion(int pRoot, int qRoot) {
        int pSize = this.sizes[pRoot];
        int qSize = this.sizes[qRoot];

        if (pSize < qSize) {
            this.tree[pRoot] = qRoot;
            this.sizes[qRoot] += pSize;
        } else {
            this.tree[qRoot] = pRoot;
            this.sizes[pRoot] += qSize;
        }
    }
}
