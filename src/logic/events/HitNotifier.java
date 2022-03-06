package logic.events;
/**
 *  HitNotifier.
 *
 *  This is an interface for notifiers for hit event.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl a listener
     */
    void addHitListener(HitListener hl);
    /**
     * Add removes hl from the list of listeners to hit events.
     * @param hl a listener
     */
    void removeHitListener(HitListener hl);
}
