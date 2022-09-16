package com.devtitans.titanstationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowInsets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.devtitans.titanstationapp.databinding.ActivitySplashScreenBinding;
import com.devtitans.titanstationapp.view.MainActivity;


public class SplashScreenActivity extends AppCompatActivity {

    private static final int UI_ANIMATION_DELAY = 10;
    private static final int NAVIGATION_DELAY = 1500;

    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {

                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };



    private final Runnable movetoMainRunnable = new Runnable() {
        @Override
        public void run() {
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(SplashScreenActivity.this,
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent, bundle);
        }
    };

    private View mControlsView;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        mHideHandler.postDelayed(movetoMainRunnable, NAVIGATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}