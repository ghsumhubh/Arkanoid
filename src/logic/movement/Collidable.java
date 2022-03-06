package logic.movement;
// dependencies
import logic.general.GameLevel;
import logic.geometry.Ball;
import logic.geometry.base.Point;
import logic.geometry.base.Rectangle;

/**
 *  Collidable.
 *
 *  This is an interface for objects affected by collision.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return the collision shape of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param collisionPoint The point of collision
     * @param currentVelocity the current velocity of the object.
     * @param hitter  the hitting ball
     * @return a new velocity based on the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Adds the colldiable to the game.
     * @param g the game
     */
    void addToGame(GameLevel g);
    /**
     * Removes the colldiable from the game.
     * @param g the game
     */
    void removeFromGame(GameLevel g);
}
