package coregame;
import java.io.Serializable;

/**
 * @author shaytzir
 *
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * constructor.
     * @param name player's name
     * @param score player's score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     *
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }
    /**
     *
     * @return the player's score
     */
    public int getScore() {
        return this.score;
    }
}