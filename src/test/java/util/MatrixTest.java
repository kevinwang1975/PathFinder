package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

    @Test
    public void setDimension() {
        Matrix<String> matrix = new Matrix<String>();
        matrix.setDimension(3, 5);
        assertEquals(3, matrix.getRows());
        assertEquals(5, matrix.getColumns());
    }

    @Test
    public void getValueReturnsSetValue() {
        Matrix<String> matrix = new Matrix<String>();
        matrix.setDimension(2, 2);
        matrix.setValue(1, 1, "hello");
        assertEquals("hello", matrix.getValue(1, 1));
    }

    @Test
    public void defaultValueIsNull() {
        Matrix<String> matrix = new Matrix<String>();
        matrix.setDimension(2, 2);
        assertNull(matrix.getValue(0, 0));
        assertNull(matrix.getValue(1, 1));
    }

    @Test
    public void overwriteValue() {
        Matrix<String> matrix = new Matrix<String>();
        matrix.setDimension(1, 1);
        matrix.setValue(0, 0, "first");
        matrix.setValue(0, 0, "second");
        assertEquals("second", matrix.getValue(0, 0));
    }

    @Test
    public void getRowsUninitializedReturnsZero() {
        Matrix<String> matrix = new Matrix<String>();
        assertEquals(0, matrix.getRows());
        assertEquals(0, matrix.getColumns());
    }

    @Test
    public void setValueReturnsOldValue() {
        Matrix<String> matrix = new Matrix<String>();
        matrix.setDimension(1, 1);
        assertNull(matrix.setValue(0, 0, "a"));
        assertEquals("a", matrix.setValue(0, 0, "b"));
    }
}
