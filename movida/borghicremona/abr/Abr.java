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

	/**
	 * It searches for a key in a binary search tree.
	 *
	 * @param key Key to search for.
	 * @param tree Where to search for the key.
	 *
	 * @return true, if key is found, false otherwise.
	 */
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

	/**
	 * It updates the child of a parent node.
	 *
	 * @param parent The actual parent of child.
	 * @param child The actual child of parent.
	 * @param newChild The new child to assign to parent.
	 *
	 * @attention Calling this function may lead to an inconsistent state of an Abr, do
	 * not call it.
	 */
	private void updateChild(Abr parent, Abr child, Abr newChild) {
		if (null != parent) {
			if (child == parent.leftChild)
				parent.leftChild = newChild;
			else
				parent.rightChild = newChild;
		}
	}

	/**
	 * It updates the grandparent of a child node, turning it into his parent, making the
	 * previous parent his parent no more.
	 *
	 * @param grandparent The grandparent node of child.
	 * @param parent The actual parent of child.
	 * @param child The node that is changing his parent.
	 *
	 * @attention Calling this function may lead to an inconsistent state of an Abr, do
	 * not call it.
	 */
	private void moveParentLink(Abr grandparent, Abr parent, Abr child) {
		if (null != grandparent) {
			if (grandparent.leftChild == parent)
				grandparent.leftChild = child;
			else
				grandparent.rightChild = child;
		}

		child.parent = grandparent;
	}

	/**
	 * It finds the predecessor of a node.
	 *
	 * @param node The node from which the predecessor has to be found.
	 *
	 * @return The predecessor of node.
	 *
	 * @attention The existence of a predecessor in the immediate left subtree is supposed
	 * to be true, so the instantiation of node.leftChild is a non-checked runtime error.
	 */
	private Abr predecessor(Abr node) {
		Abr predecessor = node.leftChild;

		while (null != predecessor.rightChild)
			predecessor = predecessor.rightChild;

		return predecessor;
	}

	/**
	 * It removes all the links of a node within an Abr.
	 *
	 * @param node The node from which every links have to be removed.
	 *
	 * @attention Calling this function may lead to an inconsistent state of an Abr, do
	 * not call it.
	 */
	private void removeLinks(Abr node) {
		Abr parent = node.parent;

		if (null == node.leftChild && null == node.rightChild)
			updateChild(parent, node, null);

		else if (null == node.leftChild)
			moveParentLink(parent, node, node.rightChild);

		else if (null == node.rightChild)
			moveParentLink(parent, node, node.leftChild);

		else {
			Abr predecessor = predecessor(node);

			//TODO: docs of this part
			updateChild(parent, node, predecessor);
			if (node != predecessor.parent)
				predecessor.parent.rightChild = predecessor.leftChild;
			predecessor.parent = parent;
			predecessor.leftChild = node.leftChild;
			predecessor.rightChild = node.rightChild;
			node.leftChild.parent = predecessor;
			node.rightChild.parent = predecessor;
		}
	}

	/**
	 * It searches for a key and it removes it from the binary search tree,
	 * if the key exists.
	 *
	 * @param key The key to delete.
	 * @param tree Where the key has to be searched and deleted.
	 */
	private void __delete(Comparable key, Abr tree) {
		if (null == tree)
			return;

		else {
			Integer diff = key.compareTo(tree.entry.getKey());

			if (0 == diff) {
				removeLinks(tree);
				tree = null;
			}

			else if (0 > diff)
				__delete(key, tree.leftChild);

			else
				__delete(key, tree.rightChild);
		}
	}

	public void delete(Comparable key) {
		__assertNotNullKey(key);

		__delete(key, this);
	}
}
