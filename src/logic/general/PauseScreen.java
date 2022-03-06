package logic.general;

import biuoop.DrawSurface;
import logic.graphics.Animation;
/**
 *  PauseScreen.
 *
 *  This is a class which describes a pause screen.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * Constructor.
     */
    public PauseScreen() {
        this.stop = false;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "Paused -- press SPACE to continue", 32);
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
