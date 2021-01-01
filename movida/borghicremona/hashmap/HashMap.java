package movida.borghicremona.hashmap;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import movida.borghicremona.Assert;
import movida.borghicremona.Hash;
import java.lang.Exception;
import java.util.*;

/**
 * Open addressing hashmap.
 * ATTENTION! Instances of this class use keys only with type String. Using a different type may cause
 * inconsistent state of an object or the throwing of particular exceptions. 
 */
public class HashMap implements Dictionary {
    private KeyValueElement[] table;

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

    public Object search(Comparable key) {
		try {
			Assert.notNullKey(key);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}
		__assertNotDeletedKey(key);

        int index = Hash.hash((String) key) % this.table.length;

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
	 * It inserts an item in a table, if the latter is not full.
	 *
	 * @param table The table where the insertion has to be performed.
	 * @param item The element to insert.
	 *
	 * @attention Passing an index not calculated with hash function may lead at an inconsistent state.
	 *
	 * @return true if the insertion is successful, false otherwise.
	 */
	private static Boolean __insert(KeyValueElement[] table, KeyValueElement item) {
        boolean inserted = false;

        int index = Hash.hash((String) item.getKey()) % table.length;
		// Linear inspection: every time the inspection gets a collision, the index is incremented by 1.
        for (int attempt = 0; table.length > attempt; ++attempt) {
            int i = (index + attempt) % table.length;

            if (null == table[i] || _DELETED_ == table[i]) {
                table[i] = item;
                inserted = true;
                break;
            }

			// Duplicates not allowed.
			else if (0 == table[i].getKey().compareTo(item.getKey())) {
				inserted = true;
				break;
			}
        }

        return inserted;
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

		boolean inserted = __insert(this.table, item).booleanValue();

		// In this case the table is full, so its length must be increased.
		if (!inserted) {
			Hash.InsertOperation ins = (t, i) -> __insert(t, i);
			this.table = Hash.grow(this.table, ins, 10);
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

        int index = Hash.hash((String) key) % this.table.length;

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

	/**
	 * It gathers all the values in the hashmap in an array.
	 *
	 * @return An Object array.
	 */
	public Object[] toArray() {
		List<Object> l = new LinkedList<Object>();

		for (int i = 0; this.table.length > i; ++i) {
			if (null == this.table[i]) continue;
		
			l.add(table[i].getValue());
		}

		if (0 != l.size())
			return l.toArray();
		else
			return null;
	}
}
