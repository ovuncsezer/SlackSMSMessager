package com.sezer.slacksmsmessager.slack;

import android.util.Log;

import com.sezer.slacksmsmessager.MainActivity;
import com.sezer.slacksmsmessager.R;
import com.sezer.slacksmsmessager.sms.SMSHandler;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author ovuncsezer on 20/06/2019
 *
 * Class implemented to build the bridge between SMS - Slack messages
 */
public class SlackMessageHandler {

    private static final String TAG = SlackMessageHandler.class.getSimpleName();

    /** Unique ID of the Slack channel to be used to send SMS messages */
    private static String CHANNEL_ID = MainActivity.getAppContext().getResources()
            .getString(R.string.slack_channel_id);

    /** Called when a new message is received to Slack, send the message as an SMS
     * @param event JSONObject of the message event received from Slack RTM API */
    public static void handleReceivingSlackMessage(JSONObject event){
        try {
            String message = event.getString("text");
            String timestamp = event.getString("event_ts");

            /** Sends the received message as SMS */
            SMSHandler.sendSMS(message);
        } catch (JSONException e) {
            Log.e(TAG, "Error getting attributes from Slack event", e);
        }
    }

    /** Called when a new SMS is received to send message to Slack via RTM API
     * @param message the SMS message received */
    public static void sendMessageToSlack(String message){

        JSONObject sendMessageJSON = new JSONObject();
        try {
            /** Constructs the JSON object to send Slack RTM API*/
            sendMessageJSON.put("id", System.currentTimeMillis());
            sendMessageJSON.put("type", "message");
            sendMessageJSON.put("channel", CHANNEL_ID);
            sendMessageJSON.put("text", message);
        } catch (JSONException e) {
            Log.e(TAG, "Error constructing JSON from SMS for Slack RTM API", e);
        }

        /** Send SMS message to Slack RTM API via websocket */
        SlackRTM.sendToSocket(sendMessageJSON.toString());
    }

    /** Sets the unique ID of the Slack channel to be used to send messages */
    public static void setChannelID(String channelID){
        CHANNEL_ID = channelID;
    }
}
