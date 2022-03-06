package logic.graphics;

import biuoop.DrawSurface;
import logic.general.GameLevel;
import logic.geometry.base.Point;

import java.awt.Color;

/**
 *  LevelNameIndicator.
 *
 *  This is a class which describes a level name indicator.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class LevelNameIndicator implements Sprite {
    private String name;
    private Color backgroundColor;
    private Color textColor;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    /**
     * Constructor.
     * @param name the name of the level.
     * @param topLeft  top left pos of the score.
     * @param bottomRight  bottom right pos of the score.
     * @param backgroundColor the color of the background of score indicator.
     * @param textColor the color of the text.
     */
    public LevelNameIndicator(String name, logic.geometry.base.Point topLeft,
                              logic.geometry.base.Point bottomRight, Color backgroundColor, Color textColor) {
        this.name = name;
        this.x1 = (int) topLeft.getX();
        this.x2 = (int) bottomRight.getX();
        this.y1 = (int) topLeft.getY();
        this.y2 = (int) bottomRight.getY();
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
    @Override
    public void drawOn(DrawSurface drawSurface) {
        // gets textSize and position
        int textSize = calculateTextSize(x1, x2, y1, y2);
        logic.geometry.base.Point textPos = calculateTextPosition(x1, x2, y1, y2);
        // draw background
        drawSurface.setColor(backgroundColor);
        drawSurface.fillRectangle(x1, y1, x2 - x1, y2 - y1);
        // draw text
        drawSurface.setColor(textColor);
        drawSurface.drawText((int) textPos.getX(), (int) textPos.getY(), "Level Name: " + name, textSize);
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

    /**
     * Calculates the text size with a formula we decided on.
     * @param x1 x1
     * @param x2 x2
     * @param y1 y1
     * @param y2 y2
     * @return the required text size
     */
    private static int calculateTextSize(int x1, int x2, int y1, int y2) {
        int distanceX = x2 - x1;
        int distanceY = y2 - y1;
        distanceY *= 3;
        int minDistance = Math.min(distanceX, distanceY);
        return minDistance / 5;
    }

    /**
     * Calculates the text pos with a formula we decided on.
     * @param x1 x1
     * @param x2 x2
     * @param y1 y1
     * @param y2 y2
     * @return the position of the text.
     */
    private static logic.geometry.base.Point calculateTextPosition(int x1, int x2, int y1, int y2) {
        int y =  y1 + (int) (0.5 * (y2 - y1));
        int x =  x1 + (int) (0.44 * (x2 - x1));
        return new Point(x, y);
    }
}
