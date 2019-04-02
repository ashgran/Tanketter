package com.ashgrav.tanketter.pinger;

import java.io.IOException;
import java.net.MalformedURLException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHandler {




    public void httpSend(String ip, String command) {
        OkHttpClient client = new OkHttpClient();
        java.net.URL url = null;

        try {
            String URL = "http://" + ip + "/?State=" + command;
            Request request = new Request.Builder().url(URL).build();
            Response response = client.newCall(request).execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
