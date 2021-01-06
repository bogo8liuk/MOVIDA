package movida.borghicremona.graph;

import movida.borghicremona.KeyValueElement;

public interface Graph {
	/**
	 * It returns the number of nodes in the graph.
	 *
	 * @return The number of nodes.
	 */
	public int nodesNumber();
   
	/**
	 * It returns the number of archs in the graph.
	 *
	 * @return The number of archs.
	 */
	public int archsNumber();

	/**
	 * It counts the number of adjacent nodes of a given node, if the latter exists.
	 *
	 * @param nodeKey The node from which the counting has to be performed.
	 *
	 * @return The number of adjacent nodes of nodeKey.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public int grade(Comparable nodeKey);

	/**
	 * It gets the sequence of incident archs of a given node, if the latter exists.
	 *
	 * @param nodeKey The node from which getting the archs.
	 *
	 * @return An array of Arch.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public Arch[] incidentArchs(Comparable nodeKey);

	/**
	 * It gets the two nodes of a given arch, if the arch exists.
	 *
	 * @param arch The arch from which getting the two nodes.
	 *
	 * @return A two-long array of Comparable (nodes).
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public Comparable[] edges(Arch arch);

	/**
	 * It gets the opposite node of a given node in a given arch, if the latter exists.
	 *
	 * @param nodeKey The node from which searching the opposite.
	 * @param arch The arch from which searching the opposite of nodeKey.
	 *
	 * @return The opposite node of nodeKey.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public Comparable opposite(Comparable nodeKey, Arch arch);

	/**
	 * It tells if two nodes are adjacents, if they exist.
	 *
	 * @param nodeKey First node to check.
	 * @param nodeKey Second node to check.
	 *
	 * @return true if nodeKeyA and nodeKeyB are adjacent, false otherwise.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public boolean areAdjacent(Comparable nodeKeyA, Comparable nodeKeyB);

	/**
	 * It inserts a new node, if the latter does not already exist.
	 *
	 * @param nodeKey The node to insert.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public void addNode(Comparable nodeKey);

	/**
	 * It adds an arch between two already existent nodes, if the arch does not already exist.
	 *
	 * @param nodeKeyA The first node of the arch to add.
	 * @param nodeKeyB The second node of the arch to add.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public void addArch(Comparable nodeKeyA, Comparable nodeKeyB);

	/**
	 * It removes a node, if the latter exists.
	 *
	 * @param nodeKey The node to remove.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public void removeNode(Comparable nodeKey);

	/**
	 * It removes an arch, if the latter exists.
	 *
	 * @param arch The arch to remove.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public void removeArch(Arch arch);

	/**
	 * It performs a breadth first visit, namely, from a starting node, it visits every reachable
	 * node according to an increase of the distance.
	 *
	 * @param op The operation to perform on every visited node.
	 * @param start Starting node.
	 *
	 * @return The array of visited nodes.
	 *
	 * @attention The check on the validity of parameters is an implementation-defined error.
	 */
	public Comparable[] breadthFirstVisit(NodeOperation op, Comparable start);
}
