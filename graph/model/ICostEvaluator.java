package model;

/**
 * A cost evaluator is responsible for evaluating the weight of a given edge, 
 * the cost and the heuristic value of a given node with a necessary context.
 * 
 * @author Kevin Wang
 *
 */
public interface ICostEvaluator {

	/**
	 * Evaluates the weight of an edge.
	 * 
	 * @param edge
	 * @return the weight
	 */
	int evaluateWeight(IEdge edge);
	
	/**
	 * Evaluates the heuristic value of a node.
	 * 
	 * @param node
	 * @param start
	 * @param end
	 * @return the heuristic value
	 */
	int evaluateHeuristic(INode node, INode start, INode end);
	
	/**
	 * Evaluates the cost of a node.
	 * 
	 * @param candidate
	 * @param edge
	 * @param start
	 * @param end
	 * @return the cost
	 */
	int evaluateCost(INode candidate, IEdge edge, INode start, INode end);

}
