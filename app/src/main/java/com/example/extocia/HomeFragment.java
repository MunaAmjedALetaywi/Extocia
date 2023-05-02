package com.example.extocia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class HomeFragment extends Fragment {

    ImageView share;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        transparentStatusbarAndNavigation();
        share = view.findViewById(R.id.share_btn);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shaint = new Intent(Intent.ACTION_SEND);
                shaint.setType("text/plain");
                String shareBody = "Your body hear";
                String shareSub = "Your Subject here";
                shaint.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                shaint.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(shaint,"Share using"));
            }
        });
        return view ;
    }
     private void transparentStatusbarAndNavigation() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(int i, boolean b) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (b) {
            winParams.flags |= i;
        } else {
            winParams.flags &= ~i;
        }
        win.setAttributes(winParams);
    }

}