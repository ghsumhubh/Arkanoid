package logic.geometry.base;
// dependencies
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Random;

/**
 *  Line.
 *
 *  This is a class which describes a line and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Line {
    // start and end of the line
    private Point start;
    private Point end;
    // used for double equality checks
    static final double EPSILON = Math.pow(10, -10);

    /**
     * Normal constructor for Line using 2 Points.
     * @param start the starting point of the line
     * @param end the ending point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * A constructor for Line using 2 coordinates given as 4 doubles.
     * @param x1 x coordinate of 1st Point.
     * @param y1 y coordinate of 1st Point.
     * @param x2 x coordinate of 2nd Point.
     * @param y2 y coordinate of 2nd Point.
     */
    public  Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of a line.
     * @return the length of a line.
     */
    public double length() {
       return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     * @return middle point of the line.
     */
    public Point middle() {
        double avgX = (this.start.getX() + this.end.getX()) / 2;
        double avgY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(avgX, avgY);
    }

    /**
     * Returns the starting point of the line.
     * @return the starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the ending point of the line.
     * @return the ending point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks if a line is vertical.
     * @return true: a line is vertical, false otherwise
     */
    private boolean isVertical() {
        if (this.start.getX() == this.end.getX()) {
            return true;
        }
        return false;
    }

    /**
     * returns the slope of the line.
     * @return the slope of the line in the form y =mx +a
     */
    private double getSlope() {
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * Gives the a to the equation: y = mx + a for that line.
     * @return the a for the equation y = mx + a for the line
     */
    private double getA() {
        // y= mx +a <-> a = y-mx
        return this.start.getY() - this.getSlope() * this.start.getX();
    }

    /**
     * Checks if a given point is inside the line segment.
     * @param a point
     * @return true- point is in line segment, false otherwise.
     */
    public boolean isInLine(Point a) {
        double maxX = Math.max(this.start.getX(), this.end.getX());
        double maxY = Math.max(this.start.getY(), this.end.getY());
        double minX = Math.min(this.start.getX(), this.end.getX());
        double minY = Math.min(this.start.getY(), this.end.getY());
        boolean inRange = (minX <= a.getX()) && (a.getX() <= maxX) && (minY <= a.getY()) && (a.getY() <= maxY);
        if (!inRange) {
            return false;
        }
        /* Now assuming x,y>0 we can say the point is on the line if it satisfies this:
         *  Distance from this.start -> point + point->this.end = this.start->this.end
         */
        double length = this.length();
        double distance = start.distance(a) + a.distance(this.end);
        if (Math.abs(length - distance) < EPSILON) {
            return true;
        }
        return false;
    }

    /**
     * Checks the orientation of 3 points going a->b->c.
     * @param a first point
     * @param b second point
     * @param c third point
     * @return returns 1 = clockwise orientation, -1 = counterclockwise orientation, 0= collinear.
     */
    private int orientation(Point a, Point b, Point c) {
        double res = (b.getY() - a.getY()) * (c.getX() - b.getX()) - (c.getY() - b.getY()) * (b.getX() - a.getX());
        if (res > 0) {
            return 1;
        } else if (res < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * This checks if 2 lines are intersecting.
     * @param other other line
     * @return returns true if both intersect, false if they don't, and null otherwise.
     */
    public boolean isIntersecting(Line other) {
        int o1 = orientation(this.start, this.end, other.start());
        int o2 = orientation(this.start, this.end, other.end());
        int o3 = orientation(other.start(), other.end(), this.start);
        int o4 = orientation(other.start(), other.end(), this.end);

        //general case:
        if (o1 != o2 && o3 != o4) {
            return true;
        }
        /* Special cases:
         * If the lines are collinear: check if the 3rd point is in the line
         */
        if (o1 == 0 && this.isInLine(other.start())) {
            return true;
        }
        if (o2 == 0 && this.isInLine(other.end())) {
            return true;
        }
        if (o3 == 0 && other.isInLine(this.start)) {
            return true;
        }
        if (o4 == 0 && other.isInLine(this.end)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if 2 lines intersect while the line calling this is vertical.
     * and the line "other" is not vertical
     * @param other a non-vertical line
     * @return the intersection point
     */
    private Point verticalIntersection(Line other) {
        // we know the m for y = mx + a for "other"
        double slope = other.getSlope();
        // y = mx + a -> a = y- mx, therefore:
        double a = other.start().getY() - slope * other.start().getX();
        // we now know "other" = slope(X)+ a so we plug x = any point from this line
        double intersectionX = this.start.getX();
        double intersectionY = slope * this.start.getX() + a;
        return new Point(intersectionX, intersectionY);

    }

    /**
     * This is the usual case for intersection.
     * 2 Lines that form an X
     * @param other other line
     * @return the intersection point
     */
    private Point usualIntersection(Line other) {
        if (this.isVertical()) {
            return this.verticalIntersection(other);
        }
        if (other.isVertical()) {
            return other.verticalIntersection(this);
        }
        /* we got here if no line is vertical which means we can represent them both
         * as y= mx+a
         */
        double intersectionX = (other.getA() - this.getA()) / (this.getSlope() - other.getSlope());
        double intersectionY = this.getSlope() * intersectionX + this.getA();
        return new Point(intersectionX, intersectionY);

    }

    /**
     * Works similar to previous one, but here we output the intersection Point.
     * @param other other line
     * @return the intersection point with both lines, null if doesn't exist.
     */
    public Point intersectionWith(Line other) {
        /*
         * There are 4 possibilities:
         * 1) Both lines vertical
         * 2) 1 Line is vertical
         * 3) None are vertical
         *
         * We take care of 1-> if orientation =0 in the special case
         * 2 + 3 -> we take care in the general case -> Which will form an X always
         */
        int o1 = orientation(this.start, this.end, other.start());
        int o2 = orientation(this.start, this.end, other.end());
        int o3 = orientation(other.start(), other.end(), this.start);
        int o4 = orientation(other.start(), other.end(), this.end);

        /*
         * general case:
         * In this case the lines will always look like this:
         * X
         */
        if (o1 != o2 && o3 != o4) {
           return usualIntersection(other);
        }
        /* Special cases:
         * If the lines are collinear: check if the 3rd point is in the line
         * If so: return the point.
         */
        if (o1 == 0 && this.isInLine(other.start())) {
            return other.start();
        }
        if (o2 == 0 && this.isInLine(other.end())) {
            return other.end();
        }
        if (o3 == 0 && other.isInLine(this.start)) {
            return this.start;
        }
            return this.end;
    }

    /**
     * Checks if 2 lines are the same.
     * @param other other line
     * @return true if both lines are the same.
     */
    public boolean equals(Line other) {
        if (this.start.equals(other.start()) && this.end.equals(other.end())) {
            return true;
        }
        if (this.start.equals(other.end()) && this.end.equals(other.start())) {
            return true;
        }
        return false;
    }

    /**
     * Generates a new like withing the boundaries given.
     * @param width width of window
     * @param height height of window
     * @return a new linen within those boundaries
     */
    public static Line generateRandomLine(int width, int height) {
        Random rnd = new Random();
        double x1 = rnd.nextDouble() * width;
        double x2 = rnd.nextDouble() * width;
        double y1 = rnd.nextDouble() * height;
        double y2 = rnd.nextDouble() * height;
        return new Line(x1, y1, x2, y2);
    }

    /**
     * Draws a line on a surface.
     * @param line the line
     * @param surface the draw surface
     */
    public static void drawLine(Line line, DrawSurface surface) {
        surface.setColor(Color.black);
        int x1 = (int) line.start().getX();
        int y1 = (int) line.start().getY();
        int x2 = (int) line.end().getX();
        int y2 = (int) line.end().getY();
        surface.drawLine(x1, y1, x2, y2);
    }

    /**
     * Gets the closest intersection to the start of the line given a rectangle.
     * @param rect the rectangle.
     * @return the closest intersection point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // make nan ew list
       java.util.List<Point> list =  rect.intersectionPoints(this);
       // initialize to max value
       double minimumDistance = Double.MAX_VALUE;
       Point closestIntersection = new Point();
       // iterates through the list
       for (Point point: list) {
           // if found a closer point -> this is our closest for now
           if (point.distance(this.start) < minimumDistance) {
               minimumDistance = point.distance((this.start));
               closestIntersection = point;
           }
       }
       // if we didn't iterate at all-> hence we are still at Double.MAX_VALUE-> return null
       if (minimumDistance == Double.MAX_VALUE) {
           return null;
       }
       // return the closest intersection
       return closestIntersection;
    }
}