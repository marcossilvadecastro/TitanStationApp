package com.devtitans.titanstationapp.model;

import android.media.Image;

public class Sensor {
    private String name;
    private String description;
    private String value;
    private Image image;

    public Sensor(String name, String description, String value, Image image) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.image = image;
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
}
