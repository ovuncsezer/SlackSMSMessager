package com.sezer.slacksmsmessager.slack;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.sezer.slacksmsmessager.MainActivity;
import com.sezer.slacksmsmessager.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * Overrides StringRequest to send GET request throught REST to establish initial
 * connection with Slack RTM API
 */
public class SlackRTMConnectRequest extends StringRequest {

    /** OAuth token specific to the Slack app provided by Slack */
    private static final String token = MainActivity.getAppContext().
            getResources().getString(R.string.slack_oauth_token);
    /** URL of the REST service for initial connection with Slack RTM API */
    private static final String restURL = "https://slack.com/api/rtm.connect?token=" + token;

    public SlackRTMConnectRequest(Response.Listener<String> listener,
                                  Response.ErrorListener errorListener) {
        super(Request.Method.GET, restURL, listener, errorListener);
    }

    /** Overrides Content-Type header * */
    @Override
    public Map getHeaders(){

        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        return headers;
    }
}
