package movida.borghicremona.bstree;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import java.lang.*;

public class BinarySearchTree implements Dictionary {
	private class Node {
		private KeyValueElement entry;
		private Node parent;
		private Node leftChild;
		private Node rightChild;

		public Node(KeyValueElement item) {
			this.entry = item;
			this.parent = null;
			this.leftChild = null;
			this.rightChild = null;
		}

		/**
		 * It instantiates a new Node object, defining the parent.
		 *
		 * @param item Element representing the node.
		 * @param parent Current node's parent.
		 *
		 * @attention It may lead to an unwanted state of the object.
		 */
		private Node(KeyValueElement item, Node parent) {
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
		 * @attention It may lead to an inconsistent state of Node: do not use this method.
		 */
	    private void __insert(KeyValueElement item, Node tree) {
			Integer diff = item.getKey().compareTo(tree.entry.getKey());

			// It does not allow duplicated keys
			if (0 == diff)
				return;

			else if (0 > diff) {
				if (null == tree.leftChild) {
					tree.leftChild = new Node(item, tree);
					return;
				}
				__insert(item, tree.leftChild);
			}

			else {
				if (null == tree.rightChild) {
					tree.rightChild = new Node(item, tree);
					return;
				}
				__insert(item, tree.rightChild);
			}
	    }

		public void insert(KeyValueElement item) {
			if (null == this.entry)
				this.entry = item;
			else
				__insert(item, this);
		}

		/**
		 * It searches for a key in a binary search tree.
		 *
		 * @param key Key to search for.
		 * @param tree Where to search for the key.
		 *
		 * @return true, if key is found, false otherwise.
		 */
	    private boolean __search(Comparable key, Node tree) {
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
			return __search(key, this);
		}

		/**
		 * It updates the child of a parent node.
		 *
		 * @param parent The actual parent of child.
		 * @param child The actual child of parent.
		 * @param newChild The new child to assign to parent.
		 *
		 * @attention Calling this function may lead to an inconsistent state of a Node, do
		 * not call it.
		 */
		private void updateChild(Node parent, Node child, Node newChild) {
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
		 * @attention Calling this function may lead to an inconsistent state of a Node, do
		 * not call it.
		 */
		private void moveParentLink(Node grandparent, Node parent, Node child) {
			updateChild(grandparent, parent, child);
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
		private Node predecessor(Node node) {
			Node predecessor = node.leftChild;

			while (null != predecessor.rightChild)
				predecessor = predecessor.rightChild;

			return predecessor;
		}

		/**
		 * It removes all the links of a node within a Node.
		 *
		 * @param node The node from which every links have to be removed.
		 *
		 * @return The node which took the place in the tree of the removed node.
		 * If the removed node had no child, it is returned.
		 *
		 * @attention Calling this function may lead to an inconsistent state of a Node, do
		 * not call it.
		 */
		private Node removeLinks(Node node) {
			Node parent = node.parent;

			if (null == node.leftChild && null == node.rightChild) {
				updateChild(parent, node, null);
				return node;
			}

			else if (null == node.leftChild) {
				moveParentLink(parent, node, node.rightChild);
				return node.rightChild;
			}

			else if (null == node.rightChild) {
				moveParentLink(parent, node, node.leftChild);
				return node.leftChild;
			}

			else {
				Node predecessor = predecessor(node);

				//TODO: docs of this part
				updateChild(parent, node, predecessor);
				if (node != predecessor.parent)
					predecessor.parent.rightChild = predecessor.leftChild;
				predecessor.parent = parent;

				if (predecessor != node.leftChild) {
					predecessor.leftChild = node.leftChild;
					node.leftChild.parent = predecessor;
				}

				predecessor.rightChild = node.rightChild;
				node.rightChild.parent = predecessor;

				return predecessor;
			}
		}

		/**
		 * It searches for a key and it removes it from the binary search tree,
		 * if the key exists.
		 *
		 * @return The node that took the place of the removed node, if the removed
		 * node is the root, null otherwise.
		 *
		 * @param key The key to delete.
		 * @param tree Where the key has to be searched and deleted.
		 */
		private Node __delete(Comparable key, Node tree) {
			if (null == tree)
				return null;

			else {
				Integer diff = key.compareTo(tree.entry.getKey());

				if (0 == diff) {
					Node res = removeLinks(tree);
					tree.entry = null;
					return (this == tree) ? res : null;
				}

				else if (0 > diff)
					return __delete(key, tree.leftChild);

				else
					return __delete(key, tree.rightChild);
			}
		}

		/**
		 * See __delete().
		 */
		public Node delete(Comparable key) {
			return __delete(key, this);
		}

		/**
		 * It carried out an in-order visit on tree.
		 */
		private void __inOrderVisitPrint(Node tree) {
			if (null == tree)
				return;

			else {
				__inOrderVisitPrint(tree.leftChild);
				if (null != tree.entry)
					System.out.print(tree.entry.getKey() + " ");
				__inOrderVisitPrint(tree.rightChild);
			}
		}

		/**
		 * See __inOrderVisitPrint().
		 */
		public void inOrderVisitPrint() {
			__inOrderVisitPrint(this);
			System.out.println("");
		}
	}

	private Node root;

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

	public BinarySearchTree(KeyValueElement item) {
		this.root = new Node(item);
	}

	public boolean search(Comparable key) {
		__assertNotNullKey(key);

		return this.root.search(key);
	}

	public void insert(KeyValueElement item) {
		__assertNotNullData(item);
		__assertNotNullKey(item.getKey());

		this.root.insert(item);
	}

	public void delete(Comparable key) {
		__assertNotNullKey(key);

		Node newRoot = this.root.delete(key);
		if (null != newRoot)
			this.root = newRoot;
	}

	public void inOrderVisitPrint() {
		this.root.inOrderVisitPrint();
	}
}
