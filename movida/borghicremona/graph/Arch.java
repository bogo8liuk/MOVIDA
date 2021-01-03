package movida.borghicremona.graph;

public class Arch {
	private Comparable nodeKeyA;
	private Comparable nodeKeyB;

	public Arch(Comparable nodeKeyA, Comparable nodeKeyB) {
		this.nodeKeyA = nodeA;
		this.nodeKeyB = nodeB;
	}

	public Comparable[] getArchNodes() {
		Comparable[] pair = new Comparable[2];
		pair[0] = this.nodeKeyA;
		pair[1] = this.nodeKeyB;
		return pair;    
	}
}

