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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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
            if(vlrTemperature != -2) {
                vlrTemperature /= 100;
            }
            sensors.add(new Temperature(vlrTemperature + " °C"));

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
            if(vlrHumidity != -2) {
                vlrHumidity /= 100;
            }
            sensors.add(new Humidity(vlrHumidity + " %"));

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

    private String leitura = null;
    public void setLeitura(List<Sensor> sensors){
        float temperatura = 0;
        float umidade = 0;
        float luminosidade = 0;
        int chuva = 0;
        float qchuva = 0;
        float vento = 0;
        float direcao = 0;

        for(Sensor sensor : sensors){
            if(sensor instanceof Temperature){
                temperatura = Float.parseFloat(sensor.getValue());
            }else if(sensor instanceof Luminosity){
                luminosidade = Float.parseFloat(sensor.getValue());
            }else if(sensor instanceof Humidity) {
                umidade =  Float.parseFloat(sensor.getValue());
            }
        }

        this.leitura = String.format("{\"Temperatura\": %.2f," +
                "\"Umidade\": %.2f," +
                "\"Luminosidade\": %.2f," +
                "\"Chuva\": %d," +
                "\"Qchuva\": %.2f," +
                "\"Vento\": %.2f," +
                "\"Direcao\": %s}",temperatura,umidade,luminosidade,chuva,qchuva,vento,direcao);

        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd-MM-yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH-mm-ss").format(dataHoraAtual);
        databaseReference.child("leituras").child(data + "-" + hora).setValue(leitura);
    }

    public void fakeSensors() {
        sensors = new ArrayList<>(
                Arrays.asList(
                        new Temperature( "99"),
                        new Humidity("99"),
                        new Luminosity( "99")
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
