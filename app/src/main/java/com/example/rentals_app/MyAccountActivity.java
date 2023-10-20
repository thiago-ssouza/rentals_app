package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rentals_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyAccountActivity extends AppCompatActivity {

    private EditText name, lastname, email, phone, password;
    private Button btnSave, btnProfile;
    private TextView backToLogin;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        UserModel loggedUser = UserModel.getSession();

        if (loggedUser != null) {
            name = findViewById(R.id.editTextName);
            lastname = findViewById(R.id.editTextLastName);
            email = findViewById(R.id.editTextEmail);
            phone = findViewById(R.id.editTextPhone);
            btnSave = findViewById(R.id.btnSave);
            btnProfile = findViewById(R.id.btnProfile);

            name.setText(loggedUser.getFirstName());
            lastname.setText(loggedUser.getLastName());
            email.setText(loggedUser.getEmail());
            phone.setText(loggedUser.getPhone());

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            userRef = database.getReference("tenants").child(loggedUser.getId());
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String enteredName = name.getText().toString();
                    String enteredLastname = lastname.getText().toString();
                    String enteredEmail = email.getText().toString();
                    String enteredPhone = phone.getText().toString();

                    HashMap<String, Object> updates = new HashMap<>();
                    updates.put("firstName", enteredName);
                    updates.put("lastName", enteredLastname);
                    updates.put("email", enteredEmail);
                    updates.put("phone", enteredPhone);

                    userRef.updateChildren(updates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MyAccountActivity.this, "User data updated successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyAccountActivity.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }

        else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
