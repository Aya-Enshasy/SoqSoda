package com.lira_turkish.dollarstocks.utils.util;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {

    public static boolean isPermissionGranted(String[] permissions, Context context) {
        boolean isGranted = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
        }
        return isGranted;
    }
}