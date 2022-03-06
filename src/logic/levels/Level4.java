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
import java.util.Random;

/**
 *  Level1.
 *
 *  This is a class that describes the first level of our game.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Level4 implements LevelInformation {
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
    public Level4() {
        ballCount = 4;
        setVelocity();
        paddleSpeed = 8;
        paddleWidth = 120;
        createPattern();
        levelName = "Final Four";
        background = new Level4Background();
    }

    /**
     * Sets the velocity array.
     */
    private void setVelocity() {
        ballVelocity = new ArrayList<>();
        ballVelocity.add(Velocity.fromAngleAndSpeed(10, 4.5));
        ballVelocity.add(Velocity.fromAngleAndSpeed(-10, 4.5));
        ballVelocity.add(Velocity.fromAngleAndSpeed(40, 4.5));
        // note: added 4th ball for symmetry, but this can easily be changed if needed for 3 balls.
        ballVelocity.add(Velocity.fromAngleAndSpeed(-40, 4.5));



    }

    /**
     * Creates the block pattern.
     */
    private void createPattern() {
        // a counter for balls added
        int ballsAdded = 0;
        // makes a new list of blocks
        blocks = new ArrayList<>();
        // makes a new random
        Random rnd = new Random();
        for (int i = 3; i < 10; i++) {
            // for each line generate a random color.
            float r = rnd.nextFloat();
            float g = rnd.nextFloat();
            float b = rnd.nextFloat();
            Color color = new Color(r, g, b);
            for (int j = 2; j < Constants.BLOCKS_PER_ROW; j++) {
                // for each block in line -> make it and add to game
                Point upperLeft = new Point((Constants.BLOCKS_PER_ROW - j) * Constants.BLOCK_LENGTH,
                        i * Constants.BLOCK_HEIGHT);
                Block block = new Block(upperLeft, Constants.BLOCK_LENGTH, Constants.BLOCK_HEIGHT, color);
                blocks.add(block);
                // update the counter
                ballsAdded++;
            }
            // update blocksToRemove
            blocksToRemove = ballsAdded;
        }
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
