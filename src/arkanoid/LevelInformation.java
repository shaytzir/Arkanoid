package arkanoid;
import java.util.List;

import coregame.Sprite;
import coregame.Velocity;

/**
 * @author shaytzir
 *
 */
public interface LevelInformation {
     /**
      *
      * @return number of balls in the level
      */
     int numberOfBalls();

    /**
     *
     * @return The initial velocity of each ball (initialBallVelocities().size() == numberOfBalls())
     */
    List<Velocity> initialBallVelocities();
    /**
     *
     * @return the speed of the paddle
     */
    int paddleSpeed();
    /**
     *
     * @return the width in int of the paddle
     */
    int paddleWidth();

    /**
     *
     * @return the level name will be displayed at the top of the screen.
     */
    String levelName();

    /**
     *
     * @return  Returns a sprite with the background of the level
     */
    Sprite getBackground();
    /**
     *
     * @return The Blocks that make up this level, each block contains size,color and location
     */
    List<Block> blocks();

    /**
     *
     * @return Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     */
    int numberOfBlocksToRemove();
}