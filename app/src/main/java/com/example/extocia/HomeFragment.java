package com.example.extocia;

import android.annotation.SuppressLint;
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

import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {
    MaterialButton like1,like2,like3,like4,like5,like6,share1,share2,share3,share4,share5,share6;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        like1 = view.findViewById(R.id.like_1);
        like2 = view.findViewById(R.id.like_2);
        like3 = view.findViewById(R.id.like_3);
        like4 = view.findViewById(R.id.like_4);
        like5 = view.findViewById(R.id.like_5);
        like6 = view.findViewById(R.id.like_6);

        final boolean[] isLiked = {false};
        like1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the button background color
                if (isLiked[0]) {
                    like1.setIconResource(R.drawable.favorite_border);
                } else {
                    like1.setIconResource(R.drawable.favorite);
                }
                isLiked[0] = !isLiked[0];
            }
        });
        like2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the button background color
                if (isLiked[0]) {
                    like2.setIconResource(R.drawable.favorite_border);
                } else {
                    like2.setIconResource(R.drawable.favorite);
                }
                isLiked[0] = !isLiked[0];
            }
        });
        like3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the button background color
                if (isLiked[0]) {
                    like3.setIconResource(R.drawable.favorite_border);
                } else {
                    like3.setIconResource(R.drawable.favorite);
                }
                isLiked[0] = !isLiked[0];
            }
        });
        like4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the button background color
                if (isLiked[0]) {
                    like4.setIconResource(R.drawable.favorite_border);
                } else {
                    like4.setIconResource(R.drawable.favorite);
                }
                isLiked[0] = !isLiked[0];
            }
        });
        like5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the button background color
                if (isLiked[0]) {
                    like5.setIconResource(R.drawable.favorite_border);
                } else {
                    like5.setIconResource(R.drawable.favorite);
                }
                isLiked[0] = !isLiked[0];
            }
        });
        like6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the button background color
                if (isLiked[0]) {
                    like6.setIconResource(R.drawable.favorite_border);
                } else {
                    like6.setIconResource(R.drawable.favorite);
                }
                isLiked[0] = !isLiked[0];
            }
        });
        share1 = view.findViewById(R.id.share_1);
        share2 = view.findViewById(R.id.share_2);
        share3 = view.findViewById(R.id.share_3);
        share4 = view.findViewById(R.id.share_4);
        share5 = view.findViewById(R.id.share_5);
        share6 = view.findViewById(R.id.share_6);

        share1.setOnClickListener(new View.OnClickListener() {
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
        share2.setOnClickListener(new View.OnClickListener() {
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
        share3.setOnClickListener(new View.OnClickListener() {
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
        share4.setOnClickListener(new View.OnClickListener() {
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
        share5.setOnClickListener(new View.OnClickListener() {
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
        share6.setOnClickListener(new View.OnClickListener() {
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