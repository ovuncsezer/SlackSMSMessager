package com.sezer.slacksmsmessager.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sezer.slacksmsmessager.MainActivity;
import com.sezer.slacksmsmessager.rest.RestServiceHandler;
import com.sezer.slacksmsmessager.sms.SMSHandler;

import java.util.Map;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * Class used to handle firebase operations
 * Gets token, saves token, handle received notifications
 */
public class SlackFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = SlackFirebaseMessagingService.class.getSimpleName();

    /** Firebase Token provided by Firebase Servers */
    private static String firebaseToken;

    /** Called when firebase token is refreshed by Firebase
     * @param token new token in String format */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token changed: " + token);
        firebaseToken = token;
        /** Sends new token to the application server */
        sendRegistrationTokenToServer();
    }

    /** Called when a new push notification is received
     * @param remoteMessage contains data send by the notification */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        // Check if message contains a data payload.
        if (data.size() > 0) {
            Log.d(TAG, "Message data payload: " + data);
            String message = data.get("message");
            /** Sends the message inside the notification data as SMS */
            SMSHandler.sendSMS(message);
        }
    }

    /** Called while app initialization progress to save firebase token
     * on application server if not saved before or lost */
    public static void sendRegistrationToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        /** Get new Instance ID as firebase token */
                        firebaseToken = task.getResult().getToken();
                        Log.d(TAG, "Registration Token: " + firebaseToken);
                        sendRegistrationTokenToServer();
                    }
                });
    }

    /** Sends the firebase token to the application server via rest api */
    private static void sendRegistrationTokenToServer(){

        /** Builds the connection request object for RTM API */
        StringRequest serverRequest = new StringRequest(
                Request.Method.GET,
                MainActivity.getServerApiUrl() + "registerToken/" + firebaseToken,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "Registration Token sent!");
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on sending token to server", error);
                    }
                }
        );

        Log.d(TAG, "Sending registration token to server...");
        /** Sends firebase token via rest service */
        RestServiceHandler.getInstance().addToRequestQueue(serverRequest);
    }
}
