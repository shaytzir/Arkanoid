package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author shaytzir
 *
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyS;
    private String stopK;
    private Animation animation;
    private boolean shouldStop;
    private boolean isAlreadyPressed;
    private boolean doNothing;

    /**
     *
     * @param sensor a keyboard sensor
     * @param key a key which stops this animation running
     * @param animation an animation to run untill the wanted key is pressed
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyS = sensor;
        this.stopK = key;
        this.animation = animation;
        this.shouldStop = false;
        this.doNothing = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.isAlreadyPressed) {
            if (this.keyS.isPressed(this.stopK)) {
                this.doNothing = true;
            }
            this.isAlreadyPressed = false;
        }
        this.animation.doOneFrame(d, dt);
        if (this.keyS.isPressed(this.stopK)) {
            if (!this.doNothing) {
                this.shouldStop = true;
            }
        } else {
            this.doNothing = false;
        }
    }

    @Override
    public boolean shouldStop() {
    if ((this.shouldStop) && (this.isAlreadyPressed)) {
            this.isAlreadyPressed = true;
            this.shouldStop = false;
            return  this.shouldStop;
        } else if ((this.shouldStop) && (!this.isAlreadyPressed)) {
            this.isAlreadyPressed = true;
            return this.shouldStop;
        }
        return this.shouldStop;
    }

}
