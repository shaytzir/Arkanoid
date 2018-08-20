package io;
import java.awt.Color;
import java.awt.Image;

import arkanoid.GameLevel;
import biuoop.DrawSurface;
import coregame.Sprite;

/**
 * @author shaytzir
 *
 */
public class Background implements Sprite {
    private Image image;
    private Color color;

    /**
     * constructor.
     * @param image an image, setting color as null
     */
    public Background(Image image) {
        this.image = image;
        this.color = null;
    }

    /**
     * constructor.
     * @param c a color, setting image as null
     */
    public Background(Color c) {
        this.image = null;
        this.color = c;
    }

    /**
     *
     * @param d a drawing surface to draw sprite on
     */
    public void drawOn(DrawSurface d) {
        if (this.image == null) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, 800, 600);
        } else {
            d.drawImage(0, 0, this.image);
        }
    }

    @Override
    public void timePassed(double dt) {
        return;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     *
     * @return true if this background holds an image, else - false
     */
    public boolean isImage() {
        if ((this.image != null) && (this.color == null)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return true if this background holds a color, else - false
     */
    public boolean isColor() {
        if ((this.image == null) && (this.color != null)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return the color of this background
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     *
     * @return the image of this background
     */
    public Image getImage() {
        return this.image;
    }
}
