package movida.borghicremona;

//TODO: docs
/* Class that offers to modules that implement a hashmap with an array
   of KeyValueElement some useful operations and a hash function on String. */
public class Hash {

	// Deafult hash value, necessary for hash function.
    private final static int HASH = 7;
    private final static int MAXINT = 2147483647;

	/* Objects of this type should be initialized with a function in order to achieve a parametric
	   behaviour for insert operations in hashmaps. */
	public interface InsertOperation {
		//TODO: change __insert() in HashMap in order to match its parameters with the ones of this function.
		public Object insert(KeyValueElement[] table, KeyValueElement item);
	}

    public static int hash(String key) {
		try {
			Assert.notNullKey(key);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}

        int h = HASH;

        for (int i = 0; i < key.length(); ++i) {
            h = h * 31 + key.charAt(i);
			// To avoid overflows
            if (h < 0) h = (h + MAXINT) + 1;
        }

        return h;
    }

	private static void rehash(KeyValueElement[] newTable, KeyValueElement[] oldTable, InsertOperation op) {
		for (int i = 0; oldTable.length > i; ++i)
			op.insert(newTable, oldTable[i]);
	}

	public static void grow(KeyValueElement[] oldTable, InsertOperation insert, int quantity) {
		if (0 >= quantity)
			return;

		int length = oldTable.length + quantity;
		KeyValueElement[] newTable = new KeyValueElement[length];

		rehash(newTable, oldTable, insert);
	}
}
