package com.example.mazilalpari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class UserLoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    ImageView eyeIcon,logo;
    AppCompatButton btnLogin;
    TextView newUser;
    ProgressDialog progressDialog;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    GoogleSignInOptions googleSignInOptions;

    GoogleSignInClient googleSignInClient;
    TextView tvSignInWithGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_user);

        etUsername = findViewById(R.id.et_UserLogin_username);
        etPassword = findViewById(R.id.et_UserLogin_Password);
        eyeIcon = findViewById(R.id.iv_UserLogin_ShowPassword);
        logo = findViewById(R.id.iv_UserLogin_Logo);
        btnLogin = findViewById(R.id.btn_UserLogin_Login);
        newUser = findViewById(R.id.tv_UserLogin_NewUser);
        tvSignInWithGoogle = findViewById(R.id.tv_Login_SigninWithGoogle);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(UserLoginActivity.this,googleSignInOptions);

        tvSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                    // Hide
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeIcon.setImageResource(R.drawable.passvisibleicon);

                } else {
                    // Show password

                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeIcon.setImageResource(R.drawable.passunvisibleicon);
                }
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this,UserRegistrationActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username cannot be empty");
                } else if (etUsername.length() < 6) {
                    etUsername.setError("Username must be at least 6 characters long");
                } else if (!etUsername.getText().toString().matches(".*\\d.*")) {
                    etUsername.setError("username must contain at least one number");
                }
                else if(etUsername.getText().toString().contains(" ")) {
                    etUsername.setError("Spacing is Invalid in username");
                }
                else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password cannot be empty");
                }
                else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password must be at least 8 characters long");
                }
                else if (!etPassword.getText().toString().matches(".*[a-z].*")) {
                    etPassword.setError("Password must contain at least one lowercase letter");
                }
                else if (!etPassword.getText().toString().matches(".*\\d.*")) {
                    etPassword.setError("Password must contain at least one number");
                }
                else if (!etPassword.getText().toString().matches(".*[@#\\$%^&+=].*")) {
                    etPassword.setError("Password must contain at least one special character (@#$%^&+=)");
                }
                else {
                    progressDialog=new ProgressDialog(UserLoginActivity.this);
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setMessage("Login in Progress");
                    progressDialog.show();
                    userLogin();
                }
            }
        });

    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 999)
        {

            Task<GoogleSignInAccount> task =GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(UserLoginActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }

    }

    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }


    private void userLogin() {
        Intent i = new Intent(UserLoginActivity.this,HomeActivity.class);
        startActivity(i);
}


}