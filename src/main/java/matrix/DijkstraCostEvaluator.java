package matrix;

import model.INode;

/**
 * The difference of Dijkstra algorithm to A* is that it does not have heuristic.
 * 
 * @author Kevin Wang
 *
 */
public class DijkstraCostEvaluator extends AStarCostEvaluator {

	@Override
	public int evaluateHeuristic(INode node, INode start, INode end) {
		return 0;
	}

}
