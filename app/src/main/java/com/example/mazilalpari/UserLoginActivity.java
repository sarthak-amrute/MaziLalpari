package com.example.mazilalpari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.LongUnaryOperator;

import cz.msebera.android.httpclient.Header;

public class UserLoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    ImageView eyeIcon, logo;
    AppCompatButton btnLogin;
    TextView newUser;
    ProgressDialog progressDialog;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username cannot be empty");
                } else if (etUsername.length() < 6) {
                    etUsername.setError("Username must be at least 6 characters long");
                } else if (!etUsername.getText().toString().matches(".*\\d.*")) {
                    etUsername.setError("username must contain at least one number");
                } else if (etUsername.getText().toString().contains(" ")) {
                    etUsername.setError("Spacing is Invalid in username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password cannot be empty");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password must be at least 8 characters long");
                } else if (!etPassword.getText().toString().matches(".*[a-z].*")) {
                    etPassword.setError("Password must contain at least one lowercase letter");
                } else if (!etPassword.getText().toString().matches(".*\\d.*")) {
                    etPassword.setError("Password must contain at least one number");
                } else if (!etPassword.getText().toString().matches(".*[@#\\$%^&+=].*")) {
                    etPassword.setError("Password must contain at least one special character (@#$%^&+=)");
                } else {
                    progressDialog = new ProgressDialog(UserLoginActivity.this);
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setMessage("Login in Progress");
                    progressDialog.show();
                    userLogin();
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this, UserRegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    private void userLogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", etUsername.getText().toString());
        params.put("password", etPassword.getText().toString());

        client.post("http://192.168.13.78:80/MaziLalpariAPI/userlogin.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        progressDialog.dismiss();
                        Toast.makeText(UserLoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserLoginActivity.this, HomeActivity.class);
                        i.putExtra("username", etUsername.getText().toString());
                        startActivity(i);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UserLoginActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(UserLoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
