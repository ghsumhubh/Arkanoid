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
public class BlockRemover implements  HitListener {
    private final GameLevel game;
    private final Counter remainingBlocks;
    private final Counter removedBlocks;

    /**
     * Constructor.
     * @param game game instance.
     * @param removedBlocks amount of blocks removed before.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        remainingBlocks = new Counter();
        this.removedBlocks = removedBlocks;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // removes listener
        beingHit.removeHitListener(this);
        // removes from game
        beingHit.removeFromGame(game);
        // updates counters
        remainingBlocks.decrease(1);
        removedBlocks.increase(1);
    }

    /**
     * Returns the amount of blocks left.
     * @return the amount of blocks left.
     */
    public int getRemainingBlocks() {
        return remainingBlocks.getValue();
    }
    @Override
    public void added() {
        remainingBlocks.increase(1);
    }
}
