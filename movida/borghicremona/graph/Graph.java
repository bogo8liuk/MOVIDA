package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;

public interface Graph {
    public int nodesNumber();
   
    public int archsNumber();

    public int grade(int node);
 
    public Arch[] incidentArchs(int node);

    public int[] edges(Arch arch);

    public int opposite(int node, Arch arch);

    public boolean areAdjacent(int nodeA, int nodeB);

    public void addNode(int node, Object data);

    public void addArch(int nodeA, int nodeB);

    public void removeNode(int node);

    public void removeArch(Arch arch);

	public VisitTree breadthFirstVisit(NodeOperation item, int start);

	public void depthFirstVisit(NodeOperation item);
}
