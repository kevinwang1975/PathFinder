package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class EdgeTest {

    @Test
    public void setAndGetNodes() {
        Node a = new Node();
        Node b = new Node();
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);
        assertSame(a, edge.getNodeA());
        assertSame(b, edge.getNodeB());
    }

    @Test
    public void getOppositeNodeA() {
        Node a = new Node();
        Node b = new Node();
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);
        assertSame(b, edge.getOpposite(a));
    }

    @Test
    public void getOppositeNodeB() {
        Node a = new Node();
        Node b = new Node();
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);
        assertSame(a, edge.getOpposite(b));
    }

    @Test
    public void getOppositeUnknownNodeReturnsNull() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);
        assertNull(edge.getOpposite(c));
    }

    @Test
    public void getOppositeNullNodeReturnsNull() {
        Node a = new Node();
        Node b = new Node();
        Edge edge = new Edge();
        edge.setNodeA(a);
        edge.setNodeB(b);
        assertNull(edge.getOpposite(null));
    }

    @Test
    public void weightGetterAndSetter() {
        Edge edge = new Edge();
        edge.setWeight(42);
        assertEquals(42, edge.getWeight());
        edge.setWeight(0);
        assertEquals(0, edge.getWeight());
    }

    @Test
    public void acceptINodeAsParameter() {
        Node a = new Node();
        Edge edge = new Edge();
        edge.setNodeA(a);
        assertSame(a, edge.getNodeA());
    }
}
