package DynamicConnection;


public class QuickFind {
    private final int[] tree;

    public QuickFind(int N) {
        // Initialize with N objects
        this.tree = new int[N];

        // Initial fill components with value=index
        for (int i = 0; i < this.tree.length; i++)
            this.tree[i] = i;
    }


    /**
     * Check if index p value is equal to q value
     * If the value is the same then they are connected
     *
     * @param p index
     * @param q index
     * @return boolean true if connected else false
     */
    public boolean connected(int p, int q) {
        return this.tree[p] == this.tree[q];
    }

    /**
     * Replace value for all values = p value
     * With q value [p,p,p,q] => [q,q,q,q] to assoc index in same component
     *
     * @param p index
     * @param q index
     */
    public void union(int p, int q) {
        // value for index p
        int idp = this.tree[p];
        // value for index q
        int idq = this.tree[q];

        for (int i = 0; i < this.tree.length; i++)
            if (this.tree[i] == idp) this.tree[i] = idq;
    }
}
