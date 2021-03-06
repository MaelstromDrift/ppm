package edu.txstate.mjg.ppm.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.core.Task;
import edu.txstate.mjg.ppm.core.User;

public class ServerUtils {

    //TODO: Need to create a method to implement the interface in APIRequest.
    ApiRequest api = new ApiRequest("http://70.113.44.188:5000/");

    public void setAsyncListener(ApiRequest.AsyncTaskListener a) {
        api.setAsyncTaskListener(a);
    }

    /*
    params: NONE
    returns: An array of all created Processes
 */
    public ArrayList<Process> getAllProcesses() {
        ArrayList<Process> processes = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(api.get("all_processes/").trim());

            for(int i = 0; i < json.length(); i++) {
                Process temp = new Process(json.getJSONObject(i));

                JSONArray tasksList = json.getJSONObject(i).getJSONArray("tasks");

                for(int j = 0; j < tasksList.length(); j++) {
                    temp.addTask(getTask(tasksList.getInt(j)));
                }
                processes.add(temp);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return processes;
    }


    public ArrayList<Process> getFollowedProcesses(final int userId) {
        ArrayList<Process> processes = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(api.get("followed_processes/" + Integer.toString(userId)).trim());

            for(int i = 0; i < json.length(); i++) {
                Process temp = new Process(json.getJSONObject(i));

                JSONArray tasksList = json.getJSONObject(i).getJSONArray("tasks");

                for (int j = 0; j < tasksList.length(); j++) {
                    temp.addTask(getTask(tasksList.getInt(j)));
                }
                processes.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processes;
    }
    /*
        params: Integer that identifies the user
        returns: An array of Processes that the user has created
     */
    public ArrayList<Process> getUserCreatedProcesses(final int userId) {
        ArrayList<Process> processes = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(api.get("user_processes/" + Integer.toString(userId)).trim());

            for(int i = 0; i < json.length(); i++) {
                Process temp = new Process(json.getJSONObject(i));

                JSONArray tasksList = json.getJSONObject(i).getJSONArray("tasks");

                for(int j = 0; j < tasksList.length(); j++) {
                    temp.addTask(getTask(tasksList.getInt(j)));
                }
                processes.add(temp);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return processes;
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
        try {
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("processId", processId);
            api.delete("unfollow/", json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        the User specified by userId will now follow the process specified by processId if it exists.
        params: int userId, int processId
        returns: nothing
     */
    public void followProcess(int userId, int processId) {
        try {
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("processId", processId);

            api.post("follow/", json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
        Creates a new task on the server.
        params: Task
        returns: Nothing
     */
    public void createNewTask(Task task) {
        try {
            JSONObject body = new JSONObject();
            body.put("title", task.getTitle());
            body.put("description", task.getDescription());
            body.put("creatorId", task.getCreatorID());

            String result = api.post("task/", body.toString()).trim();
            task.setUniqueId(Integer.parseInt(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        Creates a new process on the server on the server and will automatically subscribe the logged in user to the process.
        params: Process
        returns: Nothing
     */
    public void createNewProcess(Process process) {
        try {
            ArrayList<Task> tasks = process.getTasks();
            int[] tasksList = new int[tasks.size()];
            for(Task t: tasks) {
                createNewTask(t);
            }

            JSONObject body = new JSONObject();
            body.put("title", process.getTitle());
            body.put("description", process.getDescription());

            for(int i = 0; i < tasksList.length; i++) {
                tasksList[i] = tasks.get(i).getTaskId();
            }
            body.put("tasks", new JSONArray(tasksList));
            body.put("creatorId", process.getCreatorID());
            body.put("categoryId", 1);

            String result = api.post("process/", body.toString()).trim();
            process.setUniqueID(Integer.parseInt(result));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*
        Creates a new user on the server if it does not already exist.
        params: User
        returns: Nothing
     */
    public void createNewUser(User user) {
        try {
            JSONObject body = new JSONObject();

            body.put("username", user.getUsername());
            body.put("firstName", user.getFirstName());
            body.put("lastName", user.getLastName());
            body.put("password", user.getPassword());
            body.put("email", user.getEmail());

            String result = api.post("user/", body.toString().trim());
            user.setUserId(Integer.parseInt(result.trim()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        params: Integer
        returns: All user related information.
     */
    public User getUserInfo(final int userId) throws JSONException {
        return new User(new JSONObject(api.get("user/" + Integer.toString(userId))));
    }
    /*
        Used to verify the login information for an existing user.
        params: String username, String Password
        returns: User if info is correct. null otherwise
     */
    public User verifyUser(String username, String password) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);

            JSONObject response = new JSONObject(api.post("verify/", json.toString()));
            if(response.length() == 0) {
                return null;
            } else {
                return new User(response);
            }
        } catch (JSONException e) {
            return null;
        }
    }

    /*
    params: Integer that identifies the task
    returns: The task identified by the param, null if it does not exist
 */
    public Task getTask(final int taskId) {
        try {
            JSONObject json = new JSONObject(api.get("task/" + Integer.toString(taskId)));
            return new Task(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
        params: Integer that identifies the process
        returns: The process identified by the param, null if it does not exist
     */
    public Process getProcess(final int processId) {
        try {
            JSONObject json = new JSONObject(api.get("process/" + Integer.toString(processId)));
            Process process = new Process(json);

            JSONArray tasksList = json.getJSONArray("tasks");
            for(int i = 0; i < tasksList.length(); i++) {
                process.addTask(getTask(tasksList.getInt(i)));
            }
            return process;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
