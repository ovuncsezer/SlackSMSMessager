package com.sezer.slacksmsmessager.broadcastrecievers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.util.Log;

import com.sezer.slacksmsmessager.sms.SMSHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * Class used to handle broadcast receiver operations
 * Registers and unregisters the receivers
 */
public class BroadcastReceiverHandler {

    private static final String TAG = BroadcastReceiverHandler.class.getSimpleName();

    /** Permission required to be notified when SMS receives */
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    /** Permission vs Receiver Map, used to unregister receivers */
    private static Map<String, BroadcastReceiver> receiverMap = new HashMap<>();

    /** Registers given receiver to the activity */
    private static void registerBroadcastReciever(Activity activity, String broadcastName,
                                                  BroadcastReceiver receiver){
        IntentFilter intentFilter = new IntentFilter(broadcastName);

        Log.d(TAG, "Registering receiver: " + broadcastName);
        activity.registerReceiver(receiver, intentFilter);
    }

    /** Registers RECEIVE_SMS receiver to be notified when SMS receives */
    public static void registerSMSRecieveBroadcast(Activity activity){
        SMSHandler smsListener = new SMSHandler();
        receiverMap.put(SMS_RECEIVED, smsListener);

        registerBroadcastReciever(activity, SMS_RECEIVED, smsListener);
    }

    /** Unregisters all receivers during closure */
    public static void unregisterReceivers(Activity activity) {
        for(BroadcastReceiver receiver : receiverMap.values()){
            Log.d(TAG, "Unregistering receiver: " + receiver);
            activity.unregisterReceiver(receiver);
        }
    }
}
