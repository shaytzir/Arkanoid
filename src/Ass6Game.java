

import io.GameSet;

import java.io.InputStream;

/**
 * @author shaytzir
 *
 */
public class Ass6Game {
    /**
     *
     * @param args may have path to level sets filr
     */
    public static void main(String[] args) {
        String setLevels = null;
        if (args.length == 1) {
            setLevels = args[0];
        } else {
            setLevels = "level_set.txt";
        }

        try {
            InputStream ls = ClassLoader.getSystemClassLoader().getResourceAsStream(setLevels);
            GameSet gameSet = new GameSet(ls);
            gameSet.run();
        } catch (Exception e) {
            System.out.println("something's wrong. cant create");
            System.exit(1);
        }
    }
}
