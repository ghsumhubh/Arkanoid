package logic.graphics;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 *  AnimationRunner.
 *
 *  This is a class for running animations.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructor.
     * @param gui a gui.
     * @param framesPerSecond fps.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * Runs the animation loop on the Animation object.
     * @param animation an animation object.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            // calculates time rn
            long startTime = System.currentTimeMillis();
            // draws one frame
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);
            // checks how much time we took and matches to FPS needed.
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

    }
}
