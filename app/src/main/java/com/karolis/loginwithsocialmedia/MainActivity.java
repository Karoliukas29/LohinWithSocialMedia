package com.karolis.loginwithsocialmedia;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public ImageView profileImage;
    public EditText profileName, profileEmail;
    public FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = findViewById(R.id.Profile_photo_field);
        profileName = findViewById(R.id.ProfileName);
        profileEmail = findViewById(R.id.ProfileEmail);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(this).load(user.getPhotoUrl().toString()).into(profileImage);

        profileName.setText(user.getDisplayName());
        profileEmail.setText(user.getEmail());
//        String photoUrl = user.getPhotoUrl().toString();
//        String profileName = user.getDisplayName();

    }

}