package logic.events;
// dependencies
import logic.general.Counter;
import logic.general.GameLevel;
import logic.geometry.Ball;
import logic.geometry.Block;

/**
 *  BlockRemover.
 *
 *  This is a class which describes a ball remover and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class BallRemover implements  HitListener {
    private final GameLevel game;
    private final Counter remainingBalls;

    /**
     * Constructor.
     * @param game game instance.
     * @param remainingBalls the amount of balls left.
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // removes ball from game
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
    }
    @Override
    public void added() {
    }

}
