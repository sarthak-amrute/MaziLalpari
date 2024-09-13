package com.example.mazilalpari;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mazilalpari.R;

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


                }

            }
        });



        setOTPVerification();
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
