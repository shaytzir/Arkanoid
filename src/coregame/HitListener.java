package coregame;

import arkanoid.Ball;
import arkanoid.Block;

/**
 * @author shaytzir
 *
 */
public interface HitListener {
    /**
     * this method is called whenever the beinghit object is hit.
     * @param beingHit an object being hit by the hitter
     * @param hitter the ball that hits the "beingHit"
     */
    void hitEvent(Block beingHit, Ball hitter);
}