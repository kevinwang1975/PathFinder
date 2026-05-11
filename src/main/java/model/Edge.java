package model;


public class Edge implements IEdge {

	private INode nodeA;
	private INode nodeB;
	private int weight;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INode getNodeA() {
		return this.nodeA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INode getNodeB() {
		return this.nodeB;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INode getOpposite(INode node) {
		if (node == this.nodeA) {
			return this.nodeB;
		}
		if (node == this.nodeB) {
			return this.nodeA;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Set the weight of the edge.
	 * 
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Set node A.
	 * 
	 * @param nodeA
	 */
	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	/**
	 * Set node B.
	 * 
	 * @param nodeB
	 */
	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

}
