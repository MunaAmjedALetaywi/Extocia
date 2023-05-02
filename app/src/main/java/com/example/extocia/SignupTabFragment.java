package com.example.extocia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupTabFragment extends Fragment {
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://extocia-5b74c-default-rtdb.firebaseio.com/");

    private String uname,phone , email,pass,ConPass,selectedGender,date;
    private TextInputLayout signPhone,signUser,signEmail,signPass,signDate,signConPass;
    private TextInputEditText signupDateEditText;
    private RadioGroup RGgender;
    private ProgressBar progressBar;
    private static final String TAG="SignupTabFragment";
    private EditText editTextpass, editTextConPass;
    private Button signupButton;
    private DatePickerDialog picker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        signPhone = view.findViewById(R.id.signup_phone);
        signUser = view.findViewById(R.id.signup_username);
        signEmail = view.findViewById(R.id.signup_email);
        signPass = view.findViewById(R.id.signup_password);
        signConPass = view.findViewById(R.id.signup_confirmpassword);
        RGgender = view.findViewById(R.id.genderRadioGroup);
        signDate = view.findViewById(R.id.signup_date);
        signupDateEditText = signDate.findViewById(R.id.signup_date_edittext);

        //Setting up DatePicker on EditText
        signupDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                //Date Picker Dialog
                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        signupDateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                picker.show();
            }
        });
        //button Register
        signupButton =view.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from EditTexts into String variables
                phone =signPhone.getEditText().getText().toString().trim();
                uname =signUser.getEditText().getText().toString().trim();
                email =signEmail.getEditText().getText().toString().trim();
                pass =signPass.getEditText().getText().toString().trim();
                ConPass =signConPass.getEditText().getText().toString().trim();
                int selectedRadioButtonId = RGgender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
                selectedGender = selectedRadioButton.getText().toString();
                date =signDate.getEditText().getText().toString();

                //check if user fill all the fields before sending before sending the data to firebase
                if (phone.isEmpty()) {
                    signPhone.setError("Phone is required");
                    signPhone.requestFocus();
                }else if(phone.length() != 10){
                    signPhone.setError("Mobile Number should be 10 digits");
                    signPhone.requestFocus();
                }else if (uname.isEmpty()) {
                    signUser.setError("User name cannot be empty");
                    signUser.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    signEmail.setError("Valid email is required");
                    signEmail.requestFocus();
                }else if (email.isEmpty()) {
                    signEmail.setError("Email cannot be empty");
                    signEmail.requestFocus();
                }else if (date.isEmpty()) {
                    signDate.setError("date of Birth is required");
                    signDate.requestFocus();
                }else if (pass.isEmpty()) {
                    signPass.setError("password cannot be empty");
                    signPass.requestFocus();
                }else if (pass.length() < 9){
                    signPass.setError("Password too weak");
                    signPass.requestFocus();
                }else if (ConPass.isEmpty()) {
                    signConPass.setError("Password Confirmation is required ");
                    signConPass.requestFocus();
                }
                //check if password are matching with each other
                //if not show the toast message
                else if(!pass.equals(ConPass)){
                    Toast.makeText(getActivity(),"Password are not matching",Toast.LENGTH_SHORT).show();
                    signConPass.setError("Password Confirmation is required");
                    signConPass.requestFocus();

                    editTextpass.clearComposingText();
                    editTextConPass.clearComposingText();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    RegisterUser( uname, phone, selectedGender, email, pass, date);
                }
            }
        });

        return view;
    }
    private void RegisterUser(String uname,String phone,String selectedGender,String email,String pass,String date){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser= auth.getCurrentUser();

                    //Enter User info into the firebase realtime
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(uname,date,phone,selectedGender,email);

                    //Extracting user reference from Database for "Register users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //send verification Email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(getActivity(),"User registered Successfully",Toast.LENGTH_SHORT).show();
                                /*Intent intent = new Intent(getActivity(),navigation_bottom.class);
                                //To prevent User from returning back to Register
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                getActivity().finish();*/
                            }else{
                                Toast.makeText(getActivity(),"User registered failed. Please try again",Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        signPass.setError("Your password is too weak.Kindly use mix alphabets,numbers and spatial characters");
                        signPass.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        signPass.setError("Your email is invalid or already in use . Kindly re-enter.");
                        signPass.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        signPass.setError("User is already registered with this email . Use another email.");
                        signPass.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

}