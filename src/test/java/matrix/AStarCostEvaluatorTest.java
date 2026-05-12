package matrix;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Edge;
import model.INode;

public class AStarCostEvaluatorTest {

    private MatrixNode node(int row, int col) {
        MatrixNode n = new MatrixNode();
        n.setRow(row);
        n.setCol(col);
        return n;
    }

    @Test
    public void straightEdgeWeightIsFactor() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        MatrixNode a = node(0, 0);
        MatrixNode b = node(0, 1);
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);

        assertEquals(10, evaluator.evaluateWeight(edge));
    }

    @Test
    public void diagonalEdgeWeightIsFactorTimesSqrt2() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        MatrixNode a = node(0, 0);
        MatrixNode b = node(1, 1);
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);

        assertEquals(14, evaluator.evaluateWeight(edge));
    }

    @Test
    public void heuristicIsManhattanDistanceTimesFactor() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        MatrixNode node = node(3, 7);
        MatrixNode start = node(0, 0);
        MatrixNode end = node(5, 2);

        int expected = 10 * (Math.abs(3 - 5) + Math.abs(7 - 2));
        assertEquals(expected, evaluator.evaluateHeuristic(node, start, end));
    }

    @Test
    public void heuristicOfEndNodeIsZero() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        MatrixNode end = node(5, 2);
        assertEquals(0, evaluator.evaluateHeuristic(end, null, end));
    }

    @Test
    public void costAccumulatesPredecessorCostPlusWeight() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        MatrixNode start = node(0, 0);
        MatrixNode mid = node(0, 1);
        MatrixNode end = node(0, 2);

        Edge e1 = new Edge();
        e1.setNodeA(start);
        e1.setNodeB(mid);
        e1.setWeight(10);

        Edge e2 = new Edge();
        e2.setNodeA(mid);
        e2.setNodeB(end);
        e2.setWeight(10);

        start.setCost(0);
        mid.setCost(10);
        mid.setPredecessor(start);

        assertEquals(20, evaluator.evaluateCost(end, e2, start, end));
    }

    @Test(expected = AStarCostEvaluator.EvaluatorDisabledException.class)
    public void disabledEvaluatorThrowsException() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        evaluator.setEnabled(false);

        MatrixNode a = node(0, 0);
        MatrixNode b = node(0, 1);
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);

        evaluator.evaluateCost(b, edge, a, b);
    }

    @Test
    public void enabledStateToggle() {
        AStarCostEvaluator evaluator = new AStarCostEvaluator();
        assertTrue(evaluator.isEnabled());
        evaluator.setEnabled(false);
        assertFalse(evaluator.isEnabled());
        evaluator.setEnabled(true);
        assertTrue(evaluator.isEnabled());
    }
}
