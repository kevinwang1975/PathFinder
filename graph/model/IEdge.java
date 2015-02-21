package model;

/**
 * Defines a graph edge. In graph theory, a graph contains nodes and edges, an edge connects
 * two nodes.
 * 
 * @author Kevin Wang
 *
 */
public interface IEdge {

	/**
	 * @return node A
	 */
	INode getNodeA();
	
	/**
	 * @return node B
	 */
	INode getNodeB();
	
	/**
	 * Given either node A, or B, return the other node of the two.
	 * 
	 * @param node
	 * @return the other node opposite to the given node
	 */
	INode getOpposite(INode node);
	
	/**
	 * @return the weight of the edge
	 */
	int getWeight();

}
