package movida.borghicremona.hashmap;

import movida.borghicremona.Dictionary;
import movida.borghicremona.StringKeyValueElement;
import java.lang.Exception;

public class HashMap implements Dictionary {
    private StringKeyValueElement[] table;
    private final static int HASH = 7;
    private final static int MAXINT = 2147483647;

    public HashMap(int length) {
        try {
            if (0 >= length) throw new IllegalArgumentException("Cannot have a negative length");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        this.table = new StringKeyValueElement[length];
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

    public boolean search(String key) {
        try {
            if (null == key) throw new IllegalArgumentException("Cannot have an empty key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
        int index = hash(key) % this.table.length;
        boolean found = false;

        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) + this.table.length;
            if (this.table[i].getKey() == key) {
                found = true;
                break;
            }
            else if ((this.table[i]) == null) {
                break;
            }
        }

        return found;
    }

    private void rehash() {
        
    }

    private void grow(int quantity) {
        if (0 >= quantity) return;

        StringKeyValueElement[] oldTable = this.table;
        int newLength = this.table.length + quantity;
        StringKeyValueElement[] newTable = new StringKeyValueElement[newLength];
        this.table = newTable;
        //TODO: insertion and rehashing of newTable
    }

    public void insert(StringKeyValueElement item) {
        try {
            if (null == item.getKey()) throw new IllegalArgumentException("Cannot have an empty key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
        int index = hash(item.getKey()) % this.table.length;
        boolean inserted = false;

        for (int attempt = 0; this.table.length > attempt; ++attempt) {
            int i = (index + attempt) % this.table.length;
            if (null == this.table[i]) {
                this.table[i] = item;
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            grow(10);
            insert(item);
        }
    }

    public void delete(String key) {

    }
}
