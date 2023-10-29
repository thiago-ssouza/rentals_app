package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyAccountActivity extends AppCompatActivity {

    private EditText nameEditText, lastnameEditText, emailEditText, phoneEditText;
    private Button btnSave, btnProfile;
    private TextView goBack;
    Intent intent = null;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    UserModel loggedUser;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        nameEditText = findViewById(R.id.editTextName);
        lastnameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmail);
        phoneEditText = findViewById(R.id.editTextPhone);
        btnSave = findViewById(R.id.btnSave);
        goBack = findViewById(R.id.goBackLink);

        loggedUser = UserModel.getSession();
        mAuth = FirebaseAuth.getInstance();


        if (loggedUser != null && loggedUser instanceof OwnerModel) {

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("owners");

            Query checkUserAccount = reference.child(mAuth.getCurrentUser().getUid());

            checkUserAccount.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        user = new UserModel();

                        user.setFirstName(snapshot.child("firstName").getValue(String.class));
                        user.setLastName(snapshot.child("lastName").getValue(String.class));
                        user.setEmail(snapshot.child("email").getValue(String.class));
                        user.setPhone(snapshot.child("phone").getValue(String.class));

                        // set text fields with database data
                        nameEditText.setText(user.getFirstName());
                        lastnameEditText.setText(user.getLastName());
                        emailEditText.setText(user.getEmail());
                        phoneEditText.setText(user.getPhone());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            Toast.makeText(MyAccountActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();

            intent = new Intent(MyAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updatedName = nameEditText.getText().toString();
                String updatedLastName = lastnameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();
                String updatedPhone = phoneEditText.getText().toString();

                UserModel updatedUser = new UserModel();

                updatedUser.setFirstName(updatedName);
                updatedUser.setLastName(updatedLastName);
                updatedUser.setEmail(updatedEmail);
                updatedUser.setPhone(updatedPhone);

                reference.child(mAuth.getCurrentUser().getUid()).setValue(updatedUser)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(MyAccountActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                                intent = new Intent(MyAccountActivity.this, ApartmentsListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MyAccountActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                                intent = new Intent(MyAccountActivity.this, ApartmentsListActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyAccountActivity.this, ApartmentsListActivity.class);
                startActivity(intent);
            }
        });



    }

}
