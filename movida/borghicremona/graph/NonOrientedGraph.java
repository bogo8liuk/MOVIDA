package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;
import movida.borghicremona.Hash;
import movida.borghicremona.Assert;
import java.lang.RuntimeException;
import java.util.*;
//import java.util.List;
//import java.util.LinkedList;

public class NonOrientedGraph implements Graph {
	/* NonOrientedGraph has been implemented with adjacency lists. Moreover, we keep track
	   of every node by means of a hashmap, in particular the nodes (String) are the keys and
	   the associated data are the adjacency list for a given node. */
	private KeyValueElement[] adjacencyLists;

	// Label useful to identify elements that do not stand in the table anymore.
	private static final KeyValueElement _DELETED_ = new KeyValueElement("_DELETED_", null);

	private static void __assertNotDeletedNode(KeyValueElement item) {
		if (_DELETED_ == item || "_DELETED_" == (String) item.getKey()) {
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

		for (int attempt = 0; this.adjacencyLists.length < attempt; ++attempt) {
			int index = (i + attempt) % this.adjacencyLists.length;

			// If the index-th node is null, the searched node does not exist.
			if (null == this.adjacencyLists[index] || _DELETED_ == this.adjacencyLists[index])
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

		for (int attempt = 0; this.adjacencyLists.length < attempt; ++attempt) {
			int index = (i + attempt) % this.adjacencyLists.length;

			if (null == this.adjacencyLists[index] || _DELETED_ == this.adjacencyLists[index])
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
	 * @throws RuntimeException If the searched arch does not exist
	 */
	private void __assertArchExists(Arch arch) throws RuntimeException {
		if (null == arch)
			throw new RuntimeException();

		String[] archNodes = (String[]) arch.getArchNodes();

		if (null == archNodes[0] || null == archNodes[1])
			throw new RuntimeException();

		LinkedList<String> adjList0;
		boolean found = false;
		int index = 0;

		int i = Hash.hash(archNodes[0]) % this.adjacencyLists.length;

		// Looking for the node first node of the arch.
		for (int attempt = 0; this.adjacencyLists.length < attempt; ++attempt) {
			index = (i + attempt) % this.adjacencyLists.length;

			if (null == this.adjacencyLists[index] || _DELETED_ == this.adjacencyLists[index])
				throw new RuntimeException();
			else if (archNodes[0] == (String) this.adjacencyLists[index].getKey()) {
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
		if (-1 == adjList0.indexOf(archNodes[1]))
			throw new RuntimeException();

		/* The check must not be carried out on the second node, because we grant that if a node A
		   stands in an adjacency list of another node B, then A must exist. */
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

		for (KeyValueElement k: this.adjacencyLists)
			if (null != k && _DELETED_ != k) {
				LinkedList<String> list = (LinkedList<String>) k.getValue();
				counter += list.size();
			}

		/* The presence of a node A in the adjacency list of another node B implies that the node B
		   stands in the adjacency list of the node A, so every node is counted twice. */
		return counter / 2;
	}

	public int grade(Comparable nodeKey) {
		try {
			Assert.notNullKey(nodeKey)
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		__assertNotDeletedKey((String) nodeKey);

		try {
			__assertNodeExist((String) nodeKey)
		} catch (RuntimeException exception) {
			return -1;
		}

		int i = Hash.hash((String) nodeKey) % this.adjacencyLists.length;

		LinkedList<String> list = (LinkedList<String>) this.adjacencyLists[i].getValue();
		return list.size();
	}

	public Arch[] incidentArchs(Comparable nodeKey) {
		try {
			Assert.notNullKey(nodeKey)
		} catch (IllegalArgumentException exception) {
			System.err.println("Invalid node: aborting");
			System.exit(-1);
		}

		__assertNotDeletedKey((String) nodeKey);

		try {
			__assertNodeExist((String) nodeKey)
		} catch (RuntimeException exception) {
			return null;
		}

		int i = Hash.hash((String) nodeKey) % this.adjacencyLists.length;
		LinkedList<String> list = (LinkedList<String>) this.adjcencyLists[i].getValue();

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
		} catch (IllegalArgumentExcepiton exception) {
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
	 */
	private static Boolean insertNode(KeyValueElement[] table, KeyValueElement item) {
		int i = Hash.hash((String) item.getKey()) % this.adjacencyLists.length;

		for (int attempt = 0; table.length > attempt; ++attempt) {
			int index = (i + attempt) % this.adjacencyLists.length;

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
			__assertNotDeletedKey(entry);
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

		try {
			__assertNodeExists(nodeKeyA);
			__assertNodeExists(nodeKeyB);
		} catch (RuntimeException exception) {
			return;
		}

		String keyA = (String) nodeKeyA;
		String keyB = (String) nodeKeyB;

		int a = Hash.hash(keyA) % this.adjacencyLists.length;
		while (0 != nodeKeyA.compareTo(this.adjacencyLists[a].getKey()))
			a = (a + 1) % this.adjacencyLists.length;

		int b = Hash.hash(keyB) % this.adjacencyLists.length;
		while (0 != nodeKeyB.compareTo(this.adjacencyLists[b].getKey()))
			b = (b + 1) % this.adjacencyLists.length;
		/* The previous loops are granted to terminate and not to come across a null or _DELETED_ node
		   because the existence of the two nodes have been already asserted. */

		LinkedList<String> listA = (LinkedList<String>) this.adjacencyLists[a].getValue();
		LinkedList<String> listB = (LinkedList<String>) this.adjacencyLists[b].getValue();

		// If the arch already exists, then no insertion will be performed.
		if (-1 == listA.indexOf(b)) {
			listA.add(b);
			listB.add(a);
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

		int i = Hash.hash(key0) % this.adjacencyLists.length;
		while (0 != pair[0].compareTo(this.adjacencyLists[i].getKey()))
			i = (i + 1) % this.adjacencyLists.length;

		int j = Hash.hash(key1) % this.adjacencyLists.length;
		while (0 != pair[1].compareTo(this.adjacencyLists[j].getKey()))
			j = (j + 1) % this.adjacencyLists.length;

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

		__assertNodeExists(nodeKey);

		int i = Hash.hash((String) nodeKey) % this.adjacencyLists.length;

		while (0 != nodeKey.compareTo(this.adjacencyLists[i].getKey()))
			i = (i + 1) % this.adjacencyLists.length;

		LinkedList<String> list = (LinkedList<String>) this.adjacencyLists[i].getValue();
		Iterator<String> iter = list.iterator();

		/* Deletion of the archs of the node to remove: the adjacency list of every node involved in the
		   archs has to be modified. */
		while (iter.hasNext()) {
			String cur = iter.next();

			int j = Hash.hash(cur) % this.adjacencyLists.length;
			while (cur != (String) this.adjacencyLists[j].getKey())
				j = (j + 1) % this.adjacencyLists.length;

			LinkedList<String> curList = (LinkedList<String>) this.adjacencyLists[j].getValue();
			curList.remove((String) nodeKey);
		}

		this.adjacencyLists[i] = _DELETED_;
	}

	/*private static void apply(NodeOperation item, int node) {
		item.operation(node);
	}
	
	public VisitTree breadthFirstVisit(NodeOperation item, int start) {
		__assertNodeExists(start);

		VisitTree tree = new VisitTree(start);
		VisitTree currentNode = null;
		LinkedList<Integer> queue = new LinkedList<Integer>(); 
		
		queue.add(start);
		this.adjacencyList[start].mark = true;
		
		while (0 < queue.size()) {
			Integer n = queue.remove();
			int node = n.intValue();
			currentNode = tree.getTree(node);
			
			apply(item, node);
			Iterator<Integer> iter = this.adjacencyList[node].list.iterator();
			
			while (iter.hasNext()) {
				__couple_list currentItem = this.adjacencyList[iter.next()];
				if (!currentItem.mark && !currentItem.emptyNode) {
					currentItem.mark = true;
					queue.add(iter.next());
					tree.addChild(iter.next(), currentNode);
				}
			}
		}

		for (__couple_list k: this.adjacencyList) {
			k.mark = false;
		}

		return tree;
	}

	public void depthFirstVisit(NodeOperation item) {

	}*/
}
