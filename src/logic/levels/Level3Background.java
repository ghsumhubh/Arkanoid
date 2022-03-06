package logic.levels;

import biuoop.DrawSurface;
import logic.general.GameLevel;
import logic.general.Constants;
import logic.geometry.base.Point;
import logic.graphics.Sprite;
import java.awt.Color;

/**
 *  Level1Background.
 *
 *  This is a class for the level 1 background.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Level3Background implements Sprite {
    private Point smileyLocation;
    /**
     * Constructor.
     */
    public Level3Background() {
        smileyLocation = new Point(400, 400);
    }
    @Override
    public void drawOn(DrawSurface d) {
        // draw background
        d.setColor(Color.GREEN);
        d.fillRectangle(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // draw an ascii smiley (liked it since looks odd)
        d.setColor(Color.BLACK);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY(), "     .-\"\"\"\"\"\"-.", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 20, "   .'          '.", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 40, "  /  O     O  \\", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 60, " :                :", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 80, " |                |   happy", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 100, " : ',          ,' :", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 120, "  \\  '-......-'  /", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 140, "   '.          .'", 20);
        d.drawText((int) smileyLocation.getX(), (int) smileyLocation.getY() + 160, "     '-......-'", 20);
    }
    @Override
    public void timePassed() {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}
