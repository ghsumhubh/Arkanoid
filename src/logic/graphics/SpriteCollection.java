package logic.graphics;
// dependencies
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *  SpriteCollection.
 *
 *  This is a class which describes a SpriteCollection and has several line related methods.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class SpriteCollection {
    // a list of Sprites
    private final java.util.List<Sprite> sprites;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Adds a sprite to the collection.
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Removes a sprite from the collection.
     * @param s a sprite
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }
    /**
     * Notifies all sprites that a tick happened.
     */
    public void notifyAllTimePassed() {
        // the reason for copy is, we don't want to iterate while deleting items from the list.
        java.util.List<Sprite> spritesCopy = new LinkedList<>();
        spritesCopy.addAll(sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }
    /**
     * Draws the sprites on a DrawSurface.
     * @param d a draw surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}
