package logic.general;

// dependencies
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import logic.events.BallRemover;
import logic.events.BlockRemover;
import logic.events.HitListener;
import logic.events.ScoreTrackingListener;
import logic.geometry.Ball;
import logic.geometry.Block;
import logic.geometry.Paddle;
import logic.geometry.base.Point;
import logic.movement.Collidable;
import logic.movement.GameEnvironment;
import logic.movement.Velocity;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import logic.graphics.Animation;
import logic.graphics.AnimationRunner;
import logic.graphics.Sprite;
import logic.graphics.SpriteCollection;
import logic.graphics.ScoreIndicator;
import logic.graphics.LevelNameIndicator;
/**
 *  GameLevel.
 *
 *  This is a class which describes a Game level and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class GameLevel implements Animation {
    // vars
    private boolean running;
    private final AnimationRunner runner;
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final GUI gui;
    private final KeyboardSensor keyboardSensor;
    // Counters
    private final Counter createdBlocks;
    private final Counter blocksRemovedFromBlockRemover;
    // note: createdBlocks includes all types of blocks (even non-breakable)
    private final Counter score;
    private final Counter remainingBalls;
    // Listeners
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private ScoreTrackingListener scoreTrackingListener;

    // more info
    private final int  numberOfBalls;
    private final List<Velocity> initialBallVelocity;
    private final int paddleWidth;
    private final int paddleSpeed;
    private final String levelName;
    private final Sprite background;
    private final List<Block> removableBlocks;
    private final int blocksToRemove;
    private boolean won;


    // constants for Balls
    private static final int BALL_RADIUS = 6;
    private static final Color BALL_COLOR = Color.WHITE;
    private static final int BALL_SPAWN_X = Constants.WIDTH / 2;
    private static final int BALL_SPAWN_Y = (2 * Constants.HEIGHT) / 3;

    // constants for paddle
    private static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_Y_COORDINATE = 500;
    private static final Color PADDLE_COLOR = Color.red;

    // constants for boundaries
    private static final Color BOUNDARIES_COLOR = Color.DARK_GRAY;

    // constants for ScoreIndicator
    private static final int INDICATOR_X1_POS = 0;
    private static final int INDICATOR_X2_POS = Constants.WIDTH;
    private static final int INDICATOR_Y1_POS = 0;
    private static final int INDICATOR_Y2_POS = 20;

    /**
     * Constructor.
     * @param levelInformation the specific level info.
     * @param gui the gui window
     * @param animationRunner the animation runner
     * @param keyboardSensor the keyboard sensor.
     * @param score the score (even from prev levels)
     */
    public GameLevel(LevelInformation levelInformation, GUI gui,
                     AnimationRunner animationRunner, KeyboardSensor keyboardSensor, int score) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.createdBlocks = new Counter();
        this.score = new Counter();
        this.score.increase(score);
        this.remainingBalls = new Counter();
        this.blocksRemovedFromBlockRemover = new Counter();
        this.running = true;
        this.gui = gui;
        this.keyboardSensor = keyboardSensor;
        this.runner = animationRunner;

        this.initialBallVelocity = levelInformation.initialBallVelocities();
        this.numberOfBalls = levelInformation.numberOfBalls();
        this.paddleWidth = levelInformation.paddleWidth();
        this.paddleSpeed = levelInformation.paddleSpeed();
        this.levelName = levelInformation.levelName();
        this.background = levelInformation.getBackground();
        this.removableBlocks = levelInformation.blocks();
        this.blocksToRemove = levelInformation.numberOfBlocksToRemove();
        this.won = false;

    }

    /**
     * Initializes the listeners.
     */
    private void initializeListeners() {
        this.blockRemover = new BlockRemover(this, blocksRemovedFromBlockRemover);
        this.ballRemover = new BallRemover(this, remainingBalls);
        this.scoreTrackingListener = new ScoreTrackingListener(score);
    }
    /**
     * Adds a collidable to GameEnvironment.
     * @param c a colldiable
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }
    /**
     * Adds a sprite to sprites.
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }
    /**
     * Removes a collidable from GameEnvironment.
     * @param c a colldiable
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }
    /**
     * Removes a sprite from sprites.
     * @param s a sprite
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }
    /**
     * Initializes the game.
     */
    public void initialize() {

        // init listeners
        initializeListeners();
        // paddle creation
        addPaddle();
        // Balls creation
        addBalls();
        // Boundaries creation
        addBoundaries();
        // Block pattern creation
        addBlockPattern();
        // scoreIndicator creation
        createScoreIndicator();
        // nameIndicator creation
        createNameIndicator();
    }
    /**
     * runs the animation loop.
     */
    public void run() {
        // run countdown
        this.runner.run(new CountdownAnimation(2, 3, this.sprites, background));
        // run level animation
        this.running = true;
        this.runner.run(this);
    }
    /**
     * Adds a block Row to the game in row "row".
     * fills from start (x coordinate) to end (x coordinate)
     * @param row the row to fill
     * @param start the starting block in that row
     * @param end the ending block in that row
     * @param color the color of the row
     * @param hasOutline if has outline
     * @param hitListeners list of all hit listeners
     * @param visible true if blocks are visible
     */
    public void addBlockRow(int row, int start, int end, Color color,
                            boolean hasOutline, java.util.List<HitListener> hitListeners, boolean visible) {
        for (int i = start; i <= end; i++) {
            double yPos = row * Constants.BLOCK_HEIGHT;
            double xPos = i * Constants.BLOCK_LENGTH;
            Point pt = new Point(xPos, yPos);
            Block block = new Block(pt, Constants.BLOCK_LENGTH, Constants.BLOCK_HEIGHT, color, hasOutline, visible);
            for (HitListener hl : hitListeners) {
                // add listener
                block.addHitListener(hl);
                // notify the listener it has been added
                hl.added();
            }
            // inc block counter.
            this.createdBlocks.increase(1);
            // add block to game
            block.addToGame(this);
        }
    }
    /**
     * Adds a block column to the game in column "column".
     * fills from start (y coordinate) to end (y coordinate)
     * @param column the column to fill
     * @param start the starting block in that column
     * @param end the ending block in that column
     * @param color the color of the column
     * @param hasOutline if has outline
     * @param hitListeners list of all hit listeners
     * @param visible true if blocks are visible
     */
    public void addBlockColumn(int column, int start, int end,
                               Color color, boolean hasOutline,
                               java.util.List<HitListener> hitListeners, boolean visible) {
        for (int i = start; i <= end; i++) {
            double xPos = column * Constants.BLOCK_LENGTH;
            double yPos = i * Constants.BLOCK_HEIGHT;
            Point pt = new Point(xPos, yPos);
            Block block = new Block(pt, Constants.BLOCK_LENGTH, Constants.BLOCK_HEIGHT, color, hasOutline, visible);
            for (HitListener hl : hitListeners) {
                // adds listener
                block.addHitListener(hl);
                // informs listener it has been added
                hl.added();
            }
            // inc block counter
            this.createdBlocks.increase(1);
            // add block to game
            block.addToGame(this);
        }
    }

    /**
     * Draws the background .
     * @param d the surface we draw the background on.
     */
    private void drawBackground(DrawSurface d) {
        background.drawOn(d);
    }

    /**
     * adds a paddle to the game.
     */
    private void addPaddle() {
        Paddle paddle = new Paddle(gui.getKeyboardSensor(),
                paddleWidth,
                PADDLE_HEIGHT,
                PADDLE_COLOR,
                PADDLE_Y_COORDINATE,
                Constants.BLOCK_LENGTH,
                paddleSpeed);
        paddle.addToGame(this);
    }
    /**
     * Adds some balls to the game.
     */
    private void addBalls() {
        // gets point of spawn
        Point ballStartingPoint = new Point(BALL_SPAWN_X, BALL_SPAWN_Y);
        for (int i = 0; i < numberOfBalls; i++) {
            // sets velocity
            Velocity velocity = initialBallVelocity.get(i);
            // makes a ball
            Ball ball1 = new Ball(ballStartingPoint, BALL_RADIUS, BALL_COLOR, velocity, environment);
            // adds to game
            ball1.addToGame(this);
            // inc ball count
            this.remainingBalls.increase(1);
        }
    }

    /**
     * Adds boundaries to the game. (In reality adds 4 blocks)
     */
    private void addBoundaries() {
        // empty list of listeners -> for normal walls
        List<HitListener> noListeners = new LinkedList<>();
        // add list with remover listeners -> for "death" walls
        List<HitListener> remover = new LinkedList<>();
        remover.add(ballRemover);
        // top boundary -> normal
        addBlockRow(0, 0, Constants.BLOCKS_PER_ROW - 1, BOUNDARIES_COLOR,
                false, noListeners, true);
        // bottom boundary -> "death wall"
        addBlockRow(Constants.BLOCKS_PER_COLUMN - 1,  0,
                Constants.BLOCKS_PER_ROW - 1, Color.BLACK, false, remover, false);
        // left boundary -> normal
        addBlockColumn(0, 0, Constants.BLOCKS_PER_ROW - 1,
                BOUNDARIES_COLOR, false, noListeners, true);
        // right boundary -> normal
        addBlockColumn(Constants.BLOCKS_PER_ROW - 1, 0,
                Constants.BLOCKS_PER_ROW - 1, BOUNDARIES_COLOR, false, noListeners, true);
    }

    /**
     * Adds the pattern of blocks requested to the game.
     */
    private void addBlockPattern() {
        for (Block block : removableBlocks) {
            block.addHitListener(this.scoreTrackingListener);
            this.scoreTrackingListener.added();
            block.addHitListener(this.blockRemover);
            blockRemover.added();
            block.addToGame(this);
        }

    }


    /**
     * Creates the score indicator.
     */
    private void createScoreIndicator() {
        // gets coordinates for the indicator
        Point topLeft = new Point(INDICATOR_X1_POS - 250, INDICATOR_Y1_POS);
        Point bottomRight = new Point(INDICATOR_X2_POS - 250, INDICATOR_Y2_POS);
        // makes the indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(score, topLeft, bottomRight, Color.WHITE, Color.BLACK);
        // adds it to game
        scoreIndicator.addToGame(this);
    }
    /**
     * Creates the name indicator.
     */
    private void createNameIndicator() {
        // gets coordinates for the indicator
        Point topLeft = new Point(INDICATOR_X1_POS + 250, INDICATOR_Y1_POS);
        Point bottomRight = new Point(INDICATOR_X2_POS + 250, INDICATOR_Y2_POS);
        // makes the indicator
        LevelNameIndicator levelNameIndicator = new LevelNameIndicator(levelName, topLeft,
                bottomRight, Color.WHITE, Color.BLACK);
        // adds it to game
        levelNameIndicator.addToGame(this);
    }

    /**
     * The win sequence.
     */
    private void win() {
        this.won = true;
        score.increase(100);
    }
    /**
     * The loss sequence.
     */
    private void lose() {
    }
    /**
     * returns true if game won or lost, and checks for it.
     * @return true - if game won or lost, false otherwise
     */
    private boolean wonOrLost() {
        // if game won
        if (blocksRemovedFromBlockRemover.getValue() >= this.blocksToRemove) {
            win();
            return true;
        }
        // if game lost
        if (remainingBalls.getValue() == 0) {
            lose();
            return true;
        }
        // if game continues
        return false;
    }

    /**
     * Returns true if player won.
     * @return true if player won.
     */
    public boolean hasWon() {
        return won;
    }
    /**
     * Checks if game needs to stop.
     * @return true if game needs to stop.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Does one frame on DrawSurface d.
     * @param d the DrawSurface
     */
    public void doOneFrame(DrawSurface d) {
        if (this.keyboardSensor.isPressed("p")) {
            // if pressed "p" -> pause
            Animation pauseScreen = new KeyPressStoppableAnimation(keyboardSensor,
                    KeyboardSensor.SPACE_KEY, new PauseScreen());
            this.runner.run(pauseScreen);
        }
        if (wonOrLost()) {
            // if won / lost -> stop animation
            this.running = false;
        } else {
            // draw everything and update sprites
            drawBackground(d);
            sprites.drawAllOn(d);
            sprites.notifyAllTimePassed();
        }
    }

    /**
     * Gets the score.
     * @return the score.
     */
    public int getScore() {
        return score.getValue();
    }


}
