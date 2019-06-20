package com.sezer.slacksmsmessager.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * Class used to handle permissions requests required for the app
 */
public class PermissionHandler {

    /** Request code for asking all permissions */
    private static final int PERMISSIONS_ALL = 1;

    /** Array of permissions required */
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
    };

    /** Asking for permission of user for all listed in PERMISSIONS array if not taken before */
    public static void askForPermissions(Activity activity){
        if (!hasPermissions(activity, PERMISSIONS)){
            askForPermissions(activity, PERMISSIONS);
        }
    }

    /** Asking for permission of user for all listed in PERMISSIONS array*/
    public static void askForPermissions(Activity activity, String[] permissions){
        ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_ALL);
    }

    /** Checks if given list of permissions has been taken before
     * Returns false if any of the permissions is not taken from user */
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
