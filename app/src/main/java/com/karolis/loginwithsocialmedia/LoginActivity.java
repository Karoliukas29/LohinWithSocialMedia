package com.karolis.loginwithsocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn, login_With_Facebook_btn, login_With_Google_btn;
    private EditText usernameET, passwordET;
    private TextView signUpIfNoAccount;
    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        login_btn = findViewById(R.id.login_btn);
        usernameET = findViewById(R.id.userNameET);
        passwordET = findViewById(R.id.passwordET);
        login_With_Facebook_btn = findViewById(R.id.facebook_login_btn);
        login_With_Google_btn = findViewById(R.id.google_login_btn);

        login_With_Google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginWithGoogle.class);
                startActivity(intent);
            }
        });

        login_With_Facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginWithFacebook.class);
                startActivity(intent);
            }
        });
        signUpIfNoAccount = findViewById(R.id.signUpIfNoAccount);

        signUpIfNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();

            }



            private void loginUserAccount() {
                String email = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter Username...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter Password!", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Login failed! " +
                                            task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}