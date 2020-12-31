package movida.borghicremona.graph;

public class Arch {
	private Comparable nodeKeyA;
	private Comparable nodeB;

	public Arch(Comparable nodeKeyA, Comparable nodeKeyB) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
	}

	public Comparable[] getArchNodes() {
		Comparable[] pair = new Comparable[2];
		pair[0] = this.nodeA;
		pair[1] = this.nodeB;
		return pair;    
	}
}

