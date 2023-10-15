package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.model.UserModel;
import com.example.rentals_app.source.LoginTypes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword, phoneNumber;
    private Button btnRegister, btnToLogin;
    private Spinner registerType;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        phoneNumber = findViewById(R.id.phoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);
        registerType = findViewById(R.id.registerType);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        LoginTypes[] items = LoginTypes.values();
        ArrayAdapter<LoginTypes> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        registerType.setAdapter(adapter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    private void registerUser() {
        String name = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cpass = confirmPassword.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String type = registerType.getSelectedItem().toString().trim();

        if (name.isEmpty() || lname.isEmpty() || mail.isEmpty() || pass.isEmpty() || cpass.isEmpty()
                || phone.isEmpty()) {
            Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!pass.equals(cpass)) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();

            return;
        }

        if (pass.length() < 8) {
            Toast.makeText(this, "Password should be 8 characters at least", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Please set a valid email");
            email.requestFocus();

            return;
        }

        UserModel newUser;
        if (type.equals("TENANT")) {
            newUser = new TenantModel(name, lname, mail, phone);
            reference = database.getReference("tenants");
        } else {
            newUser = new OwnerModel(name, lname, mail, phone);
            reference = database.getReference("owners");
        }

        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //MODEL
                    reference.child(mAuth.getCurrentUser().getUid()).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "There was an error, Please try again. (002)", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "There was an error, Please try again. (001)", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}