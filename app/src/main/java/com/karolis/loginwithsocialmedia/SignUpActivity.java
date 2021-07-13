package com.karolis.loginwithsocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private Button signUp_btn, facebook_btn;
    private EditText usernameET, passwordET, emailET;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView signInIfHaveAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        signUp_btn = findViewById(R.id.signUp_btn);
        usernameET = findViewById(R.id.userNameET1);
        passwordET = findViewById(R.id.passwordET1);
        emailET = findViewById(R.id.emailNameET);
        facebook_btn = findViewById(R.id.facebook_btn_signUp);
        signInIfHaveAccount = findViewById(R.id.signInIfHaveAccount);


        signInIfHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignUpWithFacebook.class);
                startActivity(intent);
            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }



            private void registerNewUser() {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String username = usernameET.getText().toString();


                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please enter username!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registration successful!"
                                            , Toast.LENGTH_LONG).show();

                                    FirebaseUser Auth_user = mAuth.getCurrentUser();
                                    User newUser = new User(); // creating new user and giving values.

                                    newUser.userId = Auth_user.getUid();
                                    newUser.userEmail = Auth_user.getEmail();
                                    newUser.userName = username;

                                    //Create new empty document "record" in database
                                    DocumentReference newUserRef = db.collection("Users").document(newUser.userId);

                                    // update empty record with User object
                                    newUserRef.set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast t = Toast.makeText(SignUpActivity.this, "New User Created", Toast.LENGTH_SHORT);
                                            t.setGravity(Gravity.CENTER, 0, 0);
                                            t.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast t = Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT);
                                            t.setGravity(Gravity.CENTER, 0, 0);
                                            t.show();
                                        }
                                    });
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Registration failed! " + task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        });




}
}
