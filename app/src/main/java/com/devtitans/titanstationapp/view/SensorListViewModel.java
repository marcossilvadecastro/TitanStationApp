package com.devtitans.titanstationapp.view;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devtitans.titanstationapp.model.Humidity;
import com.devtitans.titanstationapp.model.Luminosity;
import com.devtitans.titanstationapp.model.Sensor;
import com.devtitans.titanstationapp.model.Temperature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import devtitans.adademanager.AdadeManager;

public class SensorListViewModel extends ViewModel {

    private AdadeManager manager;
    private boolean isConnected = false;
    private List<Sensor> sensors = new ArrayList<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(true);
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<List<Sensor>> _sensorsLD = new MutableLiveData<>(sensors);
    LiveData<List<Sensor>> sensorsLD = _sensorsLD;


    public void initialize() {
        manager = AdadeManager.getInstance();
    }

    public void tryConnect() {
        try {
            isConnected =  manager.connect() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            isConnected =  false;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    private void getData() {
        int count = 0;
        int tryCount = 20;
        int waitTime = 50;

        this.sensors.clear();

        try {
            int vlrTemperature = 0;
            do {
                Thread.sleep(waitTime);
                count++;
                vlrTemperature = manager.getTemperature();
                if (count >= tryCount) {
                    vlrTemperature = -2;
                }
            } while (vlrTemperature == -1);
            sensors.add(new Temperature(vlrTemperature/100 +  " °C"));

            count = 0;
            int vlrHumidity = 0;
            do {
                Thread.sleep(waitTime);
                count++;
                vlrHumidity = manager.getHumidity();
                if (count >= tryCount) {
                    vlrHumidity = -2;
                }
            } while (vlrHumidity == -1);
            sensors.add(new Humidity(vlrHumidity/100 + " %"));

            count = 0;
            int vlrLuminosity = 0;
            do {
                count++;
                Thread.sleep(waitTime);
                vlrLuminosity = manager.getLuminosity();
                if (count >= tryCount) {
                    vlrLuminosity = -2;
                }
                Thread.sleep(waitTime);
            } while (vlrLuminosity == -1);
            sensors.add(new Luminosity( vlrLuminosity + " %"));
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void fakeSensors() {
        sensors = new ArrayList<>(
                Arrays.asList(
                        new Temperature( "32 °C"),
                        new Humidity("80%"),
                        new Luminosity( "99 cd/m2")
                )
        );
    }

    public void refresh() {
        new GetDataAsyncTask().execute();
    }
    private class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            _isLoading.postValue(true);
            if(isConnected){
                getData();
            }else{
                fakeSensors();
            }
            _isLoading.postValue(false);
            _sensorsLD.postValue(sensors);
            return null;
        }
    }


}
