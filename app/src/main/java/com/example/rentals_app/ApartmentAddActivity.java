package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.source.LocationTypes;
import com.example.rentals_app.source.RentTypes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ApartmentAddActivity extends AppCompatActivity {

    EditText txtTitle, txtPrice, txtUnitNumber, txtAddress, txtLocation, txtPostalCode, txtSize, txtBedrooms, txtBathrooms, txtStageFloor, txtRentType;
    CheckBox checkHasParking, checkHasHeating;
    Button addButton;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_add);

        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtUnitNumber = findViewById(R.id.txtUnitNumber);
        txtAddress = findViewById(R.id.txtAddress);
        txtLocation = findViewById(R.id.txtLocation);
        txtPostalCode = findViewById(R.id.txtPostalCode);
        txtSize = findViewById(R.id.txtSize);
        txtBedrooms = findViewById(R.id.txtBedrooms);
        txtBathrooms = findViewById(R.id.txtBathrooms);
        txtStageFloor = findViewById(R.id.txtStageFloor);
        txtRentType = findViewById(R.id.txtRentType);
        checkHasParking = findViewById(R.id.checkHasParking);
        checkHasHeating = findViewById(R.id.checkHasHeating);
        addButton = findViewById(R.id.addButton);

        reference = FirebaseDatabase.getInstance().getReference().child("apartments");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = txtTitle.getText().toString();
                double price = Double.parseDouble(txtPrice.getText().toString());
                int unitNumber = Integer.parseInt(txtUnitNumber.getText().toString());
                String address = txtAddress.getText().toString();
                String location = txtLocation.getText().toString();
                String postalCode = txtPostalCode.getText().toString();
                int size = Integer.parseInt(txtSize.getText().toString());
                int bedrooms = Integer.parseInt(txtBedrooms.getText().toString());
                int bathrooms = Integer.parseInt(txtBathrooms.getText().toString());
                int stageFloor = Integer.parseInt(txtStageFloor.getText().toString());
                String rentType = txtRentType.getText().toString();
                boolean hasParking = checkHasParking.isChecked();
                boolean hasHeating = checkHasHeating.isChecked();


                ApartmentModel apartment = new ApartmentModel(unitNumber, address, postalCode, LocationTypes.CITY_OF_MONTREAL, size, bedrooms,
                        bathrooms, hasParking, hasHeating, stageFloor, RentTypes.ROOM_RENTALS_AND_ROOMMATES, price, title, "", null, null, null, null);

                reference.push().setValue(apartment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    Class destination = ApartmentsListActivity.class;
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ApartmentAddActivity.this, "New apartment added successfully", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ApartmentAddActivity.this, destination);

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ApartmentAddActivity.this, "Failed to add new apartment", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}