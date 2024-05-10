package com.example.petworlds;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class OwnerActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTopic, Price, Age, Breed, Gender, Color,Description;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        uploadTopic = findViewById(R.id.uploadTopic);
        saveButton = findViewById(R.id.saveButton);
        uploadImage = findViewById(R.id.uploadImage);
        Price = findViewById(R.id.Price);
        Age = findViewById(R.id.Age);
        Breed = findViewById(R.id.Breed);
        Gender = findViewById(R.id.Gender);
        Color = findViewById(R.id.Color);
        Description = findViewById(R.id.Petowner);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(OwnerActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    public void saveData() {
        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Image")
                    .child(uri.getLastPathSegment());

            AlertDialog.Builder builder = new AlertDialog.Builder(OwnerActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri urlImage = task.getResult();
                                imageURL = urlImage.toString();
                                dialog.dismiss();
                                uploadData();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(OwnerActivity.this, "Failed to get image URL", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(OwnerActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(OwnerActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadData() {
        String topic = uploadTopic.getText().toString();
        String price = Price.getText().toString();
        String age = Age.getText().toString();
        String breed = Breed.getText().toString();
        String gender = Gender.getText().toString();
        String color = Color.getText().toString();
        String description = Description.getText().toString();

        Dataclass dataclass = new Dataclass(topic, price, age, breed, gender, color, description, imageURL);

        FirebaseDatabase.getInstance().getReference("Upload Details").child(topic)
                .setValue(dataclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OwnerActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(OwnerActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OwnerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}