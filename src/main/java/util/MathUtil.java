package util;

public class MathUtil {

	private MathUtil() {}

	/**
	 * Returns the direction from point (x1,y1) to point (x2,y2).
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the direction in radians
	 */
	public static double direction(double x1, double y1, double x2, double y2) {
		double xdiff = x2 - x1;
		double ydiff = y2 - y1;
		return Math.atan2(ydiff, xdiff);
	}

	/**
	 * Returns the distance between point (x1,y1) and point (x2,y2).
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the distance
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		double xdiff = x2 - x1;
		double ydiff = y2 - y1;
		return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
	}

	/**
	 * Check if line (x1,y1-x2,y2) and line (x3,y3-x4,y4) intersect with each other.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return true if the two lines intersects, false otherwise
	 */
	public static boolean isIntersected(double x1, double y1, 
										double x2, double y2,
										double x3, double y3, 
										double x4, double y4) 
	{
		return findIntersection(x1, y1, x2, y2, x3, y3, x4, y4) != null;
	}

	/**
	 * Returns the intersection (x,y) of line (x1,y1-x2,y2) and line (x3,y3-x4,y4).
	 * Returns null if the two lines don't intersect with each other.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return the coordinate of the intersection point
	 */
	public static double[] findIntersection(double x1, double y1, 
											double x2, double y2,
											double x3, double y3, 
											double x4, double y4) 
	{
		double den = (y4 - y3)*(x2 - x1) - (x4 - x3)*(y2 - y1);
		if (den != 0) {
			double ua = ((x4 - x3)*(y1 - y3) - (y4 - y3)*(x1 - x3)) / den;
			double ub = ((x2 - x1)*(y1 - y3) - (y2 - y1)*(x1 - x3)) / den;
			if (ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1) {
				double intersection[] = new double[2];
				intersection[0] = x1 + ua*(x2 - x1); // intersection point - x
				intersection[1] = y1 + ua*(y2 - y1); // intersection point - y
				return intersection;
			}
		}
		return null;
	}

	/**
	 * Find the coordinate at a direction and distance from the point (x,y). 
	 * 
	 * @param x
	 * @param y
	 * @param direction in radians
	 * @param distance
	 * @return the coordinate
	 */
	public static double[] coordinate(double x, double y, double direction, double distance) {
		double coordinate[] = new double[2];
		double xoffset = distance * Math.cos(direction);
		double yoffset = distance * Math.sin(direction);
		coordinate[0] = x + xoffset;
		coordinate[1] = y + yoffset;
		return coordinate;
	}

	/**
	 * Normalize an angle to the range of [0, 180].
	 * @param angle in degrees
	 * @return the normalized angle in degrees
	 */
	public static double normalizeAngle180(double angle) {
		double theta = angle % 360;
		if (theta < 0) theta += 360;
		if (theta > 180) theta = 360 - theta;
		return theta;
	}
	
}
