package movida.borghicremona.hashmap;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import java.lang.Exception;

public class HashMap implements Dictionary {
    private KeyValueElement[] table;
    private final static int HASH = 7;
    private final static int MAXINT = 2147483647;
    private final static KeyValueElement _DELETED_ = new KeyValueElement("_DELETED_", null);

    public HashMap(int length) {
        try {
            if (0 >= length) throw new IllegalArgumentException("Cannot have a negative length");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        this.table = new KeyValueElement[length];
    }

    public int length() {
        if (null != table) return this.table.length;
        else return 0;
    }

    public int hash(String key) {
        try {
            if (null == key) throw new IllegalArgumentException("Cannot have an empty key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        int h = HASH;

        for (int i = 0; i < key.length(); ++i) {
            h = h * 31 + key.charAt(i);
            if (h < 0) h = (h + MAXINT) + 1;
        }

        return h;
    }

    public boolean search(Comparable key) {
        try {
            if (null == key) throw new IllegalArgumentException("Cannot have an empty key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        try {
            if ("_DELETED_" == (String) key) throw new IllegalArgumentException("Illegal key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        int index = hash((String) key) % this.table.length;
        boolean found = false;

        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;

            if (null == this.table[i]) {
                break;
            }
            else if ((String) this.table[i].getKey() == (String) key) {
                found = true;
                break;
            }
        }

        return found;
    }

    private boolean __insert(KeyValueElement item, int index) {
        boolean inserted = false;

        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;

            if (null == this.table[i] || _DELETED_ == this.table[i]) {
                this.table[i] = item;
                inserted = true;
                break;
            }
        }

        return inserted;
    }

    private void rehash(KeyValueElement[] oldTable) {
        for (int oldIndex = 0; oldTable.length > oldIndex; ++oldIndex) {
            int index = hash((String) oldTable[oldIndex].getKey()) % this.table.length;

            __insert(oldTable[oldIndex], index);
        }
    }

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
            if (null == item.getKey()) throw new IllegalArgumentException("Cannot have an empty key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        try {
            if ("_DELETED_" == (String) item.getKey()) throw new IllegalArgumentException("Illegal key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        int index = hash((String) item.getKey()) % this.table.length;
        boolean inserted = __insert(item, index);

        if (!inserted) {
            grow(10);
            insert(item);
        }
    }

    public void delete(Comparable key) {
        try {
            if (null == key) throw new IllegalArgumentException("Cannot have an empty key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        try {
            if ("_DELETED_" == (String) key) throw new IllegalArgumentException("Illegal key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        int index = hash((String) key) % this.table.length;

        if (null == this.table[index]) return;

        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;

            if ((String) this.table[i].getKey() == (String) key) {
                this.table[i] = _DELETED_;
                break;
            }
        }
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
