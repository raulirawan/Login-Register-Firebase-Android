package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin;
    TextView tvRegister;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin= findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.linkRegister);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if ( mFirebaseUser != null ){
                Toast.makeText(LoginActivity.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);

                }

                else{
                    Toast.makeText(LoginActivity.this,"Silahkan Login Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString();
                String pwd  = password.getText().toString();

                if(mail.isEmpty()){
                    email.setError("Silahkan Masukan Email");
                    email.requestFocus();

                }
                else if(pwd.isEmpty()){
                    password.setError("Silahkan Masukan Password");
                    password.requestFocus();
                }

                else if(mail.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Email dan Password Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                }

                else if(!(mail.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });

                }

                else{
                    Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
