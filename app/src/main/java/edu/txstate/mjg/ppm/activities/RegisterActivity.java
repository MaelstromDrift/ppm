package edu.txstate.mjg.ppm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.txstate.mjg.ppm.R;
import edu.txstate.mjg.ppm.core.User;
import edu.txstate.mjg.ppm.server.ServerUtils;

public class RegisterActivity extends AppCompatActivity {

    ServerUtils server;
    private EditText mUsernameView;
    private EditText mFirstnameView;
    private EditText mLastnameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmView;

    private Button mCreateView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        server = new ServerUtils();

        mUsernameView = (EditText) findViewById(R.id.register_username);
        mFirstnameView = (EditText) findViewById(R.id.register_firstname);
        mLastnameView = (EditText) findViewById(R.id.register_lastname);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmView = (EditText) findViewById(R.id.register_password_confirm);

        mCreateView = (Button) findViewById(R.id.register_create_button);
        mCreateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempt_register();
            }
        });
    }

    public void attempt_register() {
        final String username = mUsernameView.getText().toString();
        final String firstname = mFirstnameView.getText().toString();
        final String lastname = mLastnameView.getText().toString();
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String confirm = mConfirmView.getText().toString();

        //TODO: Check for already used Username and Emails.
        if(TextUtils.isEmpty(username)) {
            mUsernameView.setError("Please enter a username.");
        } else if(TextUtils.isEmpty(firstname)) {
            mFirstnameView.setError("Please enter a name.");
        } else if(TextUtils.isEmpty(lastname)) {
            mLastnameView.setError("Please enter a name.");
        } else if(TextUtils.isEmpty(email)) {
            mEmailView.setError("Please enter an email.");
        } else if(TextUtils.isEmpty(password)) {
            mPasswordView.setError("Please enter a password.");
        } else if(TextUtils.isEmpty(confirm)) {
            mConfirmView.setError("Please confirm your password.");
        } else if(!verify_password(password, confirm)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
        } else {
            server.createNewUser(new User(username, firstname, lastname, email, password));
            done();
        }
    }

    public void done() {
        Intent temp = new Intent();
        temp.setClass(this, edu.txstate.mjg.ppm.LoginActivity.class);
        this.startActivity(temp);
    }
    public boolean verify_password(String password, String confirm) {
        if(password.equals(confirm)) {
            return true;
        }
        return false;
    }
}
