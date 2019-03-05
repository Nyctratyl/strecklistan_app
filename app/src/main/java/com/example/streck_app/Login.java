package com.example.streck_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.widget.Button button = findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempt_login();
            }
        });
    }

    private void attempt_login() {

        android.widget.EditText nameField = findViewById(R.id.username);
        String username = nameField.getText().toString();
        String params = "apiType=getID&username=" + username;
        int userID = Integer.parseInt(HTTP.doRequest("api.php", params));

        if (userID != -1) {
            Intent loginInfo = new Intent(Login.this, MainActivity.class);
            loginInfo.putExtra("username", username);
            loginInfo.putExtra("id", userID);
            startActivity(loginInfo);

        }
    }

}
