package movida.borghicremona.abr;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import movida.borghicremona.abr.Node;	
import java.lang;

public class Abr implements Dictionary {
	private KeyValueElement entry;
	private Abr parent;
	private Abr leftChild;
	private Abr rightChild;

	private static void __assertNotNullData(KeyValueElement item) {
		try {
			if (null == item) throw new IllegalArgumentException("Invalid data: aborting");
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}
	}

	private static void __assertNotNullKey(Comparable key) {
		try {
			if (null == key) throw new IllegalArgumentException("Invalid key: aborting");
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}
	}

	public Abr(KeyValueElement item) {
		this.entry = item;
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
	}

	/**
	 * It instantiates a new Abr object, defining the parent.
	 *
	 * @param item Element representing the node.
	 * @param parent Current node's parent.
	 *
	 * @attention It may lead to an unwanted state of the object.
	 */
	private Abr(KeyValueElement item, Abr parent) {
		this.entry = item;
		this.parent = parent;
		this.leftChild = null;
		this.rightChild = null;
	}

	/**
	 * It creates a new node, according to the rules defined by binary search trees.
	 *
	 * @param item Element representing the node to insert.
	 * @param tree Instance where item has to be inserted.
	 * @param parent The parent of the node to create
	 *
	 * @attention It may lead to an inconsistent state of Abr: do not use this method.
	 */
    private void __insert(KeyValueElement item, Abr tree, Abr parent) {
		if (null == tree)
			tree = new Abr(item, parent);

		else {
			Integer diff = item.getKey().compareTo(tree.entry.getKey());

			// It does not allow duplicated keys
			if (0 == diff)
				return;

			else if (0 < diff)
				__insert(item, tree.leftChild, tree);

			else
				__insert(item, tree.rightChild, tree);
		}
    }

	public void insert(KeyValueElement item) {
		__assertNotNullData(item);
		__assertNotNullKey(item);

		__insert(item, this, this.parent);
	}

    private boolean __search(Comparable key, Abr tree) {
		if (null == tree)
			return false;

		else {
			Integer diff = key.compareTo(tree.entry.getKey());

			if (0 == diff)
				return true;

			else if (0 > diff)
				return __search(key, tree.leftChild);

			else
				return __search(key, tree.rightChild);
		}
    }
	
	public boolean search(Comparable key) {
		__assertNotNullKey(key);

		return __search(key, this);
	}

	public void delete(Comparable key) {
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
