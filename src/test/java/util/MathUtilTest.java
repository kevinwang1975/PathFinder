package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathUtilTest {

    private static final double DELTA = 0.0001;

    @Test
    public void directionEast() {
        double dir = MathUtil.direction(0, 0, 10, 0);
        assertEquals(0, dir, DELTA);
    }

    @Test
    public void directionNorth() {
        double dir = MathUtil.direction(0, 0, 0, -10);
        assertEquals(-Math.PI / 2, dir, DELTA);
    }

    @Test
    public void distanceHorizontal() {
        assertEquals(10, MathUtil.distance(0, 0, 10, 0), DELTA);
    }

    @Test
    public void distanceVertical() {
        assertEquals(5, MathUtil.distance(0, 0, 0, 5), DELTA);
    }

    @Test
    public void distanceDiagonal() {
        assertEquals(Math.sqrt(2), MathUtil.distance(0, 0, 1, 1), DELTA);
    }

    @Test
    public void distanceZero() {
        assertEquals(0, MathUtil.distance(3, 4, 3, 4), DELTA);
    }

    @Test
    public void coordinateEast() {
        double[] coord = MathUtil.coordinate(0, 0, 0, 10);
        assertEquals(10, coord[0], DELTA);
        assertEquals(0, coord[1], DELTA);
    }

    @Test
    public void coordinateNorth() {
        double[] coord = MathUtil.coordinate(0, 0, -Math.PI / 2, 5);
        assertEquals(0, coord[0], DELTA);
        assertEquals(-5, coord[1], DELTA);
    }

    @Test
    public void linesIntersect() {
        assertTrue(MathUtil.isIntersected(0, 0, 10, 10, 0, 10, 10, 0));
    }

    @Test
    public void linesDoNotIntersect() {
        assertFalse(MathUtil.isIntersected(0, 0, 5, 0, 0, 5, 5, 5));
    }

    @Test
    public void findIntersectionReturnsCorrectPoint() {
        double[] pt = MathUtil.findIntersection(0, 0, 10, 10, 0, 10, 10, 0);
        assertNotNull(pt);
        assertEquals(5, pt[0], DELTA);
        assertEquals(5, pt[1], DELTA);
    }

    @Test
    public void findIntersectionParallelLinesReturnsNull() {
        double[] pt = MathUtil.findIntersection(0, 0, 10, 0, 0, 5, 10, 5);
        assertNull(pt);
    }

    @Test
    public void normalizeAngle180() {
        assertEquals(0, MathUtil.normalizeAngle180(0), DELTA);
        assertEquals(90, MathUtil.normalizeAngle180(90), DELTA);
        assertEquals(180, MathUtil.normalizeAngle180(180), DELTA);
        assertEquals(90, MathUtil.normalizeAngle180(270), DELTA);
        assertEquals(30, MathUtil.normalizeAngle180(330), DELTA);
        assertEquals(10, MathUtil.normalizeAngle180(370), DELTA);
        assertEquals(10, MathUtil.normalizeAngle180(-10), DELTA);
        assertEquals(30, MathUtil.normalizeAngle180(-330), DELTA);
    }
}
