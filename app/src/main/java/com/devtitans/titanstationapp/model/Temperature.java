package com.devtitans.titanstationapp.model;

import com.devtitans.titanstationapp.R;

public class Temperature extends Sensor{

    public Temperature(String name, String description, String value) {
        super(name, description, value);

        setImage(R.drawable.ic_launcher_background);
    }
}
