package com.sezer.slacksmsmessager.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.sezer.slacksmsmessager.MainActivity;
import com.sezer.slacksmsmessager.R;
import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastReceiverHandler;
import com.sezer.slacksmsmessager.slack.SlackMessageHandler;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * Listener class to be notified when a new SMS is received
 * onReceive method is ready to be called after
 * permissions is taken and broadcast receiver registers
 */
public class SMSHandler extends BroadcastReceiver {

    private static final String TAG = SMSHandler.class.getSimpleName();

    /** Space seperated list of phone numbers for SMS to be sent */
    private static final String PHONE_NUMBERS = MainActivity.getAppContext().
            getResources().getString(R.string.phone_numbers);

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "New broadcast received: " + intent.getAction());
        if(intent.getAction().equals(BroadcastReceiverHandler.SMS_RECEIVED)){
            /** Retrieve the last SMS from intent */
            Bundle extras = intent.getExtras();
            if(extras != null){
                Object[] pdus = (Object[]) extras.get("pdus");
                SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[0]);
                String msgBody = message.getMessageBody();
                Log.d(TAG, "Last SMS Received: " + msgBody);

                /** Sends the SMS message to Slack channel */
                SlackMessageHandler.sendMessageToSlack(msgBody);
            }
        }
    }

    /** Sends the given message as SMS to the app defined PHONE_NUMBERS list */
    public static void sendSMS(String smsBody){
        SmsManager smsManager = SmsManager.getDefault();
        for(String number : PHONE_NUMBERS.split(" ")){
            Log.d(TAG, String.format("Sending SMS to number {}: {}",number, smsBody));
            smsManager.sendTextMessage(number, null, smsBody, null, null);
        }
    }
}
