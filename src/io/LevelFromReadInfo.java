package io;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arkanoid.Block;
import arkanoid.LevelInformation;
import coregame.Sprite;
import coregame.Velocity;

/**
 * @author shaytzir
 *
 */
public class LevelFromReadInfo implements LevelInformation {
    private String levelsName;
    private List<Velocity> ballsVelo;
    private int paddleSpeed;
    private int paddleWidth;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private int numBlocks;
    private Background background;
    private String blockDef;
    private BlocksFromSymbolsFactory factory;
    private List<Block> blocks;

    /**
     * constructor.
     */
    public LevelFromReadInfo() {
        this.levelsName = null;
        this.ballsVelo = null;
        this.paddleSpeed = 0;
        this.paddleWidth = 0;
        this.blocksStartX = 0;
        this.blocksStartY = 0;
        this.rowHeight = 0;
        this.numBlocks = 0;
        this.background = null;
        this.blockDef = null;
        this.factory = null;
        this.blocks = new ArrayList<Block>();

    }

    /**
     * adding information to the level.
     * @param line a line from the level informations file
     */
    public void add(String line) {
        String[] parts = line.split(":");
        String key = parts[0].trim();
        String value = parts[1].trim();
        if (key.equals("level_name")) {
            this.levelsName = value;
        } else if (key.equals("paddle_speed")) {
            this.paddleSpeed = Integer.parseInt(value);
        } else if (key.equals("paddle_width")) {
            this.paddleWidth = Integer.parseInt(value);
        } else if (key.equals("blocks_start_x")) {
            this.blocksStartX = Integer.parseInt(value);
        } else if (key.equals("blocks_start_y")) {
            this.blocksStartY = Integer.parseInt(value);
        } else if (key.equals("row_height")) {
            this.rowHeight = Integer.parseInt(value);
        } else if (key.equals("num_blocks")) {
            this.numBlocks = Integer.parseInt(value);
        } else if (key.equals("background")) {
            dealBackground(value);
        } else if (key.equals("ball_velocities")) {
            createVelo(value);
        } else if (key.equals("block_definitions")) {
            this.blockDef = value;
        }
    }

    /**
     * creating velocities from velocity line.
     * @param value velocities as String, seperated by " "
     */
    public void createVelo(String value) {
        List<Velocity> velocities = new ArrayList<>();

        String[] angleSpeed = value.split("\\s+");
        for (int i = 0; i < angleSpeed.length; i++) {
            int angle = Integer.parseInt(angleSpeed[i].split(",")[0]);
            int speed = Integer.parseInt(angleSpeed[i].split(",")[1]);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            velocities.add(v);
        }
        this.ballsVelo = velocities;
    }

    /**
     *
     * @return the x for the first block in each row
     */
    public int getBlocksStartX() {
        return this.blocksStartX;
    }

    /**
     * creating a background type for a given line.
     * @param value a line: "color(..) / color(RGB(..)) / image(..)"
     */
    public void dealBackground(String value) {
        //dealing with image
        if (value.startsWith("image")) {
            String imageName = value.split("\\(")[1].split("\\)")[0];
            Image img = null;
            java.io.InputStream is = null;
            try {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
                java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(is);
                this.background = new Background(image);
            } catch (IOException e) {
                System.out.println("could not load image");
                System.exit(0);
            }
            //dealing with rgb
        } else if (value.startsWith("color(RGB")) {
            String[] rgb = (value.split("\\(")[2].split("\\)")[0]).split(",");
            int[] rgbVal = new int[rgb.length];
            for (int i = 0; i < rgbVal.length; i++) {
                rgbVal[i] = Integer.parseInt(rgb[i]);
            }
            this.background = new Background(new Color(rgbVal[0], rgbVal[1], rgbVal[2]));
        } else if (value.startsWith("color(")) {
            String color = value.split("\\(")[1].split("\\)")[0];
            ColorParse parser = new ColorParse();
            Color c = parser.colorFromString(color);
            this.background = new Background(c);
        } else {
            System.out.print("something wrong with creating the background");
            System.exit(1);
        }

    }

    @Override
    public int numberOfBalls() {
        return this.ballsVelo.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballsVelo;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelsName;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }

    /**
     *
     * @param factoryBS a factory which holds block creator for blocks symbols, and info about spacers
     */
    public void addFactory(BlocksFromSymbolsFactory factoryBS) {
        this.factory = factoryBS;
    }

    /**
     *
     * @return blocks and symbols factory
     */
    public BlocksFromSymbolsFactory getFactory() {
        return this.factory;
    }

    /**
     *
     * @return y of the first row that was set
     */
    public int getBlocksStartY() {
        return this.blocksStartY;
    }

    /**
     *
     * @return row height getter
     */
    public int getRowHeight() {
        return this.rowHeight;
    }

    /**
     * adding a block to this level's blocks.
     * @param b a block
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
    }
}
