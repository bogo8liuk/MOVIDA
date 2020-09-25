package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;
import java.lang.RuntimeException;
import java.util.List;

public class NonOrientedGraph implements Graph {
    static private class __couple_list {
		public boolean emptyNode;
        public Object value;
        public List<Integer> list;
    }

    private __couple_list[] adjacencyList;

	private void __assertNodeExists(int node) {
        try {
            if (this.adjacencyList.length <= node || 0 > node || this.adjacencyList[node].emptyNode)
				throw new IllegalArgumentException("Nonexistent node");
        } catch(IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
	}

	private void __assertInexistentNode(int node) {
		try {
			if (!(this.adjacencyList.length <= node || 0 > node || this.adjacencyList[node].emptyNode))
				throw new IllegalArgumentException("Already existent node");
		} catch(IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
		}
	}

	private void __assertArchExists(Arch arch) {
		int[] couple = arch.getArchNodes();

		try {
			if (this.adjacencyList.length <= couple[0] || 0 > couple[0] || this.adjacencyList[couple[0]].emptyNode)
				throw new IllegalArgumentException("Nonexistent arch");

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
		int nodesNumber = 0;

		for (__couple_list k: this.adjacencyList) {
			if (!k.emptyNode)
				++nodesNumber;
		}

		return nodesNumber;
    }

    public int archsNumber() {
        int counter = 0;

        for (__couple_list k: this.adjacencyList)
			if (null != k.list)
            	counter += k.list.size();

        return counter / 2;
    }

    public int grade(int node) {
		__assertNodeExists(node);

        return this.adjacencyList[node].list.size();
    }

    public Arch[] incidentArchs(int node) {
		__assertNodeExists(node);

		if (null == this.adjacencyList[node].list)
			return null;

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

		return arch.getArchNodes();
	}

	public int opposite(int node, Arch arch) {
		__assertNodeExists(node);
		__assertArchExists(arch);

		int[] couple = arch.getArchNodes();
		if (couple[0] == node)
			return couple[1];
		else if (couple[1] == node)
			return couple[0];
		else {
			try {
				throw new IllegalArgumentException("The node does not belong to the arch");
			} catch (IllegalArgumentException exception) {
				System.err.println(exception.getMessage());
			}
		}
	}

	public boolean areAdjacent(int nodeA, int nodeB) {
		__assertNodeExists(nodeA);
		__assertNodeExists(nodeB);

		int sizeNodeA = this.adjacencyList[nodeA].list.size();
		int sizeNodeB = this.adjacencyList[nodeB].list.size();

		if (sizeNodeA < sizeNodeB) {
			for (int n = 0; n < sizeNodeA; ++n) {
				if (this.adjacencyList[nodeA].list.get(n) == nodeB)
					return true;
			}

			return false;
		} else {
			for (int n = 0; n < sizeNodeB; ++n) {
				if (this.adjacencyList[nodeB].list.get(n) == nodeA)
					return true;
			}

			return false;
		}
	}

	public void addNode(int node, Object data) {
		__assertInexistentNode(node);

		if (this.adjacencyList.length > node) {
			this.adjacencyList[node].emptyNode = false;
			this.adjacencyList[node].value = data;
			this.adjacencyList[node].list = null;

		} else {
			__couple_list[] newVector = new __couple_list[node + 1];

			newVector[node].emptyNode = false;
			newVector[node].value = data;
			newVector[node].list = null;

			int oldLength = this.adjacencyList.length;
			for (int n = 0; node > n; ++n) {
				if (oldLength > n)
					newVector[n] = this.adjacencyList[n];
				else {
					newVector[n].emptyNode = true;
					newVector[n].value = null;
					newVector[n].list = null;
				}
			}

			this.adjacencyList = newVector;
		}
	}
}
