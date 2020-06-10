package movida.borghicremona.hashmap;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;

public class HashMap implements Dictionary {
    private KeyValueElement[] table;

    public HashMap(int length) {
        if (0 <= length)
            this.table = new KeyValueElement[length];
    }

    public int hash(String key) {
        return 0;
    }

    public boolean search(String key) {
        return false;   // to quiet the compiler
    }

    private void rehash() {
        
    }

    private void grow(int quantity) {
        if (0 >= quantity) return;

        KeyValueElement[] oldTable = this.table;
        int newLength = this.table.length + quantity;
        KeyValueElement[] newTable = new KeyValueElement[newLength];
        this.table = newTable;
        //TODO: insertion and rehashing of newTable
    }

    public void insert(KeyValueElement item) {

    }

    public void delete(String key) {

    }
}
