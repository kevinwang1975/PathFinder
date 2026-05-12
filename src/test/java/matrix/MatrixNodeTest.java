package matrix;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixNodeTest {

    @Test
    public void defaultEnabled() {
        MatrixNode node = new MatrixNode();
        assertTrue(node.isEnabled());
    }

    @Test
    public void setEnabled() {
        MatrixNode node = new MatrixNode();
        node.setEnabled(false);
        assertFalse(node.isEnabled());
        node.setEnabled(true);
        assertTrue(node.isEnabled());
    }

    @Test
    public void rowAndCol() {
        MatrixNode node = new MatrixNode();
        node.setRow(3);
        node.setCol(7);
        assertEquals(3, node.getRow());
        assertEquals(7, node.getCol());
    }

    @Test
    public void inheritsNodeBehavior() {
        MatrixNode node = new MatrixNode();
        node.setVisited(true);
        assertTrue(node.isVisited());
        node.setCost(42);
        assertEquals(42, node.getCost());
    }
}
