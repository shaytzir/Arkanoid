package arkanoid;
import java.io.File;
import java.util.List;

import animation.AnimationRunner;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import coregame.Counter;
import coregame.HighScoresTable;
import coregame.ScoreInfo;
import animation.KeyPressStoppableAnimation;

/**
 * @author shaytzir
 *
 */
public class GameFlow {
   // private int width;
   // private int height;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private GUI gui;
    private biuoop.KeyboardSensor keyboard;
    private List<LevelInformation> levels;
    private int borderSize;
    private HighScoresTable table;

    /**
     * constructor.
     * @param gui a Gui
     * @param ar an Animator runner to run also the game flow
     * @param ks Keyboard sensor. same sensor for all levels
     * @param levels the pre designed levels
     * @param borderSize the height/width of the horizontal/vertical blocks borders
     * @param table an highscore table
     */
    public GameFlow(GUI gui, AnimationRunner ar,
            KeyboardSensor ks, List<LevelInformation> levels, int borderSize, HighScoresTable table) {
      //  this.width = width;
       // this.height = height;
        this.gui = gui;
        this.keyboard = ks;
        this.runner = ar;
        this.levels = levels;
        this.score = new Counter(0);
        this.lives = new Counter(7);
        this.borderSize = borderSize;
        this.table = table;
    }
     /**
     * runs the pre designed levels. if the lives are 0 in some point, the user loses and the program terminates
     * else the user wins (all blocks are removed)
     * @param levelsInf the pre designed levels, given as a list of levelInformation
     */
    public void runLevels(List<LevelInformation> levelsInf) {
    //    final GUI gui = this.gui;

        DialogManager dialog = this.gui.getDialogManager();

           for (LevelInformation levelInfo : levelsInf) {
               //giving the new level all of it's parameters
              GameLevel level = new GameLevel(levelInfo, this.runner, this.gui,  this.keyboard,
                      this.score, this.lives, this.borderSize);

              level.initialize();
               int enoughBlocksToPass = level.getBlockNum() - levelInfo.numberOfBlocksToRemove();
              //if there are still blocks to remove and the user has enough lives - keep the game going
              while ((this.lives.getValue() > 0) && (level.getBlockNum() > enoughBlocksToPass)) {
                  level.playOneTurn();
              }

              //if the lives ended at this point the user lost, gui closed.
              if (this.lives.getValue() == 0) {
                  LosingScreen gameOver = new LosingScreen(this.keyboard, this.score);
                  KeyPressStoppableAnimation gameOverKey =
                          new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY, gameOver);
                  this.runner.run(gameOverKey);
                  if (table.getRank(this.score.getValue()) <= table.size()) {
                      String name = dialog.showQuestionDialog("Name", "What is your name?", "");
                      table.add(new ScoreInfo(name, this.score.getValue()));
                      try {
                          table.save(new File("highscores"));
                      } catch (Exception ex) {
                          System.out.println("something went wrong");
                          System.exit(1);
                      }
                  }
                  break;
              }
           }
           //if the loop has ended it means the user passed all levels and has enough lives - the user won!
           if (this.lives.getValue() > 0) {
                WinningScreen win = new WinningScreen(this.keyboard, this.score);
                KeyPressStoppableAnimation winKey =
                        new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY, win);
                this.runner.run(winKey);
                if (table.getRank(this.score.getValue()) <= table.size()) {
                    String name = dialog.showQuestionDialog("Name", "What is your name?", "");
                    table.add(new ScoreInfo(name, this.score.getValue()));
                  try {
                      table.save(new File("highscores"));
                  } catch (Exception ex) {
                      System.out.println("something went wrong");
                      System.exit(1);
                  }
                }
           }
        }
}