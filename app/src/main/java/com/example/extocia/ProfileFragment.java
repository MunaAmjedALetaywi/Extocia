package com.example.extocia;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.zip.Inflater;

public class ProfileFragment extends Fragment {
    private ImageView imageViewProfile ;
    private ImageButton menuItem;

    private TextView tvname,textViewuUname,textViewEmail,textViewPhone,textViewDate,textViewGender;
    private String Email,Uname,Gender,Phone,Date;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private Button btnEditProfile;


    private FirebaseUser firebaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        tvname = view.findViewById(R.id.rename);
        textViewuUname = view.findViewById(R.id.textView_show_full_name);
        textViewEmail = view.findViewById(R.id.textView_show_email);
        textViewPhone = view.findViewById(R.id.textView_show_mobile);
        textViewDate = view.findViewById(R.id.textView_show_dob);
        textViewGender = view.findViewById(R.id.textView_show_gender);
        progressBar = view.findViewById(R.id.progressBar);
        btnEditProfile = view.findViewById(R.id.btn_editProfile);
        menuItem = view.findViewById(R.id.menu_item);
        //registerForContextMenu(menuItem);
        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting About
                Intent intentS = new Intent(getActivity(),Setting_menu.class);
                startActivity(intentS);
            }
        });

        //Set OnClickListener on imageView to Open UploadProfilePic
        imageViewProfile = view.findViewById(R.id.imageView);
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UploadProfilePic.class);
                startActivity(intent);
            }
        });
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(getActivity(), "Something went wrong ! user's details are not available at the moment", Toast.LENGTH_SHORT).show();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(getActivity(),UpdateProfile.class);
                startActivity(intentEdit);
                getActivity().finish();
            }
        });


        return view;
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        //Extracting User Reference from "DatabaseReference"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails !=null){
                    // userName,BirthdayDate,numberPhone,MFSelectedGender,eemail
                    Email = firebaseUser.getEmail();
                    Uname = readUserDetails.userName;
                    Gender = readUserDetails.MFSelectedGender;
                    Phone = readUserDetails.numberPhone;
                    Date = readUserDetails.BirthdayDate;

                    tvname.setText(Uname);
                    textViewuUname.setText(Uname);
                    textViewEmail.setText(Email);
                    textViewDate.setText(Date);
                    textViewGender.setText(Gender);
                    textViewPhone.setText(Phone);

                    //Set user DP(After user has Uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();
                    //ImageView setImageURI
                    Picasso.get().load(uri).into(imageViewProfile);


                } else {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}