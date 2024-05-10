package com.example.petworlds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Details extends AppCompatActivity {

    TextView detailuploadTopic, detailPrice, detailAge, detailBreed, detailGender, detailColor, detailDescription;
    ImageView detailuploadImage;

    Button booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailuploadTopic = findViewById(R.id.detailuploadTopic);
        detailPrice = findViewById(R.id.detailPrice);
        detailAge = findViewById(R.id.detailAge);
        detailBreed = findViewById(R.id.detailBreed);
        detailGender = findViewById(R.id.detailGender);
        detailColor = findViewById(R.id.detailColor);
        detailDescription = findViewById(R.id.detailDescription);
        detailuploadImage = findViewById(R.id.detailuploadImage);
        booking = findViewById(R.id.booking);

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Booking.class));
            }
        });



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailuploadTopic.setText(bundle.getString("Topic"));
            detailPrice.setText(bundle.getString("Price"));
            detailAge.setText(bundle.getString("Age"));
            detailBreed.setText(bundle.getString("Breed"));
            detailGender.setText(bundle.getString("Gender"));
            detailColor.setText(bundle.getString("Color"));
            detailDescription.setText(bundle.getString("Description"));
            Glide.with(this).load(bundle.getString("Image")).into(detailuploadImage);
        }
    }
}