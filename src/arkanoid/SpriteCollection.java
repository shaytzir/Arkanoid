package arkanoid;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import coregame.Sprite;
/**
 *
 * @author shaytzir
 *
 */
public class SpriteCollection {
    private ArrayList<Sprite> allSprite;

    /**
     * constructor - creates new sprite array.
     */
    public SpriteCollection() {
        this.allSprite = new ArrayList<Sprite>();
    }
    /**
     * adds a sprite to allsprite array.
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        this.allSprite.add(s);
    }
    /**
     *
     * @return all sprite array
     */
    public ArrayList<Sprite> getAllSprite() {
        return this.allSprite;
    }

    /**
     * call timePassed() on all sprites.
     * @param dt - seconds passed since last call
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> allSprites = new ArrayList<Sprite>(this.allSprite);
        for (int i = 0; i < allSprites.size(); i++) {
            allSprites.get(i).timePassed(dt);
        }
    }

    /**
     *
     * @param d a drawing surface to draw all sprites on
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.getAllSprite().size(); i++) {
            this.getAllSprite().get(i).drawOn(d);
        }
    }
}