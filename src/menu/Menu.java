package menu;

import animation.Animation;

/**
 *
 * @param <T>
 */
public interface Menu<T> extends Animation {
    /**
     *
     * @param key symbol for choice
     * @param message the message written on screen
     * @param returnVal the value the choice returns
     */
       void addSelection(String key, String message, T returnVal);

    /**
     *
     * @return the status of the menu
     */
    T getStatus();

    /**
     * adding a sub menu to the current menu.
     * @param key symbol for choicr
     * @param message a message written on screen
     * @param subMenu a submenu to add as a choice
     */
       void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     *
     * @param s changing key to true;
     */
    void clicked(String s);
    }
