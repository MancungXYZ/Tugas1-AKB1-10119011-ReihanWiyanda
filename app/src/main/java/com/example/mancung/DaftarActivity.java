package com.example.mancung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

//Nama : Reihan Wiyanda
//Kelas : IF-1
//Tanggal : 19/04/2022

public class DaftarActivity extends AppCompatActivity {
    TextView Email, Password, Nama, Nim, Kelas, Deskripsi;
    Button Daftar;
    ProgressBar proses;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        //
        Email = findViewById(R.id.text1);
        Password = findViewById(R.id.text2);
        Nama = findViewById(R.id.namalengkap);
        Nim = findViewById(R.id.nim);
        Kelas = findViewById(R.id.kelas);
        Deskripsi = findViewById(R.id.deskripsi);
        Daftar = findViewById(R.id.b1);
        proses = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String pass = Password.getText().toString().trim();
                String name = Nama.getText().toString();
                String nim = Nim.getText().toString();
                String klas = Kelas.getText().toString();
                String desk = Deskripsi.getText().toString();
                //validasi
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Username tidak boleh kosong!");
                    return;
                }

                if (TextUtils.isEmpty((pass))) {
                    Password.setError("Password tidak boleh kosong!");
                    return;
                }

                if (pass.length() < 8 ) {
                    Password.setError("Password harus lebih dari 8 Karakter!");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Nama.setError("Nama tidak boleh kosong!");
                    return;
                }

                if (TextUtils.isEmpty(klas)) {
                    Kelas.setError("Kelas tidak boleh kosong!");
                    return;
                }

                if (TextUtils.isEmpty(desk)) {
                    Deskripsi.setError("Username tidak boleh kosong!");
                    return;
                }

                proses.setVisibility(View.VISIBLE);

                //mulai memasukan data
                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DaftarActivity.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Nama", name);
                            user.put("Nim", nim);
                            user.put("Kelas", klas);
                            user.put("Desc", desk);
                            user.put("Email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "Sukses memasukan data" + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(DaftarActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });






        //sembunyikan bar
        getSupportActionBar().hide();
        //Implicit Intent
        TextView Login = findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
            }
        });
    }
}