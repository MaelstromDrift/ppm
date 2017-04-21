package edu.txstate.mjg.ppm.core;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    //TODO: Password NEEDS to be encrypted in someway.
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public User() {
        userId = 1;
        username = "DefaultUserAccount";
        firstName = "John";
        lastName = "lastName";
        password = "test";
        email = "default@default.com";
    }

    public User(int userId, String username, String password, String firstName, String lastName, String email) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public User(JSONObject object) {
        try {
            userId = object.getInt("_id");
            username = object.getString("username");
            firstName = object.getString("firstName");
            lastName = object.getString("lastName");
            password  = object.getString("password");
            email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUserId(int userId) { this.userId = userId;}
    public void setUsername(String username) { this.username = username; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
}
