package logic.movement;
// dependencies
import logic.geometry.base.Point;

/**
 *  CollisionInfo.
 *
 *  This is a class which describes a collisions that have a point and an object.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * A constructor for CollisionInfo.
     * @param collisionPoint the point of collision.
     * @param collisionObject the object of collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Gets the collision point.
     * @return collision point.
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Gets the collision object.
     * @return collision object
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
