package com.sezer.slacksmsmessager.slack;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * WebSocketListener to handle websocket operations such as inital connection
 * or receiveing new messages from socket
 */
public class SlackWebsocketListener extends WebSocketListener {

    public static final String TAG = SlackWebsocketListener.class.getSimpleName();
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.i(TAG, "Websocket connection is established!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.v(TAG, "Received:" + text);

        try {
            JSONObject eventJSON = new JSONObject(text);
            if (eventJSON.has("type") && eventJSON.get("type").equals("message")){
                Log.d(TAG, "New Message Received:" + text);
                SlackMessageHandler.handleReceivingSlackMessage(eventJSON);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error converting action to JSON", e);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(TAG,"Received bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d(TAG, "Closing socket: " + code + " / " + reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG, "Error : " + t.getMessage(), t);
    }
}
