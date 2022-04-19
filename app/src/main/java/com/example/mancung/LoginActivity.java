package com.example.mancung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//Nama : Reihan Wiyanda
//Kelas : IF-1
//Tanggal : 19/04/2022
public class LoginActivity extends AppCompatActivity {
    TextView Email, Password;
    Button Login;
    ProgressBar proses;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.text1);
        Password = findViewById(R.id.text2);
        Login = findViewById(R.id.b1);
        proses = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String pass = Password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Username tidak boleh kosong!");
                    return;
                }

                if (TextUtils.isEmpty((pass))) {
                    Password.setError("Password tidak boleh kosong!");
                    return;
                }

                proses.setVisibility(View.VISIBLE);

                //Auth data pengguna
                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            proses.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        getSupportActionBar().hide();
        TextView Daftar = findViewById(R.id.Daftar);

        //underline
        SpannableString content = new SpannableString("Daftar Sekarang");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        Daftar.setText(content);

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, DaftarActivity.class));
            }
        });


    }
}