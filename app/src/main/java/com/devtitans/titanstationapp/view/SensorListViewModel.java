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
import java.util.List;

import devtitans.adademanager.AdadeManager;

public class SensorListViewModel extends ViewModel {

    private AdadeManager manager;
    private boolean isConnected = false;
    private List<Sensor> sensors = new ArrayList<>();


    public void initialize() {
        manager = AdadeManager.getInstance();
    }

    public void tryConnect() {
        isConnected = true; //getConnection();
    }


    private boolean getConnection() {
        try {
            return manager.connect() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    private final Handler mGetDataHandler = new Handler(Looper.myLooper());
    private final Runnable mGetDataRunnable = new Runnable() {
        @Override
        public void run() {
            getData();
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
            sensors.add(new Temperature("" + vlrTemperature));

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
            sensors.add(new Humidity("" + vlrHumidity));

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
            sensors.add(new Luminosity("" + vlrLuminosity));
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void fakeSensors() {
        sensors = new ArrayList<Sensor>(
                Arrays.asList(
                        new Temperature( "32 °C"),
                        new Humidity("80%"),
                        new Luminosity( "99 cd/m2")
                )
        );
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void refresh() {
        mGetDataHandler.removeCallbacks(mGetDataRunnable);
        mGetDataHandler.post(mGetDataRunnable);
    }
}
