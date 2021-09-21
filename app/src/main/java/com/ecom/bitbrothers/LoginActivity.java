package com.ecom.bitbrothers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    Button login;
    TextView new_user;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.signinemail);
        pass = findViewById(R.id.signinpassword);
        login = findViewById(R.id.login);
        new_user = findViewById(R.id.newuser);

        firebaseAuth = FirebaseAuth.getInstance();

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()){
                    email.setError("Enter a valid Email Address");
                    return;
                }
                if (pass.getText().toString().isEmpty()){
                    pass.setError("Invalid Password");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"Invalid Email Or Password. Please Retry !",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,SignUpActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}