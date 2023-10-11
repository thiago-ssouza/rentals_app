package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetActivity extends AppCompatActivity {

    EditText  email;
    Button btnReset;
    TextView loginLink, registerLink;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        email = findViewById(R.id.email);

        btnReset = findViewById(R.id.btnReset);
        loginLink = findViewById(R.id.loginLink);
        registerLink = findViewById(R.id.forgotLink);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();

                if (userEmail.isEmpty()) {
                    Toast.makeText(ResetActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetActivity.this, "A password reset email has been sent", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ResetActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(ResetActivity.this, "Error sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
