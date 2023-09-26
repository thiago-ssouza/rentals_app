package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    userName.setError("Username is required");
                    userName.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    userPassword.setError("Password is required");
                    userPassword.requestFocus();
                    return;
                }
                // To change once firebase is implemented
                if (username.equals("admin") && password.equals("admin")) {
                    // Intent to go to the next activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
