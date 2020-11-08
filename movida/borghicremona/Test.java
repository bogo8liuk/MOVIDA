package movida.borghicremona;

import movida.borghicremona.hashmap.HashMap;

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
        int hashTmp = HashMap.hash(toHash) % length;
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
        System.out.println("Testing the implementation of HashMap\n");

        System.out.println("Hashmap of 10 elements");
        HashMap hashmap = new HashMap(10);

        System.out.println("Insertion of 10 \"Tony Parker\" keys");
        int oldLen = hashmap.length();
        for (int i = 0; oldLen > i; ++i) {
            KeyValueElement tp = new KeyValueElement("Tony Parker", null);
            hashmap.insert(tp);
        }
        if (hashmap.length() != oldLen)
            throw new Exception("The length must not change after a number of insertions equal to the length of an empty hashmap");

        System.out.println("");
        hashmap.printTable();
        System.out.println("");

        System.out.println("Insertion of \"DeAndre Jordan\" key");
        KeyValueElement daj = new KeyValueElement("DeAndre Jordan", null);
        hashmap.insert(daj);

        if (hashmap.length() == oldLen)
            throw new Exception("The length must change after a number of insertions higher to the length of an hashmap, without any delete()");

        System.out.println("");
        hashmap.printTable();
        System.out.println("");

        System.out.println("New hashmap of 10 elements created");
        hashmap = new HashMap(10);

        if (hashmap.search("Russell Westbrook"))
            throw new Exception("search() must return false if the table is empty");

        System.out.println("Insertion of \"Joe Johnson\" key");
        final String s = "Joe Johnson";
        KeyValueElement jj = new KeyValueElement(s, null);
        hashmap.insert(jj);

        if (!hashmap.search(s))
            throw new Exception("search() must return true if its parameter is a key already inserted and not deleted");

        System.out.println("");
        hashmap.printTable();
        System.out.println("");

        System.out.println("Insertion of 3 \"Bo McCalebb\" keys");
        int oldLen1 = hashmap.length();
        String t = "Bo McCalebb";
        for (int i = 0; 3 > i; ++i) {
            KeyValueElement bmc = new KeyValueElement(t, null);
            hashmap.insert(bmc);
        }

        if (hashmap.length() != oldLen1)
            throw new Exception("The number of insertions did not overcome the length of the hashmap");

        System.out.println("Removal of \"Bo McCalebb\" key");
        hashmap.delete(t);

        if (!hashmap.search(t))
            throw new Exception("The searched key should have been inserted three times and deleted once, search() must return true");

        System.out.println("");
        hashmap.printTable();
        System.out.println("");

        System.out.println("Removal of 2 \"Bo McCalebb\" keys");
        hashmap.delete(t);
        hashmap.delete(t);

        if (hashmap.search(t))
            throw new Exception("The elements with the searched key should have been removed all, search() must return false");

        System.out.println("");
        hashmap.printTable();
        System.out.println("");

        System.out.println("Insertion of \"Bo McCalebb\" key");
        KeyValueElement bmc = new KeyValueElement(t, null);
        hashmap.insert(bmc);

        System.out.println("Insertion of 10 \"Bill Russell\" keys");
        int l = hashmap.length();
        for (int i = 0; l > i; ++i) {
            KeyValueElement br = new KeyValueElement("Bill Russell", null);
            hashmap.insert(br);
        }

        if (!hashmap.search(t))
            throw new Exception("The growing of the table must not affect the success of searching an existing key in the table");

        System.out.println("");
        hashmap.printTable();
        System.out.println("");

        System.out.println("HashMap implementation test terminated successfully!");
    }

    public static void main(String[] args) {

        // To test the number of collisions due to hash function of HashMap class.
        collisionTestHashMap();

        // To test the implementation of Dictionary interface is correct.
        try {
            implementationTestHashMap();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
