package com.devtitans.titanstationapp.model;

import com.devtitans.titanstationapp.R;

public class Temperature extends Sensor{

    public Temperature(String value) {
        super("Temperature", value);

        setImage(R.drawable.ic_baseline_whatshot_24);

    }
}
