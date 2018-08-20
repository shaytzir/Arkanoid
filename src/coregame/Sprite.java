package coregame;
import arkanoid.GameLevel;
import biuoop.DrawSurface;

/**
 *
 * @author shaytzir
 *
 */
public interface Sprite {
   /**
    *
    * @param d a drawing surface to draw sprite on
    */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed.
     * @param dt seconds passed since last call
     */
    void timePassed(double dt);
    /**
     *
     * @param g a game to add the sprite to
     */
    void addToGame(GameLevel g);
}