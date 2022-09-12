package com.devtitans.titanstationapp.model;

import android.graphics.Color;
import android.media.Image;

import com.devtitans.titanstationapp.util.ColorUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sensor {

    private String name;
    private String description;
    private String value;
    private Image image;
    private int color;

    public Sensor(String name, String description, String value, Image image) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.image = image;
        this.color = ColorUtils.getRandomColorByName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
