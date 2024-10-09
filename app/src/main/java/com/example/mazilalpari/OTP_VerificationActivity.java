package com.example.mazilalpari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mazilalpari.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

public class OTP_VerificationActivity extends AppCompatActivity {

    TextView tvMobileNo, tvResendOTP;
    EditText etInputCode1, etInputCode2, etInputCode3, etInputCode4, etInputCode5, etInputCode6;
    Button btnVerify;
    ProgressDialog progressDialog;
    private String strVerificationCode,strName,strMobileNo,strEmailId,strUsername,strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);

        tvMobileNo = findViewById(R.id.tv_OTPVerification_Mobileno);
        tvResendOTP = findViewById(R.id.tv_OTPVerification_ResendOTP);
        etInputCode1 = findViewById(R.id.et_OTPVerification_inputCode1);
        etInputCode2 = findViewById(R.id.et_OTPVerification_inputCode2);
        etInputCode3 = findViewById(R.id.et_OTPVerification_inputCode3);
        etInputCode4 = findViewById(R.id.et_OTPVerification_inputCode4);
        etInputCode5 = findViewById(R.id.et_OTPVerification_inputCode5);
        etInputCode6 = findViewById(R.id.et_OTPVerification_inputCode6);
        btnVerify = findViewById(R.id.btn_OTPVerification_Verify);

        strVerificationCode = getIntent().getStringExtra("verificationCode");
        strName = getIntent().getStringExtra("name");
        strMobileNo = getIntent().getStringExtra("mobileno");
        strEmailId = getIntent().getStringExtra("emailid");
        strUsername = getIntent().getStringExtra("username");
        strPassword = getIntent().getStringExtra("password");


        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+strMobileNo,60, TimeUnit.SECONDS,OTP_VerificationActivity.this,
                        new  PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                        {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressDialog.dismiss();
                                Toast.makeText(OTP_VerificationActivity.this, "Verificaton Successfull", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressDialog.dismiss();

                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Invalid request
                                    Toast.makeText(OTP_VerificationActivity.this, "Invalid request, please check the phone number!", Toast.LENGTH_SHORT).show();
                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                    // SMS quota exceeded
                                    Toast.makeText(OTP_VerificationActivity.this, "Quota exceeded, please try again later.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(OTP_VerificationActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                strVerificationCode = verificationCode;
                            }
                            });
            }
        });


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etInputCode1.getText().toString().trim().isEmpty() || etInputCode2.getText().toString().trim().isEmpty() ||  etInputCode3.getText().toString().trim().isEmpty() ||
                        etInputCode4.getText().toString().trim().isEmpty() ||  etInputCode5.getText().toString().trim().isEmpty() ||  etInputCode6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(OTP_VerificationActivity.this, "Invalid OTP !!", Toast.LENGTH_SHORT).show();
                }

                String otpCode = etInputCode1.getText().toString().trim()+etInputCode2.getText().toString().trim()+etInputCode3.getText().toString().trim()+etInputCode4.getText().toString().trim()+
                        etInputCode5.getText().toString().trim()+etInputCode6.getText().toString().trim();

                if (strVerificationCode!=null)
                {
                    progressDialog = new ProgressDialog(OTP_VerificationActivity.this);
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setMessage("Verification in Progress");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();


                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            strVerificationCode,
                            otpCode
                    );

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                userregister();
                            }
                            else
                            {
                                Toast.makeText(OTP_VerificationActivity.this, "OTP Verification Fail !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });



        setOTPVerification();
    }
    private void userregister() {

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();

        params.put("name",strName);
        params.put("mobileno",strMobileNo);
        params.put("emailid",strEmailId);
        params.put("username",strUsername);
        params.put("password",strPassword);

        client.post("http://192.168.253.78:80/MaziLalpariAPI/userregistration.php",params,
                new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status = response.getString("success");
                            if (status.equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(OTP_VerificationActivity.this, "Registration Successfully Done ", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(OTP_VerificationActivity.this,UserLoginActivity.class);
                                startActivity(i);

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(OTP_VerificationActivity.this, "Data is Already Exist", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(OTP_VerificationActivity.this,"Server Error",Toast.LENGTH_SHORT).show();

                    }
                });
    }



    private void setOTPVerification() {

        etInputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    etInputCode2.requestFocus();
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etInputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etInputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etInputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etInputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etInputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etInputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etInputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etInputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etInputCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etInputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
