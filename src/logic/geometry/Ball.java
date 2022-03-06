package logic.geometry;
// dependencies
import biuoop.DrawSurface;
import logic.general.GameLevel;
import logic.graphics.Sprite;
import logic.movement.CollisionInfo;
import logic.movement.GameEnvironment;
import logic.movement.Velocity;
import logic.geometry.base.Line;
import logic.geometry.base.Point;

import java.awt.Color;
/**
 *  Ball.
 *
 *  This is a class which describes a ball and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Ball implements Sprite {
    // vars
    private Point point;
    private int size;
    private Color clr;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;
    private Point[] history;
    // constants
    private static final int MOVE_HISTORY_LENGTH = 3;
    static final double MAX_POSSIBLE_SPEED = 1000;
    private static final double COLLISION_MOVE_BACK_FACTOR = 1 / MAX_POSSIBLE_SPEED;

    // a few constructors (Will finalize those once more information is given)
    /**
     * A constructor for Ball.
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, Color color) {
        this.point = center;
        this.size = r;
        this.clr = color;
        this.velocity = new Velocity(0, 0);
        history = new Point[MOVE_HISTORY_LENGTH];
    }

    /**
     * A constructor for Ball.
     * @param center   the center point of the ball
     * @param r        the radius of the ball
     * @param color    the color of the ball
     * @param velocity the velocity of the ball
     */
    public Ball(Point center, int r, Color color, Velocity velocity) {
        this.point = center;
        this.size = r;
        this.clr = color;
        this.velocity = velocity;
        history = new Point[MOVE_HISTORY_LENGTH];
    }
    /**
     * A constructor for Ball.
     * @param center   the center point of the ball
     * @param r        the radius of the ball
     * @param color    the color of the ball
     * @param velocity the velocity of the ball
     * @param gameEnvironment the game environment.
     */
    public Ball(Point center, int r, Color color, Velocity velocity, GameEnvironment gameEnvironment) {
        this.point = center;
        this.size = r;
        this.clr = color;
        this.velocity = velocity;
        this.gameEnvironment = gameEnvironment;
        history = new Point[MOVE_HISTORY_LENGTH];
    }

    /**
     * A constructor for Ball.
     * @param x     the x coordinates
     * @param y     the y coordinates
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(int x, int y, int r, Color color) {
        this.point = new Point(x, y);
        this.size = r;
        this.clr = color;
        this.velocity = new Velocity(0, 0);
        history = new Point[MOVE_HISTORY_LENGTH];
    }

    /**
     * Basic get method.
     * @return x coordinate of the ball
     */
    public int getX() {
        return (int) this.point.getX();
    }

    /**
     * Basic get method.
     * @return y coordinate of the ball
     */
    public int getY() {
        return (int) this.point.getY();
    }

    /**
     * Basic get method.
     * @return size of the ball
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Basic get method.
     * @return clr color of the ball
     */
    public Color getColor() {
        return this.clr;
    }

    /**
     * Draws a circle (the ball).
     * @param surface surface on which we draw the ball on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.clr);
        surface.fillCircle(this.getX(), this.getY(), this.size);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.size);
    }

    /**
     * sets velocity.
     * @param v new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball.
     * @param dx x speed
     * @param dy y speed
     */
    public void setVelocity(double dx, double dy) {
        this.setVelocity(new Velocity(dx, dy));
    }

    /**
     * Gets the velocity.
     * @return velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moves 1 step based on the velocity and collidables in game environment.
     */
    public void moveOneStep() {
        // gets trajectory line
        double endX = this.point.getX() + this.velocity.getDx();
        double endY = this.point.getY() + this.velocity.getDy();
        Line trajectory = new Line(this.point, new Point(endX, endY));
        // calculates how much the ball needs to move in total in that step
        double totalMoveLength = trajectory.length();
        double moveLengthLeft = totalMoveLength;
        // now we will loop moving the ball and bouncing until our moveLength is 0 or we have no collisions.
        do {
            // gets the closest collision
            CollisionInfo ci = gameEnvironment.getClosestCollision(trajectory);

            // if no collision - > move normally
            if (ci == null) {
                this.point = new Point(endX, endY);
                return;
            }

            // if there is a collision-> get it
            Point collisionPoint = ci.collisionPoint();


            /* Here we want to move the ball just until the next collision, change direction and iterate
             *  while remembering the length we have left each time.
             */

            // this changes the velocity accordingly and notifies the object.
            this.velocity = ci.collisionObject().hit(this, collisionPoint, this.velocity);

            // calculate how much distance we made till collision
            Line travelLine = new Line(this.point, collisionPoint);
            double distanceTraveled = travelLine.length();

            // calculate how much distance left
            moveLengthLeft -= distanceTraveled;
            double partOfWayLeft = moveLengthLeft / totalMoveLength;

            // puts the ball in front of the point of collision (not inside it)
            double tempX = collisionPoint.getX() + COLLISION_MOVE_BACK_FACTOR * Math.signum(this.velocity.getDx());
            double tempY = collisionPoint.getY() + COLLISION_MOVE_BACK_FACTOR * Math.signum(this.velocity.getDy());
            this.point = new Point(tempX, tempY);

            // now we need to make a new trajectory with new distance left
            endX = this.point.getX() +  partOfWayLeft * this.velocity.getDx();
            endY = this.point.getY() +  partOfWayLeft * this.velocity.getDy();
            trajectory = new Line(this.point, new Point(endX, endY));
        } while (moveLengthLeft > 0);
    }

    /**
     * A function that tells how the object behaves after 1 tick.
     */
    public void timePassed() {
        // makes one step
        moveOneStep();

        // logs the history of the ball.
        for (int i = MOVE_HISTORY_LENGTH - 1; i > 0; i--) {
            history[i] = history[i - 1];
        }
        history[0] = point;

        // checks if all history is same point
        boolean isStuck = true;
        for (int i = 0; i < MOVE_HISTORY_LENGTH - 1; i++) {
            if (history[i] != history[i + 1]) {
                isStuck = false;
                break;
            }
        }
        // if the ball is in same coordinate for a few turns -> rescue the ball :)
        if (isStuck) {
            rescue();
        }
    }

    /**
     * A method that rescues a ball if last steps is same place = stuck.
     */
    public void rescue() {
        double endX = this.point.getX() - 2 * size *  this.velocity.getDx();
        double endY = this.point.getY() - 2 * size *  this.velocity.getDy();
        this.point = new Point(endX, endY);
    }
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    @Override
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }


}
