package com.devtitans.titanstationapp.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class GetSensorsWorker extends Worker {

    public GetSensorsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("GetSensorsWorker", " trabalhando");
        return Result.retry();
    }
}
