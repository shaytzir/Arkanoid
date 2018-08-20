package arkanoid;
import java.util.Random;
import biuoop.DrawSurface;
import coregame.CollisionInfo;
import coregame.Sprite;
import coregame.Velocity;
import geometry.Line;
import geometry.Point;
/**
 *
 * @author shaytzir
 *
 */
public class Ball implements Sprite {
    private Point location;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment envi;

    /**
     * constructor calling the "main" constructor.
     * @param center Point of the circle
     * @param r the radius of the ball
     * @param color the color of the ball
     * @param envi a game environment to attach to the ball
     */
    public Ball(Point center, int r,
            java.awt.Color color,
            GameEnvironment envi) {
        this(center.getX(), center.getY(), r, color, envi);
    }
    /**
     * "main" constructor.
     * @param x the x value of center point
     * @param y the y value of center point
     * @param r the radius of the ball
     * @param color color of the ball
     * @param envi a game environment to attach to the ball
     */
    public Ball(double x, double y, int r,
            java.awt.Color color, GameEnvironment envi) {
        //creates the center point
        Point point = new Point(x, y);
        this.location = point;
        this.radius = r;
        this.color = color;
        Velocity defaultV = new Velocity(0, 0);
        // making sure the program wont crash
        this.velocity = defaultV;
        this.envi = envi;
    }
    /**
     *
     * @return the game environment of the ball
     */
    public GameEnvironment getEnvi() {
        return this.envi;
    }
    /**
     *
     * @return the x value of the center(location) of the ball
     */
    public int getX() {
        return (int) this.location.getX();
    }
    /**
     *
     * @return the y value of the center(location) of the ball
     */
    public int getY() {
        return (int) this.location.getY();
    }
    /**
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }
    /**
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * method draws the ball on a given surface.
     * @param surface drawing surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(java.awt.Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
    }
    /**
     * setting the ball to have specific velocity.
     * @param v given velocity
     */
    public void setVelocity(Velocity v) {
      this.velocity = v;
    }
    /**
     * creating velocity by dx and dy and setting the ball
     * to have this specific velocity.
     * @param dx changes in x
     * @param dy changes in y
     */
    public void setVelocity(double dx, double dy) {
        Velocity newVelocity = new Velocity(dx, dy);
        this.velocity = newVelocity;
    }
    /**
     *
     * @return the ball's velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * moves the ball in one step, concidering potential
     * intersections.
     * @param dt - seconds passed since last call
     */
    public void moveOneStep(double dt) {
        Velocity v = new Velocity(this.velocity.getDx() * dt, this.velocity.getDy() * dt);
        Point endTrace = new Point(this.location.getX()
                    + v.getDx(),
                    this.location.getY() + v.getDy());
            //how the ball will move without any obstacles
            Line trajectory = new Line(this.location, endTrace);
            //Check if moving on this trajectory will hit anything.
            CollisionInfo col = this.getEnvi().getClosestCollision(trajectory);
            //if not
            if (col == null) {
                //move the ball to the end of the trajectory.
                this.location = v.applyToPoint(this.location);
                return;
            }
            //else
            Point collisionP = col.collisionPoint();
            //find the changes needed before intersecting
            Point change = col.collisionObject().getCollisionRectangle().
                    checkHitChangeLocation(collisionP, v);
            //update the hit and find the new velocity needed
            Velocity newV = col.collisionObject().hit(this, collisionP, v);
            //meaning the ball hit the side of the paddle(only the paddle)!
            if (newV == v) {
                //take it a little after the collision point and act normally
                this.location = new Point(400, 605);
                return;
            }
            //move the ball to "almost" the hit point, but just slightly before it.
            this.location = new Point((collisionP.getX() + change.getX()),
                    (collisionP.getY() + change.getY()));
            this.setVelocity(newV.getDx() / dt, newV.getDy() / dt);
    }
    /**
     * this method gives a ball a random angle, and setting the
     * spseed to be according to the ball's radius. thus, setting
     * the ball random velocity.
     */
    public void setRandomVelocity() {
        Random rand = new Random();
        double angle = rand.nextDouble() * 180;
        //smaller balls will have greater speed.
        double speed = 150 / this.getSize();
        //creating the velocity out of angle and speed
        Velocity newVel = Velocity.fromAngleAndSpeed(angle, speed);
        //setting the ball new velocity
        this.setVelocity(newVel);
    }
    /**
     * calls move one step.
     * @param dt - seconds passed since last call
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * @param g a game to add ball to
     * adding this ball to a game.
     */
    public void addToGame(GameLevel g) {
        //ball implements a sprite, thus adding to the sprite list
        g.addSprite(this);
    }
    /**
     * @param gameLevel the game level to add this sprite to
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}