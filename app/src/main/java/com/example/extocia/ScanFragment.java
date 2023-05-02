package com.example.extocia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.github.dhaval2404.imagepicker.ImagePicker;


public class ScanFragment extends Fragment {

    private ImageButton imagebutton;
    private LinearLayout fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scan, container, false);

        //imagebutton = view.findViewById(R.id.imageButton);
        fragment = view.findViewById(R.id.fragcamera);

        fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ScanFragment.this)
                        .crop()	   //Crop image(Optional), Check Customization for more option
                        .compress(1024)	//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        /*imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ScanFragment.this)
                        .crop()	   //Crop image(Optional), Check Customization for more option
                        .compress(1024)	//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });*/
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imagebutton.setImageURI(uri);
    }
}