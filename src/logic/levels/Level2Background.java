package logic.levels;

import biuoop.DrawSurface;
import logic.general.GameLevel;
import logic.general.Constants;
import logic.geometry.base.Line;
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
public class Level2Background implements Sprite {
    /**
     * Constructor.
     */
    public Level2Background() {
    }
    @Override
    public void drawOn(DrawSurface d) {
        Random rnd = new Random();
        // draws background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // makes a random line that appears if small enough
        Line line =  Line.generateRandomLine(Constants.WIDTH, Constants.WIDTH);
        if (line.length() < 100) {
            d.setColor(Color.WHITE);
            d.drawLine((int) line.start().getX(), (int) line.start().getY(),
                    (int) line.end().getX(), (int) line.end().getY());
        }
        // makes the text "WHY???" (in red) appear on random places with random number of times.
        while (true) {
            int xPos = rnd.nextInt(Constants.WIDTH);
            int yPos = rnd.nextInt(Constants.HEIGHT);
            d.setColor(Color.RED);
            if (rnd.nextInt(50) == 1) {
                d.drawText(xPos, yPos, "WHY???", 10);
            }
            // it hits here 50% to stop the loop.
            if (rnd.nextBoolean()) {
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
