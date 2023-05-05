package com.example.extocia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button buttonPassReset;
    private TextInputLayout  textInputLayoutPassReset;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textInputLayoutPassReset = findViewById(R.id.password_reset_email);
        buttonPassReset = findViewById(R.id.button_password_reset);
        progressBar = findViewById(R.id.progressBar);

        buttonPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textInputLayoutPassReset.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(ForgotPassword.this, "Please enter your register email", Toast.LENGTH_SHORT).show();
                    textInputLayoutPassReset.setError("Email is required");
                    textInputLayoutPassReset.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ForgotPassword.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    textInputLayoutPassReset.setError("Email is required");
                    textInputLayoutPassReset.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    ResetPassword(email);
                }

            }
        });

    }

    private void ResetPassword(String email) {
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    //Clear stack to prevent user coming back to ForgotPassword
                    Intent intent = new Intent(ForgotPassword.this,SignLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(ForgotPassword.this, "Something Wont Wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}