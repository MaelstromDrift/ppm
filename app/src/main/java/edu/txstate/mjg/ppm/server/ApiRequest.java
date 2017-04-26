package edu.txstate.mjg.ppm.server;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRequest{

    public interface AsyncTaskListener {
        void onStart();
        void onCompletion(String result);
    }

    String baseURL;

    public ApiRequest() {
        asyncTaskListener = new AsyncTaskListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompletion(String result) {

            }
        };
    }
    public ApiRequest(String baseURL) {
        this();
        this.baseURL = baseURL;
    }
    //TODO: make sure everything is actually ASynchronous. AsyncTask.get() isn't needed, replace all calls to this function when possible.
    URL apiUrl;
    HttpURLConnection apiConnection;

    AsyncTaskListener asyncTaskListener;

    public void setAsyncTaskListener(AsyncTaskListener asyncTaskListener) {
        this.asyncTaskListener = asyncTaskListener;
    }

    public String get(final String URL) {
        try {
            AsyncTask<Void, Void, String> networkRequest = new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    asyncTaskListener.onStart();

                }
                protected String doInBackground(Void... v) {
                    try {
                        connect(URL);
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
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    asyncTaskListener.onCompletion(s);
                }
            };
            return networkRequest.execute().get();
        } catch (Exception e) {
            return null;
        }
    }

    public String post(final String URL, final String body) {
        try {
            AsyncTask<Void, Void, String> networkRequest = new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    asyncTaskListener.onStart();

                }
                protected String doInBackground(Void... v) {
                    try {
                        connect(URL);
                        try {
                            //Setup the connection to make a POST request.
                            apiConnection.setDoOutput(true);
                            apiConnection.setRequestMethod("POST");
                            apiConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                            OutputStream os = apiConnection.getOutputStream();
                            os.write(body.getBytes("UTF-8"));
                            os.close();

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

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    asyncTaskListener.onCompletion(s);
                }
            };
            return networkRequest.execute().get();
        } catch(Exception e) {
            return null;
        }
    }

    public String delete(final String URL, final String body) {
        try {
            AsyncTask<Void, Void, String> networkRequest = new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    asyncTaskListener.onStart();

                }
                protected String doInBackground(Void... v) {
                    try {
                        connect(URL);
                        try {
                            //Setup the connection to make a POST request.
                            apiConnection.setDoOutput(true);
                            apiConnection.setRequestMethod("DELETE");
                            apiConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                            OutputStream os = apiConnection.getOutputStream();
                            os.write(body.getBytes("UTF-8"));
                            os.close();

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

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    asyncTaskListener.onCompletion(s);
                }
            };
            return networkRequest.execute().get();
        } catch(Exception e) {
            return null;
        }
    }
    private void connect(String api) throws IOException {
        apiUrl = new URL(baseURL + api);
        apiConnection = (HttpURLConnection) apiUrl.openConnection();
    }
}
