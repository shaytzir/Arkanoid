package animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author shaytzir
 *
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;

    /**
     * constructor.
     * @param gui a GUI to draw the animation on
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        //setting as requested
        this.framesPerSecond = 60;
    }

    /**
     * running the animation.
     * @param animation the animation we want to run
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        Sleeper sleeper = new Sleeper();
        //as long as it should happen
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            double dt = 1.0 / this.framesPerSecond;

            //show one frame of the wanted animation on the GUI
            animation.doOneFrame(d, dt);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
               sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}