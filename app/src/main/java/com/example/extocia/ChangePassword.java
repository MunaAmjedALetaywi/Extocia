package com.example.extocia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePassword extends AppCompatActivity {

    private FirebaseAuth authProfile;
    private TextInputLayout textPassCur,textPassNew,textPassConNew;
    private TextView textViewAuthenticated;
    private String PassCurrent;
    private ProgressBar progressBar;
    private Button buttonAuthenticate,buttonChangePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        textPassCur = findViewById(R.id.password_old);
        textPassNew = findViewById(R.id.password_new);
        textPassConNew = findViewById(R.id.password_new2);

        textViewAuthenticated = findViewById(R.id.textView_change_pwd_authenticated);

        progressBar = findViewById(R.id.progressBar);
        buttonAuthenticate = findViewById(R.id.button_authenticate);
        buttonChangePass = findViewById(R.id.update_password);

        textPassNew.setEnabled(false);
        textPassConNew.setEnabled(false);
        buttonChangePass.setEnabled(false);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        
        if (firebaseUser.equals("")){
            Toast.makeText(this, "Something wont wrong! User's details not available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePassword.this,navigation_bottom.class);
            startActivity(intent);
            finish();
        } else {
            ReAuthenticateUser(firebaseUser);
        }
    }

    //ReAuthenticate User before Changing Password
    private void ReAuthenticateUser(FirebaseUser firebaseUser) {
        buttonAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassCurrent = textPassCur.getEditText().getText().toString();

                if(TextUtils.isEmpty(PassCurrent)) {
                    Toast.makeText(ChangePassword.this, "Password is needed", Toast.LENGTH_SHORT).show();
                    textPassCur.setError("Please enter your current password to authenticate");
                    textPassCur.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    //ReAuthenticate User now
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), PassCurrent);

                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);

                                textPassCur.setEnabled(false);
                                textPassNew.setEnabled(true);
                                textPassConNew.setEnabled(true);

                                buttonAuthenticate.setEnabled(false);
                                buttonChangePass.setEnabled(true);

                                textViewAuthenticated.setText("You are authentication/verified."+
                                        "You can change password now!");
                                Toast.makeText(ChangePassword.this, "Password has been verified."+"Change Password now.", Toast.LENGTH_SHORT).show();

                                buttonChangePass.setBackgroundTintList(ContextCompat.getColorStateList(
                                        ChangePassword.this,R.color.primaryLightColor));

                                buttonChangePass.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ChangePass(firebaseUser);
                                    }
                                });
                            } else {
                                try{
                                    throw task.getException();
                                } catch (Exception e) {
                                    Toast.makeText(ChangePassword.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void ChangePass(FirebaseUser firebaseUser) {
        String PassNew = textPassNew.getEditText().getText().toString();
        String PassConNew = textPassConNew.getEditText().getText().toString();

        if (TextUtils.isEmpty(PassNew)) {
            Toast.makeText(this, "New Password is needed", Toast.LENGTH_SHORT).show();
            textPassNew.setError("Please enter your new password");
            textPassNew.requestFocus();
        } else if (TextUtils.isEmpty(PassConNew)) {
            Toast.makeText(this, "Please confirm your new password", Toast.LENGTH_SHORT).show();
            textPassConNew.setError("Please re-enter your new password");
            textPassConNew.requestFocus();
        } else if (!PassNew.matches(PassConNew)){
            Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
            textPassConNew.setError("Please re-enter same password");
            textPassConNew.requestFocus();
        } else if (!PassConNew.matches(PassNew)) {
            Toast.makeText(this, "New Password can not be same as old password", Toast.LENGTH_SHORT).show();
            textPassNew.setError("Please enter a new password");
            textPassNew.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            firebaseUser.updatePassword(PassNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChangePassword.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePassword.this,navigation_bottom.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(ChangePassword.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}