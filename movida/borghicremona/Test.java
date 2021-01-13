package movida.borghicremona;

import movida.borghicremona.hashmap.HashMap;
import movida.borghicremona.sort.Vector;
import movida.borghicremona.bstree.BinarySearchTree;

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

		System.out.println("BinarySearchTree implementation test terminated successfully!\n");
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
    }
}

