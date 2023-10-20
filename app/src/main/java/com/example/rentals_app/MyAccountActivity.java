package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyAccountActivity extends AppCompatActivity {

    private EditText nameEditText, lastnameEditText, emailEditText, phoneEditText;
    private Button btnSave, btnProfile;
    private TextView backToLogin;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        UserModel loggedUser = UserModel.getSession();

        if (loggedUser != null) {
            nameEditText = findViewById(R.id.editTextName);
            lastnameEditText = findViewById(R.id.editTextLastName);
            emailEditText = findViewById(R.id.editTextEmail);
            phoneEditText = findViewById(R.id.editTextPhone);
            btnSave = findViewById(R.id.btnSave);
            btnProfile = findViewById(R.id.btnProfile);

            nameEditText.setText(loggedUser.getFirstName());
            lastnameEditText.setText(loggedUser.getLastName());
            emailEditText.setText(loggedUser.getEmail());
            phoneEditText.setText(loggedUser.getPhone());

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            if (loggedUser instanceof TenantModel) {
                userRef = database.getReference("tenants");
            } else {
                userRef = database.getReference("owners");
            }

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String enteredName = nameEditText.getText().toString();
                    String enteredLastname = lastnameEditText.getText().toString();
                    String enteredEmail = emailEditText.getText().toString();
                    String enteredPhone = phoneEditText.getText().toString();

                    HashMap<String, Object> userDataUpdates = new HashMap<>();
                    userDataUpdates.put("firstName", enteredName);
                    userDataUpdates.put("lastName", enteredLastname);
                    userDataUpdates.put("email", enteredEmail);
                    userDataUpdates.put("phone", enteredPhone);

                    userRef.updateChildren(userDataUpdates)
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
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
