package movida.borghicremona.bstree;

import movida.borghicremona.Dictionary;
import movida.borghicremona.KeyValueElement;
import movida.borghicremona.Assert;
import java.lang.*;

public class BinarySearchTree implements Dictionary {
	private class Node {
		private KeyValueElement entry;
		private Node parent;
		private Node leftChild;
		private Node rightChild;

		private class DeleteReturn {
			public Node newRoot;
			public Object returnValue;

			public DeleteReturn(Node root, Object res) {
				this.newRoot = root;
				this.returnValue = res;
			}
		}

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

			// Duplicates not allowed.
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
		 * @return the data associated with the key, if key is found, null otherwise.
		 */
	    private Object __search(Comparable key, Node tree) {
			if (null == tree)
				return null;

			else {
				Integer diff = key.compareTo(tree.entry.getKey());

				if (0 == diff)
					return tree.entry.getValue();

				else if (0 > diff)
					return __search(key, tree.leftChild);

				else
					return __search(key, tree.rightChild);
			}
	    }

		public Object search(Comparable key) {
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
		 * node is the root, null otherwise and the data associated with the deleted
		 * key, if the latter existed, null otherwise.
		 *
		 * @param key The key to delete.
		 * @param tree Where the key has to be searched and deleted.
		 */
		private DeleteReturn __delete(Comparable key, Node tree) {
			if (null == tree || null == tree.entry)
				return null;

			else {
				Integer diff = key.compareTo(tree.entry.getKey());

				if (0 == diff) {
					Node res = removeLinks(tree);
					Object value = tree.entry.getValue();
					tree.entry = null;
					return (this == tree) ? new DeleteReturn(res, value) : new DeleteReturn(null, value);
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
		public DeleteReturn delete(Comparable key) {
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

	public BinarySearchTree() {
		this.root = new Node(null);
	}

	public BinarySearchTree(KeyValueElement item) {
		this.root = new Node(item);
	}

	public Object search(Comparable key) {
		try {
			Assert.notNullKey(key);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}

		return this.root.search(key);
	}

	public void insert(KeyValueElement item) {
		try {
			Assert.notNullData(item);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}
	
		try {
			Assert.notNullKey(item.getKey());
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}

		this.root.insert(item);
	}

	public Object delete(Comparable key) {
		try {
			Assert.notNullKey(key);
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage() + ": aborting");
			System.exit(-1);
		}

		Node.DeleteReturn res = this.root.delete(key);

		if (null != res) {
			Node newRoot = res.newRoot;
			if (null != newRoot)
				this.root = newRoot;

			return res.returnValue;
		}

		return null;
	}

	public void inOrderVisitPrint() {
		this.root.inOrderVisitPrint();
	}
}
