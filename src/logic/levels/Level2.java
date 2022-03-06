package logic.levels;

import logic.general.LevelInformation;
import logic.geometry.Block;
import logic.geometry.base.Point;
import logic.graphics.Sprite;
import logic.movement.Velocity;
import logic.general.Constants;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *  Level1.
 *
 *  This is a class that describes the first level of our game.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Level2 implements LevelInformation {
    private static int ballCount;
    private static List<Velocity> ballVelocity;
    private static int paddleSpeed;
    private static int paddleWidth;
    private static String levelName;
    private static Sprite background;
    private static List<Block> blocks;
    private static int blocksToRemove;
    /**
     * Constructor.
     */
    public Level2() {
        ballCount = 10;
        setVelocity();
        paddleSpeed = 4;
        paddleWidth = (int) (Constants.WIDTH * 0.75);
        createPattern();
        levelName = "Wide Easy";
        background = new Level2Background();
    }

    /**
     * Sets the velocity array.
     */
    private void setVelocity() {
        // make 10 diff velocities appearing as a semi-circle when balls move.
        double angle = 260;
        ballVelocity = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            angle += (float) 180 / ballCount;
            ballVelocity.add(Velocity.fromAngleAndSpeed(angle, 4));
        }
    }

    /**
     * Creates the block pattern.
     */
    private void createPattern() {
        blocks = new ArrayList<>();
        for (int i = 1; i < Constants.BLOCKS_PER_ROW - 1; i++) {
            // make a yellow line
            Point upperLeft = new Point(i * Constants.BLOCK_LENGTH, 5 * Constants.BLOCK_HEIGHT);
            Color color = Color.YELLOW;
            Block block = new Block(upperLeft, Constants.BLOCK_LENGTH, Constants.BLOCK_HEIGHT, color);
            blocks.add(block);
        }
        blocksToRemove = Constants.BLOCKS_PER_ROW - 2;

    }
    @Override
    public int numberOfBalls() {
        return ballCount;
    }
    @Override
    public List<Velocity> initialBallVelocities() {
        return ballVelocity;
    }
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }
    @Override
    public String levelName() {
        return levelName;
    }
    @Override
    public Sprite getBackground() {
        return background;
    }
    @Override
    public List<Block> blocks() {
        return blocks;
    }
    @Override
    public int numberOfBlocksToRemove() {
        return blocksToRemove;
    }
}
