package coregame;

import geometry.Point;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 * @author shaytzir
 *
 */
public class Velocity {
    private double dx;
    private double dy;
    /**
     * Constructor.
     * @param dx changes in x axis
     * @param dy changes in y axis
     */
   public Velocity(double dx, double dy) {
       this.dx = dx;
       this.dy = dy;
   }
   /**
    * creating velocity out of angle and speed rather than
    * dx and dy.
    * @param angle an angle for the ball to move
    * @param speed speed of the ball
    * @return the new velocity created by those parameters.
    */
   public static Velocity fromAngleAndSpeed(double angle, double speed) {
       //changing the angle to be in radians
       double radAngle = Math.toRadians(angle);
       double dx = Math.sin(radAngle) * speed;
       double dy = -1 * Math.cos(radAngle) * speed; // maybe * -1;
       return new Velocity(dx, dy);
   }

   /**
    *
    * @return the speed value of this velocity
    */
   public double getTheSpeed() {
       //by the formula root of(x^2 + y^2)
       double beforeRoot = (this.getDx() * this.getDx()) + (this.getDy() * this.getDy());
       double speed = Math.sqrt(beforeRoot);
       return speed;
   }

   /**
    * after getting the speed, this method does the
    * opposite to "fromAngleAndSpeed" and finds the angle.
    * @return the angle
    */
   public double getTheAngle() {
       //using the "fromAngleAndSpeed
       double beforeSin = this.getDx() / this.getTheSpeed();
       //using arc sinus
       double angleInRad = Math.asin(beforeSin);
       //changing back to degrees from radians
       double angle = Math.toDegrees(angleInRad);
       return angle;
   }

   /**
    * Take a point with position (x,y) and return a new point
    * with position (x+dx, y+dy).
    * @param p a point to change be velocity
    * @return a new point
    */
   public Point applyToPoint(Point p) {
       double newX = p.getX() + this.dx;
       double newY = p.getY() + this.dy;
       Point newPoint = new Point(newX, newY);
       return newPoint;
   }
   /**
    *
    * @return dx value of velocity.
    */
   public double getDx() {
       return this.dx;
   }
   /**
    *
    * @return dy value of velocity.
    */
   public double getDy() {
       return this.dy;
   }
}