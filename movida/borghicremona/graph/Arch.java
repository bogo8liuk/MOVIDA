package movida.borghicremona.graph;

public class Arch {
    private Comparable nodeA;
    private Comparable nodeB;

    public Arch(Comparable nodeA, Comparable nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public Comparable[] getArchNodes() {
        Comparable[] couple = new Comparable[2];
        couple[0] = this.nodeA;
        couple[1] = this.nodeB;
        return couple;    
    }
}

