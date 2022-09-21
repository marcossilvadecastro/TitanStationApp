package com.devtitans.titanstationapp.model;

import com.devtitans.titanstationapp.R;

public class Luminosity extends Sensor{

    public Luminosity(String value) {
        super("Luminosity", value);

        setImage(R.drawable.ic_outline_wb_sunny_24);
    }
}
