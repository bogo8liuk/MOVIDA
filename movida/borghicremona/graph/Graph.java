package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;

public interface Graph {
	public int nodesNumber();
   
	public int archsNumber();

	public int grade(Comparable nodeKey);

	public Arch[] incidentArchs(Comparable nodeKey);

	public Comparable[] edges(Arch arch);

	public Comparable opposite(Comparable nodeKey, Arch arch);

	public boolean areAdjacent(Comparable nodeKeyA, Comparable nodeKeyB);

	public void addNode(KeyValueElement item);

	public void addArch(Comparable nodeKeyA, Comparable nodeKeyB);

	public void removeNode(Comparable nodeKey);

	public void removeArch(Arch arch);

	public VisitTree breadthFirstVisit(NodeOperation item, int start);
}
