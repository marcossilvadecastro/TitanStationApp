package com.devtitans.titanstationapp.view;

import androidx.lifecycle.ViewModel;

import com.devtitans.titanstationapp.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorListViewModel extends ViewModel {

    private List<Sensor> sensors = new ArrayList<>();

    private void fakeSensors(){
        Sensor temperature = new Sensor("Temperature", "Temperature", "123123", null);
        sensors.add(temperature);
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
