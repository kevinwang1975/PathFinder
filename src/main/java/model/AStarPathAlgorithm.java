package model;

import java.util.Collection;
import java.util.Comparator;

import util.BinaryHeap;

/**
 * An implementation of A* path search algorithm.
 * 
 * @author Kevin Wang
 *
 */
public class AStarPathAlgorithm implements IPathAlgorithm {

	private ICostEvaluator evaluator;

	private final BinaryHeap<INode> binaryHeap = new BinaryHeap<INode>(new Comparator<INode>() {
		@Override
		public int compare(INode o1, INode o2) {
			return (o1.getCost() + o1.getHeuristic() - (o2.getCost() + o2.getHeuristic()));
		}
	});

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean searchPath(INode start, INode end) {
		try {
			return search(start, end);
		}
		finally {
			binaryHeap.clear();
		}
	}

	/**
	 * @param start
	 * @param end
	 * @return true if a path is found, false otherwise
	 */
	private boolean search(INode start, INode end) {
		INode node = start;
		node.setVisited(true);
		node.setCost(0);

		binaryHeap.add(node);
		while (binaryHeap.size() > 0) {
			// take the node with the lowest cost, and check the nodes that are connected to it.
			node = binaryHeap.remove();
			// the node has the lowest cost, close it so it won't be visited again.
			// even though a node has the lowest cost, it still can be not in the selected path
			// in the end of the path search:
			//   - if it has no connection to the destination, or its connection has a higher
			//     cost so lost the battle in the competition, then it won't be in the selected path
			//   - if it remains as a link on the chain connecting the origin and the destination,
			//     then it will be in the selected path
			node.setOpen(false);
			Collection<IEdge> edges = node.getEdges();
			// check the nodes connected by the edges
			for (IEdge edge : edges) {
				Node candidate = (Node) edge.getOpposite(node);
				if (!candidate.isOpen()) {
					continue;
				}
				int cost = evaluator.evaluateCost(candidate, edge, start, end);
				// if the node has never been visited since the search begins, add it
				// into the binary heap
				if (!candidate.isVisited()) {
					candidate.setVisited(true);
					candidate.setCost(cost);
					candidate.setPredecessor(node);
					binaryHeap.add(candidate);
				}
				// if the candidate node has been visited before, i.e. it can be reached
				// from the origin through another node, which is currently the predecessor
				// of the candidate node, but the cost is higher in comparison with that
				// if it takes the path through the "node" that is currently being tested.
				// so update the predecessor and cost.
				else if (cost < candidate.getCost()) {
					candidate.setCost(cost);
					candidate.setPredecessor(node);
					// the removing and adding of the candidate is simply to put the candidate
					// at a new place as its cost has changed.
					binaryHeap.remove(candidate);
					binaryHeap.add(candidate);
				}
			}
			// reached the destination, mark the path and stop 
			if (binaryHeap.contains(end)) {
				markPath(start, end);
				return true;
			}
		}
		return false;
	}

	/**
	 * Mark all the nodes that form the path as selected, from the last node backwards
	 * through the predecessors, to the origin.
	 * 
	 * @param start
	 * @param end
	 */
	private void markPath(INode start, INode end) {
		INode node = end;
		while (node != start) {
			node.setSelected(true);
			node = node.getPredecessor();
		}
		node.setSelected(true);
	}

	public ICostEvaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(ICostEvaluator evaluator) {
		this.evaluator = evaluator;
	}

}
