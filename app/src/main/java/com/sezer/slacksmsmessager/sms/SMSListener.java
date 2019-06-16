package com.sezer.slacksmsmessager.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastRecieverHandler;

public class SMSListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(BroadcastRecieverHandler.SMS_RECEIVED)){
            //---get the SMS message passed in---
            Bundle extras = intent.getExtras();
            if(extras != null){
                Object[] pdus = (Object[]) extras.get("pdus");
                SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[0]);
                String msgBody = message.getMessageBody();
                Log.d(this.getClass().getSimpleName(), msgBody);
            }
        }
    }
}
