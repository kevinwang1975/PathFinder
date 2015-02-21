package matrix;

import model.ICostEvaluator;
import model.IEdge;
import model.INode;

/**
 * An implementation for a graph in matrix form.
 * 
 * @author Kevin Wang
 *
 */
public class AStarCostEvaluator implements ICostEvaluator {

	volatile boolean enabled = true;
	final int factor = 10;

	@SuppressWarnings("serial")
	public static class EvaluatorDisabledException extends RuntimeException {}

	@Override
	public int evaluateWeight(IEdge edge) {
		MatrixNode nodeA = (MatrixNode) edge.getNodeA();
		MatrixNode nodeB = (MatrixNode) edge.getNodeB();
		if (nodeA.getRow() == nodeB.getRow() || nodeA.getCol() == nodeB.getCol()) {
			return factor*1;
		}
		else {
			return (int) (factor*1.4);
		}
	}

	@Override
	public int evaluateHeuristic(INode node, INode start, INode end) {
		return factor*(Math.abs(((MatrixNode) node).getRow() - ((MatrixNode) end).getRow()) + Math.abs(((MatrixNode) node).getCol() - ((MatrixNode) end).getCol()));
	}

	@Override
	public int evaluateCost(INode candidate, IEdge edge, INode start, INode end) {
		if (isEnabled()) {
			return edge.getOpposite(candidate).getCost() + edge.getWeight();
		}
		throw new EvaluatorDisabledException();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
