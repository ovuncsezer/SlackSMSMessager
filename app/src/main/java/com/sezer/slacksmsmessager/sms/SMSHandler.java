package com.sezer.slacksmsmessager.sms;

import android.telephony.SmsManager;
import android.util.Log;

public class SMSHandler {

    public static void sendSMS(String smsBody){
        Log.d("SMSHandler", "Sending SMS: " + smsBody);
        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage("5556", null,
                smsBody, null, null);
    }
}
