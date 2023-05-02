package com.example.extocia;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.zip.Inflater;

public class ProfileFragment extends Fragment {
    private ImageView imageView ;
    private ImageButton menuItem;
    private FloatingActionButton button;
    private TextView tvname,textViewuUname,textViewEmail,textViewPhone,textViewDate,textViewGender;
    private String Email,Uname,Gender,Phone,Date;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private Button btnEditProfile;
    private StorageReference storageReference;
    private Uri uri;
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
                Intent intentS = new Intent(getActivity(),SignLogin.class);
                startActivity(intentS);
            }
        });

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

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



        //Set User's current DP ImageView (if uploaded).
        imageView = view.findViewById(R.id.imageView);

        storageReference = FirebaseStorage.getInstance().getReference("DisplayPics");
        uri = firebaseUser.getPhotoUrl();

        button = view.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .crop()       //Crop image(Optional), Check Customization for more option
                        .compress(1024)    //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                Glide.with(ProfileFragment.this)
                        .load(uri)
                        .into(imageView);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            StorageReference imageRef = storageReference.child("image.jpg");
            imageView.setImageURI(uri);
            imageRef.putFile(uri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error uploading image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.common_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Toast.makeText(getActivity(), "Setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }*/

}