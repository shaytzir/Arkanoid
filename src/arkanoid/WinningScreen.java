package arkanoid;
import java.awt.Color;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import coregame.Counter;

/**
 * animation shown when the user wins.
 * @author shaytzir
 *
 */
public class WinningScreen implements Animation {
     private KeyboardSensor keyboard;
     private Counter score;
     private boolean stop;

     /**
      * constructor.
      * @param k keyboard sensor
      * @param score the score counter from the game flow
      */
     public WinningScreen(KeyboardSensor k, Counter score) {
         this.keyboard = k;
         this.score  = score;
         this.stop = false;
     }

     /**
      * @param d a drawing surface
      * @param dt - seconds passed since last call
      */
     public void doOneFrame(DrawSurface d, double dt) {
         Color c = new Color(175, 150, 220);
         d.setColor(c);
         d.fillRectangle(0, 0, 800, 600);
         d.setColor(Color.white);
         d.drawText(d.getWidth() / 2 - 300 + 3, 150, " YOU WON ", 70);
         d.drawText(d.getWidth() / 2 - 300 - 3, 150, " YOU WON ", 70);
         d.drawText(d.getWidth() / 2 - 300, 150 + 3, " YOU WON ", 70);
         d.drawText(d.getWidth() / 2 - 300, 150 - 3, " YOU WON ", 70);
         d.setColor(new Color(175, 220, 225));
         d.drawText(d.getWidth() / 2 - 300, 150, " YOU WON ", 70);
         d.drawText(d.getWidth() / 4 - 100, 500, " Press Space To Continue ", 40);
         d.setColor(Color.white);
         d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2, "Your Score is " + this.score.getValue(), 32);
     }

     /**
      * @return the current state of should stop
      */
     public boolean shouldStop() {
         return this.stop;
     }
}
