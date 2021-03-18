package com.example.android.quakereport.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NetworkUtils {


    private static final String BASE_USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?";
    public static final int DURATION = 40;


    private static String buildURL() {
        String startTime = formartDate(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(DURATION)));
        String endTime = formartDate(new Date());
        Uri.Builder builder = Uri.parse(BASE_USGS_URL).buildUpon()
                .appendQueryParameter("format", "geojson")
                .appendQueryParameter("starttime", startTime)
                .appendQueryParameter("endtime", endTime)
                .appendQueryParameter("orderby", "magnitude")
                .appendQueryParameter("minmagnitude", "5");

        String baseUrl = builder.build().toString();
        return baseUrl;
    }

    private static String formartDate(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String stringDate = simpleDateFormat.format(date);
        return stringDate;
    }

    public static  String run(){
        String jsonResponse = null;

        OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(buildURL())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                jsonResponse = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

        Log.i(NetworkUtils.class.getName(), jsonResponse);

        return jsonResponse;
    }
}
