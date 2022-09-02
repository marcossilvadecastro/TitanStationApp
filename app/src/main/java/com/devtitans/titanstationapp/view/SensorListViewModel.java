package com.devtitans.titanstationapp.view;

import androidx.lifecycle.ViewModel;

import com.devtitans.titanstationapp.model.Sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SensorListViewModel extends ViewModel {

    private List<Sensor> sensors = new ArrayList<>();



    public void fakeSensors(){
        sensors = new ArrayList<Sensor>(
                Arrays.asList(
                        new Sensor("Sensor 1", "desc sensor 1", "100", null),
                        new Sensor("Sensor 2", "desc sensor 2", "10", null),
                        new Sensor("Sensor 3", "desc sensor 3", "99999", null)
                )
        );
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
