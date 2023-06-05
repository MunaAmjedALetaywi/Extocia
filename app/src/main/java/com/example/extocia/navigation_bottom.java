package com.example.extocia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.extocia.databinding.ActivityMainBinding;
import com.example.extocia.databinding.ActivityNavigationBottomBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

public class navigation_bottom extends AppCompatActivity {

    ActivityNavigationBottomBinding binding;
    //we have 3 tab so value between 1 to 3. default value is 1
    private int selectedTab = 1;
    private static final int CAMERA_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBottomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout scanLayout = findViewById(R.id.scanLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView scanImage = findViewById(R.id.scanImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView scanTxt = findViewById(R.id.scanTxt);
        final TextView profileTxt = findViewById(R.id.profileTxt);

        //set home fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();
        homeImage.setImageResource(R.drawable.outline_selected_home);
        homeLayout.setBackgroundResource(R.drawable.round_back_home);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedTab != 1) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();
                    scanTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    scanImage.setImageResource(R.drawable.outline_camera);
                    profileImage.setImageResource(R.drawable.person_outline);

                    scanLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.outline_selected_home);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;
                }

            }
        });
        scanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedTab != 2) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ScanFragment.class, null)
                            .commit();
                    homeTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.outline_home);
                    profileImage.setImageResource(R.drawable.person_outline);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    scanTxt.setVisibility(View.VISIBLE);
                    scanImage.setImageResource(R.drawable.outline_selected_camera);
                    scanLayout.setBackgroundResource(R.drawable.round_back_home);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    scanLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;


                    /*ImagePicker.with(navigation_bottom.this)
                            .crop()
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .start();*/

                }

            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedTab != 3) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
                            .commit();
                    scanTxt.setVisibility(View.GONE);
                    homeTxt.setVisibility(View.GONE);

                    scanImage.setImageResource(R.drawable.outline_camera);
                    homeImage.setImageResource(R.drawable.outline_home);

                    scanLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.person_selected_outline);
                    profileLayout.setBackgroundResource(R.drawable.round_back_home);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            // The image was successfully picked, get its URI
            Uri uri = data.getData();

            /*ScanFragment fragment = (ScanFragment) getSupportFragmentManager().findFragmentById(R.id.ScanFragmentS);
            if (fragment != null) {
                fragment.setImageUri(uri);}*/


        }

    }
}