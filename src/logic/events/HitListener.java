package logic.events;

import logic.geometry.Ball;
import logic.geometry.Block;

/**
 *  HitListener.
 *
 *  This is an interface for listeners for hit event.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public interface HitListener {
    /**
     * This method is called whenever the object is hit.
     * The hitter parameter is the ball that's doing the hitting.
     * @param beingHit the block being hit.
     * @param hitter the hitting ball
     */
    void hitEvent(Block beingHit, Ball hitter);

    /**
     * Notifies the listener it has been added.
     */
    void added();

}
