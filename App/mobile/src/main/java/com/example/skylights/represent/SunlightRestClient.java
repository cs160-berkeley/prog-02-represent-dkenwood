package com.example.skylights.represent;

import android.preference.PreferenceActivity;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylights on 3/10/2016.
 */
public class SunlightRestClient {
    private static final String BASE_URL = "https://congress.api.sunlightfoundation.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    protected static List<RepInfo> thereps = new ArrayList<RepInfo>();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    protected static List<RepInfo> getThemPlease() {
        return thereps;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
