package menu;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 *
 * @param <T> type of menu
 */
public class MenuAnimation<T> implements Menu<T> {
    private String name;
    private KeyboardSensor keyboard;
    private Map<String, String> mapMessage;
    private Map<String, T> mapReturn;
    private T status;
    private Map<String, Menu<T>> subMenu;
    private AnimationRunner runner;
    private List<String> allKeys;
    private Map<String, Boolean> allKeysPressed;
    private boolean stop;
    private Map<String, Boolean> wasPressed;

    /**
     *
     * @param name name of menu
     * @param keyboard a keyboard sensor
     * @param runner animation runner
     */
    public MenuAnimation(String name, KeyboardSensor keyboard, AnimationRunner runner) {
        this.name = name;
        this.keyboard = keyboard;
        this.mapMessage = new TreeMap<String, String>();
        this.mapReturn = new TreeMap<String, T>();
        this.status = null;
        this.subMenu = new TreeMap<String, Menu<T>>();
        this.runner = runner;
        this.allKeys = new ArrayList<String>();
        this.allKeysPressed = new TreeMap<String, Boolean>();
        this.stop = false;
        this.wasPressed = new TreeMap<String, Boolean>();
    }


    @Override
    public void doOneFrame(DrawSurface d, double dt) {
            d.setColor(new Color(200, 115, 171));
            d.fillRectangle(0, 0, 800, 600);
            d.setColor(new Color(0, 0, 0));
            d.drawText((int) (d.getWidth() * 0.3) + 2, 150, "MAIN MENU", 40);
            d.drawText((int) (d.getWidth() * 0.3) - 2, 150, "MAIN MENU", 40);
            d.drawText((int) (d.getWidth() * 0.3), 150 + 2, "MAIN MENU", 40);
            d.drawText((int) (d.getWidth() * 0.3), 150 - 2, "MAIN MENU", 40);
            d.setColor(java.awt.Color.white);
            d.drawText((int) (d.getWidth() * 0.3), 150, "MAIN MENU", 40);
            d.drawLine((int) (d.getWidth() * 0.25), 155, 500, 155);
            int screenL = 600;
            int startY = 200;
            int perMessage = (screenL / 4) / this.mapMessage.size();
            for (String key : this.mapMessage.keySet()) {
                d.drawText(150, startY, "(" + key + ")" + this.mapMessage.get(key), 30);
                startY = startY + perMessage;
            }
        }


    @Override
    public boolean shouldStop() {

        for (String key: this.allKeysPressed.keySet()) {
            Menu<T> gotSubMenu = this.subMenu.get(key);
            if (this.keyboard.isPressed(key)) {
                if ((this.mapReturn.get(key) != null) && (!(this.wasPressed.get(key)))) {
                    this.status = this.mapReturn.get(key);
                    this.stop = true;
                    return this.stop;
                } else if ((gotSubMenu != null) && (!this.wasPressed.get(key))) {
                    this.subMenu.get(key).clicked(key);
                    this.runner.run(this.subMenu.get(key));
                    this.status = this.subMenu.get(key).getStatus();
                    this.stop = true;
                    return this.stop;
                }
            } else {
                this.wasPressed.replace(key, false);
            }
        }
        this.stop = false;
        return  this.stop;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.allKeys.add(key);
        this.allKeysPressed.put(key, true);
        this.mapMessage.put(key, message);
        this.mapReturn.put(key, returnVal);
        this.wasPressed.put(key, false);
    }

    @Override
    public T getStatus() {
        return this.status;
    }


    @Override
    public void addSubMenu(String key, String message, Menu<T> subMen) {
        this.allKeys.add(key);
        this.allKeysPressed.put(key, true);
        this.mapMessage.put(key, message);
        this.subMenu.put(key, subMen);
        this.wasPressed.put(key, false);
    }

    /**
     *
     * @param s a String ket
     */
    public void clicked(String s) {
        if (this.wasPressed.get(s) != null) {
            this.wasPressed.replace(s, true);
        }
    }



}
