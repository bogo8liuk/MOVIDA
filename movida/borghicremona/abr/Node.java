package movida.borghicremona.abr;

import movida.borghicremona.KeyValueElement.java;

public class Node {
	private KeyValueElement value;
	private Node left;
	private Node right;
	
	public Node(KeyValueElement value) {
		value = value;
		left = null;
		right = null;
}

	public KeyValueElement getValue() { 
	
		return value; 
	
	}

	public void setLeftChild(Node child) { 
	
		this.left = child; 
		
	}
	
	public void setRightChild(Node child) { 
	
		this.right = child; 
		
	}
	
	public Node getLeftChild() { 
	
		return left; 
		
	}
	
	public Node getRightChild() { 
	
		return right; 
	
	}
}

