package logic.general;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import logic.graphics.Animation;
import logic.graphics.AnimationRunner;

import java.util.List;

/**
 *  GameFlow.
 *
 *  This is a class which describes the game flow.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private GUI gui;
    private List<LevelInformation> levels;
    private int score;
    private boolean won;

    /**
     * Constructor.
     * @param levels the levels to be played.
     * @param animationRunner the animation runner
     * @param gui the gui
     * @param keyboardSensor the keyboard sensor.
     */
    public GameFlow(List<LevelInformation> levels, GUI gui,
                    AnimationRunner animationRunner, KeyboardSensor keyboardSensor) {
        this.levels = levels;
        score = 0;
        won = true;
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.keyboardSensor = keyboardSensor;
    }
    /**
     * Runs the levels.
     */
    public void runLevels() {
        for (LevelInformation levelInfo : levels) {
            // makes a new level from LevelInfo
            GameLevel level = new GameLevel(levelInfo, gui, animationRunner, keyboardSensor, score);
            // initializes + starts the level
            level.initialize();
            level.run();
            // we are here when the level ended
            if (level.hasWon()) {
                // if won level -> update score and DONT change won (default = true)
                score = level.getScore();

            } else {
                // if lost level -> update score and change won to false
                score = level.getScore();
                won = false;
                // leave the loop
                break;
            }
        }
        // make an end screen based on if won/lost + score
        EndScreen endScreen = new EndScreen(keyboardSensor, won, score);
        // decorate it with SPACE_KEY = close
        Animation finalScreen = new KeyPressStoppableAnimation(keyboardSensor, KeyboardSensor.SPACE_KEY, endScreen);
        // run the final screen.
        animationRunner.run(finalScreen);
        // close game
        gui.close();
    }
}
