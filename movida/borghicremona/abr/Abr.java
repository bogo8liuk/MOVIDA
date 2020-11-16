package movida.borghicremona.abr;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import movida.borghicremona.abr.Node;	
import java.lang;

public class Abr implements Dictionary {
    private Node root;


    public Abr() {
        this.root = null;
    }

	public Abr(Node node) {
		this.root = node;
	} 

    private void __insert(KeyValueElement node) {
		try {
            if (null == node) throw new IllegalArgumentException("cannot insert empty node");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        Abr node = root;

        while (null != node) {
            if (value.compareTo(node.value) < 0)
                node = this.left;
            else {
                if (value.compareTo(node.value()) > 0)
                    node = this.right;
                else 
                    return;
                }
        }

        node = new Abr(node);
    }
    
    public void insert(KeyValueElement node) {
            __insert(node);       
        }

    private boolean __search(KeyValueElement key) {
		try {
            if (null == key) throw new IllegalArgumentException("Cannot search null key");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }   

		Abr node = root;
        boolean found = false;
        while ((null != node) && (!found)) {
            if (key.compareTo(node.value) < 0)
                node = node.left;
            else {
                if (key.compareTo(node.value) > 0)
                    node = node.right;
                else
                    found = true;
            }
        }
        return found;
    }
	
	public boolean search(KeyValueElement key) {
		__search(key);
	}

	private void __insertTree(Node treeRoot, Node addedTreeRoot) {
		Node parent = null;
		while (null != treeRoot) {
			parent = treeRoot; treeRoot = treeRoot.getLeftChild();
		}
		if (null != parent) parent.setLeftChild(addedTreeRoot);
	}

	public void delete(KeyValueElement key) throws TreeException {
		if (key.compareTo(root.getValue()) == 0 )
			insert(root.getRightChild(), root.getLeftChild());
		else {
			if (key.compareTo(root.getValue()) < 0 )
				__delete(key, root, root.getLeftChild());
			else
				__delete(key, root, root.getRightChild());
		}
	}

	private void __delete(KeyValueElement key, Node parent, Node currentNode) throws TreeException {
		if (null == currentNode) throw new TreeException("node inexistent");
		
		if (key.compareTo(currentNode.getValue()) < 0)
			__delete(key, currentNode, currentNode.getLeftChild());
		else {
			if (key.compareTo(currentNode.getValue()) > 0)
			__delete(key, currentNode, currentNode.getRightChild());
			else { 
				Node temp;
				if (null == currentNode.getRightChild())
				temp = currentNode.getLeftChild();
				else {
					temp = currentNode.getRightChild();
					__insertTree(currentNode.getRightChild(), currentNode.getLeftChild() );
				}
			if (parent.getLeftChild() == currentNode)
				parent.setLeftChild(temp);
			else
				parent.setRightChild(temp);
			}
		} 
	}

/*	protected Node __max(Abr n) {
		while (null != n && null != n.getRightChild(n)) {
			n = n.right;
		}
		return n;
	}
	
	private Node __min(Node n) {
	

}*/

}
