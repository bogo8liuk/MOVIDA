package movida.borghicremona.abr;

import movida.borghicremona.Dictionary;
import movida.borghicremona.abr.Node;
import java.lang;

public class Abr implements Dictionary {
    private KeyValueElement nodeValue;
    private Abr leftChild;
    private Abr rightChild;

    public Abr(KeyValueElement nodeValue) {
        this.nodeValue = nodeValue;
        this.leftChild = null;
        this.rightChild = null;
    }
    //gestione valori uguali ?
    public void insert(KeyValueElement Value) {
        Abr node = root;
        while (null != node) {
            if (value.compareTo(node.nodeValue) < 0)
                node = node.leftChild;
            else {
                if (value.compareTo(node.nodeValue()) > 0)
                    node = node.rightChild;
                else 
                    return;
                }
        }
        node = new Abr(value);
    }
    
    public boolean search(KeyValueElement value) {
        Abr node = root;
        boolean found = false;
        while ((null != node) && (!found)) {
            if (value.compareTo(node.nodeValue) < 0)
                node = node.leftChild;
            else {
                if (value.compareTo(Node.nodeValue) > 0)
                    node = node.rightChild;
                else
                    found = true;
            }
        }
        return found;
    }

    public void delete(KeyValueElement value) throws TreeException {
    
    }

}


