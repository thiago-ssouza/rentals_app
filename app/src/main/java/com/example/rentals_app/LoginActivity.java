package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, userPassword;
    private Button btnLogin, btnRegister;
    private Spinner loginType;
    private FirebaseAuth mAuth;
    private TextView forgotPasswordLink;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        loginType = findViewById(R.id.loginType);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        LoginTypes[] items = LoginTypes.values();
        ArrayAdapter<LoginTypes> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        loginType.setAdapter(adapter);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String username = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String type = loginType.getSelectedItem().toString().trim();

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

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();

            return;
        }

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (type.equals("TENANT")) {
                        reference = database.getReference("tenants");
                    } else {
                        reference = database.getReference("owners");
                    }

                    Query checkUserDB = reference.orderByChild("email").equalTo(username);
                    checkUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserModel loggedUser;
                                Class destination;
                                String dbName = snapshot.child(mAuth.getCurrentUser().getUid()).child("firstName").getValue(String.class);
                                String dbLastName = snapshot.child(mAuth.getCurrentUser().getUid()).child("lastName").getValue(String.class);
                                String dbEmail = snapshot.child(mAuth.getCurrentUser().getUid()).child("email").getValue(String.class);
                                String dbPhone = snapshot.child(mAuth.getCurrentUser().getUid()).child("phone").getValue(String.class);

                                if (type.equals("TENANT")) {
                                    loggedUser = new TenantModel(mAuth.getCurrentUser().getUid(), dbName, dbLastName, dbEmail, dbPhone);
                                    destination = SearchApartmentsActivity.class;
                                } else {
                                    loggedUser = new OwnerModel(mAuth.getCurrentUser().getUid(), dbName, dbLastName, dbEmail, dbPhone);
                                    destination = ApartmentsListActivity.class;
                                }

                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, destination);

                                UserModel.setSession(loggedUser);
                                intent.putExtra("userUUid", mAuth.getCurrentUser().getUid());

                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "User not found. Please check credentials and try again", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please check credentials and try again", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
