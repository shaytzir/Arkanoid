package arkanoid;

import coregame.Counter;
import coregame.HitListener;

/**
 * @author shaytzir
 *
 */
public class ScoreTrackingListener implements HitListener {
       private Counter currentScore;

       /**
        * constructor.
        * @param scoreCounter a counter for the score
        */
       public ScoreTrackingListener(Counter scoreCounter) {
          this.currentScore = scoreCounter;
       }

       /**
        * if the block is being hit add 5 points, if block is destroyed add 10.
        * @param beingHit a block being hit
        * @param hitter a ball hitting the block
        */
       public void hitEvent(Block beingHit, Ball hitter) {
           int hitsLeft = beingHit.getStartHits() - beingHit.getCurrentHits();
           if (hitsLeft > 0)  {
               this.currentScore.increase(5);
           } else if (hitsLeft == 0) {
               this.currentScore.increase(10);
           }
           return;
       }
}