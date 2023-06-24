package com.example.watchup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity /* implements FetchDataCallback */ {

//    Button btnGetStarted;
//    Button btnTest;
    private EditText emailEt;
    private EditText passwordEt;
    private Button loginBtn;
    private ProgressBar progressBar;
    private TextView registerHereTV;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        //Check notifications permissions and starts the foreground service
        Utils.checkNotifPermission(MainActivity.this, MainActivity.this);
        Intent serviceIntent = new Intent(this, DataFetchService.class);
        startService(serviceIntent);

        emailEt = findViewById(R.id.emailInput);
        passwordEt = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginButton);
        registerHereTV = findViewById(R.id.registerHereTV);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = emailEt.getText().toString();
            password = passwordEt.getText().toString();

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this,"Please enter your email", Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Authentication succeded.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });


        registerHereTV.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),RegisterPage.class);
            startActivity(intent);
        });


//        btnGetStarted = findViewById(R.id.butonGetStarted);
//        btnGetStarted.setOnClickListener(view -> {
//            Intent it = new Intent(this.getApplicationContext(), SecondPage.class);
//            startActivity(it);
//        });
//
//        btnTest = findViewById(R.id.butonTestList);
//        btnTest.setOnClickListener(view -> {
//            Intent intent = new Intent(this.getApplicationContext(), ListActivity.class);
//            startActivity(intent);
//        });



    }
}