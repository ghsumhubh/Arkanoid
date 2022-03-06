package logic.general;

import logic.geometry.Block;
import logic.graphics.Sprite;
import logic.movement.Velocity;

import java.util.List;
/**
 *  LevelInformation.
 *
 *  This is an interface for describing levels.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public interface LevelInformation {
    /**
     * Returns the number of balls in the game level.
     * @return the number of balls in the game level.
     */
    int numberOfBalls();

    /**
     * Returns a list of velocities of all balls.
     * @return a list of velocities of all balls.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the paddle speed.
     * @return the paddle speed.
     */
    int paddleSpeed();

    /**
     * Returns the paddle width.
     * @return the paddle width.
     */
    int paddleWidth();

    /**
     * Returns the name of the level.
     * @return the name of the level.
     */
    String levelName();

    /**
     * Returns the background of the level.
     * @return the background of the level.
     */
    Sprite getBackground();

    /**
     * Returns the blocks that will be added to the level. (Only removable!)
     * @return the blocks that will be added to the level. (Only removable!)
     */
    List<Block> blocks();

    /**
     * Returns the number of blocks that should be removed before a level is cleared.
     * @return the number of blocks that should be removed before a level is cleared.
     */
    int numberOfBlocksToRemove();


}
