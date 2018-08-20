package arkanoid;

import coregame.Counter;
import coregame.HitListener;

/**
 * @author shaytzir
 *
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * constructor.
     * @param g a specific game level
     * @param counter the counter of balls in the game
     */
    public BallRemover(GameLevel g, Counter counter) {
        this.gameLevel = g;
        this.remainingBalls = counter;
    }

    /**
     * removes the ball from the given game level, and decreases the balls number.
     * @param beingHit a block that was hit
     * @param hitter the ball that hit "being hit"
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBalls.decrease(1);
        hitter.removeFromGame(this.gameLevel);
        return;
    }
}