package com.sezer.slacksmsmessager.broadcastrecievers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.sezer.slacksmsmessager.sms.SMSListener;

import java.util.HashMap;
import java.util.Map;

public class BroadcastRecieverHandler {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private static Map<String, BroadcastReceiver> receiverMap = new HashMap<>();

    private static void registerBroadcastReciever(Activity activity, String broadcastName,
                                                  BroadcastReceiver receiver){
        IntentFilter intentFilter = new IntentFilter(broadcastName);
        activity.registerReceiver(receiver, intentFilter);
    }

    public static void registerSMSRecieveBroadcast(Activity activity){
        SMSListener smsListener = new SMSListener();
        receiverMap.put(SMS_RECEIVED, smsListener);
        registerBroadcastReciever(activity, SMS_RECEIVED, smsListener);
    }

    public static void unregisterRecievers(Activity activity) {
        for(BroadcastReceiver receiver : receiverMap.values()){
            activity.unregisterReceiver(receiver);
        }
    }
}
