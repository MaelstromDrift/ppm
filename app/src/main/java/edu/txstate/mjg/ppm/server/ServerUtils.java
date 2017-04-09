package edu.txstate.mjg.ppm.server;

public class ServerUtils {


    public String getUserProcesses(final int userId) {
        ApiRequest request = new ApiRequest();
        try {
            //TODO: need to make this asynchronous. get() messes everything up and halts the UI thread until it returns
            return request.execute("user_processes/" + Integer.toString(userId) + "/").get();
        } catch(Exception e) {
            return "Error";
        }
    }


}
