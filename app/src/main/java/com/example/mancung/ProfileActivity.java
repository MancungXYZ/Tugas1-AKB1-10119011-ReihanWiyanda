package com.example.mancung;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

//Nama : Reihan Wiyanda
//Kelas : IF-1
//Tanggal : 19/04/2022

public class ProfileActivity extends AppCompatActivity {
    TextView Nim, Nama, Kelas, Deskripsi;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();

        //ambil data pada tampilan android
        Nama = findViewById(R.id.pengguna);
        Nim = findViewById(R.id.nim);
        Kelas = findViewById(R.id.IF1);
        Deskripsi = findViewById(R.id.desk);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Nama.setText(documentSnapshot.getString("Nama"));
                Kelas.setText(documentSnapshot.getString("Kelas"));
                Deskripsi.setText(documentSnapshot.getString("Desc"));
            }
        });
    }
}