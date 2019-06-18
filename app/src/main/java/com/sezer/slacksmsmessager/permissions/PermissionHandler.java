package com.sezer.slacksmsmessager.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionHandler {

    private static final int PERMISSIONS_ALL = 1;

    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
    };

    public static void askForPermissions(Activity activity){
        if (!hasPermissions(activity, PERMISSIONS)){
            askForPermissions(activity, PERMISSIONS);
        }
    }

    public static void askForPermissions(Activity activity, String[] permissions){
        ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_ALL);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }
}
