package model;

import java.util.Collection;

/**
 * Defines a graph node. In graph theory, a graph contains nodes and edges, the nodes
 * are connected to each other through edges.
 *  
 * @author Kevin Wang
 *
 */
public interface INode {

	/**
	 * @return the edges through which this node is connected to other nodes.
	 */
	Collection<IEdge> getEdges();
	
	/**
	 * @return the predecessor of the node
	 */
	INode getPredecessor();
	
	/**
	 * Set the predecessor of the node. In the graph path search, an algorithm finds the nodes
	 * to form possibly the best path (the best is relative, depending on how the algorithm
	 * evaluate the cost) between the origin and destination. The search goes node by node
	 * from the origin to the destination, for every two consecutive nodes, the leading node
	 * is the predecessor of the trailing node.
	 * 
	 * @param node
	 */
	void setPredecessor(INode node);
	
	/**
	 * @return true if the node is open, false otherwise
	 * @see #setOpen
	 */
	public boolean isOpen();
	
	/**
	 * Makes the node open or closed. When set to closed, it will no longer be considered
	 * as a candidate in the graph path search.
	 * 
	 * @param open
	 */
	public void setOpen(boolean open);
	
	/**
	 * @return if this node has been visited during the graph path search
	 */
	public boolean isVisited();
	
	/**
	 * Indicates whether or not the node has been visited during the graph path search.
	 * 
	 * @param visited
	 */
	public void setVisited(boolean visited);
	
	/**
	 * @return true if the node has been selected to be part of the resulted path, false otherwise
	 */
	public boolean isSelected();
	
	/**
	 * Indicates whether or not the node has been selected to be part of the resulted path.
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected);
	
	/**
	 * @return the heuristic value of the node
	 */
	int getHeuristic();
	
	/**
	 * Set the heuristic value evaluated as the cost from the node to the destination.
	 * 
	 * @param heuristic
	 */
	void setHeuristic(int heuristic);
	
	/**
	 * @return the cost of node
	 */
	int getCost();
	
	/**
	 * Set the cost evaluated from the origin to the node.
	 * 
	 * @param cost
	 */
	void setCost(int cost);

}
