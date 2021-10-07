package DynamicConnection;

public class QuickUnion {
    private final int[] tree;

    public QuickUnion(int N) {
        // Initialize with N objects
        this.tree = new int[N];

        // Initial fill components with value=index
        for (int i = 0; i < this.tree.length; i++)
            this.tree[i] = i;
    }


    /**
     * Return the root in tree for node i
     *
     * @param i node to search root for
     * @return the root for node i
     */
    private int find(int i) {
        // Go up through the tree until find the `reflexive node` = root
        while (i != this.tree[i]) i = this.tree[i];
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

        // Add p's root as parent for p component
        this.tree[pRoot] = qRoot;
    }
}
