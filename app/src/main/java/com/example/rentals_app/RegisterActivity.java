package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName, lastName, email, password, confirmPassword, phoneNumber;
    Button btnRegister;
    TextView loginText, registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        phoneNumber = findViewById(R.id.phoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
        loginText = findViewById(R.id.loginLink);
        registerText = findViewById(R.id.forgotLink);

    }
}