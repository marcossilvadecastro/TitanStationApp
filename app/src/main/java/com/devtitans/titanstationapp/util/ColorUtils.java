package com.devtitans.titanstationapp.util;

import android.graphics.Color;

import java.util.Random;

public class ColorUtils {

    public static int getRandomColorByName(String name) {
        Random rnd = new Random(name.hashCode());
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
