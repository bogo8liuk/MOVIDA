package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;
import java.lang.RuntimeException;
import java.util.List;

public class NonOrientedGraph implements Graph {
    static private class __couple_list {
        private Object value;
        private List<Integer> list;
    }

    private __couple_list[] adjacencyList;

	private void __assertNodeExists(int node) {
        try {
            if (this.adjacencyList.length <= node || 0 > node) throw new IllegalArgumentException("Nonexistent node");
        } catch(IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
	}

	private void __assertArchExists(Arch arch) {
		int[] couple = arch.getArchNodes(); // TODO: it does not compile!!!

		try {
			if (this.adjacencyList.length <= couple[0] || 0 > couple[0]) throw new IllegalArgumentException("Nonexistent arch");

			boolean found = false;
			for (int n = 0; this.adjacencyList[couple[0]].list.size() > n; ++n) {
				if (this.adjacencyList[couple[0]].list.get(n) == couple[1]) {
					found = true;
					break;
				}
			}

			if (!found) throw new IllegalArgumentException("Nonexistent arch");

		} catch(IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
		}
	}

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
		__assertNodeExists(node);

        return this.adjacencyList[node].list.size();
    }

    public Arch[] incidentArchs(int node) {
		__assertNodeExists(node);
        Arch[] archs = new Arch[this.adjacencyList[node].list.size()];

        for (int n = 0; this.adjacencyList[node].list.size() > n; ++n) {
            int opposite = this.adjacencyList[node].list.get(n);
            Arch arch = new Arch(node, opposite);
            archs[n] = arch;
        }

        return archs;
    }

	public int[] edges(Arch arch) {
		__assertArchExists(arch);

		return arch.getArchNodes(); // TODO: it does not compile!!!
	}
}
