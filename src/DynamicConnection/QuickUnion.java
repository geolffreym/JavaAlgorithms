package DynamicConnection;

public class QuickUnion {
    private int[] components;

    void constructor(int N) {
        // Initialize with N objects
        this.components = new int[N];
    }

    void fillComponents() {
        // Initial fill components with value=index
        for (int i = 0; i < this.components.length; i++)
            this.components[i] = i;
    }

    /**
     * Return the root in tree for node i
     *
     * @param i node to search root for
     * @return the root for node i
     */
    private int getRoot(int i) {
        // Go up through the tree until find the `reflexive node` = root
        while (i != this.components[i]) i = this.components[i];
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
    boolean isConnected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    /**
     * Replace one value only in array to connect components
     * With p and q 's root corresponding [0,1] => [1,1] p is now sub-tree of q's root
     *
     * @param p index
     * @param q index
     */
    void addUnion(int p, int q) {
        // root for index p
        int pRoot = getRoot(p);
        // root for index q
        int qRoot = getRoot(q);

        // Add p's root as parent for p component
        this.components[pRoot] = qRoot;
    }
}
