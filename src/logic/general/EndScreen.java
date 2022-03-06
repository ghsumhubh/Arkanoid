package logic.general;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import logic.graphics.Animation;
/**
 *  PauseScreen.
 *
 *  This is a class which describes a pause screen.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class EndScreen implements Animation {
    private final KeyboardSensor keyboardSensor;
    private final boolean stop;
    private final boolean hasWon;
    private final int score;

    /**
     * Constructor.
     * @param k a keyboard sensor.
     * @param hasWon true if won the game
     * @param score  the score of the player.
     */
    public EndScreen(KeyboardSensor k, boolean hasWon, int score) {
        this.keyboardSensor = k;
        this.stop = false;
        this.hasWon = hasWon;
        this.score = score;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        if (hasWon) {
            // if won -> winning screen
            d.drawText(200, d.getHeight() / 2, "You Win! Your score is " + score, 32);
        } else {
            // else, if lost -> losing screen
            d.drawText(200, d.getHeight() / 2, "Game Over. Your score is " + score, 32);
        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
