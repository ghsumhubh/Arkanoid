package logic.movement;
// dependencies
import logic.geometry.base.Line;
import logic.geometry.base.Point;
import java.util.ArrayList;

/**
 *  GameEnvironment.
 *
 *  This is a class which describes a collection of collidables inside the game.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class GameEnvironment {
    // a list of collidables
    private final java.util.List<Collidable> collidables;

    /**
     * Constructor for GameEnvironment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a collidable to GameEnvironment.
     * @param c a collidable.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Removes a collidable from GameEnvironment.
     * @param c a collidable.
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }
    /**
     * Gives the first collision of a line in the environment.
     * @param trajectory the line.
     * @return the first collision of a line.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // make a list of points and collidables
        java.util.List<Point> collisionPoints = new ArrayList<>();
        java.util.List<Collidable> collidablesList = new ArrayList<>();
        Point pt;
        // for each collidable: if collides with line -> add to both lists
        for (Collidable col : collidables) {
            pt = trajectory.closestIntersectionToStartOfLine(col.getCollisionRectangle());
            // if intersects: add to both lists
            if (pt != null) {
                collisionPoints.add(pt);
                collidablesList.add(col);
            }
        }
        // now we need to check which is the closest out of the ones we got
        Point closestPoint = new Point();
        // set minDistance to max value
        double minDistance = Double.MAX_VALUE;
        // get the closest point from trajectory.start()
        for (Point point : collisionPoints) {
            if (trajectory.start().distance(point) < minDistance) {
                minDistance = trajectory.start().distance(point);
                closestPoint = point;
            }
        }
        // if no points of intersection
        if (minDistance == Double.MAX_VALUE) {
            return null;
        }
        // Gets the closest collidable -> trivial from the way we made the lists (same order)
        Collidable closestCL = collidablesList.get(collisionPoints.indexOf(closestPoint));
        return new CollisionInfo(closestPoint, closestCL);
    }
}
