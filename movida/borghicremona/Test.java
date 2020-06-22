package movida.borghicremona;

import movida.borghicremona.hashmap.HashMap;

public class Test {

    private static void __hashTest(String toHash, HashMap h, Integer[] store, int index) {
        int hashTmp = HashMap.hash(toHash) % h.length();
        store[index] = hashTmp;
        System.out.println(toHash + ": " + hashTmp);
    }

    public static void collisionTestHashMap(int length) {
        if (0 >= length) return;
        HashMap hashmap = new HashMap(length);

        System.out.println("Testing the number of collision with an hashmap with " + length + " elements and 50 different strings:\n");

        Integer[] hashValues = new Integer[hashmap.length()];
        int index = 0;

        __hashTest("Scottie Pippen", hashmap, hashValues, index++);
        __hashTest("Patrick Ewing", hashmap, hashValues, index++);
        __hashTest("Hakeem Olajuwon", hashmap, hashValues, index++);
        __hashTest("Allen Iverson", hashmap, hashValues, index++);
        __hashTest("Juan Carlos Navarro", hashmap, hashValues, index++);
        __hashTest("Carmelo Anthony", hashmap, hashValues, index++);
        __hashTest("Zion Williamson", hashmap, hashValues, index++);
        __hashTest("Oscar Schmidt", hashmap, hashValues, index++);
        __hashTest("JaVale McGee", hashmap, hashValues, index++);
        __hashTest("Rudy Gobert", hashmap, hashValues, index++);
        __hashTest("Lebron James", hashmap, hashValues, index++);
        __hashTest("Kobe Bryant", hashmap, hashValues, index++);
        __hashTest("Carlton Myers", hashmap, hashValues, index++);
        __hashTest("Yao Ming", hashmap, hashValues, index++);
        __hashTest("Ja Morant", hashmap, hashValues, index++);
        __hashTest("Kelly Olynyk", hashmap, hashValues, index++);
        __hashTest("John Stockton", hashmap, hashValues, index++);
        __hashTest("Nando De Colo", hashmap, hashValues, index++);
        __hashTest("Kevin Garnett", hashmap, hashValues, index++);
        __hashTest("Tim Duncan", hashmap, hashValues, index++);
        __hashTest("Manu Ginobili", hashmap, hashValues, index++);
        __hashTest("Vasilis Spanoulis", hashmap, hashValues, index++);
        __hashTest("Dario Saric", hashmap, hashValues, index++);
        __hashTest("Chris Bosh", hashmap, hashValues, index++);
        __hashTest("Danilo Gallinari", hashmap, hashValues, index++);
        __hashTest("Dirk Nowitzki", hashmap, hashValues, index++);
        __hashTest("James Harden", hashmap, hashValues, index++);
        __hashTest("Joakim Noah", hashmap, hashValues, index++);
        __hashTest("Pete Maravich", hashmap, hashValues, index++);
        __hashTest("Chauncey Billups", hashmap, hashValues, index++);
        __hashTest("Jeremy Lin", hashmap, hashValues, index++);
        __hashTest("Boris Diaw", hashmap, hashValues, index++);
        __hashTest("Stephen Curry", hashmap, hashValues, index++);
        __hashTest("Nikola Jokic", hashmap, hashValues, index++);
        __hashTest("Michael Jordan", hashmap, hashValues, index++);
        __hashTest("Shaquille O'Neal", hashmap, hashValues, index++);
        __hashTest("Ray Allen", hashmap, hashValues, index++);
        __hashTest("Klay Thompson", hashmap, hashValues, index++);
        __hashTest("Glenn Robinson III", hashmap, hashValues, index++);
        __hashTest("Marco Belinelli", hashmap, hashValues, index++);
        __hashTest("Tim Hardaway Jr.", hashmap, hashValues, index++);
        __hashTest("Dennis Rodman", hashmap, hashValues, index++);
        __hashTest("Steve Nash", hashmap, hashValues, index++);
        __hashTest("Nathan Jawai", hashmap, hashValues, index++);
        __hashTest("Nate Robinson", hashmap, hashValues, index++);
        __hashTest("Felipe Reyes", hashmap, hashValues, index++);
        __hashTest("Bol Bol", hashmap, hashValues, index++);
        __hashTest("Paul Pierce", hashmap, hashValues, index++);
        __hashTest("Chris Paul", hashmap, hashValues, index++);
        __hashTest("Jason Kidd", hashmap, hashValues, index++);

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

        System.out.println("HashMap implementation is correct!");
    }

    public static void main(String[] args) {

        // To test the number of collisions due to hash function
        collisionTestHashMap(50);

        // To test the implementation of Dictionary interface is correct
        try {
            implementationTestHashMap();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
