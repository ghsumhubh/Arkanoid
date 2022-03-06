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
public class Level3 implements LevelInformation {
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
    public Level3() {
        ballCount = 2;
        setVelocity();
        paddleSpeed = 6;
        paddleWidth = 120;
        createPattern();
        levelName = "Green 3";
        background = new Level3Background();
    }

    /**
     * Sets the velocity array.
     */
    private void setVelocity() {
        ballVelocity = new ArrayList<>();
        ballVelocity.add(new Velocity(3.0000001, -3));
        ballVelocity.add(new Velocity(-3.0000001, -3));
    }

    /**
     * Creates the block pattern.
     */
    private void createPattern() {
        // makes a counter for balls added
        int ballsAdded = 0;
        // makes a new list for balls
        blocks = new ArrayList<>();
        // makes a new random
        Random rnd = new Random();
        // loops over lines
        for (int i = 5; i < 10; i++) {
            // for each line pick a random RBG color
            float r = rnd.nextFloat();
            float g = rnd.nextFloat();
            float b = rnd.nextFloat();
            Color color = new Color(r, g, b);
            for (int j = 2; j < Constants.BLOCKS_PER_ROW - i + 1; j++) {
                // for each cell in line make a rectangle and add it
                Point upperLeft = new Point((Constants.BLOCKS_PER_ROW - j) * Constants.BLOCK_LENGTH,
                        i * Constants.BLOCK_HEIGHT);
                Block block = new Block(upperLeft, Constants.BLOCK_LENGTH, Constants.BLOCK_HEIGHT, color);
                blocks.add(block);
                // update counter
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
