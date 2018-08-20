package coregame;

import geometry.Point;

/**
 *
 * @author shaytzir
 *
 */
public class CollisionInfo {
    private Point collisionP;
    private Collidable collsionOb;

    /**
     *
     * @param colP collision point
     * @param colOb Collidable
     */
    public CollisionInfo(Point colP, Collidable colOb) {
        this.collisionP = colP;
        this.collsionOb = colOb;
    }

   /**
    *
    * @return the point at which the collision occurs.
    */
    public Point collisionPoint() {
       return this.collisionP;
   }

   /**
    *
    * @return the collidable object involved in the collision.
    */
   public Collidable collisionObject() {
       return this.collsionOb;
   }
}