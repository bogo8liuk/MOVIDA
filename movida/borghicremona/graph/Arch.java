package movida.borghicremona.graph;

public class Arch {
    private int nodeA;
    private int nodeB;

    public Arch(int nodeA, int nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public int[] getArchNodes() {
        int[] couple = new int[2];
        couple[0] = this.nodeA;
        couple[1] = this.nodeB;
        return couple;    
    }
}

