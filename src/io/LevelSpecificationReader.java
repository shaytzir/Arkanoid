package io;
import java.util.ArrayList;
import java.util.List;

import arkanoid.Block;
import arkanoid.LevelInformation;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author shaytzir
 *
 */
public class LevelSpecificationReader {
    /**
     *
     * @param reader a reader for the levels file
     * @return list of levels information
     */
     public List<LevelInformation> fromReader(java.io.Reader reader) {
         List<LevelInformation> levelsFromFile = new ArrayList<LevelInformation>();
         BufferedReader readsLines = new BufferedReader(reader);
         boolean dealingBlockRead;
         boolean dealingBlockCreate = false;
         try {
             String newLine = null;
             while ((newLine = readsLines.readLine()) != null) {
                 //start of level
                 if (newLine.equals("START_LEVEL")) {
                     LevelFromReadInfo newLevel = new LevelFromReadInfo();
                     newLine = readsLines.readLine();
                     //as long as it isnt over or didnt start with block
                     while ((!(newLine.equals("END_LEVEL")) && (!(newLine == "START_BLOCKS")))) {
                         dealingBlockRead = false;
                         //reads blocks file
                         if (newLine.startsWith("block_definitions")) {
                             dealingBlockRead = true;
                             BlockInfoFromFile blockInfo = new BlockInfoFromFile();
                             //reads the line and creates all blocks factory
                             BlocksFromSymbolsFactory factory = blockInfo.fromReader(new java.io.
                                    InputStreamReader(ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(newLine.split(":")[1].trim())));
                             //adding the factory to the level
                             newLevel.addFactory(factory);
                        }

                        // start creating blocks for the level
                        if (newLine.startsWith("START_BLOCKS")) {
                             dealingBlockCreate = true;
                             int blockStartY = newLevel.getBlocksStartY();
                             BlocksFromSymbolsFactory getFactory = newLevel.getFactory();
                            newLine = readsLines.readLine();
                             //aslong as the blocks writing isnt over
                             while (!(newLine.equals("END_BLOCKS"))) {
                                int blockStartX = newLevel.getBlocksStartX();
                                 boolean onlySpacers = true;
                                 String[] symbols = newLine.split("");
                                 //check if there are only spacers in the line
                                 for (int i = 0; i < symbols.length; i++) {
                                     if (!getFactory.isSpaceSymbol(symbols[i].trim())) {
                                         onlySpacers = false;
                                    }
                                }
                                //if there are also blocks in the line:
                                if (!onlySpacers) {
                                     for (int j = 0; j < symbols.length; j++) {
                                         if (getFactory.isSpaceSymbol(symbols[j])) {
                                             blockStartX = blockStartX + getFactory.getSpaceWidth(symbols[j]);
                                        } else if (getFactory.isBlockSymbol(symbols[j])) {
                                             Block b = getFactory.getBlock(symbols[j], blockStartX, blockStartY);
                                             //add block to the level
                                            newLevel.addBlock(b);
                                            blockStartX = blockStartX + b.getWidth();
                                        }
                                    }
                                }
                                //if there was only spacers/ end of blocks line - get one line down
                                blockStartY = blockStartY + newLevel.getRowHeight();
                                newLine = readsLines.readLine();
                            }
                        }
                        //if its not dealing with block reading or creating add normaly to the level
                        if ((!dealingBlockRead) && (!dealingBlockCreate)) {
                            newLevel.add(newLine);
                        }
                        dealingBlockCreate = false;
                        newLine = readsLines.readLine();

                     }
                     //if no more level info to add, add this level info to the general list
                     if (newLine.equals("END_LEVEL")) {
                         levelsFromFile.add(newLevel);
                     }
                 }

             }
             //return the entire list of levels information
             return levelsFromFile;
       } catch (IOException e) {
           System.out.println("something's wrong");
           System.exit(1);
       }
       return null;
     }
}
