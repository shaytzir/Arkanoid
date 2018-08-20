package arkanoid;
import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import coregame.Counter;
import java.awt.Color;

/**
 * animation when the user loses.
 * @author shaytzir
 *
 */
public class LosingScreen implements Animation {
     private KeyboardSensor keyboard;
     private Counter score;
     private boolean stop;

     /**
      * constructor.
      * @param k keyboard sensor
      * @param score the conter of score in the game flow
      */
     public LosingScreen(KeyboardSensor k, Counter score) {
         this.keyboard = k;
         this.score  = score;
         this.stop = false;
     }

     /**
      * @param d drawing surface
      * @param dt - seconds passed since last call
      */
     public void doOneFrame(DrawSurface d, double dt) {
         d.setColor(java.awt.Color.black);
         d.fillRectangle(0, 0, 800, 600);
         d.setColor(new Color(95, 161, 170));
         d.drawLine(0, 220, 800, 220);
         d.setColor(java.awt.Color.white);
         d.drawText(d.getWidth() / 2 - d.getWidth() / 4 - 2, 200, "GAME OVER", 45);
         d.drawText(d.getWidth() / 2 - d.getWidth() / 4 + 2, 200, "GAME OVER", 45);
         d.drawText(d.getWidth() / 2 - d.getWidth() / 4, 200 + 2, "GAME OVER", 45);
         d.drawText(d.getWidth() / 2 - d.getWidth() / 4, 200 - 2, "GAME OVER", 45);
         d.setColor(new Color(85, 155, 187));
         d.drawText(d.getWidth() / 2 - d.getWidth() / 4, 200, "GAME OVER", 45);
         d.drawText(d.getWidth() / 4, d.getHeight() / 2, "Your Score is " + this.score.getValue(), 32);
     }

     /**
      * @return the current state of should stop
      */
     public boolean shouldStop() {
         return this.stop;
     }
}
