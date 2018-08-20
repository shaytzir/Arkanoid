package coregame;
/**
 * @author shaytzir
 *
 */
public interface HitNotifier {
    /**
     * add hl as a listener to hit events.
     * @param hl HitListener
     */
   void addHitListener(HitListener hl);
   /**
    * remove hl from the list of listeners to hit events.
    * @param hl HitListener
    */
   void removeHitListener(HitListener hl);
}