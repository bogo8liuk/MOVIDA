package movida.borghicremona.graph;

public interface NodeOperation <T,U> {

	public T operation(int node, U... args);

}
