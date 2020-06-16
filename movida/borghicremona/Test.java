package movida.borghicremona;

import movida.borghicremona.hashmap.HashMap;

// TODO: module on hash
public class Test {

    private static void __hashTest(String toHash, HashMap h, Integer[] store, int index) {
        int hashTmp = h.hash(toHash) % h.length();
        store[index] = hashTmp;
        if (toHash.length() < 30) {
            System.out.println(toHash + ": " + hashTmp);
        } else {
            System.out.println("_long_string_at_" + index + ": " + hashTmp);
        }
    }

    public static void hashTest(int length) {
        if (1 > length) return;
        HashMap hashmap = new HashMap(length);
        Integer[] hashValues = new Integer[length];
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
    }

    public static void main(String[] args) {
        hashTest(50);
    }
}
