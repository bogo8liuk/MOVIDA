package movida.borghicremona.graph;

public interface Graph {
    public int vertexNumber();
   
    public int archsNumber();

    public int grade(Vertex vertex);
 
    public Arch[] incidentArchs(Vertex vertex);

    public VertexCouple edges(Arch arch);

    public Vertex opposite(Vertex vertex, Arch arch);

    public boolean areAdjacent(VertexCouple couple);

    public void addVertex(Vertex vertex);

    public void addArch(VertexCouple couple);

    public void removeVertex(Vertex vertex);

    public void removeArch(Arch arch);
}
