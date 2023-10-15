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

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText  email;
    Button btnReset, btnToLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.email);

        btnReset = findViewById(R.id.btnReset);
        btnToLogin = findViewById(R.id.btnToLogin);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();

                if (userEmail.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "A password reset email has been sent", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Error sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
