package io;

import arkanoid.Block;

/**
 * Created by shaytzir on 13/06/2017.
 */
public interface BlockCreator {
    /**
     *
     * @param xpos x position
     * @param ypos y position
     * @return the wanted block in the position xpos,ypos
     */
    Block create(int xpos, int ypos);
}
