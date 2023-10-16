package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.source.LocationTypes;
import com.example.rentals_app.source.RentTypes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApartmentAddActivity extends AppCompatActivity {

    Button addButton;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_add);

        addButton = findViewById(R.id.addButton);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApartmentModel apartment = new ApartmentModel();
                apartment.setTitle("2 Bedroom Apartment for Rent in Brampton!");
                apartment.setPrice(2490.00);
                apartment.setUnitNumber(1234);
                apartment.setAddress("Saint-Catherine St W");
                apartment.setLocation(LocationTypes.CITY_OF_MONTREAL);
                apartment.setPostalCode("H3H 2T2");
                apartment.setSize(4);
                apartment.setBedrooms(3);
                apartment.setBathrooms(2);
                apartment.setStageFloor(4);
                apartment.setHasParking(true);
                apartment.setHasHeating(true);
                apartment.setRentType(RentTypes.ROOM_RENTALS_AND_ROOMMATES);

                reference.push().setValue(apartment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(ApartmentAddActivity.this, "New apartment success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ApartmentAddActivity.this, "New apartment FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}