package arkanoid;
import animation.Animation;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;



/**
 * @author shaytzir
 *
 */
public class CountdownAnimation implements Animation {
    private double numOfSec;
    private int countFrom;
    private SpriteCollection gameScreen;
    private long countPerNum;

    /**
     * constructor.
     * @param numOfSeconds the total amount of seconds we want the animation to occur
     * @param countFrom a number. the animation will count from that number to 1
     * @param gameScreen all of the sprites of the game so we could draw them in the background
     */
    public CountdownAnimation(double numOfSeconds,
                                      int countFrom,
                                      SpriteCollection gameScreen) {
        this.numOfSec = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        //keeping one more member showing how much time there will be for each number (in millisecs)
        this.countPerNum = (long) ((this.numOfSec / this.countFrom) * 1000);
    }

    /**
     * the method represents one frame of this animation.
     * @param d a drawing surface
     * @param dt - seconds passed since last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //drawing all sprites in the background
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.BLACK);
        d.drawText(40 + 2, d.getHeight() / 2 + 100, "The Game Will Start In " + Integer.toString(this.countFrom), 48);
        d.drawText(40 - 2, d.getHeight() / 2 + 100, "The Game Will Start In " + Integer.toString(this.countFrom), 48);
        d.drawText(40, d.getHeight() / 2 + 102, "The Game Will Start In " + Integer.toString(this.countFrom), 48);
        d.drawText(40, d.getHeight() / 2 + 98, "The Game Will Start In " + Integer.toString(this.countFrom), 48);
        d.setColor(Color.WHITE);
        d.drawText(40, d.getHeight() / 2 + 100, "The Game Will Start In " + Integer.toString(this.countFrom), 48);
        //current time
        long start = System.currentTimeMillis();
        Sleeper sleep = new Sleeper();
        //time passed from start
        long used = System.currentTimeMillis() - start;
        long left = this.countPerNum - used;
        //if we have enough time - sleep for that time
        if (left > 0) {
            sleep.sleepFor(left);
        }
        this.countFrom--;
    }

    /**
     * @return false if there are more numbers to count, else - true
     */
    public boolean shouldStop() {
        //if we have more numbers to count - shouldnt stop
        if (this.countFrom >= 0) {
            return false;
        }
        return true;
    }
}