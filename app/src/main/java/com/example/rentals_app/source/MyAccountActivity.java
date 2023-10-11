package com.example.rentals_app.source;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rentals_app.R;

public class MyAccountActivity extends AppCompatActivity {

    EditText name, email, phone, password, confirmPassword;
    Button btnSave;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

    }
}