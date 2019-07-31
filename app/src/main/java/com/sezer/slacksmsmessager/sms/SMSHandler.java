package com.sezer.slacksmsmessager.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sezer.slacksmsmessager.MainActivity;
import com.sezer.slacksmsmessager.R;
import com.sezer.slacksmsmessager.broadcastrecievers.BroadcastReceiverHandler;
import com.sezer.slacksmsmessager.rest.RestServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

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
    private static final List<String> PHONE_NUMBERS = Arrays.asList(MainActivity.getAppContext().
            getResources().getString(R.string.phone_numbers).split(" "));

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "New broadcast received: " + intent.getAction());
        if(intent.getAction().equals(BroadcastReceiverHandler.SMS_RECEIVED)){
            /** Retrieve the last SMS from intent */
            Bundle extras = intent.getExtras();
            if(extras != null){
                Object[] pdus = (Object[]) extras.get("pdus");
                SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[0]);
                String sender = message.getOriginatingAddress();
                if (PHONE_NUMBERS.contains(sender)){
                    Log.d(TAG, "Sender: " + sender);
                    String msgBody = message.getMessageBody();
                    Log.d(TAG, "Last SMS Received: " + msgBody);

                    /** Sends the SMS message to Slack channel */
                    try {
                        sendSMSToServer(msgBody);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error on sending SMS to server", e);
                    }
                }
            }
        }
    }

    /** Sends the given message as SMS to the app defined PHONE_NUMBERS list */
    public static void sendSMS(String smsBody){
        SmsManager smsManager = SmsManager.getDefault();
        for(String number : PHONE_NUMBERS){
            Log.d(TAG, String.format("Sending SMS to number {}: {}",number, smsBody));
            smsManager.sendTextMessage(number, null, smsBody, null, null);
        }
    }

    private static void sendSMSToServer(String message) throws JSONException {
        /** Builds the connection request object for RTM API */
        JsonObjectRequest serverRequest = new JsonObjectRequest(
                Request.Method.POST,
                MainActivity.getServerApiUrl() + "sms",
                new JSONObject("{'message':\"" + message + "\"}"),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "SMS sent to server!");
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on sending sms to server", error);
                    }
                }
        );

        Log.d(TAG, "Sending SMS message to server...");
        /** Sends firebase token via rest service */
        RestServiceHandler.getInstance().addToRequestQueue(serverRequest);
    }
}
