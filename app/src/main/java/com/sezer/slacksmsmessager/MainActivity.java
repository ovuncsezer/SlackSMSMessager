package com.sezer.slacksmsmessager;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastReceiverHandler;
import com.sezer.slacksmsmessager.permissions.PermissionHandler;
import com.sezer.slacksmsmessager.slack.SlackRTM;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        /** Asks necessary permissions to user if not given before */
        PermissionHandler.askForPermissions(this);
        /** Registers to RECEIVE_SMS broadcast */
        registerBroadcastReceivers();

        /** Opens Web Socket connection to Slack RTM API */
        SlackRTM.rtmConnect();
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
        /** Unregister all broadcast recievers */
        BroadcastReceiverHandler.unregisterReceivers(this);

        /** Close websocket connection to Slack */
        SlackRTM.getWebSocket().close(1000, null);
    }
}
