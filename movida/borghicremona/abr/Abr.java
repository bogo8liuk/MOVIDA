package movida.borghicremona.abr;

import movida.borghicremona.Dictionary;
import movida.borghicremona.abr.Node;
import java.lang;

public class Abr implements Dictionary {
    private KeyValueElement nodeValue;
    private Abr left;
    private Abr right;

    public Abr(KeyValueElement nodeValue) {
        this.nodeValue = nodeValue;
        this.left = null;
        this.right = null;
    }
    //gestione valori uguali ?
	// soluzione 1: decidiamo arbitrariamente di metterli sempre o a destra o a sinistra
	// soluzione 2: decidiamo di aggiungere un contatore di fianco al valore del nodo, che indica quanti nodi hanno quel valore 
    public void insert(KeyValueElement value) {
        Abr node = root;
        while (null != node) {
            if (value.compareTo(node.nodeValue) < 0)
                node = node.left;
            else {
                if (value.compareTo(node.nodeValue()) > 0)
                    node = node.right;
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
                node = node.left;
            else {
                if (value.compareTo(Node.nodeValue) > 0)
                    node = node.right;
                else
                    found = true;
            }
        }
        return found;
    }
	
	
	
    public void delete(KeyValueElement value) throws TreeException {
    
    }

}


