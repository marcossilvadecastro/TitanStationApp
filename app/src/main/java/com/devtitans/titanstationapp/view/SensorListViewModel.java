package com.devtitans.titanstationapp.view;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

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

    private final Handler mGetDataHandler = new Handler(Looper.myLooper());
    private final Runnable mGetDataRunnable = new Runnable() {
        @Override
        public void run() {
            if(isConnected){
                getData();
            }else{
                fakeSensors();
            }

            if(mOnGetData != null){
                for (IOnGetData data : mOnGetData){
                    if(data != null){
                        data.onGetDada(sensors);
                    }
                }
            }
        }
    };

    private void getData() {
        int count = 0;
        int tryCount = 100;
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

    private Set<IOnGetData> mOnGetData = new HashSet<>();
    public void refresh(IOnGetData onGetData) {
        mOnGetData.add(onGetData);
        mGetDataHandler.removeCallbacks(mGetDataRunnable);
        mGetDataHandler.post(mGetDataRunnable);
    }

    interface IOnGetData {

        void onGetDada(List<Sensor> sensors);
    }

}
