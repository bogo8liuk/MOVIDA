package movida.borghicremona;

/* Class that offers to modules that implement a hashmap with an array
   of KeyValueElement some useful operations, e.g. a hash function on String. */
public class Hash {

	// Deafult hash value, necessary for hash function.
	private final static int HASH = 7;
	private final static int MAXINT = 2147483647;

	/* Objects of this type should be initialized with a lambda expression in order to achieve a
	   parametric behaviour for insert operations in hashmaps. */
	public interface InsertOperation {
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
			if (h < 0)
				h = (h + MAXINT) + 1;
		}

		return h;
	}

	/**
	 * It moves the items of an old table in a new bigger (in size) one.
	 *
	 * @param newTable The table where all items have to be moved.
	 * @param oldTable Previous table containing the items to move.
	 * @param op Insert operation to perform at the moment of moving the items from oldTable to
	 * newTable.
	 *
	 * @attention The overwriting of elements inside the newTable is an unchecked runtime error
	 * and passing a non-empty newTable may lead to an inconsistent state of the hashmap and to
	 * to the loss of previous data.
	 */
	private static void rehash(KeyValueElement[] newTable, KeyValueElement[] oldTable, InsertOperation op) {
		for (int i = 0; oldTable.length > i; ++i)
			op.insert(newTable, oldTable[i]);
	}

	/**
	 * It increases the length of a table.
	 *
	 * @param oldTable Table where the resizing has to be performed.
	 * @param insert Insert operation to carry out at the moment of the creation of a new
	 * bigger (in size) table.
	 * @param quantity Value of which the length of the hashmap has to increase.
	 *
	 * @return The new resized table, if quantity is greater than 0, else oldTable.
	 *
	 * @attention (null == oldTable) and (null == insert) are unchecked runtime errors.
	 */
	public static KeyValueElement[] grow(KeyValueElement[] oldTable, InsertOperation insert, int quantity) {
		if (0 >= quantity)
			return oldTable;

		int length = oldTable.length + quantity;
		KeyValueElement[] newTable = new KeyValueElement[length];

		rehash(newTable, oldTable, insert);

		return newTable;
	}
}
