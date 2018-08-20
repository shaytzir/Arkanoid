package io;
import java.io.BufferedReader;
import java.util.Map;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by shaytzir on 13/06/2017.
 */
public class BlockInfoFromFile {

    private static final String DEFAULT = "default";
    private static final String BLOCK_DEF = "bdef";
    private static final String SPACER_DEF = "sdef";

    /**
     *
     * @param reader a reader for the blocks info file
     * @return a factory from symbol to block creator
     */
    public BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();
        String[] defaultString = null;
        Map<String, String> defaultsMap = new TreeMap<String, String>();
        BufferedReader readsLines = new BufferedReader(reader);

        try {
            String newLine = null;
            while ((newLine = readsLines.readLine()) != null) {
                //setting default values of blocks
                if (newLine.startsWith(DEFAULT)) {
                    defaultString = newLine.substring(DEFAULT.length() + 1).split("\\s+");
                    //creating a default Map
                    for (String s: defaultString) {
                        defaultsMap.put(s.split(":")[0].trim(), s.split(":")[1].trim());
                    }
                    //if this is a new block definition
                } else if (newLine.startsWith(BLOCK_DEF)) {
                    String symbol = null;
                    Map<String, String> blockDef = new TreeMap<String, String>();
                    String[] blockDefString = newLine.substring(BLOCK_DEF.length() + 1).split("\\s+");
                    //and add all default values also to this block map
                    if (!defaultsMap.isEmpty()) {
                        for (String key: defaultsMap.keySet()) {
                            blockDef.put(key, defaultsMap.get(key));
                        }
                    }
                    //creating Block info Map including all of the default keys and values
                    for (String blockKey: blockDefString) {
                        blockDef.put(blockKey.split(":")[0], blockKey.split(":")[1]);
                        if (blockKey.split(":")[0].trim().equals("symbol")) {
                            symbol = blockKey.split(":")[1].trim();
                        }
                    }
                    //by using the specific block create, use all of the maps info and create a block creator
                    SpecificBlockCreate blockCreator = new SpecificBlockCreate(blockDef);
                    //add it to the factory
                    factory.addToFactory(symbol, blockCreator);
                    //if it's a spacer definition
                } else if (newLine.startsWith(SPACER_DEF)) {
                    String[] symbolAndWidthS = newLine.substring(SPACER_DEF.length() + 1).split("\\s+");
                    Map<String, String> symbolAndWidthMap = new TreeMap<String, String>();
                    for (String str: symbolAndWidthS) {
                        symbolAndWidthMap.put(str.split(":")[0].trim(), str.split(":")[1].trim());
                    }
                    //add this symbol to the factory
                    factory.addToFactory(symbolAndWidthMap.
                            get("symbol"), Integer.parseInt(symbolAndWidthMap.get("width")));
                }
            }
            return factory;
        } catch (IOException e) {
            System.out.print("SOMETHINGS WRONG ABOUT READING BLOCK FILES");
            System.exit(1);
        }
        return null;

    }
}