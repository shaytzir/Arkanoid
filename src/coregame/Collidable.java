package coregame;

import arkanoid.Ball;
import geometry.Point;
import geometry.Rectangle;

/**
 *
 * @author shaytzir
 *
 */
public interface Collidable {
    /**
     *
     * @return the collision shape of the object
     */
   Rectangle getCollisionRectangle();

   /**
    *
    * @param collisionPoint location of the nearest collision
    * @param currentVelocity the velocity of the hitting object
    * @param hitter a ball thats hitting the collidable object
    * @return new velocity expected after the hit
    */
   Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}