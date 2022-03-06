import biuoop.GUI;
import biuoop.KeyboardSensor;
import logic.levels.Level1;
import logic.levels.Level2;
import logic.levels.Level3;
import logic.levels.Level4;
import logic.general.GameFlow;
import logic.general.Constants;
import logic.general.LevelInformation;
import logic.graphics.AnimationRunner;
import java.util.ArrayList;
import java.util.List;


public class Ass6Game {
    /**
     * This is the main function which will start the game.
     * @param args string arguments
     */
    public static void main(String[] args) {
        // makes GUI
        GUI gui = new GUI("Arkanoid Game", Constants.WIDTH, Constants.HEIGHT);
        // gets Keyboard sensor
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();
        // makes an animation runner
        AnimationRunner runner = new AnimationRunner(gui, Constants.FRAMES_PER_SECOND);
        // initializes the level list
        List<LevelInformation> levels = new ArrayList<>();
        if (args.length == 0) {
            // default levels if no arguments
            addDefaultLevels(levels);
        } else {
            // levels per the strings
           addLevels(levels, args);
            }
        if (levels.isEmpty()) {
            // if string has no levels -> default levels.
            addDefaultLevels(levels);
            }
        // make a new gameflow
        GameFlow gameFlow = new GameFlow(levels, gui, runner, keyboardSensor);
        // run
        gameFlow.runLevels();
    }

    /**
     * Adds default levels to level list.
     * @param levels the level list.
     */
    private static void addDefaultLevels(List<LevelInformation> levels) {
        levels.add(new Level1());
        levels.add(new Level2());
        levels.add(new Level3());
        levels.add(new Level4());
    }

    /**
     * Adds specific levels to level list.
     * @param levels level list.
     * @param strings the string containing the list of levels.
     */
    private static void addLevels(List<LevelInformation> levels, String[] strings) {
        for (String str : strings) {
            switch (str) {
                case "1":
                    levels.add(new Level1());
                    break;
                case "2":
                    levels.add(new Level2());
                    break;
                case "3":
                    levels.add(new Level3());
                    break;
                case "4":
                    levels.add(new Level4());
                    break;
                default:
                    break;
            }
        }
    }
}
