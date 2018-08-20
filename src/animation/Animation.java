package animation;
import biuoop.DrawSurface;

/**
 * @author shaytzir
 *
 */
public interface Animation {
    /**
     *
     * @param d drawing surface to draw on
     * @param dt seconds passed since last call
     */
   void doOneFrame(DrawSurface d, double dt);
   /**
    *
    * @return tells if the animation should stop or not
    */
   boolean shouldStop();
}