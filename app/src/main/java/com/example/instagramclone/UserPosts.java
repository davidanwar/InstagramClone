package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UserPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        linearLayout = findViewById(R.id.linearLayout);

        // menerima data dari intent di avtivity atau fragment lain
        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this, receivedUserName, Toast.LENGTH_SHORT,
                FancyToast.SUCCESS, true).show();

        setTitle(receivedUserName + "'s Posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Photo");
        parseQuery.whereEqualTo("username", receivedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(UserPosts.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null){
                    for (ParseObject post : objects){
                        final TextView postDescription = new TextView(UserPosts.this);
                        postDescription.setText(post.get("image_des") + ""); // jika memakai toString() dan datanya kosong maka aplikasi akan crash
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e == null){

                                    // convert from server
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView potsImageView = new ImageView(UserPosts.this);
                                    LinearLayout.LayoutParams imageViewParams =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageViewParams.setMargins(5, 5, 5, 5);
                                    potsImageView.setLayoutParams(imageViewParams);
                                    potsImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    potsImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,15);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    // tambahkan gambar dan deskripsi ke linear layout
                                    linearLayout.addView(potsImageView);
                                    linearLayout.addView(postDescription);


                                }
                            }
                        });

                    }
                } else {
                    FancyToast.makeText(UserPosts.this, receivedUserName + " doesn't have any post", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();
                    finish();
                }
                progressDialog.dismiss();
            }

        });

    }
}
