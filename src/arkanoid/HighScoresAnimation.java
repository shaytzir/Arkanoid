package arkanoid;
import java.awt.Color;
import java.util.List;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import coregame.HighScoresTable;
import coregame.ScoreInfo;

/**
 * @author shaytzir
 *
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;
    private String endKey;
    private KeyboardSensor keySens;
    private boolean stop;

    /**
     * constructor.
     * @param scores an high score table
     * @param endKey a key that end the animation
     * @param keyS keyboard sensor
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, KeyboardSensor keyS) {
        this.scores = scores;
        this.endKey = endKey;
        this.keySens = keyS;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
         Color c = new Color(143, 170, 120);
         d.setColor(c);
         d.fillRectangle(0, 0, 800, 600);

         d.setColor(new Color(177, 85, 149));
         d.drawText(d.getWidth() / 4 + 2, 100, "High Scores", 55);
        d.drawText(d.getWidth() / 4 - 2, 100, "High Scores", 55);
        d.drawText(d.getWidth() / 4, 100 + 2, "High Scores", 55);
        d.drawText(d.getWidth() / 4, 100 - 2, "High Scores", 55);
        d.setColor(new Color(222, 205, 0));
        d.drawText(d.getWidth() / 4, 100, "High Scores", 55);

         Color textC = new Color(250, 255, 245);
         d.setColor(textC);

         int y = 200;
         int yDiff = (600 - y) / this.scores.size() - 25;
         List<ScoreInfo> scoreL = this.scores.getHighScores();
         for (int i = 0; i < scoreL.size(); i++) {
             String text = scoreL.get(i).getName();
             d.drawText(100, y, text, 30);
             d.drawText(550, y, Integer.toString(scoreL.get(i).getScore()), 30);
             y = y + yDiff;
         }

        d.setColor(new Color(222, 205, 0));
        d.drawText(100 + 2, 150, "Player's Name                     Score", 37);
        d.drawText(100 - 2, 150, "Player's Name                     Score", 37);
        d.drawText(100, 150 + 2, "Player's Name                     Score", 37);
        d.drawText(100, 150 - 2, "Player's Name                     Score", 37);
         d.setColor(new Color(177, 85, 149));
         d.drawText(100, 150, "Player's Name                     Score", 37);
         d.drawText(100, 550, "Press Space To Continue", 40);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}