package movida.borghicremona.graph;

/* Instances of this type should be instantiated with a lambda expression in order to
   return the "weight" of an arch. */
public interface Weight {
	public Double weight(Comparable nodeKeyA, Comparable nodeKeyB);
}
