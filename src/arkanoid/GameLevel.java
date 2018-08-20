package arkanoid;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.DrawSurface;
import biuoop.GUI;
import coregame.Collidable;
import coregame.Counter;
import coregame.Sprite;
import geometry.Point;
import geometry.Rectangle;
/**
 *
 * @author shaytzir
 *
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private int width;
    private int height;
    private GUI gui;
    private biuoop.KeyboardSensor keyboard;
    private int borderLength;
    private ArrayList<Block> borders;
    private Counter blockCount;
    private Counter ballCount;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation info;
    private List<Block> gameBlocks;
    private int startBlocksNum;

    /**
     * constructor.
     * @param level level information
     * @param ar the anumation runner
     * @param gui a gui
     * @param key keyboard sensor
     * @param score score Counter
     * @param lives Lives Counter
     * @param borderSize size for the border blocks
     */
     public GameLevel(LevelInformation level, AnimationRunner ar,
             GUI gui, biuoop.KeyboardSensor key, Counter score, Counter lives, int borderSize) {
         this.info = level;
          this.sprites = new SpriteCollection();
          this.environment = new GameEnvironment();
          //can be changed;
          this.width = 800;
          this.height = 600;
          this.gui = gui;
          this.keyboard = key;
          this.borderLength = borderSize;
          this.borders = createBordersBlocks(borderLength);
          this.blockCount = new Counter(0);
          this.ballCount = new Counter(0);
          this.score = score;
          this.lives = lives;
          this.runner = ar;
          this.running =  true;
          this.gameBlocks = new ArrayList<Block>();
          for (int i = 0; i < level.blocks().size(); i++) {
              this.gameBlocks.add(level.blocks().get(i).getBlockCopy());
          }
          this.startBlocksNum = this.gameBlocks.size();
     }

     /**
      *
      * @return the current number of blocks in the level
      */
     public int getBlockNum() {
         return this.blockCount.getValue();
     }


    /**
     *
     * @param shortSide the heigth of the horizontal block
     * and width of the vertical blocks
     * @return the list of the borders block
     */
    public ArrayList<Block> createBordersBlocks(int shortSide) {
      //in consturctor build the border blocks
         ArrayList<Block> theBorders = new ArrayList<Block>();
         Block upperBlock = new Block(new Rectangle(
                    new Point(0, 0), this.width, shortSide),
                    java.awt.Color.gray, 10);
         Block leftBlock = new Block(new Rectangle(
                    new Point(0, 0), shortSide, this.height),
                    java.awt.Color.gray, 10);
         Block rightBlock = new Block(
                    new Rectangle(new Point(this.width - shortSide, 0),
                              shortSide, this.height),
                    java.awt.Color.gray, 10);
         theBorders.add(upperBlock);
       //  theBorders.add(deathRegion);
         theBorders.add(leftBlock);
         theBorders.add(rightBlock);
         return theBorders;
    }
    /**
     * creates a block which deletes balls hitting it.
     */
    public void createDeathRegion() {
        BallRemover ballRemove = new BallRemover(this, this.ballCount);
        Block deathRegion = new Block(new Rectangle(
                new Point(0, this.height + 10),
                this.width, this.borderLength), java.awt.Color.gray, 10);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemove);
    }
     /**
      * adds a collidable to the collidable array.
      * @param c collidable
      */
    public void addCollidable(Collidable c) {
         this.environment.getarray().add(c);
    }
    /**
     * adds a sprite to the sprite array.
     * @param s sprite
     */
    public void addSprite(Sprite s) {
         this.sprites.addSprite(s);
    }

    /**
     * the method removes the requested Collidable from the collidable collection (environment).
     * @param c a collideable we want to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.getarray().remove(c);
    }

    /**
     * the method removes the requested sprite from the sprite collection.
     * @param s a sprite we want to remove from the game
     */
    public void removeSprite(Sprite s) {
        this.sprites.getAllSprite().remove(s);
    }
    /**
     *
     * @return the borders blocks of this game
     */
    public ArrayList<Block> getBorders() {
         return this.borders;
    }


    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
       this.info.getBackground().addToGame(this);
       for (int i = 0; i < this.borders.size(); i++) {
           borders.get(i).addToGame(this);
           }
        createDeathRegion();

        BlockRemover remove = new BlockRemover(this, this.blockCount);

        ScoreIndicator scoring = new ScoreIndicator(this.score);
        scoring.addToGame(this);

        NameIndicator name = new NameIndicator(this.info.levelName());
        name.addToGame(this);

        LivesIndicator livesNum = new LivesIndicator(this.lives);
        livesNum.addToGame(this);

        ScoreTrackingListener scoreTrack = new ScoreTrackingListener(this.score);
        for (int i = 0; i < this.gameBlocks.size(); i++) {
            this.gameBlocks.get(i).addHitListener(remove);
            this.gameBlocks.get(i).addHitListener(scoreTrack);
            this.gameBlocks.get(i).addToGame(this);
            this.blockCount.increase(1);
        }
    }


    /**
     * runs the game.
     */
    public void run() {
        while (this.lives.getValue() > 0) {
            playOneTurn();
            this.lives.decrease(1);
        }
        this.gui.close();
    }

    /**
     *
     * @return a new paddle in the center of the screen.
     */
    public Paddle createPaddle() {
        int paddleWidth = this.info.paddleWidth();
        int paddlehHeight = (this.height - (2 * this.borderLength)) / 25;
        Paddle paddle = new Paddle(new Rectangle(new Point((this.width / 2) - (paddleWidth / 2),
                  this.height - 40), paddleWidth, paddlehHeight),
                  Color.YELLOW, this.environment, this.width,
                  this.keyboard, this.borderLength, this.info.paddleSpeed());
        return paddle;
    }

    /**
     *
     * @param gEnvi game environment
     * @param paddle a paddle
     * @return a list on the center of the paddle, moving as the given velocities in levelInformation
     */
    public List<Ball> createBalls(GameEnvironment gEnvi, Paddle paddle) {
        Color color = new Color(255, 255, 255);
        int x = (int) paddle.getCollisionRectangle().getUpperLeft().getX();
        int y = (int) paddle.getCollisionRectangle().getUpperLeft().getY();
        List<Ball> balls = new ArrayList<Ball>();
        for (int i = 0; i < this.info.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(x + this.info.paddleWidth() / 2,
                    y - 20), 9, color, gEnvi);
            ball.setVelocity(this.info.initialBallVelocities().get(i));
            balls.add(ball);
        }
     return balls;
    }

    /**
     * adding the balls to this level.
     * @param balls a list of balls of this game
     */
    public void addBallsToGame(List<Ball> balls) {
        for (int j = 0; j < balls.size(); j++) {
            this.ballCount.increase(1);
            balls.get(j).addToGame(this);
        }
    }

    /**
     * run one turn game and start the animation loop.
     */
    public void playOneTurn() {
        Paddle paddle = this.createPaddle();
        List<Ball> balls = this.createBalls(this.environment, paddle);
        paddle.addToGame(this);
        addBallsToGame(balls);

        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
        paddle.removeFromGame(this);
        for (Ball b: balls) {
            b.removeFromGame(this);
        }
    }
    /**
     * the method keeps the game logics.
     * @param d a drawing surface
     * @param dt - seconds passed since last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        int enoughBlocksToPass = this.startBlocksNum - this.info.numberOfBlocksToRemove();
            if (this.ballCount.getValue() == 0) {
                this.running = false;
                this.lives.decrease(1);
            }
            if (this.blockCount.getValue() == enoughBlocksToPass) {
                this.running = false;
                this.score.increase(100);
            }
         this.sprites.drawAllOn(d);
         this.sprites.notifyAllTimePassed(dt);
         //d.drawText(500 , 20, this.info.levelName(), 20);
         if (this.keyboard.isPressed("p")) {
             PauseScreen pause = new PauseScreen(this.keyboard);
             KeyPressStoppableAnimation pauseK =
                     new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY, pause);
             this.runner.run(pauseK);
          }
    }
    /**
     * this method informs whn to stop.
     * @return if we should stop
     */
    public boolean shouldStop() {
        return !this.running;
    }
}