package com.devtitans.titanstationapp.view;

import androidx.lifecycle.ViewModel;

import com.devtitans.titanstationapp.model.Humidity;
import com.devtitans.titanstationapp.model.Luminosity;
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
                        new Temperature("desc sensor 1", "32 °C"),
                        new Humidity( "desc sensor 2", "80%"),
                        new Luminosity("desc sensor 3", "99 cd/m2")
                )
        );
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
