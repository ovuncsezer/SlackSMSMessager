package com.sezer.slacksmsmessager.permissions;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

import java.util.HashMap;
import java.util.Map;

public class PermissionHandler {

    private static final Map<String, Integer> permissionMap = new HashMap<String, Integer>(){
        {
            put(Manifest.permission.RECEIVE_SMS, 10);
        }
    };

    public static void askForPermission(Activity activity, String permissionType){
        askForPermission(activity, permissionType, permissionMap.get(permissionType));
    }

    public static void askForPermission(Activity activity,
                                        String permissionType,
                                        int requestCode){
        ActivityCompat.requestPermissions(activity, new String[]{permissionType}, requestCode);
    }


}
