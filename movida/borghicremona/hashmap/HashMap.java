package movida.borghicremona.hashmap;

import movida.borghicremona.Dictionary;
import movida.borghicremona.Element;

public class HashMap implements Dictionary {
    private Element[] table;

    public HashMap(int length) {
        if (0 <= length)
            this.table = new Element[length];
    }

    public Integer hash(String key) {
        return 256;  // to quiet the compiler
    }

    public boolean search(String key) {
        return false;   // to quiet the compiler
    }

    private void grow(int quantity) {
        if (0 >= quantity) return;

        Element[] oldTable = this.table;
        int newLength = this.table.length + quantity;
        Element[] newTable = new Element[newLength];
        this.table = newTable;
        //TODO: insertion and rehashing of newTable
    }

    public void insert(Element item) {

    }

    public void delete(String key) {

    }
}
