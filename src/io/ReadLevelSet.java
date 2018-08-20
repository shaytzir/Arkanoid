package io;

import arkanoid.LevelInformation;

//import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaytzir on 16/06/2017.
 */
public class ReadLevelSet {

    /**
     *
     * @param reader a  reader for the file
     * @return list of the levels set information
     */
    public List<LevelSetSelectionFromRead> readAndGetSelection(Reader reader) {
        List<LevelSetSelectionFromRead> allInfo = new ArrayList<LevelSetSelectionFromRead>();
        LineNumberReader readsLines = new LineNumberReader(reader);
        try {
            String newLine = null;
            while ((newLine = readsLines.readLine()) != null) {
                int count = 0;
                String[] keyAndName = null;
                String key = null;
                String name = null;
                List<LevelInformation> levelInfo = null;
                //going throw odd or even lines
                while ((newLine != null) && (count != 2)) {
                    //if it's odd line it contains the symbol and the name
                    if (readsLines.getLineNumber() % 2 != 0) {
                        keyAndName = newLine.split(":");
                        key = keyAndName[0].trim();
                        name = keyAndName[1].trim();
                        count++;
                        newLine = readsLines.readLine();
                    } else {
                        //its even and contain the levels path
                        String levelSpecificator = newLine.trim();
                        //returnn list of levels for this specific set
                        levelInfo = new LevelSpecificationReader().
                                fromReader(new InputStreamReader(ClassLoader.getSystemClassLoader().
                                        getResourceAsStream(levelSpecificator)));
                        count++;
                    }
                }
                //return the list of level set: symbol name and list of level information
                LevelSetSelectionFromRead select = new LevelSetSelectionFromRead(key, name, levelInfo);
                allInfo.add(select);
            }
            return allInfo;
        } catch (IOException e) {
            System.out.println("something went wrong");
            System.exit(1);
        }
        return null;
    }
}