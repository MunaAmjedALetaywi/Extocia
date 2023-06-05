package com.example.extocia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import com.github.dhaval2404.imagepicker.ImagePicker;

import okhttp3.*;
import org.json.*;

import java.io.*;
public class ScanFragment extends Fragment {
    private ImageView imageView;
    private TextView textView,tretment;
    private File imageFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scan, container, false);

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.text_view);
        tretment = view.findViewById(R.id.treatment);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ScanFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            // The image was successfully picked, get its URI
            Uri uri = data.getData();

            // Convert the URI to a file
            imageFile = new File(uri.getPath());
            imageView.setImageURI(uri);

            // Upload the image
            uploadImage(imageFile);
        }
    }

    public void uploadImage(File file) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*jpg"), file))
                .build();

        Request request = new Request.Builder()
                //.url("http://127.0.0.1:8000/ExtoxiaPredict")
                .url("http://localhost:8000/ExtoxiaPredict")
                .header("Connection", "close")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        String jsonData = response.body().string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        final String message = Jobject.getString("The predicted class index is");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //textView.setText(message);
                                String[] classes = {"Sun burns", "Shingles", "Eczema","Acne","Melanoma"};
                                int classIndex = Integer.parseInt(message);
                                textView.setText(classes[classIndex - 1]);
                                if ("Sun burns" == classes[classIndex - 1]){
                                    tretment.setText("The Treatment\n\nTake a pain reliever.\nCool the skin.\nApply a moisturizer, skin cream, or gel.\nDrink more water throughout the day.\nLeave the blisters alone.\nTreat flaky skin gently.\nUse any anti-itch medication.\nApply a soothing medicated cream.\nYou should see a doctor");

                                } else if ("Shingles" == classes[classIndex - 1]) {
                                    tretment.setText("The Treatment\n\nTopical capsaicin patch.\nAntispasmodics, such as gabapentin.\nTricyclic antidepressants, such as amitriptyline\nNumbing agents, such as lidocaine, which comes as a cream, gel, spray, or skin patch\nAn injection containing corticosteroids and local anesthetics.\nYou should see a doctor");

                                } else if ("Eczema" == classes[classIndex - 1]) {
                                    tretment.setText("The Treatment\n\n1. Avoidance of irritants | Allergens.\n2. Frequent use of emollients.\n3. Topical steroids.\nYou should see a doctor");

                                } else if ("Acne" == classes[classIndex - 1]) {
                                    tretment.setText("The Treatment\n\n1. Over-the-counter topical creams containing salicylic acid or benzoyl peroxide.\n2. Topical or oral antibiotics, which are used in severe cases of acne.\n3. Corticosteroid injections in case of inflammatory acne.\n4. Products derived from vitamin A, such as tretinoin, isotretinoin, and adapalene, which are available as oral pills and topical creams and ointments.\nYou should see a doctor");

                                } else if ("Melanoma" == classes[classIndex - 1]) {
                                    tretment.setText("The Treatment\n\nTreatment according to the stage.\n1. Surgery.\n2. Doug therapy.\n3. Radiotherapy.\nYou should see a doctor");
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*public void setImageUri(Uri uri) {
        ImageView imageView = getView().findViewById(R.id.imageView);
        imageView.setImageURI(uri);
        uploadImage(imageFile);
    }*/
}