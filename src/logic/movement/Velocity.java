package logic.movement;
// dependencies
import logic.geometry.base.Point;
/**
 *  Velocity.
 *
 *  This is a class that describes velocity.
 *
 * @author Ido Tziony.
 *         ID:206534299
 */
public class Velocity {
    // vars
    private double dx;
    private double dy;

    /**
     * Constructor for velocity.
     * @param dx change in x.
     * @param dy change in y.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * gets dx.
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * gets dy.
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * gets the speed of the velocity.
     * @return the speed of the velocity.
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
    /**
     * sets dx.
     * @param dx1 change in x pos.
     */
    public void setDx(double dx1) {
        this.dx = dx1;
    }
    /**
     * sets dx.
     * @param dy1  change in y pos.
     */
    public void setDy(double dy1) {
        this.dy = dy1;
    }

    /**
     * Reverts the Dx speed.
     */
    public void reverseDx() {
        this.dx = -this.dx;
    }
    /**
     * Reverts the Dy speed.
     */
    public void reverseDy() {
        this.dy = -this.dy;
    }

    /**
     * Returns the new point location after velocity calculation.
     * @param p point.
     * @return new point of the position after velocity is applied.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Returns a velocity in terms of dx, dy from angle + speed.
     * @param angle angle of the velocity.
     * @param speed speed of the velocity.
     * @return a velocity using those values.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // translates radians + sets angle = 0 to up
        angle = ((angle  / 180) - 1) * Math.PI;
        double dx = speed * Math.sin(angle);
        double dy = speed * Math.cos(angle);
        return new Velocity(dx, dy);
    }
}
