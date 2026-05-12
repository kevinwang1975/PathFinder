package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

    @Test
    public void defaultState() {
        Node node = new Node();
        assertTrue(node.isOpen());
        assertFalse(node.isVisited());
        assertFalse(node.isSelected());
        assertEquals(0, node.getCost());
        assertEquals(0, node.getHeuristic());
        assertNull(node.getPredecessor());
        assertTrue(node.getEdges().isEmpty());
    }

    @Test
    public void openClosedTransitions() {
        Node node = new Node();
        node.setOpen(false);
        assertFalse(node.isOpen());
        node.setOpen(true);
        assertTrue(node.isOpen());
    }

    @Test
    public void visitedTransitions() {
        Node node = new Node();
        node.setVisited(true);
        assertTrue(node.isVisited());
        node.setVisited(false);
        assertFalse(node.isVisited());
    }

    @Test
    public void selectedTransitions() {
        Node node = new Node();
        node.setSelected(true);
        assertTrue(node.isSelected());
        node.setSelected(false);
        assertFalse(node.isSelected());
    }

    @Test
    public void predecessorTracking() {
        Node node = new Node();
        Node pred = new Node();
        node.setPredecessor(pred);
        assertSame(pred, node.getPredecessor());
        node.setPredecessor(null);
        assertNull(node.getPredecessor());
    }

    @Test
    public void costAndHeuristic() {
        Node node = new Node();
        node.setCost(42);
        assertEquals(42, node.getCost());
        node.setHeuristic(58);
        assertEquals(58, node.getHeuristic());
    }

    @Test
    public void addEdge() {
        Node nodeA = new Node();
        Node nodeB = new Node();
        Edge edge = new Edge();
        edge.setNodeA(nodeA);
        edge.setNodeB(nodeB);
        assertTrue(nodeA.addEdge(edge));
        assertEquals(1, nodeA.getEdges().size());
        assertSame(edge, nodeA.getEdges().iterator().next());
    }

    @Test
    public void removeEdge() {
        Node nodeA = new Node();
        Node nodeB = new Node();
        Edge edge = new Edge();
        edge.setNodeA(nodeA);
        edge.setNodeB(nodeB);
        nodeA.addEdge(edge);
        assertTrue(nodeA.removeEdge(edge));
        assertTrue(nodeA.getEdges().isEmpty());
    }

    @Test
    public void resetRestoresDefaults() {
        Node node = new Node();
        node.setOpen(false);
        node.setVisited(true);
        node.setSelected(true);
        node.setCost(100);
        node.setHeuristic(50);
        node.setPredecessor(new Node());

        node.reset();

        assertTrue(node.isOpen());
        assertFalse(node.isVisited());
        assertFalse(node.isSelected());
        assertEquals(0, node.getCost());
        assertNull(node.getPredecessor());
    }

    @Test
    public void toStringContainsCostAndHeuristic() {
        Node node = new Node();
        node.setCost(10);
        node.setHeuristic(5);
        String str = node.toString();
        assertTrue(str.contains("10"));
        assertTrue(str.contains("5"));
    }
}
