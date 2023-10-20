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
import com.example.rentals_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccountActivity extends AppCompatActivity {

    private EditText nameEditText, lastnameEditText, emailEditText, phoneEditText;
    private Button btnSave, btnProfile;
    private TextView backToLogin;
    private DatabaseReference ownersRef; // Referencia a la lista de propietarios

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        UserModel loggedUser = UserModel.getSession();

        if (loggedUser != null && loggedUser instanceof OwnerModel) {
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
            ownersRef = database.getReference("test");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Crear un objeto OwnerModel con los datos actualizados
                    OwnerModel updatedOwner = new OwnerModel();

                    updatedOwner.setId(loggedUser.getId()); // Asegúrate de mantener el ID del usuario
                    updatedOwner.setFirstName(nameEditText.getText().toString());
                    updatedOwner.setLastName(lastnameEditText.getText().toString());
                    updatedOwner.setEmail(emailEditText.getText().toString());
                    updatedOwner.setPhone(phoneEditText.getText().toString());

                    // Actualizar o agregar el propietario a la lista de propietarios
                    ownersRef.child(loggedUser.getId()).setValue(updatedOwner)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MyAccountActivity.this, "Owner data updated successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyAccountActivity.this, "Failed to update owner data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        } else {
            Toast.makeText(this, "User not logged in or not an owner", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // Método para obtener una lista de propietarios
    private void getListOfOwners() {
        ownersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ownerSnapshot : dataSnapshot.getChildren()) {
                    OwnerModel owner = ownerSnapshot.getValue(OwnerModel.class);
                    if (owner != null) {
                        // Realiza operaciones con cada propietario
                        // Por ejemplo, puedes agregarlos a una lista
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja errores si es necesario
            }
        });
    }
}
