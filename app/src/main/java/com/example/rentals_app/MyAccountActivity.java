package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyAccountActivity extends AppCompatActivity {

    EditText name, lastname, email, phone, password;
    Button btnSave, btnProfile;
    TextView backToLogin;
    DatabaseReference reference;

    String nameuser, lastnameuser, emailuser, userusername, passuser, phoneuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        reference = FirebaseDatabase.getInstance().getReference("users");
        name = findViewById(R.id.editTextName);
        lastname = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextPassword);
        btnSave = findViewById(R.id.btnSave);
        btnProfile = findViewById(R.id.btnProfile);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = name.getText().toString();
                String newLastName = lastname.getText().toString();
                String newEmail = email.getText().toString();
                String newPhone = phone.getText().toString();
                String newPassword = password.getText().toString();

                if (isNameChanged(newName)) {
                    Toast.makeText(MyAccountActivity.this, "Name saved done", Toast.LENGTH_SHORT).show();
                }
                if (isLastNameChanged(newLastName)) {
                    Toast.makeText(MyAccountActivity.this, "Last Name saved done", Toast.LENGTH_SHORT).show();
                }
                if (isEmailChanged(newEmail)) {
                    Toast.makeText(MyAccountActivity.this, "Email saved done", Toast.LENGTH_SHORT).show();
                }
                if (isPhoneChanged(newPhone)) {
                    Toast.makeText(MyAccountActivity.this, "Phone saved done", Toast.LENGTH_SHORT).show();
                }
                if (isPassChanged(newPassword)) {
                    Toast.makeText(MyAccountActivity.this, "Password saved done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyAccountActivity.this, "No change", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
                intent.putExtra("name", nameuser);
                intent.putExtra("lastname", lastnameuser);
                intent.putExtra("email", emailuser);
                intent.putExtra("username", userusername);
                intent.putExtra("password", passuser);
                intent.putExtra("phone", phoneuser);
                startActivity(intent);
            }
        });

    }

    private boolean isNameChanged(String newName) {
        if (!nameuser.equals(newName)) {
            reference.child(userusername).child("name").setValue(newName);
            nameuser = newName;
            return true;
        } else {
            return false;
        }
    }

    private boolean isLastNameChanged(String newLastName) {
        if (!lastnameuser.equals(newLastName)) {
            reference.child(userusername).child("lastname").setValue(newLastName);
            lastnameuser = newLastName;
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged(String newEmail) {
        if (!emailuser.equals(newEmail)) {
            reference.child(userusername).child("email").setValue(newEmail);
            emailuser = newEmail;
            return true;
        } else {
            return false;
        }
    }

    private boolean isPhoneChanged(String newPhone) {
        if (!phoneuser.equals(newPhone)) {
            reference.child(userusername).child("phone").setValue(newPhone);
            phoneuser = newPhone;
            return true;
        } else {
            return false;
        }
    }

    private boolean isPassChanged(String newPassword) {
        if (!passuser.equals(newPassword)) {
            reference.child(userusername).child("password").setValue(newPassword);
            passuser = newPassword;
            return true;
        } else {
            return false;
        }
    }

    public void showAllUserData() {
        Intent intent = getIntent();
        nameuser = intent.getStringExtra("name");
        lastnameuser = intent.getStringExtra("lastname");
        emailuser = intent.getStringExtra("email");
        userusername = intent.getStringExtra("username");
        passuser = intent.getStringExtra("password");
        phoneuser = intent.getStringExtra("phone");

        name.setText(nameuser);
        lastname.setText(lastnameuser);
        email.setText(emailuser);
        phone.setText(phoneuser);
        password.setText(passuser);
    }
}
