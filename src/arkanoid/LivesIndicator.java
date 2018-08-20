package arkanoid;
import biuoop.DrawSurface;
import coregame.Counter;
import coregame.Sprite;
/**
 * @author shaytzir
 *
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    /**
     * constructor.
     * @param lives current lives counter
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    /**
     * @param d drawing surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.drawText(100, 20, "Lives:" + this.lives.getValue(), 20);
    }

    /**
     * does nothing here.
     * @param dt - seconds passed since last call
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * adding tihs sprite to the game.
     * @param g a gameLevel
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}