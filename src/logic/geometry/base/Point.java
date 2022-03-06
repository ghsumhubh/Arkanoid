package logic.geometry.base;
/**
 *  Point.
 *
 *  This is a class that describes a 2D point and has
 *  several methods using points.
 *
 * @author Ido Tziony.
 *         ID:206534299
 */
public class Point {
    //x y coordinates
    private double x;
    private double y;

    /**
     * This is the constructor of Point.
     * @param x = x coordinates
     * @param y = y coordinates
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor for point.
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * @param other a different point from which we want to measure distance
     * @return returns the distance -> sqrt((x1-x2)^2+(y1-y2)^2)
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2));
    }

    /**
     * @param other a point which we want to see if is the same one as our point.
     * @return true: same points | false: different points
     */
    public boolean equals(Point other) {
        double epsilon = Math.pow(10, -15);
        if (((Math.abs(this.x - other.getX())) < epsilon) && (Math.abs(this.y - other.getY()) < epsilon)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the x coordinates.
     * @return x coordinates.
     */
    public double getX() {
        return this.x;
    }
    /**
     * Sets the x coordinates.
     * @param x1 x coordinate
     */
    public void setX(double x1) {
        this.x = x1;
    }
    /**
     * Gets the y coordinates.
     * @return y coordinates.
     */
    public double getY() {
        return this.y;
    }
    /**
     * Sets the Y coordinates.
     * @param y1 y coordinate
     */
    public void setY(double y1) {
        this.y = y1;
    }
}