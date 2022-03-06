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
public class Level1 implements LevelInformation {
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
    public Level1() {
        ballCount = 1;
        setVelocity();
        paddleSpeed = 6;
        paddleWidth = 120;
        createPattern();
        levelName = "Direct Hit";
        background = new Level1Background();
    }

    /**
     * Sets the velocity array.
     */
    private void setVelocity() {
        ballVelocity = new ArrayList<>();
        ballVelocity.add(new Velocity(0, -0.7));
    }

    /**
     * Creates the block pattern.
     */
    private void createPattern() {
        // make blocks array
        blocks = new ArrayList<>();
        // set special block size
        int blockSize = Constants.WIDTH / 31;
        // make the block
        Point upperLeft = new Point(blockSize * 15.5, Constants.HEIGHT * 0.3);
        Color color = Color.WHITE;
        Block block = new Block(upperLeft, blockSize, blockSize, color);
        // add to game
        blocks.add(block);
        // update number of blocks to remove
        blocksToRemove = 1;
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
