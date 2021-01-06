package movida.borghicremona.graph;

/* Object with this type should be instantiated with a lambda expression in order to
   use them in the visit algorithm of a Graph. */
public interface NodeOperation {

	public void visitNode(Comparable node);

}
