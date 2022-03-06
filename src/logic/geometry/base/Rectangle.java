package logic.geometry.base;
// dependencies
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
/**
 *  Rectangle.
 *
 *  This is a class which describes a rectangle.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Rectangle {
    // coordinates of the rectangle
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    // some nice ways to store positions as integers will be used in isPointInRectangle()
    public static final  int IN_RECTANGLE = 0;
    public static final  int ABOVE_RECTANGLE = 1;
    public static final  int BELOW_RECTANGLE = 2;
    public static final  int RIGHT_TO_RECTANGLE = 3;
    public static final  int LEFT_TO_RECTANGLE = 4;
    public static final  int TOP_RIGHT_OF_RECTANGLE = 5;
    public static final  int TOP_LEFT_OF_RECTANGLE = 6;
    public static final  int BOTTOM_RIGHT_OF_RECTANGLE = 7;
    public static final  int BOTTOM_LEFT_OF_RECTANGLE = 8;


    /**
     * Constructor for Rectangle using 4 coordinates.
     * @param x1 x1 coordinate
     * @param x2 x2 coordinate
     * @param y1 y1 coordinate
     * @param y2 y2 coordinate
     *           We assume: x1 < x2 and y1 < y2
     */
    public Rectangle(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    /**
     * Constructor for Rectangle using 2 points.
     * @param pt1 Point 1
     * @param pt2 Point 2
     */
    public Rectangle(Point pt1, Point pt2) {
        this.x1 = (int) pt1.getX();
        this.x2 = (int) pt2.getX();
        this.y1 = (int) pt1.getY();
        this.y2 = (int) pt2.getY();
    }

    /**
     * Constructor for Rectangle using top left point, width and height.
     * @param upperLeft the top left point
     * @param width width of rectangle
     * @param height height of rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.x1 = upperLeft.getX();
        this.y1 = upperLeft.getY();
        this.x2 = this.x1 + width;
        this.y2 = this.y1 + height;
    }

    /**
     * Getter.
     * @return coordinate
     */
    public double getX1() {
        return x1;
    }
    /**
     * Getter.
     * @return coordinate
     */
    public double getX2() {
        return x2;
    }
    /**
     * Getter.
     * @return coordinate
     */
    public double getY1() {
        return y1;
    }
    /**
     * Getter.
     * @return coordinate
     */
    public double getY2() {
        return y2;
    }
    /**
     * Gets the width.
     * @return width
     */
    public double getWidth() {
        return this.x2 - this.x1;
    }
    /**
     * Gets the height.
     * @return height
     */
    public double getHeight() {
        return this.y2 - this.y1;
    }

    /**
     * Gets a point (x,y) and size (radius).
     * Returns: object is Above/Below/Top Right etc. as Rectangle.TOP_LEFT_OF_RECTANGLE for example.
     * NOTE: we assume x1 < x2 and y1 < y2
     * @param pt the (x,y) position
     * @param size the size of the object
     * @return position of the object in relation of the rectangle.
     */
    public int isPointInRectangle(Point pt, int size) {
        // left side
        if (pt.getX() - size < this.x1) {
            if (pt.getY() - size < this.y1) {
                return Rectangle.TOP_LEFT_OF_RECTANGLE;
            } else if (pt.getY() + size > this.y2) {
                return Rectangle.BOTTOM_LEFT_OF_RECTANGLE;
            }
            return Rectangle.LEFT_TO_RECTANGLE;
        }
        // right side
        if (pt.getX() + size > this.x2) {
            if (pt.getY() - size < this.y1) {
                return  Rectangle.TOP_RIGHT_OF_RECTANGLE;
            } else if (pt.getY() + size > this.y2) {
                return Rectangle.BOTTOM_RIGHT_OF_RECTANGLE;
            }
            return Rectangle.RIGHT_TO_RECTANGLE;
        }
        //top and bottom without any right or left to it
        if (pt.getY() - size < this.y1) {
            return Rectangle.ABOVE_RECTANGLE;
        } else if (pt.getY() + size > this.y2) {
            return Rectangle.BELOW_RECTANGLE;
        } else {
            // default-> in rectangle
            return Rectangle.IN_RECTANGLE;
        }
    }

    /**
     * Gets the top left point.
     * @return top left point
     */
    public Point getUpperLeft() {
        return new Point(x1, y1);
    }

    /**
     * Draws the rectangle.
     * @param surface surface on which we draw the ball on.
     * @param color the color we want to draw the rectangle.
    */
    public void drawOn(DrawSurface surface, Color color) {
        surface.setColor(color);
        surface.fillRectangle((int) this.x1, (int) this.y1, (int) this.getWidth(), (int) this.getHeight());
    }

    /**
     * Returns the intersection points of a line with the rectangle.
     * @param line the lines which we test
     * @return an array of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> list = new ArrayList<Point>();
        // sets up points
        Point topLeft = new Point(this.x1, this.y1);
        Point topRight = new Point(this.x2, this.y1);
        Point bottomLeft = new Point(this.x1, this.y2);
        Point bottomRight = new Point(this.x2, this.y2);
        // sets up lines
        Line[] lines = new Line[4];
        // top line
        lines[0] = new Line(topLeft, topRight);
        // right line
        lines[1] = new Line(topRight, bottomRight);
        // bottom line
        lines[2] = new Line(bottomRight, bottomLeft);
        // left line
        lines[3] = new Line(bottomLeft, topLeft);
        for (int i = 0; i < 4; i++) {
            if (lines[i].isIntersecting(line)) {
                list.add(lines[i].intersectionWith(line));
            }
        }
        return list;
    }
}
