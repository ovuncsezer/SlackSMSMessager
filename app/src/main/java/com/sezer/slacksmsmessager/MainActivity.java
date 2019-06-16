package com.sezer.slacksmsmessager;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastRecieverHandler;
import com.sezer.slacksmsmessager.permissions.PermissionHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForPermissions();
        registerBroadcastReceivers();
    }

    private void askForPermissions(){
        // Asks for Recieve SMS Permission
        PermissionHandler.askForPermission(this, Manifest.permission.RECEIVE_SMS);
    }

    private void registerBroadcastReceivers(){
        BroadcastRecieverHandler.registerSMSRecieveBroadcast(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister all broadcast recievers
        BroadcastRecieverHandler.unregisterRecievers(this);
    }
}
