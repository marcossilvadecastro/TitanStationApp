package com.devtitans.titanstationapp.model;

import com.devtitans.titanstationapp.R;

public class Temperature extends Sensor{

    public Temperature(String description, String value) {
        super("Temperature", description, value);

        setImage(R.drawable.ic_baseline_whatshot_24);

    }
}
