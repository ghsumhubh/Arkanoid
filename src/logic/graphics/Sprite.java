package logic.graphics;
// dependencies
import biuoop.DrawSurface;
import logic.general.GameLevel;

/**
 *  Sprite.
 *
 *  This is an interface for objects that have sprites
 *  @author Ido Tziony.
 *          ID:206534299
 */
public interface Sprite {
    /**
     * Draws the sprite on the screen.
     * @param d a draw surface.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed.
     */
    void timePassed();
    /**
     * Adds the sprite to the game.
     * @param g the game
     */
    void addToGame(GameLevel g);
    /**
     * Removes the sprite from the game.
     * @param g the game
     */
    void removeFromGame(GameLevel g);


}
