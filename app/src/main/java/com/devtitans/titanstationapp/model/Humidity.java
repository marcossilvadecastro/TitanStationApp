package com.devtitans.titanstationapp.model;

import com.devtitans.titanstationapp.R;

public class Humidity extends Sensor{
    public Humidity(String value) {
        super("Humidity", value);

        setImage(R.drawable.ic_outline_water_damage_24);
    }
}
