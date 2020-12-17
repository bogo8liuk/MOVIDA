package movida.borghicremona.hashmap;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import movida.borghicremona.Assert;
import java.lang.Exception;

/**
 * Open addressing hashmap.
 * ATTENTION! Instances of this class use keys only with type String. Using a different type may cause
 * inconsistent state of an object or the throwing of particular exceptions. 
 */
public class HashMap implements Dictionary {
    private KeyValueElement[] table;

	// Deafult hash value, necessary for hash function.
    private final static int HASH = 7;
    private final static int MAXINT = 2147483647;

	// Label to replace removed elements.
    private final static KeyValueElement _DELETED_ = new KeyValueElement("_DELETED_", null);

	/**
	 * If the key has a value equal to _DELETED_ label, it throws an IllegalArgumentException and it handles it,
	 * terminating the process.
	 *
	 * @param key Value to check
	 *
	 * @attention key parameter is casted to String, so passing a Comparable different from type String is a
	 * non-checked runtime error.
	 */
	private static void __assertNotDeletedKey(Comparable key) {
        try {
            if ("_DELETED_" == (String) key) throw new IllegalArgumentException("Illegal key: aborting");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
			System.exit(-1);
        }
	}

	/**
	 * It initializes the table with a default size of 20 elements.
	 */
	public HashMap() {
		this.table = new KeyValueElement[20];
	}

    public HashMap(int length) {
        try {
            if (0 >= length) throw new IllegalArgumentException("Cannot have a negative length: aborting");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
			System.exit(-1);
        }

        this.table = new KeyValueElement[length];
    }

    public int length() {
        if (null != table) return this.table.length;
        else return 0;
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

    public Object search(Comparable key) {
		try {
			Assert.notNullKey(key);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}
		__assertNotDeletedKey(key);

        int index = hash((String) key) % this.table.length;

		// Linear inspection: every time the inspection gets a collision, the index is incremented by 1.
        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;

            if (null == this.table[i])
				return null;

            else if ((String) this.table[i].getKey() == (String) key)
				return this.table[i].getValue();
        }

        return null;
    }

	/**
	 * If the table is not full, it inserts an item.
	 *
	 * @param item the element to insert.
	 * @param index the starting point of the table for attempting the insertion.
	 *
	 * @attention Passing an index not calculated with hash function may lead at an inconsistent state.
	 *
	 * @return true if the insertion is successful, false otherwise.
	 */
    private boolean __insert(KeyValueElement item, int index) {
        boolean inserted = false;

		// Linear inspection: every time the inspection gets a collision, the index is incremented by 1.
        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;

            if (null == this.table[i] || _DELETED_ == this.table[i]) {
                this.table[i] = item;
                inserted = true;
                break;
            }

			// Duplicates not allowed
			else if (0 == this.table[i].getKey().compareTo(item.getKey())) {
				inserted = true;
				break;
			}
        }

        return inserted;
    }

	/**
	 * It moves the items of an old hashmap in a new bigger (in size) one.
	 *
	 * @param oldTable previous hashmap containing the items to recalculate.
	 *
	 * @attention This function should be invoked only when the length of the hashmap is increased,
	 * passing the instance of the previous table.
	 */
    private void rehash(KeyValueElement[] oldTable) {
        for (int oldIndex = 0; oldTable.length > oldIndex; ++oldIndex) {
            int index = hash((String) oldTable[oldIndex].getKey()) % this.table.length;

            __insert(oldTable[oldIndex], index);
        }
    }

	/**
	 * It increases the length of the hashmap, preserving the state of all the items.
	 *
	 * @param quantity value of which the length of the hashmap has to increase.
	 *
	 * @attention This function should be called only when the hashmap is full.
	 */
    private void grow(int quantity) {
        if (0 >= quantity) return;

        KeyValueElement[] oldTable = this.table;
        int newLength = this.table.length + quantity;
        KeyValueElement[] newTable = new KeyValueElement[newLength];
        this.table = newTable;
        rehash(oldTable);
    }

    public void insert(KeyValueElement item) {
		try {
			Assert.notNullData(item);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}
		try {
			Assert.notNullKey(item.getKey());
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}
		__assertNotDeletedKey(item.getKey());

        int index = hash((String) item.getKey()) % this.table.length;
        boolean inserted = __insert(item, index);

		// In this case the table is full, so its length must be increased.
        if (!inserted) {
            grow(10);
            insert(item);
        }
    }

    public Object delete(Comparable key) {
		try {
			Assert.notNullKey(key);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}
		__assertNotDeletedKey(key);

        int index = hash((String) key) % this.table.length;

        if (null == this.table[index]) return null;

		// Linear inspection: every time the inspection gets a collision, the index is incremented by 1
        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;

            if ((String) this.table[i].getKey() == (String) key) {
				Object value = this.table[i].getValue();
                this.table[i] = _DELETED_;
                return value;
            }
        }

		return null;
    }

    public void printTable() {
        for (int i = 0; this.table.length > i; ++i) {
            if (null == this.table[i])
                System.out.println(i + ": _NULL_");
            else
                System.out.println(i + ": " + this.table[i].getKey());
        }
    }
}
