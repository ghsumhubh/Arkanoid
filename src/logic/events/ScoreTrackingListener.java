package logic.events;
// dependencies
import logic.general.Counter;
import logic.geometry.Ball;
import logic.geometry.Block;

/**
 *  ScoreTrackingListener.
 *
 *  This is a class which describes a score tracking listener.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class ScoreTrackingListener implements  HitListener {
    private final Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter a counter of the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
    @Override
    public void added() {
    }

}
