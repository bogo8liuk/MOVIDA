package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;
import java.lang.RuntimeException;
import java.util.List;

public class NonOrientedGraph implements Graph {
    static private class __couple_list {
        private KeyValueElement couple;
        private List<Integer> list;
    }

    private __couple_list[] adjacencyList;

    public int nodesNumber() {
        return this.adjacencyList.length;
    }

    public int archsNumber() {
        int counter = 0;

        for (__couple_list k: this.adjacencyList)
            counter += k.list.size();

        return counter / 2;
    }

    public int grade(int node) {
        try {
            if (this.adjacencyList.length <= node || 0 > node) throw new RuntimeException("Nonexistent node");
        } catch(RuntimeException exception) {
            System.err.println(exception.getMessage());
        }
        int adjacencyNodes = 0;

        for (int n: this.adjacencyList[node].list)
            ++adjacencyNodes;

        return adjacencyNodes;
    }

    public Arch[] incidentArchs(int node) {
        try {
            if (this.adjacencyList.length <= node || 0 > node) throw new RuntimeException("Nonexistent node");
        } catch(RuntimeException exception) {
            System.err.println(exception.getMessage());
        }
        Arch[] archs = new Arch[this.adjacencyList[node].list.size()];

        for (int n = 0; this.adjacencyList[node].list.size() > n; ++n) {
            int opposite = this.adjacencyList[node].list.get(n);
            Arch arch = new Arch(node, opposite);
            archs[n] = arch;
        }

        return archs;
    }
}
