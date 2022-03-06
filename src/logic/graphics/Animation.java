package logic.graphics;
import biuoop.DrawSurface;

/**
 *  Sprite.
 *
 *  This is an interface for things that have an animation
 *  @author Ido Tziony.
 *          ID:206534299
 */
public interface Animation {
    /**
     * Draws one frame of the object on DrawSurface d.
     * @param d a drawsurface.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Returns true if animation should stop.
     * @return true if animationn should stop.
     */
    boolean shouldStop();



}
