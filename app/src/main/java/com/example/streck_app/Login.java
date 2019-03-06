package com.example.streck_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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
        final String params = "apiType=getID&username=" + username;
        class Req_Run implements Runnable {
            private volatile int uid;
            @Override
            public void run() {
                uid = Integer.parseInt(HTTP.doRequest("api.php", params));
            }

            public int getUid() {
                return uid;
            }
        }
        Req_Run r = new Req_Run();
        Thread req_thread = new Thread(r);
        req_thread.start();
        try {
            req_thread.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        int userID = r.getUid();
        if (userID != -1) {
            Intent loginInfo = new Intent(Login.this, MainActivity.class);
            loginInfo.putExtra("username", username);
            loginInfo.putExtra("id", userID);
            startActivity(loginInfo);
        }
    }
}