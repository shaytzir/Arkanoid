package arkanoid;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import biuoop.DrawSurface;
import coregame.Collidable;
import coregame.HitListener;
import coregame.HitNotifier;
import coregame.Sprite;
import coregame.Velocity;
import geometry.Point;
import geometry.Rectangle;
import io.Background;

/**
 *
 * @author shaytzir
 *
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle curBlock;
    private int hits;
    private java.awt.Color color;
    private TreeMap<String, Background> fillMap;
    private int startHits;
    private List<HitListener> hitListeners;
    private java.awt.Color stroke;

    /**
     * constructor.
     * @param rec a rectangle to be the block
     * @param color the color of the block
     * @param startHits the numbers of hits a block can get
     */
    public Block(Rectangle rec, java.awt.Color color, int startHits) {
        this.curBlock = rec;
        this.hits = 0;
        this.color = color;
        this.startHits = startHits;
        //new
        this.fillMap = new TreeMap<String, Background>();
        //check if need the follow:
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * another kind of constructor.
     * @param rec a rectangle
     * @param fillMap map that holds backgrounds for each type of hit
     * @param startHits how much hit points does a block holds
     * @param stroke color/null for stroke of the block
     */
    public Block(Rectangle rec, TreeMap<String, Background> fillMap, int startHits, Color stroke) {
        this.curBlock = rec;
        this.hits = 0;
        this.startHits = startHits;
        this.color = null;
        this.fillMap = fillMap;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = stroke;
    }
    /**
     * @return the rectangle which implements the block
     */
    public Rectangle getCollisionRectangle() {
        return this.curBlock;
    }
    /**
     *
     * @return the possible number of hits a block can get
     */
    public int getStartHits() {
        return this.startHits;
    }
    /**
     *
     * @return how much hits did the block get
     */
    public int getCurrentHits() {
        return this.hits;
    }
    /**
     * @param surface a drawing surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        int leftX = (int) this.curBlock.getUpperLeft().getX();
        int upperY = (int) this.curBlock.getUpperLeft().getY();
        int width = (int) this.curBlock.getWidth();
        int height = (int) this.curBlock.getHeight();
        if (this.color == null) {
            int curHitsRemain = this.startHits - this.hits;
            String curHitRemainS = Integer.toString(curHitsRemain);
            Background fill = this.fillMap.get(curHitRemainS);
            if (fill == null) {
                fill = this.fillMap.get("default");
            }
            if (fill.isColor()) {
                surface.setColor(fill.getColor());
                surface.fillRectangle(leftX, upperY, width, height);
            } else if (fill.isImage()) {
                surface.drawImage(leftX, upperY, fill.getImage());
            }
        } else if (!(this.color == null)) {
            surface.setColor(this.color);
            surface.fillRectangle(leftX, upperY,
                    width, height);
        }
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle(leftX, upperY,
                    (int) this.curBlock.getWidth(),
                    (int) this.curBlock.getHeight());
        }


    }
    /**
     * calling this method means this block was hit. the method
     * updated the hits number of this block and changes the velocity
     * according to the place the collision point is.
     * @param collisionPoint the collision point
     * @param currentVelocity specific velocity
     * @param hitter hiiting ball
     * @return new velocity for the hitting object
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //update hits
        this.hits = this.hits + 1;
        this.notifyHit(hitter);
        double upperLeftX = this.curBlock.getUpperLeft().getX();
        double upperLeftY = this.curBlock.getUpperLeft().getY();
        Point upperRight = new Point(upperLeftX + this.curBlock.getWidth(),
                upperLeftY);
        Point lowerLeft = new Point(upperLeftX, upperLeftY
                + this.curBlock.getHeight());
        Point lowerRight = new Point(upperLeftX + this.curBlock.getWidth(),
                upperLeftY + this.curBlock.getHeight());
        if (collisionPoint
                .equals(this.getCollisionRectangle().getUpperLeft())) {
            Velocity vel = new Velocity(-1 * currentVelocity.getDx(),
                    -1 * currentVelocity.getDy());
            return vel;
        }
        if (collisionPoint.equals(upperRight)) {
            Velocity vel = new Velocity(-1 * currentVelocity.getDx(),
                    -1 * currentVelocity.getDy());
            return vel;
        }
        if (collisionPoint.equals(lowerLeft)) {
            Velocity vel = new Velocity(-1 * currentVelocity.getDx(),
                    -1 * currentVelocity.getDy());
            return vel;
        }
        if (collisionPoint.equals(lowerRight)) {
            Velocity vel = new Velocity(-1 * currentVelocity.getDx(),
                    -1 * currentVelocity.getDy());
            return vel;
        }
        if ((collisionPoint.getX() == upperLeftX)
                || (collisionPoint.getX() == upperLeftX
                + this.getCollisionRectangle().getWidth())) {
            Velocity vel = new Velocity(-1 * currentVelocity.getDx(),
                    currentVelocity.getDy());
            return vel;
        }

        if ((collisionPoint.getY() == upperLeftY)
                || (collisionPoint.getY() == upperLeftY
                + this.getCollisionRectangle().getHeight())) {
            Velocity vel = new Velocity(currentVelocity.getDx(),
                    -1 * currentVelocity.getDy());
            return vel;
        }

        return null;
    }
    /**
     * @param dt - seconds passed since last call
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * because block implement both collidable and sprite
     * this method makes sure that it will be added to
     * both array of game: sprite and collidable.
     * @param g a game to add this block to
     */
    public void addToGame(GameLevel g) {
        //should maybe add (Sprite/collidable) casting
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * because block implements both collidable and sprite this method makes
     * sure it will be removed from both arrays of the gam: sprite and collidable.
     * @param gameLevel a game we want to remove this block (collidable/sprite) from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }
    /**
     * @param hl a hitListener to add to the list of hitListener(s).
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * @param hl a hitListener to remove from the list of hitListener(s).
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notifying all of this block listeners a- a hit occured.
     * @param hitter the ball that hit this block
     */
    private void notifyHit(Ball hitter) {
          // Make a copy of the hitListeners before iterating over them.
          List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
          // Notify all listeners about a hit event:
          for (HitListener hl : listeners) {
             hl.hitEvent(this, hitter);
          }
       }

    /**
     *
     * @return this blocks width - the rec that is the block
     */
    public int getWidth() {
        return (int) this.getCollisionRectangle().getWidth();
       }

    /**
     *
     * @return a copy of this particular block object
     */
    public Block getBlockCopy() {
        return new Block(this.getCollisionRectangle(), this.fillMap, this.startHits, this.stroke);
    }
}