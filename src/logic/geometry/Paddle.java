package logic.geometry;
// dependencies
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import logic.general.Constants;
import logic.general.GameLevel;
import logic.geometry.base.Line;
import logic.geometry.base.Point;
import logic.geometry.base.Rectangle;
import logic.graphics.Sprite;
import logic.movement.Collidable;
import logic.movement.Velocity;

import java.awt.Color;

/**
 *  Paddle.
 *
 *  This is a class which describes a Paddle and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboardSensor;
    private Point topLeft;
    private int width;
    private int height;
    private Color color;
    private double yCoordinate;
    private double lowestXCoordinate;
    private double highestXCoordinate;
    private int speed;


    /**
     * Constructor.
     * @param keyboardSensor the keyboard sensor
     * @param width the width of the paddle
     * @param height the height of the paddle
     * @param color  the color of the paddle
     * @param yCoordinate the y coordinate of the paddle
     * @param lowestXCoordinate the lowest x coordinate top left of the paddle can reach
     * @param speed the speed of the paddle
     */
    public Paddle(KeyboardSensor keyboardSensor,
                  int width,
                  int height,
                  Color color,
                  double yCoordinate,
                  double lowestXCoordinate,
                  int speed) {
        this.keyboardSensor = keyboardSensor;
        this.width = width;
        this.height = height;
        this.color = color;
        this.yCoordinate = yCoordinate;
        this.lowestXCoordinate = lowestXCoordinate;
        this.highestXCoordinate =  Constants.WIDTH - lowestXCoordinate - width;
        this.topLeft = new Point((highestXCoordinate + lowestXCoordinate) / 2, yCoordinate);
        this.speed = speed;
    }
    /**
     * Makes the paddle move left.
     */
    public void moveLeft() {
        double newX = topLeft.getX() - this.speed;
        // if out of boundaries
        if (newX < lowestXCoordinate) {
            newX = lowestXCoordinate;
        }
        topLeft = new Point(newX, topLeft.getY());
    }
    /**
     * Makes the paddle move Right.
     */
    public void moveRight() {
        double newX = topLeft.getX() + this.speed;
        // if out of boundaries
        if (newX > highestXCoordinate) {
            newX = highestXCoordinate;
        }
        topLeft = new Point(newX, topLeft.getY());
    }

    /**
     * Notifies the Paddle about the time tick.
     */
    public void timePassed() {
        if (keyboardSensor.isPressed("a")) {
            this.moveLeft();
        }
        if (keyboardSensor.isPressed("d")) {
            this.moveRight();
        }
    }

    /**
     * Draws the paddle.
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) topLeft.getX(), (int) topLeft.getY(), width,  height);
        d.setColor(Color.BLACK);
        d.drawRectangle((int) topLeft.getX(), (int) topLeft.getY(), width,  height);

    }

    /**
     * gets the collision rectangle.
     * @return the collision rectangle.
     */
    public Rectangle getCollisionRectangle() {
        Rectangle rectangle = new Rectangle(topLeft, width, height);
        return rectangle;
    }

    /**
     * Notifies the paddle that a collision has occurred and returns a new velocity.
     * @param collisionPoint The point of collision
     * @param currentVelocity the current velocity of the object.
     * @param hitter hitter ball.
     * @return a new velocity for the hitting object.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // set up points of block
        Point tLeft = this.topLeft;
        Point tRight = new Point(this.topLeft.getX() + this.width, this.topLeft.getY());
        Point bRight = new Point(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
        Point bLeft = new Point(this.topLeft.getX(), this.topLeft.getY() + this.height);
        double segmentLength = (tRight.getX() - tLeft.getX()) / 5;
        // points t2-t5 are between the top left and top right, in this order:
        // tLeft t2 t3 t4 t5 tRight
        Point t2 = new Point(tLeft.getX() + segmentLength, tLeft.getY());
        Point t3 = new Point(tLeft.getX() + 2 * segmentLength, tLeft.getY());
        Point t4 = new Point(tLeft.getX() + 3 *  segmentLength, tLeft.getY());
        Point t5 = new Point(tLeft.getX() + 4 *  segmentLength, tLeft.getY());

        // set up the lines
        Line[] lines = new Line[8];
        // the 5 zones on top
        lines[0] = new Line(tLeft, t2);
        lines[1] = new Line(t2, t3);
        lines[2] = new Line(t3, t4);
        lines[3] = new Line(t4, t5);
        lines[4] = new Line(t5, tRight);
        // the sides
        lines[5] = new Line(tLeft, bLeft);
        lines[6] = new Line(tRight, bRight);
        //below
        lines[7] = new Line(bLeft, bRight);
        // iterate and check which line it hit
        for (int i = 0; i < 8; i++) {
            if (lines[i].isInLine(collisionPoint)) {
                // if sides
                if (i == 5 || i == 6) {
                    currentVelocity.reverseDx();
                    return currentVelocity;
                } else if (i == 7) {
                    // if bottom
                    currentVelocity.reverseDy();
                    return  currentVelocity;
                }
                // We got here if we hit from top from one of the zones
                double speed2 = currentVelocity.getSpeed();
                double angle;
                switch (i) {
                    case 0:
                        angle = 60;
                        break;
                    case 1:
                        angle = 30;
                        break;
                    case 2:
                        Velocity sendVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
                        sendVelocity.reverseDy();
                        return sendVelocity;
                    case 3:
                        angle = -30;
                        break;
                    case 4:
                        angle = -60;
                        break;
                    default:
                        angle = 0;
                        System.out.print("Error!, Should not be here\n");
                        break;
                }
                Velocity newVelocity = Velocity.fromAngleAndSpeed(angle, speed2);
                return newVelocity;
            }
        }
        // we shouldn't be here logically
        return null;
    }
    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    @Override
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

}
