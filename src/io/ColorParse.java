package io;
import java.awt.Color;
import java.util.Map;
import  java.util.TreeMap;
/**
 * @author shaytzir
 *
 */
public class ColorParse {
    private Map<String, java.awt.Color> sToC;

    /**
     * constructor.
     */
    public ColorParse() {
        this.sToC = new TreeMap<String, Color>();
        this.sToC.put("black", java.awt.Color.BLACK);
        this.sToC.put("blue", java.awt.Color.BLUE);
        this.sToC.put("cyan", java.awt.Color.CYAN);
        this.sToC.put("gray", java.awt.Color.GRAY);
        this.sToC.put("lightGray", java.awt.Color.LIGHT_GRAY);
        this.sToC.put("green", java.awt.Color.GREEN);
        this.sToC.put("orange", java.awt.Color.ORANGE);
        this.sToC.put("pink", java.awt.Color.PINK);
        this.sToC.put("red", java.awt.Color.RED);
        this.sToC.put("white", java.awt.Color.white);
        this.sToC.put("yellow", java.awt.Color.YELLOW);
    }

    /**
     *
     * @param s String which is a colors name
     * @return the color if its written as asked, exception if not exsist
     */
    public java.awt.Color colorFromString(String s) {
        //what to do when getting a color which isnt in the list
        Color color = this.sToC.get(s);
        if (color == null) {
            System.out.println("Youre asking for a color which doesnt exist as asked");
            System.exit(1);
        }
        return color;
    }
}
