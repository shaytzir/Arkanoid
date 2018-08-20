package arkanoid;
import biuoop.DrawSurface;
import coregame.Sprite;
/**
 * @author shaytzir
 *
 */
public class NameIndicator implements Sprite {
    private String name;

    /**
     * constructor.
     * @param name the levels name
     */
    public NameIndicator(String name) {
        this.name = name;
    }
    /**
     * @param d a drawing surface to draw the score on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.drawText((int) (d.getWidth() * 0.65), 20, name, 20);
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