package io;

import arkanoid.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shaytzir on 13/06/2017.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<String, Integer>();
        this.blockCreators = new TreeMap<String, BlockCreator>();
    }

    /**
     * adding to the factory.
     * @param symbol kind of key
     * @param creator block crator associated with it
     */
    public void addToFactory(String symbol, SpecificBlockCreate creator) {
        this.blockCreators.put(symbol, creator);
        return;
    }

    /**
     * adding spacers to factory.
     * @param symbol key
     * @param width spacer associated with it
     */
    public  void addToFactory(String symbol, int width) {
        this.spacerWidths.put(symbol, width);
        return;
    }
    /**
     *
     * @param s String a symbol
     * @return true if s is a valid space symbol
     */
    public boolean isSpaceSymbol(String s) {
        if (this.spacerWidths.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param s String block symbol
     * @return true if s is a valid block symbol
     */
    public boolean isBlockSymbol(String s) {
        if (this.blockCreators.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param s a block symbol
     * @param xpos x position
     * @param ypos y position
     * @return block according to the definitions for symbol. located at xpos,ypos
     */
    public Block getBlock(String s, int xpos, int ypos) {
        if (this.blockCreators.containsKey(s)) {
            return this.blockCreators.get(s).create(xpos, ypos);
        }
        return null;
    }

    /**
     *
     * @param s space symbol
     * @return the width in pixels associated with the given spacer symbol
     */
    public int getSpaceWidth(String s) {
        if (this.isSpaceSymbol(s)) {
            return this.spacerWidths.get(s);
        } else {
            // if doesnt contain!!!!!!!
            return -10;
        }
    }
}
