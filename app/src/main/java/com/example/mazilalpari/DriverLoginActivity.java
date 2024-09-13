package com.example.mazilalpari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DriverLoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    ImageView eyeIcon,logo;
    AppCompatButton btnLogin;
    TextView newUser;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_login);

        etUsername = findViewById(R.id.et_DriverLogin_username);
        etPassword = findViewById(R.id.et_DriverLogin_Password);
        eyeIcon = findViewById(R.id.iv_DriverLogin_ShowPassword);
        logo = findViewById(R.id.iv_DriverLogin_Logo);
        btnLogin = findViewById(R.id.btn_DriverLogin_Login);
        newUser = findViewById(R.id.tv_DriverLogin_NewUser);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverLoginActivity.this,DriverRegistrationActivity.class);
                startActivity(i);
            }
        });

        };
    }
