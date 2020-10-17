package movida.borghicremona.graph;

import java.util.*;

public class VisitTree {
	List<VisitTree> children;
	VisitTree parent;
	int dist;
	int node;
	
	private VisitTree() {
		this.children = null;
		this.parent = null;
		this.dist = -1;	
		this.node = -1;
	} 

	public VisitTree(int root) {
		this.children = null;
		this.parent = null;
		this.dist = 0;
		this.node = root;
	}

	void addChild(int node, VisitTree parent) {
		if (null == parent.children)
			parent.children = new LinkedList<VisitTree>();

		VisitTree nodeToAdd = new VisitTree(node);
		parent.children.add(nodeToAdd); 
		nodeToAdd.parent = parent;
		nodeToAdd.dist = parent.dist + 1;		
	}
	
	private static void apply(int node, NodeOperation item) {
		item.operation(node);
	}

	private static void __visit(NodeOperation item, VisitTree tree) {
		if (0 > tree.node)	
			return;
		
		apply(tree.node, item);

		if (null != tree.children) {
			Iterator<VisitTree> iter = tree.children.iterator();
			
			while (iter.hasNext()) 
				__visit(item, iter.next());
		}
	}

	public void visit(NodeOperation item) {
		__visit(item, this);
	}
}
