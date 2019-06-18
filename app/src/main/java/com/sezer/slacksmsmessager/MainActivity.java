package com.sezer.slacksmsmessager;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastRecieverHandler;
import com.sezer.slacksmsmessager.permissions.PermissionHandler;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        PermissionHandler.askForPermissions(this);
        registerBroadcastReceivers();
        String str = getResources().getString(R.string.app_name);
    }

    private void registerBroadcastReceivers(){
        BroadcastRecieverHandler.registerSMSRecieveBroadcast(this);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister all broadcast recievers
        BroadcastRecieverHandler.unregisterRecievers(this);
    }


}
