package DynamicConnection;

public class QuickFind {
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
     * Check if index p value is equal to q value
     * If the value is the same then they are connected
     *
     * @param p index
     * @param q index
     * @return boolean true if connected else false
     */
    boolean isConnected(int p, int q) {
        return this.components[p] == this.components[q];
    }

    /**
     * Replace value for all values = p value
     * With q value [p,p,p,q] => [q,q,q,q] to assoc index in same component
     *
     * @param p index
     * @param q index
     */
    void addUnion(int p, int q) {
        // value for index p
        int idp = this.components[p];
        // value for index q
        int idq = this.components[q];

        for (int i = 0; i < this.components.length; i++)
            if (this.components[i] == idp) this.components[i] = idq;
    }
}
