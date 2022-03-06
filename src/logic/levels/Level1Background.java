package logic.levels;

import biuoop.DrawSurface;
import logic.general.GameLevel;
import logic.general.Constants;
import logic.graphics.Sprite;

import java.awt.Color;
import java.util.Random;

/**
 *  Level1Background.
 *
 *  This is a class for the level 1 background.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class Level1Background implements Sprite {
    /**
     * Constructor.
     */
    public Level1Background() {
    }
    @Override
    public void drawOn(DrawSurface d) {
        Random rnd = new Random();
        if (rnd.nextInt(90) != 1) {
            // normally what would be drawn
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, Constants.WIDTH, Constants.HEIGHT);
        } else {
            // albeit there's a small chance of this appearing
            d.setColor(Color.GREEN);
            d.fillRectangle(0, 0, Constants.WIDTH, Constants.HEIGHT);
            d.setColor(Color.RED);
            d.drawText(350, 250, "HELP ME", 20);
        }

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
