package menu;

import animation.Animation;
import animation.AnimationRunner;

/**
 * showing highscore task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     *
     * @param runner an anumation runner
     * @param highScoresAnimation the high score animation to run
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * the run is to show and run the highscore.
     * @return none
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}