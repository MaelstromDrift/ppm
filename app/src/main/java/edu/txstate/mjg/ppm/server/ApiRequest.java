package edu.txstate.mjg.ppm.server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRequest extends AsyncTask<String, Void, String> {

    URL apiUrl;
    HttpURLConnection apiConnection;

    protected void onPreExecute() {

    }

    protected String doInBackground(String... url) {
        try {
            connect(url[0]);
            try {
                BufferedInputStream bf = new BufferedInputStream(apiConnection.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                in.close();
                return stringBuilder.toString();
            } finally {
                apiConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String response) {
        Log.i("Server Exception", response);
    }

    public void connect(String api) throws IOException {
        apiUrl = new URL("http://10.0.0.5:5000/" + api);
        apiConnection = (HttpURLConnection) apiUrl.openConnection();
    }
}
