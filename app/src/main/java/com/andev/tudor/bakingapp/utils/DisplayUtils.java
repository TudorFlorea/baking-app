package com.andev.tudor.bakingapp.utils;

import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by tudor on 16.04.2018.
 */

public class DisplayUtils {

    public static int numberOfColumns(WindowManager manager, int gridWidth) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = gridWidth * 2;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 1) {
            return 1;
        } else {
            return nColumns;
        }

    }

}