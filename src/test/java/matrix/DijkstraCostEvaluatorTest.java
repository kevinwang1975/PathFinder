package matrix;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Edge;
import model.INode;

public class DijkstraCostEvaluatorTest {

    private MatrixNode node(int row, int col) {
        MatrixNode n = new MatrixNode();
        n.setRow(row);
        n.setCol(col);
        return n;
    }

    @Test
    public void heuristicIsZero() {
        DijkstraCostEvaluator evaluator = new DijkstraCostEvaluator();
        MatrixNode node = node(3, 7);
        MatrixNode end = node(5, 2);
        assertEquals(0, evaluator.evaluateHeuristic(node, null, end));
    }

    @Test
    public void straightEdgeWeightIsFactor() {
        DijkstraCostEvaluator evaluator = new DijkstraCostEvaluator();
        MatrixNode a = node(0, 0);
        MatrixNode b = node(0, 1);
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);

        assertEquals(10, evaluator.evaluateWeight(edge));
    }

    @Test
    public void diagonalEdgeWeightIsFactorTimesSqrt2() {
        DijkstraCostEvaluator evaluator = new DijkstraCostEvaluator();
        MatrixNode a = node(0, 0);
        MatrixNode b = node(1, 1);
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);

        assertEquals(14, evaluator.evaluateWeight(edge));
    }

    @Test
    public void costAccumulatesPredecessorCostPlusWeight() {
        DijkstraCostEvaluator evaluator = new DijkstraCostEvaluator();
        MatrixNode start = node(0, 0);
        MatrixNode mid = node(0, 1);
        MatrixNode end = node(0, 2);

        Edge e2 = new Edge();
        e2.setNodeA(mid);
        e2.setNodeB(end);
        e2.setWeight(10);

        mid.setCost(10);
        assertEquals(20, evaluator.evaluateCost(end, e2, start, end));
    }

    @Test(expected = AStarCostEvaluator.EvaluatorDisabledException.class)
    public void disabledEvaluatorThrowsException() {
        DijkstraCostEvaluator evaluator = new DijkstraCostEvaluator();
        evaluator.setEnabled(false);
        MatrixNode a = node(0, 0);
        MatrixNode b = node(0, 1);
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);

        evaluator.evaluateCost(b, edge, a, b);
    }

    @Test
    public void inheritsEnabledState() {
        DijkstraCostEvaluator evaluator = new DijkstraCostEvaluator();
        assertTrue(evaluator.isEnabled());
    }
}
