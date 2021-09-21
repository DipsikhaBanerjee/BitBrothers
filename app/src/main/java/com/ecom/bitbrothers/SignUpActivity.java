package com.ecom.bitbrothers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email,password;
    Button sign;
    TextView already;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_sign_up);


        name = findViewById(R.id.name);
        email = findViewById(R.id.signupemail);
        password = findViewById(R.id.signuppassword);
        sign = findViewById(R.id.signup);
        already = findViewById(R.id.already);

        mAuth = FirebaseAuth.getInstance();

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {

        String names = name.getText().toString();
        String emails = email.getText().toString();
        String passwords = password.getText().toString();


        if (!emails.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            if (!passwords.isEmpty() && !names.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(emails, passwords)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                password.setError("Passwords are different ! Retype");
            }
        } else {
            name.setError("Fields cannot be empty");
            password.setError("Fields cannot be empty");
        }
    }


    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}