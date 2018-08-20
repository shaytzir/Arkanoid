package io;

import arkanoid.Block;

import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shaytzir on 13/06/2017.
 */
public class SpecificBlockCreate implements BlockCreator {
    private int height;
    private int width;
    private  int hitPoints;
    private TreeMap<String, Background> fillMap;
    private Color stroke;

    /**
     * constructor.
     * @param blockInfo a map with all information for a block creator
     * @throws IOException if something goes wrong while reading
     */
    public SpecificBlockCreate(Map<String, String> blockInfo) throws IOException {
        try {
            //dealing with integers
            this.height = Integer.parseInt(blockInfo.get("height"));
            this.width = Integer.parseInt(blockInfo.get("width"));
            this.hitPoints = Integer.parseInt(blockInfo.get("hit_points"));

            this.fillMap = new TreeMap<String, Background>();
            for (String key: blockInfo.keySet()) {
                if (key.equals("fill")) {
                    Background back = this.getBackground(blockInfo.get(key));
                    this.fillMap.put("default", back);
                } else if (key.startsWith("fill-")) {
                    Background back = this.getBackground(blockInfo.get(key));
                    this.fillMap.put(key.substring(5), back);
                }
            }


            //stroke can be color(...) / color(RGB(...)) / *nothing*
            if (!blockInfo.containsKey("stroke")) {
                this.stroke = null;
            } else {
                String value = blockInfo.get("stroke");
                Color c = this.getColor(value);
                this.stroke = c;
            }
        } catch (Exception e) {
            System.out.println("something is missing");
            System.exit(1);
        }
    }

    /**
     *
     * @param value line representing color
     * @return color
     */
    public Color getColor(String value) {
        if (value.trim().startsWith("color(RGB")) {
            String[] rgb = (value.split("\\(")[2].split("\\)")[0]).split(",");
            int[] rgbVal = new int[rgb.length];
            for (int i = 0; i < rgbVal.length; i++) {
                rgbVal[i] = Integer.parseInt(rgb[i].trim());
            }
            return new Color(rgbVal[0], rgbVal[1], rgbVal[2]);
        } else if (value.trim().startsWith("color(")) {
            String color = value.split("\\(")[1].split("\\)")[0];
            ColorParse parser = new ColorParse();
            Color colorReal = parser.colorFromString(color);
            return colorReal;
        } else {
            return null;
        }
    }

    /**
     *
     * @param value line representing image
     * @return image (in background type)
     */
    public Background getBackground(String value) {
        java.awt.Color c = this.getColor(value);
        if (!(c == null)) {
            return new Background(c);
        } else {
            if (value.startsWith("image")) {
                String imageName = value.split("\\(")[1].split("\\)")[0];
                java.awt.image.BufferedImage img = null;
                java.io.InputStream is = null;
                try {
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
                    img = javax.imageio.ImageIO.read(is);
                    return new Background(img);
                } catch (IOException e) {
                    System.out.println("No background for block");
                    System.exit(0);
                }
            }
        }
        return null;
    }

    @Override
    public Block create(int xpos, int ypos) {
        return new Block(new Rectangle(new Point(xpos, ypos),
                this.width, this.height), this.fillMap, this.hitPoints, this.stroke);
    }
}
