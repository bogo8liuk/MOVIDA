package movida.borghicremona.graph;

public class Arch {
    private Vertex vertexA;
    private Vertex vertexB;

    public Arch(Vertex vertexA, Vertex vertexB) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
    }

    public VertexCouple getArchVertexes() {
        VertexCouple couple = new VertexCouple(this.vertexA, this.vertexB);
        return couple;    
    }
}

