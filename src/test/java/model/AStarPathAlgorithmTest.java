package model;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

public class AStarPathAlgorithmTest {

    private static class TestEvaluator implements ICostEvaluator {
        @Override
        public int evaluateWeight(IEdge edge) {
            return edge.getWeight();
        }

        @Override
        public int evaluateHeuristic(INode node, INode start, INode end) {
            return 0;
        }

        @Override
        public int evaluateCost(INode candidate, IEdge edge, INode start, INode end) {
            return edge.getOpposite(candidate).getCost() + edge.getWeight();
        }
    }

    private static class ManhattanEvaluator implements ICostEvaluator {
        @Override
        public int evaluateWeight(IEdge edge) {
            return edge.getWeight();
        }

        @Override
        public int evaluateHeuristic(INode node, INode start, INode end) {
            return 0;
        }

        @Override
        public int evaluateCost(INode candidate, IEdge edge, INode start, INode end) {
            return edge.getOpposite(candidate).getCost() + edge.getWeight();
        }
    }

    private void connect(Node a, Node b, int weight) {
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);
        edge.setWeight(weight);
        a.addEdge(edge);
        b.addEdge(edge);
    }

    @Test
    public void pathFoundBetweenConnectedNodes() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        connect(a, b, 10);
        connect(b, c, 10);

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, c));
        assertTrue(a.isSelected());
        assertTrue(b.isSelected());
        assertTrue(c.isSelected());
    }

    @Test
    public void noPathBetweenDisconnectedNodes() {
        Node a = new Node();
        Node b = new Node();

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertFalse(algo.searchPath(a, b));
    }

    @Test
    public void pathFoundWhenStartEqualsEnd() {
        Node a = new Node();

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, a));
        assertTrue(a.isSelected());
    }

    @Test
    public void findsOptimalPath() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        Node d = new Node();
        Node e = new Node();
        Node f = new Node();

        connect(a, b, 10);
        connect(b, c, 10);
        connect(c, f, 10);
        connect(a, d, 10);
        connect(d, e, 10);
        connect(e, f, 10);
        connect(b, e, 1);

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, f));

        int pathCost = f.getCost();
        assertEquals(21, pathCost);
    }

    @Test
    public void visitedLessThanDijkstra() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        Node d = new Node();
        connect(a, b, 10);
        connect(b, c, 10);
        connect(c, d, 10);
        connect(a, d, 50);

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, d));
        assertEquals(30, d.getCost());
        assertTrue(a.isSelected());
        assertTrue(d.isSelected());
    }

    @Test
    public void noCrossTalkBetweenSearches() {
        Node a = new Node();
        Node b = new Node();
        connect(a, b, 10);

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, b));
        assertTrue(a.isSelected());
        assertTrue(b.isSelected());

        Node c = new Node();
        assertFalse(algo.searchPath(a, c));
    }

    @Test
    public void pathCostCorrectWithMultiplePaths() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        Node d = new Node();

        connect(a, b, 5);
        connect(b, d, 5);
        connect(a, c, 1);
        connect(c, d, 100);

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, d));
        assertEquals(10, d.getCost());
    }

    @Test
    public void worksWithNodeSubtypes() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        connect(a, b, 10);
        connect(b, c, 10);

        AStarPathAlgorithm algo = new AStarPathAlgorithm();
        algo.setEvaluator(new TestEvaluator());

        assertTrue(algo.searchPath(a, c));
        assertTrue(c.isSelected());
    }
}
