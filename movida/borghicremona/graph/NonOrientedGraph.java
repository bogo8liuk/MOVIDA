package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;
import movida.borghicremona.Hash;
import movida.borghicremona.Assert;
import java.lang.RuntimeException;
import java.lang.IllegalArgumentException;
import java.util.*;

/* ATTENTION! This implementation of Graph uses only keys with String type. Using a different
   type for nodes of the graph may lead to unchecked runtime errors. */
public class NonOrientedGraph implements Graph {
	/* NonOrientedGraph has been implemented with adjacency lists. Moreover, we keep track
	   of every node by means of a hashmap, in particular the nodes (String) are the keys and
	   the associated data are the adjacency list for a given node. */
	private KeyValueElement[] adjacencyLists;

	// Label useful to identify elements that do not stand in the table anymore.
	private static final KeyValueElement _DELETED_ = new KeyValueElement("_DELETED_", null);

	private static final int ROOT_NODE = -1;

	/**
	 * It terminates the program if the key is equal to the one of _DELETED_ label.
	 *
	 * @param nodeKey Key to check.
	 */
	private static void __assertNotDeletedNode(String nodeKey) {
		if ("_DELETED_" == nodeKey) {
			System.err.println("Invalid key: aborting");
			System.exit(-1);
		}
	}

	/**
	 * It throws an exception, if the given node does not exist.
	 *
	 * @param nodeKey The node to look for.
	 *
	 * @throws RuntimeException If the searched node does not exist.
	 */
	private void __assertNodeExists(String nodeKey) throws RuntimeException {
		int i = Hash.hash(nodeKey) % this.adjacencyLists.length;
		boolean found = false;

		for (int attempt = 0; this.adjacencyLists.length > attempt; ++attempt) {
			int index = (i + attempt) % this.adjacencyLists.length;

			// If the index-th node is null, the searched node does not exist.
			if (null == this.adjacencyLists[index])
				throw new RuntimeException();
			else if (nodeKey == (String) this.adjacencyLists[index].getKey()) {
				found = true;
				break;
			}
		}

		/* If after the whole table has been scanned the searched node is not found,
		   then the exception must be thrown. */
		if (!found)
			throw new RuntimeException();
	}

	/**
	 * It throws an exception, if the given node exists.
	 *
	 * @param nodeKey The node to look for.
	 *
	 * @throws RuntimeException If the searched node exists.
	 */
	private void __assertInexistentNode(String nodeKey) throws RuntimeException {
		int i = Hash.hash(nodeKey) % this.adjacencyLists.length;

		for (int attempt = 0; this.adjacencyLists.length > attempt; ++attempt) {
			int index = (i + attempt) % this.adjacencyLists.length;

			if (null == this.adjacencyLists[index])
				break;
			else if (nodeKey == (String) this.adjacencyLists[index].getKey())
				throw new RuntimeException();
		}
	}

	/**
	 * It throws an exception, if the given arch does not exist.
	 *
	 * @param arch The arch to look for.
	 *
	 * @throws RuntimeException If the searched arch does not exist.
	 */
	private void __assertArchExists(Arch arch) throws RuntimeException {
		if (null == arch)
			throw new RuntimeException();

		Comparable[] archNodes = arch.getArchNodes();
		String archNodes0 = (String) archNodes[0];
		String archNodes1 = (String) archNodes[1];

		if (null == archNodes0 || null == archNodes1)
			throw new RuntimeException();

		LinkedList<String> adjList0;
		boolean found = false;
		int index = 0;

		int i = Hash.hash(archNodes0) % this.adjacencyLists.length;

		// Looking for the node first node of the arch.
		for (int attempt = 0; this.adjacencyLists.length > attempt; ++attempt) {
			index = (i + attempt) % this.adjacencyLists.length;

			if (null == this.adjacencyLists[index])
				throw new RuntimeException();
			else if (archNodes0 == (String) this.adjacencyLists[index].getKey()) {
				found = true;
				break;
			}
		}

		// If the first node does not exist, even the arch doen not exist.
		if (!found)
			throw new RuntimeException();

		/* If the second node of the arch does not exist in the adjacency list of the first node,
		   then even the arch does not exist. */
		adjList0 = (LinkedList<String>) this.adjacencyLists[index].getValue();
		if (-1 == adjList0.indexOf(archNodes1))
			throw new RuntimeException();

		/* The check must not be carried out on the second node, because we grant that if a node A
		   stands in an adjacency list of another node B, then A must exist. */
	}

	/**
	 * It calculates the index in the table of a node.
	 *
	 * @param nodeKey The node from which the index has to be calculated.
	 *
	 * @return The index of nodeKey inside the table.
	 *
	 * @attention The validity and the existence of nodeKey is an unchecked runtime error.
	 */
	private int getIndex(Comparable nodeKey) {
		int i = Hash.hash((String) nodeKey) % this.adjacencyLists.length;

		/* This loop is granted to terminate and not to encounter a _DELETED_ or null node only in the case
		   that nodeKey is a valid and existent node. */
		while (0 != nodeKey.compareTo(this.adjacencyLists[i].getKey()))
			i = (i + 1) % this.adjacencyLists.length;

		return i;
	}

	public NonOrientedGraph() {
		// Default number.
		this.adjacencyLists = new KeyValueElement[20];
	}

	public int nodesNumber() {
		int nodesNumber = 0;

		for (KeyValueElement k: this.adjacencyLists) {
			if (null != k && _DELETED_ != k)
				++nodesNumber;
		}

		return nodesNumber;
	}

	public int archsNumber() {
		int counter = 0;

		for (KeyValueElement k: this.adjacencyLists) {
			if (null != k && _DELETED_ != k) {
				LinkedList<String> list = (LinkedList<String>) k.getValue();
				counter += list.size();
			}
		}

		/* The presence of a node A in the adjacency list of another node B implies that the node B
		   stands in the adjacency list of the node A, so every node is counted twice. */
		return counter / 2;
	}

	public int grade(Comparable nodeKey) {
		try {
			Assert.notNullKey(nodeKey);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		__assertNotDeletedNode((String) nodeKey);

		try {
			__assertNodeExists((String) nodeKey);
		} catch (RuntimeException exception) {
			return -1;
		}

		int i = Hash.hash((String) nodeKey) % this.adjacencyLists.length;

		LinkedList<String> list = (LinkedList<String>) this.adjacencyLists[i].getValue();
		return list.size();
	}

	public Arch[] incidentArchs(Comparable nodeKey) {
		try {
			Assert.notNullKey(nodeKey);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		__assertNotDeletedNode((String) nodeKey);

		try {
			__assertNodeExists((String) nodeKey);
		} catch (RuntimeException exception) {
			return null;
		}

		int i = Hash.hash((String) nodeKey) % this.adjacencyLists.length;
		LinkedList<String> list = (LinkedList<String>) this.adjacencyLists[i].getValue();

		if (0 == list.size())
			return null;

		Arch[] archs = new Arch[list.size()];

		int j = 0;
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			Arch arch = new Arch(nodeKey, iter.next());
			archs[j++] = arch;
		}

		return archs;
	}

	public Comparable[] edges(Arch arch) {
		try {
			__assertArchExists(arch);
		} catch (RuntimeException exception) {
			return null;
		}

		return arch.getArchNodes();
	}

	public Comparable opposite(Comparable nodeKey, Arch arch) {
		try {
			Assert.notNullKey(nodeKey);
		} catch (IllegalArgumentException excepiton) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		/* The check is only on the arch, because if the node does not exist, then
		   it won't be one of the arch's nodes. */
		try {
			__assertArchExists(arch);
		} catch (RuntimeException exception) {
			return null;
		}

		Comparable[] archNodes = arch.getArchNodes();
		String archNode0 = (String) archNodes[0];
		String archNode1 = (String) archNodes[1];

		if (archNode0 == (String) nodeKey)
			return archNode1;

		else if (archNode1 == (String) nodeKey)
			return archNode0;

		else
			return null;
	}

	public boolean areAdjacent(Comparable nodeKeyA, Comparable nodeKeyB) {
		try {
			Assert.notNullKey(nodeKeyA);
			Assert.notNullKey(nodeKeyB);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		Arch arch = new Arch(nodeKeyA, nodeKeyB);

		try {
			__assertArchExists(arch);
		} catch (RuntimeException exception) {
			return false;
		}

		/* If no exception has been thrown at this point of the code, then it's assured that
		   the two nodes are adjacent. */
		return true;
	}

	/**
	 * It inserts an item in a table, if the latter is not full or if the item does not already exist.
	 *
	 * @param table Where to insert the item.
	 * @param item The element to insert in the table.
	 *
	 * @return true if the insertion has success or the item has not been inserted because of its key
	 * already exists in the key, false otherwise.
	 *
	 * @attention The allocation of the parameters is an unchecked runtime error.
	 */
	private static Boolean insertNode(KeyValueElement[] table, KeyValueElement item) {
		int i = Hash.hash((String) item.getKey());

		for (int attempt = 0; table.length > attempt; ++attempt) {
			int index = (i + attempt) % table.length;

			if (null == table[index] || _DELETED_ == table[index]) {
				table[index] = item;
				return true;
			}

			// Duplicates not allowed.
			else if (0 == item.getKey().compareTo(table[index].getKey()))
				return true;
		}

		return false;
	}

	public void addNode(Comparable nodeKey) {
		try {
			Assert.notNullKey(nodeKey);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid entry to insert: aborting");
			System.exit(-1);
		}

		LinkedList<String> list = new LinkedList<String>();
		KeyValueElement entry = new KeyValueElement(nodeKey, list);

		try {
			__assertNotDeletedNode((String) nodeKey);
		} catch (RuntimeException exception) {
			return;
		}

		boolean inserted = insertNode(this.adjacencyLists, entry).booleanValue();

		if (!inserted) {
			Hash.InsertOperation ins = (t, i) -> insertNode(t, i);
			// The table is grown by 10 elements.
			this.adjacencyLists = Hash.grow(this.adjacencyLists, ins, 10);
			insertNode(this.adjacencyLists, entry);
		}
	}

	public void addArch(Comparable nodeKeyA, Comparable nodeKeyB) {
		try {
			Assert.notNullKey(nodeKeyA);
			Assert.notNullKey(nodeKeyB);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		String keyA = (String) nodeKeyA;
		String keyB = (String) nodeKeyB;

		try {
			__assertNodeExists(keyA);
			__assertNodeExists(keyB);
		} catch (RuntimeException exception) {
			return;
		}

		/* These calls are granted to terminate and not to come across a null or _DELETED_ node
		   because the existence of the two nodes has been already asserted. */
		int a = this.getIndex(nodeKeyA);
		int b = this.getIndex(nodeKeyB);

		LinkedList<String> listA = (LinkedList<String>) this.adjacencyLists[a].getValue();
		LinkedList<String> listB = (LinkedList<String>) this.adjacencyLists[b].getValue();

		// If the arch already exists, then no insertion will be performed.
		if (-1 == listA.indexOf(keyB)) {
			listA.add(keyB);
			listB.add(keyA);
		}
	}

	public void removeArch(Arch arch) {
		try {
			__assertArchExists(arch);
		} catch (RuntimeException exception) {
			return;
		}

		Comparable[] pair = arch.getArchNodes();
		String key0 = (String) pair[0];
		String key1 = (String) pair[1];

		int i = this.getIndex(pair[0]);
		int j = this.getIndex(pair[1]);

		LinkedList<String> list0 = (LinkedList<String>) this.adjacencyLists[i].getValue();
		LinkedList<String> list1 = (LinkedList<String>) this.adjacencyLists[j].getValue();

		list0.remove(key1);
		list1.remove(key0);
	}

	public void removeNode(Comparable nodeKey) {
		try {
			Assert.notNullKey(nodeKey);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		try {
			__assertNodeExists((String) nodeKey);
		} catch (RuntimeException exception) {
			return;
		}

		int i = this.getIndex(nodeKey);

		LinkedList<String> list = (LinkedList<String>) this.adjacencyLists[i].getValue();

		if (0 != list.size()) {
			Iterator<String> iter = list.iterator();

			/* Deletion of the archs of the node to remove: the adjacency list of every node involved in the
			   archs has to be modified. */
			while (iter.hasNext()) {
				String cur = iter.next();

				int j = this.getIndex(cur);

				LinkedList<String> curList = (LinkedList<String>) this.adjacencyLists[j].getValue();
				curList.remove((String) nodeKey);
			}
		}

		this.adjacencyLists[i] = _DELETED_;
	}

	public Comparable[] breadthFirstVisit(NodeOperation op, Comparable start) {
		try {
			Assert.notNullKey(start);
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		if (null == op) {
			System.err.println("Invalid operation on nodes: aborting");
			System.exit(-1);
		}

		String startNode = (String) start;

		try {
			__assertNodeExists(startNode);
		} catch (RuntimeException exception) {
			return null;
		}

		// Map of booleans to mark nodes.
		boolean[] boolmap = new boolean[this.adjacencyLists.length];
		for (int i = 0; boolmap.length > i; ++i)
			boolmap[i] = false;

		// List to fill and return.
		LinkedList<String> list = new LinkedList<String>();

		// Queue to keep track of the nodes to visit.
		LinkedList<String> queue = new LinkedList<String>();

		list.add(startNode);
		queue.add(startNode);

		// The first node has to be marked.
		int startIndex = getIndex(start);
		boolmap[startIndex] = true;

		// If the queue is empty, then all the reachable nodes have been visited.
		while (0 != queue.size()) {
			String cur = queue.remove();

			// Node visit.
			op.visitNode(cur);
			list.add(cur);

			int i = this.getIndex(cur);
			LinkedList<String> adjList = (LinkedList<String>) this.adjacencyLists[i].getValue();

			if (0 != adjList.size()) {
				Iterator<String> iter = adjList.iterator();
				// "For each adjacent node"
				while (iter.hasNext()) {
					String node = iter.next();

					int j = this.getIndex(node);
					// If the j-th node is not marked, then add it to the queue and mark it.
					if (!boolmap[j]) {
						boolmap[j] = true;
						queue.add(node);
					}
				}
			}
		}

		String[] arrayType = new String[1];
		return (0 == list.size()) ? null : list.toArray(arrayType);
	}

	/**
	 * It finds the index of the element with the highest/lowest value in an array.
	 *
	 * @param find If MIN, it returns the index of the element with lowest value, else it returns
	 * the index of the element with highest value.
	 * @param array The array to iterate over.
	 *
	 * @return The index of the element with highest or lowest value in array, according to find.
	 *
	 * @attention The parameters != null is an unchecked runtime error.
	 * @attention It is assumed that in array there is at least a non-null element.
	 */
	private static int findIndex(NodeFind find, Double[] array) {
		switch (find) {
			case MIN:
				Integer min = -1;
				for (int i = 0; array.length > i; ++i) {
					if (null == array[i])
						continue;

					else if (-1 == min || array[min] < array[i])
						min = i;
				}

				array[min] = null;
				return min;

			case MAX:
				Integer max = -1;
				for (int i = 0; array.length > i; ++i) {
					if (null == array[i])
						continue;

					else if (-1 == max || array[max] < array[i])
						max = i;
				}

				array[max] = null;
				return max;
		}

		// Unreachable: to quiet the compiler.
		return -1;
	}

	/**
	 * It finds the indexes of the adjacent nodes of the node that has a certain index.
	 *
	 * @param nodeIndex The index of the node from which searching the adjacent nodes.
	 *
	 * @return An array of indexes, if the node with nodeIndex as index has adjacent nodes, null otherwise.
	 *
	 * @attention It is assumed that nodeIndex is a valid index and that the indexed node exists.
	 */
	private int[] adjacentNodesIndexes(int nodeIndex) {
		LinkedList<String> list = (LinkedList<String>) this.adjacencyLists[nodeIndex].getValue();

		// In this case, the node has no adjacent nodes.
		if (0 == list.size())
			return null;

		Iterator<String> iter = list.iterator();
		int[] indexes = new int[list.size()];
		int i = 0;

		while (iter.hasNext())
			// It finds the index of each adjacent node.
			indexes[i++] = this.getIndex(iter.next());

		return indexes;
	}

	/**
	 * It finds the minimum/maximum spanning tree.
	 *
	 * @param start The node from which building the the spanning tree.
	 * @param find If it is MIN, the function builds a minimum spanning tree, else it builds a maximum
	 * spanning tree.
	 * @param op The function that calculates the weight of the archs.
	 *
	 * @return An array of Arch that are part of the spanning tree.
	 *
	 * @attention The existence of start and start != null are checked runtime errors: it terminates
	 * the process.
	 * @attention If find is null, the function builds a minimum spanning tree.
	 * @attention op != null is a checked runtime error: it terminates the process.
	 */
	public Arch[] spanningTree(Comparable start, NodeFind find, Weight op) {
		try {
			Assert.notNullKey(start);
		} catch (IllegalArgumentException exception) {
			System.err.println("Illegal node: aborting");
			System.exit(-1);
		}

		if (null == op) {
			System.err.println("Illegal operation on node: aborting");
			System.exit(-1);
		}

		// The value to associate to the starting node.
		Double init;
		NodeFind finder = null;
		if (null == find) {
			finder = NodeFind.MIN;
			init = 0.0;
		}
		else {
			init = (finder == NodeFind.MIN) ? 0.0 : Double.MAX_VALUE;
			finder = find;
		}

		// The list of Arch to return.
		List<Arch> list = new LinkedList<Arch>();
		/* An array of values: the indexes correspond to the indexes of the existent nodes in adjacencyLists;
		   each value represents a temporary value for the weight of each node, the start node is associated
		   with 0, if a minimum spanning tree will be built, else it is associated with maximum double value. */
		Double[] tmpWeights = new Double[this.adjacencyLists.length];
		/* An array of values: the indexes correspond to the indexes of the existent nodes in adjacencyLists;
		   each value represent the index of the temporary father of the indexed element (e.g. in fathers[3]
		   there is the index of father of the node that has index 3 in adjacencyLists). The start node has
		   a special value (ROOT_NODE) to indicate that it has not a father in the spanning tree. */
		Integer[] fathers = new Integer[this.adjacencyLists.length];
		/* An array of boolean: the indexes correspond to the indexes of the existent node in adjacencyLists;
		   this array simulates the entrance and the exit from a queue: if queue[i] is true, than the i-th
		   node is in the queue, else it is not in the queue (because it is not been yet inserted or it is
		   already been removed). */
		boolean[] queue = new boolean[this.adjacencyLists.length];

		for (int i = 0; this.adjacencyLists.length > i; ++i) {
			if (0 == this.adjacencyLists[i].getKey().compareTo(start)) {
				// The start node is already inserted in the queue.
				queue[i] = true;
				fathers[i] = ROOT_NODE;
				tmpWeights[i] = init;
			}
			else {
				queue[i] = false;
				fathers[i] = null;
				tmpWeights[i] = null;
			}
		}

		// Counter to keep track which node enters the queue and which node exits from the queue.
		int counter = 1;

		while (0 != counter) {
			counter -= 1;

			// It finds the max/min index and it removes it from the queue.
			int index = findIndex(finder, tmpWeights);
			queue[index] = false;

			/* If index does not represent the start node, then it creates a new Arch with index node's father to
			   add to the list of archs to return. The start node has not a father, so it can't create a new Arch. */
			if (ROOT_NODE != fathers[index]) {
				Arch arch = new Arch(this.adjacencyLists[index].getKey(), this.adjacencyLists[fathers[index]].getKey());
				list.add(arch);
			}

			// It iterates over the adjacent nodes.
			int[] adjacentNodes = adjacentNodesIndexes(index);
			if (null != adjacentNodes) {
				for (int i = 0; adjacentNodes.length > i; ++i) {
					int j = adjacentNodes[i];
					Double weight = op.weight(this.adjacencyLists[index].getKey(), this.adjacencyLists[j].getKey());

					// In this case, the node is not been yet inserted.
					if (null == tmpWeights[j]) {
						tmpWeights[j] = weight;
						queue[j] = true;
						fathers[j] = index;
						counter += 1;
					}

					/* In this case, the node is been already inserted and his temporary father and weight have to be
					   updated under a certain condition (that depends from the building of a maximum or a minimum
					   spanning tree). */
					else if (queue[j]) {
						boolean entered = false;

						switch (finder) {
							case MIN:
								if (weight < tmpWeights[j])
									entered = true;
								break;

							case MAX:
								if (weight > tmpWeights[j])
									entered = true;
								break;
						}

						if (entered) {
							tmpWeights[j] = weight;
							fathers[j] = index;
						}
					}
				}
			}
		}

		Arch[] arrayType = new Arch[1];
		return (0 == list.size()) ? null : list.toArray(arrayType);
	}
}
