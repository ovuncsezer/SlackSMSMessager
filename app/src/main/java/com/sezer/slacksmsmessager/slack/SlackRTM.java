package com.sezer.slacksmsmessager.slack;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sezer.slacksmsmessager.rest.RestServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * Class implemented to connect Slack RTM API and send messages via websocket
 */
public class SlackRTM {

    private static final String TAG = SlackRTM.class.getSimpleName();

    /** Websocket client */
    private static OkHttpClient client;
    /** Websocket Listener */
    private static SlackWebsocketListener listener;
    /** Websocket object returned after connection is established */
    private static WebSocket webSocket;
    /** Websocket URL to be connect */
    private static String wsURL;

    /** Sends initial REST request to connect Slack RTM API */
    public static void rtmConnect(){

        /** Builds the connection request object for RTM API */
        StringRequest rtmConnectRequest = new SlackRTMConnectRequest(
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "rtm.connect response is received: ");
                        Log.v(TAG, response);
                        try {
                            /** Retrieves the websocket URL sent by initial request to RTM API */
                            JSONObject json = new JSONObject(response);
                            wsURL = (String) json.get("url");

                            connect();
                        } catch (JSONException e) {
                            Log.e(TAG, "Error on converting response to JSON", e);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getSimpleName(), "Error on Slack connection", error);
                    }
                }
         );

        /** Sends the rtm.connect request via rest service */
        RestServiceHandler.getInstance().addToRequestQueue(rtmConnectRequest);
    }

    /** Opens a connection to Slack RTM API via websocket */
    public static void connect(){
        Log.d("", "Connecting to Slack RTM API via websocket...");
        client = new OkHttpClient();
        listener = new SlackWebsocketListener();

        Request request = new Request.Builder().url(wsURL).build();
        webSocket = client.newWebSocket(request, listener);
    }

    /** Sends the text data to Slack RTM API through websocket
     * @param json has to be a String object of the json data with keys below
     * "id": unique id of the message to be sent
     * "type": has to be "message" for chat messages
     * "channel": unique ID of the slack channel that will receive the message
     * "text": the text value of the message to be sen */
    public static void sendToSocket(String json){
        webSocket.send(json);
    }

    /** Returns the websocket object */
    public static WebSocket getWebSocket(){
        return webSocket;
    }
}
