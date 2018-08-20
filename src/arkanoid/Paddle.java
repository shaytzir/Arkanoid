package arkanoid;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import coregame.Collidable;
import coregame.Sprite;
import coregame.Velocity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;

/**
 *
 * @author shaytzir
 *
 */
public class Paddle implements Sprite, Collidable {
   private biuoop.KeyboardSensor keyboard;
   private Rectangle rec;
   private java.awt.Color color;
   private GameEnvironment envi;
   private int widthScreen;
   private int sideBorder;
   private int numOfHits;
   private int speed;


    /**
     * constructor.
     * @param rec The paddle's specific rectangle
     * @param color The color of paddle
     * @param envi The game enviroment the paddle should using
     * @param widthScreen the width of game's screen
     * @param keyboard a keyboard sensor
     * @param sideBorder the width of the side border blocks and the
     * length of the horizontal border blocks
     * @param speed the wanted speed for the paddle
     */
   public Paddle(Rectangle rec, java.awt.Color color,
           GameEnvironment envi,
           int widthScreen, biuoop.KeyboardSensor keyboard, int sideBorder, int speed) {
       this.rec =  rec;
       this.color = color;
       double recWidth = rec.getWidth();
       double recHeight = rec.getHeight();
       this.keyboard = keyboard;
       this.envi = envi;
       this.widthScreen = widthScreen;
       this.sideBorder = sideBorder;
       this.numOfHits = 0;
       this.speed = speed;
   }

   /**
    * moving the game paddle a step left.
    * @param dt - seconds passed since last call
    */
   public void moveLeft(double dt) {
       //caculating the new "upperLeft Point" of the paddle
       double newX = this.getCollisionRectangle().getUpperLeft().getX() - (this.speed * (dt));
       double sameY = this.getCollisionRectangle().getUpperLeft().getY();
       //if the new location will keep the padlle in screen, move it there
       if (newX > this.sideBorder) {
           this.rec.changeRecLocation(new Point(newX, sameY));
       } else {
           //make it stay at the most left border
           this.rec.changeRecLocation(new Point(this.sideBorder, sameY));
       }
   }
   /**
    * moving the game paddle right.
    * @param dt - seconds passed since last call
    */
   public void moveRight(double dt) {
       //calculating the new "upperLeft Point" of the paddle
       double newX = this.getCollisionRectangle().getUpperLeft().getX() + (this.speed * (dt));
       double sameY = this.getCollisionRectangle().getUpperLeft().getY();
       //if the new location will keep the paddle in screen move it there
       if (newX + this.rec.getWidth() < this.widthScreen - this.sideBorder) {
           this.rec.changeRecLocation(new Point(newX, sameY));
       } else {
           //keep it at the most right border
           double newX2 =
                   this.widthScreen - this.rec.getWidth() - this.sideBorder;
           this.rec.changeRecLocation(new Point(newX2, sameY));
       }
   }

   /**
    *checks which keyboard button is pressed and moves the paddle accordingly.
    *@param dt - seconds passed since last call
    */
   public void timePassed(double dt) {
     if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
           this.moveLeft(dt);
         } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
             this.moveRight(dt);
         }
   }
   /**
    * @param d a drawing surface to draw on
    */
   public void drawOn(DrawSurface d) {
       d.setColor(this.color);
       int x = (int) this.rec.getUpperLeft().getX();
       int y = (int) this.rec.getUpperLeft().getY();
       int width = (int) this.rec.getWidth();
       int height = (int) this.rec.getHeight();
       d.fillRectangle(x, y, width, height);
       d.setColor(Color.BLACK);
       d.drawRectangle(x, y, width, height);
   }

   /**
    * @return the rectangle which creates this paddle
    */
   public Rectangle getCollisionRectangle() {
       return this.rec;
   }

   /**
    * @param hitter the hitting ball
    * @param collisionPoint the next collision point of this paddle
    * @param currentVelocity a velocity
    * @return new velocity to apply to the sending object
    */
   public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
       this.numOfHits = this.numOfHits + 1;
       double dividedBy5 = this.rec.getWidth() / 5;
       double paddleX = this.rec.getUpperLeft().getX();
       double paddleY = this.rec.getUpperLeft().getY();
       Line part1 = new Line(paddleX, paddleY,
               (paddleX + dividedBy5), paddleY);
       Line part2 = new Line(part1.end().getX(), paddleY,
               part1.end().getX() + dividedBy5, paddleY);
       Line part3 = new Line(part2.end().getX(), paddleY,
               part2.end().getX() + dividedBy5, paddleY);
       Line part4 = new Line(part3.end().getX(), paddleY,
               part3.end().getX() + dividedBy5, paddleY);
       Line part5 = new Line(part4.end().getX(), paddleY,
               paddleX + this.rec.getWidth(), paddleY);

       //adding the right angle according to the place the collision point is
       if (part1.checkIfInRange(collisionPoint)) {
           Velocity vel = Velocity.fromAngleAndSpeed(300, currentVelocity.getTheSpeed());
           return vel;
       } else if (part2.checkIfInRange(collisionPoint)) {
           Velocity vel = Velocity.fromAngleAndSpeed(330, currentVelocity.getTheSpeed());
           return vel;
       } else if (part3.checkIfInRange(collisionPoint)) {
           Velocity vel = new Velocity(currentVelocity.getDx(),
                   -1 * currentVelocity.getDy());
           return vel;
       } else if (part4.checkIfInRange(collisionPoint)) {
           Velocity vel = Velocity.fromAngleAndSpeed(30, currentVelocity.getTheSpeed());
           return vel;
       } else if (part5.checkIfInRange(collisionPoint)) {
           Velocity vel = Velocity.fromAngleAndSpeed(60, currentVelocity.getTheSpeed());
           return vel;
       }
       return currentVelocity;
   }

   /**
    * @param g a game to add the paddle to
    */
   public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
   }

   /**
    * removes this paddle from both lists of the game.
    * @param g the wanted game
    */
   public void removeFromGame(GameLevel g) {
       g.removeCollidable(this);
       g.removeSprite(this);
   }
}