package com.example.mazilalpari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.provider.SelfDestructiveThread;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectLoginOptionActivity extends AppCompatActivity {

    CardView cvDriver ,cvUser,cvAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_login_option);

        cvDriver = findViewById(R.id.cv_LoginOption_Driver);
        cvUser = findViewById(R.id.cv_LoginOption_Passanger);
        cvAdmin = findViewById(R.id.cv_LoginOption_Admin);

        cvDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectLoginOptionActivity.this,DriverLoginActivity.class);
                startActivity(i);
            }
        });

        cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectLoginOptionActivity.this,UserLoginActivity.class);
                startActivity(i);
            }
        });

        cvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectLoginOptionActivity.this,AdminLoginActivity.class);
                startActivity(i);
            }
        });

        };
    }
