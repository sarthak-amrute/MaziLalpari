package com.example.mazilalpari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class UserRegistrationActivity extends AppCompatActivity {

    ImageView ivLogo,google,faceBook, eyeIcon;
    EditText etName,etMobileNo,etEmailId,etUsername,etPassword;
    Button register;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Animation fadeInDown;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration);


        etName = findViewById(R.id.et_UserRegistration_Name);
        etMobileNo = findViewById(R.id.et_UserRegistration_MobileNo);
        etEmailId  = findViewById(R.id.et_UserRegistration_EmailID);
        etPassword=findViewById(R.id.et_UserRegistration_Password);
        etUsername=findViewById(R.id.et_UserRegistration_Username);
        register = findViewById(R.id.btn_UserRegistration_Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Name must be Required");
                }
                else if (etMobileNo.getText().toString().isEmpty()) {
                    etMobileNo.setError("Number must be Required");
                } else if (etMobileNo.getText().toString().length()!=10) {
                    etMobileNo.setError("Number must be 10 Digits");
                } else if (etEmailId.getText().toString().isEmpty()) {
                    etEmailId.setError("Email must Required");
                } else if (!etEmailId.getText().toString().matches(".*[0-9].*")) {
                    etEmailId.setError("Please Email must contain at least one Number");
                } else if (etEmailId.getText().toString().matches(".*[A-Z].*")) {
                    etEmailId.setError("Please Enter Lower case letter");
                } else if (!etEmailId.getText().toString().contains("@gmail.com")) {
                    etEmailId.setError("Please Enter Valid EmailId");
                }
                else if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username cannot be empty");
                } else if (etUsername.length() < 6) {
                    etUsername.setError("Username must be at least 6 characters long");
                } else if (!etUsername.getText().toString().matches(".*\\d.*"))
                {
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
                else if (!etPassword.getText().toString().matches(".*[A-Z].*")) {
                    etPassword.setError("Password must contain at least one uppercase letter");
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

                    progressDialog=new ProgressDialog(UserRegistrationActivity.this);
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setMessage("Registration in Progress");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(91+etMobileNo.getText().toString(),60, TimeUnit.SECONDS,UserRegistrationActivity.this,
                            new  PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                            {

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                    progressDialog.dismiss();
                                    Toast.makeText(UserRegistrationActivity.this, "Verificaton Successfull", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
//                                    progressDialog.dismiss();

                                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        // Invalid request
                                        Toast.makeText(UserRegistrationActivity.this, "Invalid request, please check the phone number!", Toast.LENGTH_SHORT).show();
                                    } else if (e instanceof FirebaseTooManyRequestsException) {
                                        // SMS quota exceeded
                                        Toast.makeText(UserRegistrationActivity.this, "Quota exceeded, please try again later.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(UserRegistrationActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(UserRegistrationActivity.this, OTP_VerificationActivity.class);
                                    intent.putExtra("verificationCode",verificationCode);
                                    intent.putExtra("name",etName.getText().toString());
                                    intent.putExtra("mobileno",etMobileNo.getText().toString());
                                    intent.putExtra("emailid",etEmailId.getText().toString());
                                    intent.putExtra("username",etUsername.getText().toString());
                                    intent.putExtra("password",etPassword.getText().toString());
                                    startActivity(intent);
                                }
                            }
                    );
                  //  userregister();
        };
    }});
    }

//    private void userregister() {
//
//        AsyncHttpClient client=new AsyncHttpClient();
//        RequestParams params=new RequestParams();
//
//        params.put("name",etName.getText().toString());
//        params.put("mobileno",etMobileNo.getText().toString());
//        params.put("emailid",etEmailId.getText().toString());
//        params.put("username",etUsername.getText().toString());
//        params.put("password",etPassword.getText().toString());
//
//        client.post("http://192.168.13.78:80/MaziLalpariAPI/userregistration.php",params,
//                new JsonHttpResponseHandler()
//                {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
//                        try {
//                            String status = response.getString("success");
//                            if (status.equals("1"))
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(UserRegistrationActivity.this, "Registration Successfully Done ", Toast.LENGTH_SHORT).show();
//                                Intent i=new Intent(UserRegistrationActivity.this,UserLoginActivity.class);
//                                startActivity(i);
//
//                            }
//                            else {
//                                progressDialog.dismiss();
//                                Toast.makeText(UserRegistrationActivity.this, "Data is Already Exist", Toast.LENGTH_SHORT).show();
//
//                            }
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                        progressDialog.dismiss();
//                        Toast.makeText(UserRegistrationActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//    }
}