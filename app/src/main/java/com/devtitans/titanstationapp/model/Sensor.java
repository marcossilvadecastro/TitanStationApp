package com.devtitans.titanstationapp.model;
import com.devtitans.titanstationapp.util.ColorUtils;


public class Sensor {

    private String name;
    private String value;
    private int image;
    private int color;

    public Sensor(String name, String value) {
        this.name = name;
        this.value = value;
        this.color = ColorUtils.getRandomColorByName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
