package com.sezer.slacksmsmessager;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastReceiverHandler;
import com.sezer.slacksmsmessager.firebase.SlackFirebaseMessagingService;
import com.sezer.slacksmsmessager.permissions.PermissionHandler;

public class MainActivity extends AppCompatActivity {

    /** Stores application context to be accessed by other classes */
    private static Context context;
    /** REST URL of the application server */
    private static String SERVER_API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        initializeApplication();
    }

    private void initializeApplication(){
        /** Asks necessary permissions to user if not given before */
        PermissionHandler.askForPermissions(this);
        /** Registers to RECEIVE_SMS broadcast */
        registerBroadcastReceivers();
        /** Set rest api url of the server*/
        SERVER_API_URL = context.getResources().getString(R.string.server_api_url);
        /** Get Firebase registration token and send to server */
        SlackFirebaseMessagingService.sendRegistrationToken();
    }

    private void registerBroadcastReceivers(){
        BroadcastReceiverHandler.registerSMSRecieveBroadcast(this);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        /** Unregister all broadcasted recievers */
        BroadcastReceiverHandler.unregisterReceivers(this);
    }

    public static String getServerApiUrl(){
        return SERVER_API_URL;
    }
}
