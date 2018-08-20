package arkanoid;
import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * @author shaytzir
 *
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * constructor.
     * @param k a keyboard sensor
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        //setting the first should stop to be false
        this.stop = false;
    }
    /**
     * draws the pause message on screen.
     * @param d a drawing surface
     * @param dt - seconds passed since last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }
    /**
     * @return the state of should stop.
     */
    public boolean shouldStop() {
         return this.stop;
    }
}