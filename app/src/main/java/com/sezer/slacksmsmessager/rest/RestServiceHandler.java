package com.sezer.slacksmsmessager.rest;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sezer.slacksmsmessager.MainActivity;

/**
 * Created by alper on 14/06/2017.
 */

public class RestServiceHandler extends Application {

    public static final String TAG = RestServiceHandler.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static RestServiceHandler mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized RestServiceHandler getInstance() {
        if (mInstance == null){
            mInstance = new RestServiceHandler();
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(MainActivity.getAppContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
