package movida.borghicremona;

import movida.borghicremona.hashmap.HashMap;
import movida.borghicremona.sort.Vector;
import movida.borghicremona.bstree.BinarySearchTree;
import movida.borghicremona.graph.*;

public class Test {

	/**
	 * It prints the hash value of a string and it stores it in a vector of integers in a given position.
	 *
	 * @param toHash String from which it gets the hash value.
	 * @param length Size of a hypothetical hashmap.
	 * @param store Vector of integers where the hash value of toHash is stored.
	 * @param index Index of the vector where the hash value has to be stored.
	 *
	 * @attention This function should not be called and there's no check of the index value.
	 */
	private static void __hashTest(String toHash, int length, Integer[] store, int index) {
		int hashTmp = Hash.hash(toHash) % length;
		store[index] = hashTmp;
		System.out.println(toHash + ": " + hashTmp);
	}
	
	/**
	 * It prints the results of collisions between 50 names, given the hash function of HashMap class.
	 */
	public static void collisionTestHashMap() {

		System.out.println("Testing the number of collision with an hashmap with " + 50 + " elements and 50 different strings:\n");

		Integer[] hashValues = new Integer[50];
		int index = 0;

		// Printing the results of the number of collisions given by the strings of names of random people
		__hashTest("Scottie Pippen", 50, hashValues, index++);
		__hashTest("Patrick Ewing", 50, hashValues, index++);
		__hashTest("Hakeem Olajuwon", 50, hashValues, index++);
		__hashTest("Allen Iverson", 50, hashValues, index++);
		__hashTest("Juan Carlos Navarro", 50, hashValues, index++);
		__hashTest("Carmelo Anthony", 50, hashValues, index++);
		__hashTest("Zion Williamson", 50, hashValues, index++);
		__hashTest("Oscar Schmidt", 50, hashValues, index++);
		__hashTest("JaVale McGee", 50, hashValues, index++);
		__hashTest("Rudy Gobert", 50, hashValues, index++);
		__hashTest("Lebron James", 50, hashValues, index++);
		__hashTest("Kobe Bryant", 50, hashValues, index++);
		__hashTest("Carlton Myers", 50, hashValues, index++);
		__hashTest("Yao Ming", 50, hashValues, index++);
		__hashTest("Ja Morant", 50, hashValues, index++);
		__hashTest("Kelly Olynyk", 50, hashValues, index++);
		__hashTest("John Stockton", 50, hashValues, index++);
		__hashTest("Nando De Colo", 50, hashValues, index++);
		__hashTest("Kevin Garnett", 50, hashValues, index++);
		__hashTest("Tim Duncan", 50, hashValues, index++);
		__hashTest("Manu Ginobili", 50, hashValues, index++);
		__hashTest("Vasilis Spanoulis", 50, hashValues, index++);
		__hashTest("Dario Saric", 50, hashValues, index++);
		__hashTest("Chris Bosh", 50, hashValues, index++);
		__hashTest("Danilo Gallinari", 50, hashValues, index++);
		__hashTest("Dirk Nowitzki", 50, hashValues, index++);
		__hashTest("James Harden", 50, hashValues, index++);
		__hashTest("Joakim Noah", 50, hashValues, index++);
		__hashTest("Pete Maravich", 50, hashValues, index++);
		__hashTest("Chauncey Billups", 50, hashValues, index++);
		__hashTest("Jeremy Lin", 50, hashValues, index++);
		__hashTest("Boris Diaw", 50, hashValues, index++);
		__hashTest("Stephen Curry", 50, hashValues, index++);
		__hashTest("Nikola Jokic", 50, hashValues, index++);
		__hashTest("Michael Jordan", 50, hashValues, index++);
		__hashTest("Shaquille O'Neal", 50, hashValues, index++);
		__hashTest("Ray Allen", 50, hashValues, index++);
		__hashTest("Klay Thompson", 50, hashValues, index++);
		__hashTest("Glenn Robinson III", 50, hashValues, index++);
		__hashTest("Marco Belinelli", 50, hashValues, index++);
		__hashTest("Tim Hardaway Jr.", 50, hashValues, index++);
		__hashTest("Dennis Rodman", 50, hashValues, index++);
		__hashTest("Steve Nash", 50, hashValues, index++);
		__hashTest("Nathan Jawai", 50, hashValues, index++);
		__hashTest("Nate Robinson", 50, hashValues, index++);
		__hashTest("Felipe Reyes", 50, hashValues, index++);
		__hashTest("Bol Bol", 50, hashValues, index++);
		__hashTest("Paul Pierce", 50, hashValues, index++);
		__hashTest("Chris Paul", 50, hashValues, index++);
		__hashTest("Jason Kidd", 50, hashValues, index++);

		System.out.println("");

		for (int i = 0; i < hashValues.length; ++i) {
			if (null != hashValues[i]) {
				int sameHash = 1;

				System.out.print("For " + hashValues[i] + ", there are");
				for (int j = i + 1; j < hashValues.length; ++j) {
					if (hashValues[j] == hashValues[i]) {
						++sameHash;
						hashValues[j] = null;
					}
				}
				System.out.println(": " + sameHash + " equal hash values");
			}
		}

		System.out.println("");
	}
	
	/**
	 * It tests the consistency of HashMap data structure, printing every successful result.
	 *
	 * @attention It throws an Exception if the status is not consistent.
	 * @attention There's no warranty that successful return of this function makes the implementation of HashMap correct.
	 */
	public static void implementationTestHashMap() throws Exception {
		String data = "data";
		System.out.println("Testing the implementation of HashMap\n");

		System.out.println("Hashmap of 10 elements");
		HashMap hashmap = new HashMap(10);

		if (null != hashmap.toArray())
			throw new Exception("toArray() error: the array should be null because the hashmap is empty.");

		System.out.println("Insertion of \"Tony Parker\" key");
		hashmap.insert(new KeyValueElement("Tony Parker", data));

		if (null == hashmap.toArray())
			throw new Exception("toArray() or insert() error: a new element has been inserted.");

		if (null == hashmap.search("Tony Parker"))
			throw new Exception("search() error: a element with key \"Tony Parker\" has been inserted.");

		System.out.println("");
		hashmap.printTable();
		System.out.println("");

		System.out.println("New hashmap of 10 elements created");
		hashmap = new HashMap(10);

		System.out.println("Insertion of \"Bo McCalebb\" key");
		hashmap.insert(new KeyValueElement("Bo McCalebb", data));

		System.out.println("Insertion of \"Bill Russell\" key");
		hashmap.insert(new KeyValueElement("Bill Russell", data));

		System.out.println("Insertion of \"Marcus Morris\" key");
		hashmap.insert(new KeyValueElement("Marcus Morris", data));

		System.out.println("Insertion of \"LiAngelo Ball\" key");
		hashmap.insert(new KeyValueElement("LiAngelo Ball", data));

		if (null != hashmap.search("Lonzo Ball"))
			throw new Exception("search() error: there is no element with key Lonzo Ball.");

		System.out.println("");
		hashmap.printTable();
		System.out.println("");

		KeyValueElement item = new KeyValueElement("Markieff Morris", data);
		hashmap.insert(item);
		hashmap.insert(item);

		if (null == hashmap.search(item.getKey()))
			throw new Exception("search() error: an element with key \"Markief Morris\" exists.");

		hashmap.delete(item.getKey());

		if (null != hashmap.search(item.getKey()))
			throw new Exception("search() or delete() error: HashMap does not allow duplicates.");

		System.out.println("");
		hashmap.printTable();
		System.out.println("");
		
		hashmap = new HashMap(3);
		int length = hashmap.length();
		hashmap.insert(new KeyValueElement("Dennis Rodman", data));
		hashmap.insert(new KeyValueElement("Kevin Garnett", data));
		hashmap.insert(new KeyValueElement("Kobe Bryant", data));
		hashmap.insert(new KeyValueElement("Coby White", data));

		if (length == hashmap.length())
			throw new Exception("length must be modified coherently after adding more elements beyond the hashmap initial length.");

		if (null == hashmap.search("Dennis Rodman"))
			throw new Exception("search() error: element with key \"Dennis Rodman\" exists.");

		if (null == hashmap.search("Kevin Garnett"))
			throw new Exception("search() error: element with key \"Kevin Garnett\" exists.");

		if (null == hashmap.search("Kobe Bryant"))
			throw new Exception("search() error: element with key \"Kobe Bryant\" exists.");

		if (null == hashmap.search("Coby White"))
			throw new Exception("search() error: element with key \"Coby White\" exists.");

		System.out.println("");
		hashmap.printTable();
		System.out.println("");
		
		System.out.println("HashMap implementation test terminated successfully!\n");
	}

	/**
	 * It checks if an array of Integers is sorted.
	 *
	 * @param array Array to check.
	 * @return true if array is sorted, false otherwise.
	 */
	private static boolean isSorted(Integer[] array) {
		for (int i = 0; array.length - 1 > i; ++i) {

			if (array[i] > array[i + 1])
				return false;
		}

		return true;
	}

	/**
	 * It prints the content of an array.
	 *
	 * @param array Array to print.
	 */
	private static void printV(Integer[] array) {
		for (Integer i: array)
			System.out.print(i + " ");
		System.out.println("");
	}

	/**
	 * It tests the success of the sorting algorithms, printing every successful result.
	 *
	 * @attention It throws an Exception if a case where an array is not sorted occurs.
	 * @attention There's no warranty that successful return of this function makes the implementation of Vector correct.
	 */
	public static void implementationTestSort() throws Exception {
		Integer i = new Integer(1);
		Vector<Integer> v;

		Integer[] array1 = {9, 8, 2, 4, 14, 1, 2, 8, 13, 5, 3};
		v = new Vector<Integer>(array1);
		System.out.println("array" + i);
		printV(array1);
		v.selectionSort();
		if (!isSorted(array1))
			throw new Exception("array" + i + " is not sorted");
		printV(array1);
		++i;

		Integer[] array2 = {2, 0};
		v = new Vector<Integer>(array2);
		System.out.println("array" + i);
		printV(array2);
		v.selectionSort();
		if (!isSorted(array2))
			throw new Exception("array" + i + " is not sorted");
		printV(array2);
		++i;

		Integer[] array3 = {3};
		v = new Vector<Integer>(array3);
		System.out.println("array" + i);
		printV(array3);
		v.selectionSort();
		if (!isSorted(array3))
			throw new Exception("array" + i + " is not sorted");
		printV(array3);
		++i;

		Integer[] array4 = {10, 1, 4, 12, 13, 9, 9, 2, 1, 4, 17, 11, 12, 6, 5, 3, 10};
		v = new Vector<Integer>(array4);
		System.out.println("array" + i);
		printV(array4);
		v.quickSort();
		if (!isSorted(array4))
			throw new Exception("array" + i + " is not sorted");
		printV(array4);
		++i;

		Integer[] array5 = {3, 0};
		v = new Vector<Integer>(array5);
		System.out.println("array" + i);
		printV(array5);
		v.quickSort();
		if (!isSorted(array5))
			throw new Exception("array" + i + " is not sorted");
		printV(array5);
		++i;

		Integer[] array6 = {1, 2};
		v = new Vector<Integer>(array6);
		System.out.println("array" + i);
		printV(array6);
		v.quickSort();
		if (!isSorted(array6))
			throw new Exception("array" + i + " is not sorted");
		printV(array6);
		++i;

		Integer[] array7 = {10};
		v = new Vector<Integer>(array7);
		System.out.println("array" + i);
		printV(array7);
		v.quickSort();
		if (!isSorted(array7))
			throw new Exception("array" + i + " is not sorted");
		printV(array7);

		System.out.println("retry with already sorted array1");
		v = new Vector<Integer>(array1);
		printV(array1);
		v.quickSort();
		if (!isSorted(array1))
			throw new Exception("array" + i + " is not sorted");

		System.out.println("Shuffling the array1");
		v.shuffle();
		printV(array1);

		System.out.println("\nSorting algorithms implementation test terminated successfully!\n");
	}
	
	private static void implementationTestBSTree() throws Exception {
		String data = "data";

		System.out.println("Testing implementation for BinarySearchTree");

		System.out.println("Inserting node with key 70");
		BinarySearchTree tree = new BinarySearchTree(new KeyValueElement(70, data));

		if (null == tree.search(70))
			throw new Exception("search() error: the tree has been initialised with an element.");

		if (null == tree.toArray())
			throw new Exception("toArray() error: the tree has an element.");

		System.out.println("Deleting and reinserting node with key 70");
		tree.delete(70);

		if (null != tree.search(70))
			throw new Exception("search() or delete() error: the tree has no more the element with key 70.");

		if (null != tree.toArray())
			throw new Exception("toArray() error: the tree hash no more the element with key 70.");

		System.out.println("Inserting three different elements.");
		tree.insert(new KeyValueElement(70, data));
		tree.insert(new KeyValueElement(33, data));
		tree.insert(new KeyValueElement(105, data));

		System.out.println("");
		tree.inOrderVisitPrint();
		System.out.println("");

		if (null == tree.search(105))
			throw new Exception("search() or insert() error: element with 105 as key has been already inserted.");

		System.out.println("Deleting the element with key 33.");
		tree.delete(33);

		if (null == tree.search(70))
			throw new Exception("delete() error: the element with key 70 has not been removed.");

		if (null != tree.search(33))
			throw new Exception("delete() error: the element with key 33 has been already removed.");

		System.out.println("Deleting the element with key 70.");
		tree.delete(70);

		if (null == tree.search(105))
			throw new Exception("delete() error: the element with key 105 has not been removed.");

		if (null != tree.search(70))
			throw new Exception("delete() error: the element with key 70 has been already removed.");

		System.out.println("Deleting the element with key 105.");
		tree.delete(105);

		if (null != tree.toArray())
			throw new Exception("delete() or toArray() error: the tree should be empty.");

		System.out.println("");
		tree.inOrderVisitPrint();
		System.out.println("");

		System.out.println("Inserting elements with keys 100, 75, 82, 40, 43, 49, 60");
		tree.insert(new KeyValueElement(100, data));
		tree.insert(new KeyValueElement(75, data));
		tree.insert(new KeyValueElement(82, data));
		tree.insert(new KeyValueElement(40, data));
		tree.insert(new KeyValueElement(43, data));
		tree.insert(new KeyValueElement(49, data));
		tree.insert(new KeyValueElement(60, data));

		System.out.println("Deleting the element with key 75");
		tree.delete(75);

		if (null == tree.search(60))
			throw new Exception("delete() error: the tree has to keep the property of binary search tree");

		System.out.println("");
		tree.inOrderVisitPrint();
		System.out.println("");

		System.out.println("BinarySearchTree implementation test terminated successfully!\n");
	}
	
	public static void implementationTestNonOrientedGraph() throws Exception {
		System.out.println("Testing NonOrientedGraph implementation\n");
		
		NonOrientedGraph graph = new NonOrientedGraph();
		
		System.out.println("Adding node1 to the graph");
		String node1 = "node1";
		graph.addNode(node1);

		if (graph.nodesNumber() != 1) 
			throw new Exception("addNode() or nodesNumber() error : there should be 1 node");
	
		System.out.println("Removing and reinserting node1");

		graph.removeNode(node1);

		if (graph.nodesNumber() != 0)
			throw new Exception("removeNode() error : we removed the only node in the graph");

		graph.addNode(node1);
	
		System.out.println("Adding node2 and creating arch with node1");
		
		String node2 = "node2";
		graph.addNode(node2);
		Arch arch1_2 = new Arch(node1, node2);
		Comparable[] archNodes1_2 = arch1_2.getArchNodes();
		graph.addArch(archNodes1_2[0], archNodes1_2[1]);

		if (graph.archsNumber() != 1)
			throw new Exception("archsNumber() error : there should be 1 arch");

		System.out.println("Removing and reinserting arch1_2");

		graph.removeArch(arch1_2);

		if (graph.archsNumber() != 0)
			throw new Exception("removeArch() error : we removed the Arch arch1_2");
		
		graph.addArch(archNodes1_2[0], archNodes1_2[1]);
		
		System.out.println("Adding node3 and creating another arch with the new node and node1");
		String node3 = "node3";
		graph.addNode(node3);
		graph.addArch(node1, node3);
		
		if (graph.archsNumber() != 2)
			throw new Exception("archsNumber() error : there should be 2 archs now.");

		if (graph.grade("node1") != 2)
			throw new Exception("grade() error : node1 has 2 adjacents nodes");
		
		System.out.println("Adding node4 and creating a new arch with the new node and node2");

		String node4 = "node4";
		Arch arch2_4 = new Arch(node2, node4);
		graph.addNode(node4);
		Comparable[] archNodes2_4 = arch2_4.getArchNodes();
		graph.addArch(archNodes2_4[0], archNodes2_4[1]);

		System.out.println("Testing opposite method");

		if (graph.opposite(archNodes2_4[0], arch2_4) != archNodes2_4[1])
			throw new Exception("opposite() error : node4 should be the opposite of node2 in the arch2_4");

		System.out.println("Testing edges method");
		Comparable[] edge = graph.edges(arch2_4);
	
		if (null == edge)
			throw new Exception("edges() error : arch2_4 exists ");
	
		if (0 != edge[0].compareTo(archNodes2_4[0]) && 0 != edge[1].compareTo(archNodes2_4[1]))
			throw new Exception("edges() error : node2 and node4 are the edges of arch2_4");
	
		System.out.println("Testing incidentsArch method");
		
		Arch[] incident4 = graph.incidentArchs(node4);
		Comparable[] archNodesInc4 = incident4[0].getArchNodes();
		if (!(0 == archNodes2_4[0].compareTo(archNodesInc4[0]) && 0 == archNodes2_4[1].compareTo(archNodesInc4[1])) &&
		    !(0 == archNodes2_4[0].compareTo(archNodesInc4[1]) && 0 == archNodes2_4[1].compareTo(archNodesInc4[0])))
			throw new Exception("incidentsArch() error : node4 has an arch with node2"); 
		
		System.out.println("Testing areAdjacents method");
		if (!graph.areAdjacent(node1, node2))
			throw new Exception("areAdjacent() error : node1 and node2 are adjacent");

		if (graph.areAdjacent(node2, node3))
			throw new Exception("areAdjacent() error : node2 and node3 aren't adjacent");
	
		System.out.println("Adding 17 new nodes");
		for (Integer i = 5; 22 > i; ++i) {
			String ithNode = "node" + i.toString();

			graph.addNode(ithNode);
		}

		if (21 != graph.nodesNumber())
			throw new Exception("addNode() error: there should be 21 nodes");

		System.out.println("Adding arch between node6 and node21");
		graph.addArch("node6", "node21");
		System.out.println("Adding arch between node13 and node6");
		graph.addArch("node13", "node6");
		System.out.println("Adding arch between node6 and node9");
		graph.addArch("node6", "node9");
		System.out.println("Adding arch between node17 and node8");
		graph.addArch("node17", "node8");

		System.out.println("Testing grade() and edges()");
		if (3 != graph.grade("node6"))
			throw new Exception("addArch() error: node6 has 3 incident archs");

		if (null == graph.edges(new Arch("node8", "node17")))
			throw new Exception("edges() error: the arch between node8 and node17");

		System.out.println("Trying to adding node8");
		graph.addNode("node8");

		System.out.println("Removing node8");
		graph.removeNode("node8");

		System.out.println("Testing incidentArchs()");
		if (null != graph.incidentArchs("node17"))
			throw new Exception("incidentArchs() error: node8 has been deleted, so node17 has no more archs");

		System.out.println("Creating a new graph with node1, node2, node3, node4, node5, node6");

		graph = new NonOrientedGraph();
		for (Integer i = 1; 7 > i; ++i) {
			String ithNode = "node" + i.toString();

			graph.addNode(ithNode);
		}

		System.out.println("Adding arch between node6 and node2");
		graph.addArch("node6", "node2");
		System.out.println("Adding arch between node5 and node1");
		graph.addArch("node5", "node1");
		System.out.println("Adding arch between node5 and node3");
		graph.addArch("node5", "node3");
		System.out.println("Adding arch between node3 and node4");
		graph.addArch("node3", "node4");
		System.out.println("Adding arch between node1 and node3");
		graph.addArch("node1", "node3");
		System.out.println("Adding arch between node5 and node100");
		graph.addArch("node5", "node100");

		System.out.println("Testing breadthFirstVisit method");
		NodeOperation op = n -> {};
		Comparable[] visitedNodes = graph.breadthFirstVisit(op, "node5");

		if (4 != visitedNodes.length)
			throw new Exception("breadthFirstVisit() error: the visited nodes should be 4");

		for (int i = 0; visitedNodes.length > i; ++i) {
			if (0 == visitedNodes[i].compareTo("node2") || 0 == visitedNodes[i].compareTo("node6"))
				throw new Exception("breadthFirstVisit() error: node2 and node6 should not have been visited");
		}

		System.out.println("\nNonOrientedGraph implementation test terminated successfully!\n");
	}
	
	public static void main(String[] args) {

		// To test the number of collisions due to hash function of HashMap class.
		collisionTestHashMap();

		// To test the implementation of Dictionary interface is correct.
		try {
			implementationTestHashMap();
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}

		// To test the sorting algorithms really sort arrays.
		try {
			implementationTestSort();
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}

		try {
			implementationTestBSTree();
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}
		
		try {
			implementationTestNonOrientedGraph();
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}

	}

}

