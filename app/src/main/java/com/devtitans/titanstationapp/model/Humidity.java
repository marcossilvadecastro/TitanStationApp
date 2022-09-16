package com.devtitans.titanstationapp.model;

import com.devtitans.titanstationapp.R;

public class Humidity extends Sensor{
    public Humidity(String description, String value) {
        super("Humidity", description, value);

        setImage(R.drawable.ic_outline_water_damage_24);
    }
}
