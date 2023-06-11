package com.example.bluejackpharmacyv2.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Authentication extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private EditText otpField;
    private Button enterOtpButton;
    private UserDatabaseHelper userDb;
    private String email, verificationId, mVerificationId, PHONE_NUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        userDb = new UserDatabaseHelper(this);

        email = getIntent().getStringExtra("email");
        PHONE_NUM = userDb.getPhoneNumber(email);

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Log.i("Authentication", "FirebaseAuthInvalidCredentialsException");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.i("Authentication", "FirebaseTooManyRequestsException");
                } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                    Log.i("Authentication", "FirebaseAuthMissingActivityForRecaptchaException");
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token){
                mVerificationId = verificationId;
                showToast("Verification Code Sent!");
                Log.d(TAG, "onCodeSent:" + verificationId);
            }
        };

        startPhoneNumberVerification(PHONE_NUM);

        initialize();
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void startPhoneNumberVerification(String phoneNumber){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast("Correct!");
                        userDb.verificationCompleted(email, "verified");
                        startHome();
                    } else {
                        showToast("Incorrect!");
                    }
                });
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = task.getResult().getUser();
                    }else{
                        task.getException();
                    }
                });
    }

    public String getVerificationId(){
        return mVerificationId;
    }

    private void initialize(){
        otpField = findViewById(R.id.otp_field);
        enterOtpButton = findViewById(R.id.enter_otp_button);

        setListener();
    }

    private void setListener(){
        enterOtpButton.setOnClickListener(e -> {
            String otpCode = otpField.getText().toString();

            if(otpCode.isEmpty()){
                showToast("Insert OTP Code!");
            }else{
                verifyPhoneNumberWithCode(getVerificationId(), otpCode);
            }
        });
    }

    private void startHome(){
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}