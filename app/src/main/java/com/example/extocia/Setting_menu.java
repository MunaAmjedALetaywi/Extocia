package com.example.extocia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Setting_menu extends AppCompatActivity {

    ListView listView;
    String titles[]={"Favorite","Upload Picture","Language","About","Logout"};
    int imgs[] ={R.drawable.favoritesetting,R.drawable.baseline_photo_camera_24,R.drawable.baseline_language_24,R.drawable.baseline_info_24,R.drawable.baseline_logout_24};

    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);

        authProfile = FirebaseAuth.getInstance();
        listView = findViewById(R.id.listView);
        //crating instance of class MyAdapter
        MyAdapter adapter = new MyAdapter(this,titles,imgs);
        //set adapter list
        listView.setAdapter(adapter);
        //handle item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //we have currently 4 items
                if (position == 0){
                    Toast.makeText(Setting_menu.this, "Item 1 Clicked", Toast.LENGTH_SHORT).show();
                }
                if (position == 1){
                    Toast.makeText(Setting_menu.this, "Item 2 Clicked", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Setting_menu.this,SignLogin.class);
                    startActivity(intent);
                    finish();
                }
                if (position == 2){
                    Toast.makeText(Setting_menu.this, "Item 2 Clicked", Toast.LENGTH_SHORT).show();
                }
                if (position == 3){
                    Intent intent = new Intent(Setting_menu.this,AboutApplication.class);
                    startActivity(intent);
                }
                if (position == 4){
                    authProfile.signOut();
                    Toast.makeText(Setting_menu.this, "Logged Out", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Setting_menu.this,SignLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        int[] imgs;

        MyAdapter(Context c,String[] title,int[] imgs){
            super(c,R.layout.row,R.id.text1,title);
            this.context = c;
            this.imgs = imgs;
            this.myTitles = title;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.logo);
            TextView myTitle = row.findViewById(R.id.text1);
            images.setImageResource(imgs[position]);
            myTitle.setText(titles[position]);

            return row;
        }
    }
}