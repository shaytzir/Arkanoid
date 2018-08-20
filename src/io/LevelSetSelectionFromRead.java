package io;

import arkanoid.LevelInformation;

import java.util.List;

/**
 * Created by shaytzir on 16/06/2017.
 */
public class LevelSetSelectionFromRead {
    private String key;
    private String name;
    private List<LevelInformation> levelsInfo;

    /**
     * constructor.
     * @param key a key for the levels
     * @param name the name of the levels
     * @param levelsInfo all levels information
     */
    public  LevelSetSelectionFromRead(String key, String name, List<LevelInformation> levelsInfo) {
        this.key = key;
        this.name = name;
        this.levelsInfo = levelsInfo;
    }

    /**
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the levels information list
     */
    public List<LevelInformation> getLevelsInfo() {
        return this.levelsInfo;
    }
}