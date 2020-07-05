package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;

public interface Graph {
    public int nodesNumber();
   
    public int archsNumber();

    public int grade(int node);
 
    public Arch[] incidentArchs(int node);

    public int[] edges(Arch arch);

    public int opposite(int node, Arch arch);

    public boolean areAdjacent(int[] couple);

    public void addNode(KeyValueElement node);

    public void addArch(int node0, int node1);

    public void removeNode(int node);

    public void removeArch(Arch arch);
}
