package com.example.extocia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginTabFragment extends Fragment {

    public static String PREFS_NAME ="MyPrefsFile";
    private FirebaseAuth authprofile;
    private TextInputLayout loginEmail,loginPass;
    private String email,pass;
    private ProgressBar progressBar;
    private Button bottonLogin;
    private static final String TAG="LoginTabFragment";
    private TextView forgetPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        loginEmail = view.findViewById(R.id.login_email);
        loginPass = view.findViewById(R.id.login_password);
        progressBar = view.findViewById(R.id.progressBar);
        forgetPassword =view.findViewById(R.id.forgetPassword);

        authprofile =FirebaseAuth.getInstance();

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForgetPass = new Intent(getActivity(),ForgotPassword.class);
                startActivity(intentForgetPass);
                getActivity().finish();
            }
        });
        bottonLogin = view.findViewById(R.id.login_button);

        bottonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = loginEmail.getEditText().getText().toString().trim();
                pass = loginPass.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    loginEmail.setError("Please enter your email");
                    loginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    loginEmail.setError("Valid email is required");
                    loginEmail.requestFocus();
                } else if(TextUtils.isEmpty(pass)) {
                    loginPass.setError("Password is required");
                    loginPass.requestFocus();
                } else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(email,pass);
                }
            }
        });
        return view ;
    }

    private void loginUser(String em, String pa) {
        authprofile.signInWithEmailAndPassword(em,pa).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Get instance of the current User
                    FirebaseUser firebaseUser = authprofile.getCurrentUser();

                    //Check if email id verified before user can access
                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(getActivity(), "You are Logged in now", Toast.LENGTH_SHORT).show();
                        //Open User Profile
                        startActivity(new Intent(getActivity(),navigation_bottom.class));
                        getActivity().finish();
                    }else{
                        firebaseUser.sendEmailVerification();
                        authprofile.signOut();
                        showAlertDialog();
                        }
                }else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        loginEmail.setError("User does not exists or is no longer valid. Please register again.");
                        loginEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        loginEmail.setError("Invalid credentials. Kindly, check and re-enter");
                        loginEmail.requestFocus();
                    }catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }
    private void showAlertDialog() {
        //setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Email Not Verified");
        builder.setMessage("PLease verify you email now. You can not login without email verification.");

        //Open Email Apps if user click/taps Continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //getActivity().finish();
            }
        });
        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();
        //show the AlertDialog
        alertDialog.show();
    }
    //check if User is already logged in .In Such case, straightway take the User to the User's profile
    @Override
    public void onStart() {
        super.onStart();
        if(authprofile.getCurrentUser() != null){
            //Start the navigation
            startActivity(new Intent(getActivity(),navigation_bottom.class));
            getActivity().finish();
        }else {
            Toast.makeText(getActivity(), "You can login now", Toast.LENGTH_SHORT).show();
        }
    }
}