package logic.general;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import logic.graphics.Animation;

/**
 *  PauseScreen.
 *
 *  This is a decorator class which adds the "waiting-for-key-press" functionality.
 *  @author Ido Tziony.
 *          ID:206534299
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean shouldStop;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     * @param sensor a keyboard sensor
     * @param key a key that we wait for
     * @param animation the animationn
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.shouldStop = false;
        isAlreadyPressed = true;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        // first we do one frame from of animation
        animation.doOneFrame(d);
        // then we stop it if the key is pressed
        if (this.sensor.isPressed(key) && !isAlreadyPressed) {
            this.shouldStop = true;
        } else {
            isAlreadyPressed = false;
        }
    }
    @Override
    public boolean shouldStop() {
        return shouldStop;
    }


}
