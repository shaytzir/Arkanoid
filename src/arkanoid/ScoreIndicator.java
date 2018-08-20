package arkanoid;
import biuoop.DrawSurface;
import coregame.Counter;
import coregame.Sprite;

/**
 * @author shaytzir
 *
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * constructor.
     * @param score score Counter
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }
    /**
     * @param d a drawing surface to draw the score on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 20);
        d.setColor(java.awt.Color.BLACK);
        d.drawText((int) (d.getWidth() * 0.45), 20, "Score: " + this.score.getValue(), 20);
    }

    /**
     * does nothing here.
     * @param dt - seconds passed since last call
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * adding this sprite to the game.
     * @param g a game level
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}