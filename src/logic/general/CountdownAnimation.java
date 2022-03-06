package logic.general;

import biuoop.DrawSurface;
import logic.graphics.Animation;
import logic.graphics.Sprite;
import logic.graphics.SpriteCollection;

import java.awt.Color;

/**
 *  PauseScreen.
 *
 *  This is a class which describes a countdown animation.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class CountdownAnimation implements Animation {
    private final SpriteCollection sprites;
    private final double timePerFrame;
    private final int countFrom;
    private final long startingTime;
    private boolean stop;
    private final Sprite background;

    /**
     * Constructor.
     * @param numOfSeconds number of seconds of counting down
     * @param countFrom the number we count down from
     * @param gameScreen the game screen
     * @param background the background
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen, Sprite background) {
        this.sprites = gameScreen;
        this.timePerFrame = (numOfSeconds / countFrom) * 1000;
        this.countFrom = countFrom;
        this.startingTime = System.currentTimeMillis();
        this.stop = false;
        this.background = background;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        // draws background
        background.drawOn(d);
        // draws sprites
        sprites.drawAllOn(d);
        // calculates the number to print and prints it
        long timeNow = System.currentTimeMillis();
        long timePassedSinceStart = timeNow - startingTime;
        int numberToPrint = countFrom - (int) (timePassedSinceStart / timePerFrame);
        if (numberToPrint == 0) {
            // if we reached 0 -> stop
            this.stop = true;
        } else {
            // else, if hasn't reached 0 yet -> present the number
            String str = "Game starts at " + numberToPrint;
            d.setColor(Color.WHITE);
            d.drawText(100, d.getHeight() / 2, str, 32);
        }

    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
