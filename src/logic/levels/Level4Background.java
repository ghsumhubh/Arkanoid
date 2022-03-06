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
public class Level4Background implements Sprite {
    /**
     * Constructor.
     */
    public Level4Background() {
    }
    @Override
    public void drawOn(DrawSurface d) {
        // draws the background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // makes a new random
        Random rnd = new Random();
        while (true) {
            // gets a random position + color
            int xPos = rnd.nextInt(Constants.WIDTH);
            int yPos = rnd.nextInt(Constants.HEIGHT);
            float r = rnd.nextFloat();
            float b = rnd.nextFloat();
            float g = rnd.nextFloat();
            Color color = new Color(r, b, g);
            d.setColor(color);
            // gets a string from the selection below
            String str = "";
            switch (rnd.nextInt(5)) {
                case 0:
                    str = "YOU WERE SUPPOSED TO BE THE CHOSEN ONE";
                    break;
                case 1:
                    str = "WHY DID YOU DO THIS TO US";
                    break;
                case 2:
                    str = "MURDERER";
                    break;
                case 3:
                    str = "HELP US";
                    break;
                case 4:
                    str = "WHY DO YOU KEEP REMOVING US";
                    break;
                default:
                    break;
            }
            // prints the "warning" message
            d.drawText(xPos, yPos, str, 10);
            // 10% chance to stop after printing it.
            if (rnd.nextInt(10) == 0) {
                break;
            }

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

