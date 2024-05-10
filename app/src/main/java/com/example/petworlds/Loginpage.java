package com.example.petworlds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginpage extends AppCompatActivity {

    EditText edtemail, edtuserpin;
    Button loginBtn;
    TextView createBtn;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        mAuth = FirebaseAuth.getInstance();

        edtemail = findViewById(R.id.edtemail);
        edtuserpin = findViewById(R.id.edtuserpin);
        loginBtn = findViewById(R.id.loginBtn);
        createBtn = findViewById(R.id.createtext);
        progressBar = findViewById(R.id.progressBar);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Registrationpage.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtemail.getText().toString().trim();
                String password = edtuserpin.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtemail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtuserpin.setError("Password Is Required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Loginpage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Loginpage.this, WelcomePage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Loginpage.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}