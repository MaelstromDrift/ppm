package edu.txstate.mjg.ppm.core;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    //TODO: Password NEEDS to be encrypted in someway.
    private int userId;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;

    public User() {
        userId = 1;
        username = "DefaultUserAccount";
        firstname = "John";
        lastname = "lastName";
        password = "test";
        email = "default@default.com";
    }

    public User(int userId, String username, String password, String firstname, String lastname, String email) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }

    public User(String username, String firstname, String lastname, String email, String password){
        this.userId = 0;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    public User(JSONObject object) {
        try {
            userId = object.getInt("_id");
            username = object.getString("username");
            firstname = object.getString("firstName");
            lastname = object.getString("lastName");
            password  = object.getString("password");
            email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUserId(int userId) { this.userId = userId;}
    public void setUsername(String username) { this.username = username; }
    public void setFirstName(String firstName) { this.firstname = firstName; }
    public void setLastName(String lastName) { this.lastname = lastName; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFirstName() { return firstname; }
    public String getLastName() { return lastname; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
}
