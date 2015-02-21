package model;


import java.util.ArrayList;
import java.util.Collection;

public class Node implements INode {

	private boolean open = true;
	private boolean visited = false;
	private boolean selected = false;

	private final Collection<IEdge> edges = new ArrayList<IEdge>();
	private INode predecessor;
	private int cost = 0;
	private int heuristic = 0;

	/**
	 * reset to the original values.
	 */
	public void reset() {
		predecessor = null;
		cost = 0;
		open = true;
		visited = false;
		selected = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOpen() {
		return open;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean addEdge(IEdge edge) {
		return this.edges.add(edge);
	}
	
	public boolean removeEdge(IEdge edge) {
		return this.edges.remove(edge);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IEdge> getEdges() {
		return this.edges;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public INode getPredecessor() {
		return predecessor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPredecessor(INode node) {
		this.predecessor = node;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCost() {
		return this.cost;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeuristic() {
		return heuristic;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(heuristic + cost);
		sb.append("(");
		sb.append(cost);
		sb.append(",");
		sb.append(heuristic);
		sb.append(")");;
		return sb.toString();
	}

}
