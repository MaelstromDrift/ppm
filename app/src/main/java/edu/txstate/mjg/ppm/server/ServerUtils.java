package edu.txstate.mjg.ppm.server;

import edu.txstate.mjg.ppm.core.Task;
import edu.txstate.mjg.ppm.core.User;

public class ServerUtils {

    /*
        params: Integer that identifies the user
        returns: An array of Processes that the user has created
     */
    public String getUserProcesses(final int userId) {
        ApiRequest request = new ApiRequest();
        try {
            //TODO: need to make this asynchronous. get() messes everything up and halts the UI thread until it returns
            return request.execute("user_processes/" + Integer.toString(userId) + "/").get();
        } catch(Exception e) {
            return "Error";
        }
    }

    /*
        Updates the process on the server if the current user is the creator, otherwise this will create a new process.
        params: Process, int
        returns: Nothing
     */
    public void updateProcess(Process process, int userId) {

    }

    /*
        Updates the currently chosen task if the current user is the original creator, otherwise this will create a new task
        params: Task, int
        returns: Nothing
     */
    public void updateTask(Task task, int userId) {

    }
    /*
        The user unfollows the specified process.
        params: int userId, int processId
        returns: An array of Processes that the user has created
     */
    public void unfollowProcess(int userId, int processId) {

    }

    /*
        the User specified by userId will now follow the process specified by processId if it exists.
        params: int userId, int processId
        returns: nothing
     */
    public void followProcess(int userId, int processId) {

    }

    /*
        Creates a new task on the server.
        params: Task
        returns: Nothing
     */
    public void createNewTask(Task task) {

    }

    /*
        Creates a new process on the server on the server and will automatically subscribe the logged in user to the process.
        params: Process
        returns: Nothing
     */
    public void createNewProcess(Process process) {

    }
    /*
        Creates a new user on the server if it does not already exist.
        params: User
        returns: Nothing
     */
    public void createNewUser(User user) {

    }

    /*
        params: Integer
        returns: All user related information.
     */
    public User getUserInfo(int userId) {
        return null;
    }
    /*
        Used to verify the login information for an existing user.
        params: String username, String Password
        returns: User if info is correct. null otherwise
     */
    public User verifyUser(String username, String password) {
        return null;
    }
    /*
        params: Integer that identifies the user
        returns: The process identified by the param, null if it does not exist
     */
    public Process getProcess(final int processId) {
        return null;
    }

    /*
        params: NONE
        returns: An array of all created Processes
     */
    public String getAllProcesses() {
        ApiRequest request = new ApiRequest();
        try {
            //TODO: need to make this asynchronous. get() messes everything up and halts the UI thread until it returns
            return request.execute("all_processes/").get();
        } catch(Exception e) {
            return "Error";
        }
    }
}
