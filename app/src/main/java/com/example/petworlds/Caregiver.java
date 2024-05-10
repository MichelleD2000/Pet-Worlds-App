package com.example.petworlds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Caregiver extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Dataclass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchPet;
    MyAdapter adapter;
    Button logoutButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver);

        recyclerView = findViewById(R.id.recyclerView);
        searchPet = findViewById(R.id.searchPet);
        logoutButton1 = findViewById(R.id.logoutButton1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Caregiver.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(Caregiver.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(Caregiver.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload Details");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Dataclass dataclass = itemSnapshot.getValue(Dataclass.class);
                    dataList.add(dataclass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        logoutButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Caregiver.this, Loginpage.class);
                startActivity(intent);
            }
        });

        searchPet.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPet(newText);
                return true;
            }
        });
    }

    public void searchPet(String text){
        ArrayList<Dataclass> searchPet = new ArrayList<>();
        for (Dataclass dataclass : dataList) {
            String name = dataclass.getDatauploadTopic().toLowerCase();
            String type = dataclass.getDataPrice().toLowerCase();

            if (name.contains(text.toLowerCase()) || type.contains(text.toLowerCase())) {
                searchPet.add(dataclass);
            }
        }
        adapter.searchDataList(searchPet);
    }
}