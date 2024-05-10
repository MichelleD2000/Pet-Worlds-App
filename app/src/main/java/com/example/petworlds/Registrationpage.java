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

public class Registrationpage extends AppCompatActivity {

    public static final String TAG ="TAG";
    EditText edtname, edtemail, edtaddress, edtcontact, edtuserpin;
    Button btnregister;
    TextView textView4, loginRedirectText;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);

        mAuth = FirebaseAuth.getInstance();

        edtname = findViewById(R.id.edtname);
        edtemail = findViewById(R.id.edtemail);
        edtaddress = findViewById(R.id.edtaddress);
        edtcontact = findViewById(R.id.edtcontact);
        edtuserpin = findViewById(R.id.edtuserpin);
        btnregister = findViewById(R.id.btnregister);
        textView4 = findViewById(R.id.textView4);
        progressBar = findViewById(R.id.progressBar);
        loginRedirectText = findViewById(R.id.loginRedirectText);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(edtemail.getText());
                password = String.valueOf(edtuserpin.getText());

                if (TextUtils.isEmpty(email)) {
                    edtemail.setError("Email Is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edtuserpin.setError("Password Is Required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Registrationpage.this, "Account Created.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registrationpage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registrationpage.this, Loginpage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
