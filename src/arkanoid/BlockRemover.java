package arkanoid;

import coregame.Counter;
import coregame.HitListener;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 * @author shaytzir
 *
 */
public class BlockRemover implements HitListener {
   private GameLevel gameLevel;
   private Counter remainingBlocks;

   /**
    * constructor.
    * @param gameLevel a specific game level
    * @param removedBlocks the counter of current blocks in the game
    */
   public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
       this.gameLevel = gameLevel;
       this.remainingBlocks = removedBlocks;
   }

   /**
    * Blocks that are hit and reach 0 hit-points should be removed,  removes this listener from the block.
    * @param beingHit a block being hit.
    * @param hitter the ball that hit the block
    */
   public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getStartHits() - beingHit.getCurrentHits() == 0) {
           beingHit.removeHitListener(this);
           beingHit.removeFromGame(this.gameLevel);
           this.remainingBlocks.decrease(1);
       }
       return;
   }
}