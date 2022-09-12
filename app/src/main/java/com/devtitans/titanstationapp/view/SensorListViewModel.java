package com.devtitans.titanstationapp.view;

import androidx.lifecycle.ViewModel;

import com.devtitans.titanstationapp.model.Sensor;
import com.devtitans.titanstationapp.model.Temperature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SensorListViewModel extends ViewModel {

    private List<Sensor> sensors = new ArrayList<>();



    public void fakeSensors(){
        sensors = new ArrayList<Sensor>(
                Arrays.asList(
                        new Temperature("Temperature", "desc sensor 1", "100"),
                        new Sensor("Humidity", "desc sensor 2", "10"),
                        new Sensor("Pressure", "desc sensor 3", "99999")
                )
        );
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
