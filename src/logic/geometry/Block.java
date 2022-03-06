package logic.geometry;
// dependencies
import biuoop.DrawSurface;
import logic.events.HitListener;
import logic.events.HitNotifier;
import logic.general.GameLevel;
import logic.geometry.base.Line;
import logic.geometry.base.Point;
import logic.geometry.base.Rectangle;
import logic.graphics.Sprite;
import logic.movement.Collidable;
import logic.movement.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  Block.
 *
 *  This is a class which describes a block and has several line related methods.
 *  Note: I decided to make block flexible and therefore not necessarily a square.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Block implements Collidable, Sprite, HitNotifier {
    // vars
    private Point upperLeft;
    private double width;
    private double height;
    private Color color;
    private Velocity velocity;
    private boolean hasOutline;
    private boolean needsToBeRemoved;
    private java.util.List<HitListener> hitListeners;
    private boolean visible;
    // constants
    private static final Color DEFAULT_COLOR = Color.black;
    private static final Color OUTLINE_COLOR = Color.black;



    /**
     * Constructor for Block.
     * @param upperLeft top left point of block
     * @param width width of block
     * @param height height of block
     */
    public Block(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = DEFAULT_COLOR;
        this.velocity = new Velocity(0, 0);
        this.hasOutline = true;
        this.hitListeners = new LinkedList<>();
        this.visible = true;
    }
    /**
     * Constructor for Block with color.
     * @param upperLeft top left point of block
     * @param width width of block
     * @param height height of block
     * @param color color of block
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.hasOutline = true;
        this.hitListeners = new LinkedList<>();
        needsToBeRemoved = false;
        this.visible = true;
    }

    /**
     * Constructor for Block with color and speed.
     * @param upperLeft top left point of block
     * @param width width of block
     * @param height height of block
     * @param color color of block
     * @param velocity  velocity of block
     */
    public Block(Point upperLeft, double width, double height, Color color, Velocity velocity) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        this.velocity = velocity;
        this.hasOutline = false;
        this.hitListeners = new LinkedList<>();
        needsToBeRemoved = false;
        this.visible = true;
    }
    /**
     * Constructor for Block with color.
     * @param upperLeft top left point of block
     * @param width width of block
     * @param height height of block
     * @param color color of block
     * @param hasOutline if has outline
     * @param visible true if block is visible
     */
    public Block(Point upperLeft, double width, double height, Color color, boolean hasOutline, boolean visible) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.hasOutline = hasOutline;
        this.hitListeners = new LinkedList<>();
        needsToBeRemoved = false;
        this.visible = visible;
    }



    /**
     * Returns the collision rectangle.
     * @return the collision shape
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(upperLeft, width, height);
    }

    /**
     * returns the center of the block (Might be useful later).
     * @return the center of the block
     */
    public Point getCenter() {
        double midX = this.upperLeft.getX() + width / 2;
        double midY = this.upperLeft.getY() + height / 2;
        return new Point(midX, midY);
    }

    /**
     * returns the velocity after impact at collisionPoint.
     * @param collisionPoint the point of collision
     * @param currentVelocity the velocity before
     * @param hitter the hiting ball
     * @return a new velocity after impact
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        notifyHit(hitter);
        // set up points of block
        Velocity tempVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        Point topLeft = this.upperLeft;
        Point topRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point bottomRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        Point bottomLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        // set up the lines
        Line[] lines = new Line[4];
        // top line
        lines[0] = new Line(topLeft, topRight);
        // bottom line
        lines[1] = new Line(bottomRight, bottomLeft);
        // right line
        lines[2] = new Line(topRight, bottomRight);
        // left line
        lines[3] = new Line(bottomLeft, topLeft);
        // iterate and check which line it hit
        for (int i = 0; i < 4; i++) {
            if (lines[i].isInLine(collisionPoint)) {
                // if top / bottom -> change Dy
                if (i == 0 || i == 1) {
                    tempVelocity.reverseDy();
                } else {
                    // if right / left -> change Dx
                    tempVelocity.reverseDx();
                }
                // send new velocity
                return tempVelocity;
            }
        }
        // shouldn't arrive here anyways because we already come here if we know we have a collision
        return null;
    }

    /**
     * Draws the block on a DrawSurface.
     * @param surface the DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        // for now since we dont have textures, each block is going to have an outline
        surface.setColor(this.color);
        int topLeftX = (int) this.upperLeft.getX();
        int topLeftY = (int) this.upperLeft.getY();
        int intWidth = (int) this.width;
        int intHeight = (int) this.height;
        surface.fillRectangle(topLeftX, topLeftY, intWidth, intHeight);
        if (hasOutline) {
            surface.setColor(OUTLINE_COLOR);
            surface.drawRectangle(topLeftX, topLeftY, intWidth, intHeight);
        }

    }

    @Override
    public void timePassed() {

    }

    @Override
    public void addToGame(GameLevel g) {
        if (visible) {
            g.addSprite(this);
        }
        g.addCollidable(this);
    }
    @Override
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all listeners when ball hits.
     * @param hitter the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // make copy of hit listeners
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // notify all listeners about hit
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

}
