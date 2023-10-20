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

import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.ApartmentsListActivity;
import com.example.rentals_app.model.UserModel;
import com.example.rentals_app.source.LocationTypes;
import com.example.rentals_app.source.RentTypes;
import com.example.rentals_app.source.StatusTypes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.rentals_app.model.ApartmentModel;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ApartmentEditActivity extends AppCompatActivity {
    private EditText txtTitle, txtPrice, txtUnitNumber, txtAddress, txtLocation, txtPostalCode, txtSize, txtBedrooms, txtBathrooms, txtStageFloor, txtRentType;
    private CheckBox checkHasParking, checkHasHeating;
    private Button saveButton;
    private String apartmentId;

    Intent intent = null;
    ApartmentModel apartment = null;
    FirebaseDatabase database;
    DatabaseReference reference;
    UserModel loggedUser;
    String selectedApartmentUID;
    Class destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_edit);

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
        saveButton = findViewById(R.id.saveButton);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");

        loggedUser = UserModel.getSession();

        // temporal solution - id comes from ApartmentListActivity
        intent = getIntent();
        intent.putExtra("selectedApartmentUID", "-NhCDUAw3HTKmp76MMwb");

        if (intent != null) {
            selectedApartmentUID = intent.getStringExtra("selectedApartmentUID");

            if (selectedApartmentUID != null) {

                Query checkApartmentData = reference.child(selectedApartmentUID);

                checkApartmentData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {

                            apartment = new ApartmentModel();

                            apartment.setId(selectedApartmentUID);
                            apartment.setTitle(snapshot.child("title").getValue(String.class));
                            apartment.setPrice(snapshot.child("price").getValue(Double.class));
                            apartment.setUnitNumber(snapshot.child("unitNumber").getValue(Integer.class));
                            apartment.setAddress(snapshot.child("address").getValue(String.class));
                            apartment.setLocation(snapshot.child("location").getValue(LocationTypes.class));
                            apartment.setPostalCode(snapshot.child("postalCode").getValue(String.class));
                            apartment.setSize(snapshot.child("size").getValue(Integer.class));
                            apartment.setBedrooms(snapshot.child("bedrooms").getValue(Integer.class));
                            apartment.setBathrooms(snapshot.child("bathrooms").getValue(Integer.class));
                            apartment.setStageFloor(snapshot.child("stageFloor").getValue(Integer.class));
                            apartment.setHasParking(snapshot.child("hasParking").getValue(Boolean.class));
                            apartment.setHasHeating(snapshot.child("hasHeating").getValue(Boolean.class));
                            apartment.setRentType(snapshot.child("rentType").getValue(RentTypes.class));
                            apartment.setDescription(snapshot.child("description").getValue(String.class));
                            apartment.setOwner(snapshot.child("owner").getValue(OwnerModel.class));
                            apartment.setStatus(snapshot.child("status").getValue(StatusTypes.class));

                            txtTitle.setText(apartment.getTitle());
                            txtPrice.setText("$" + String.format("%.2f",apartment.getPrice()));
                            txtUnitNumber.setText(String.format(Integer.toString(apartment.getUnitNumber())));
                            txtAddress.setText(apartment.getAddress());
                            txtLocation.setText(apartment.getLocation().toString());
                            txtPostalCode.setText(apartment.getPostalCode());
                            txtSize.setText(String.format(Integer.toString(apartment.getSize())));
                            txtBedrooms.setText(String.format(Integer.toString(apartment.getBedrooms())));
                            txtBathrooms.setText(String.format(Integer.toString(apartment.getBathrooms())));
                            txtStageFloor.setText(String.format(Integer.toString(apartment.getStageFloor())));
                            txtRentType.setText(apartment.getRentType().toString());
                            checkHasParking.setChecked(apartment.getHasParking());
                            checkHasHeating.setChecked(apartment.getHasHeating());

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        } else {
            Toast.makeText(ApartmentEditActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            intent = new Intent(ApartmentEditActivity.this, destination);
            startActivity(intent);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting the updated values
                String updatedTitle = txtTitle.getText().toString();

                // Get the price text
                String priceText = txtPrice.getText().toString();
                // Remove all non-numeric characters
                String cleanPriceText = priceText.replaceAll("[^\\d.]", "");
                double updatedPrice = Double.parseDouble(cleanPriceText);

                int updatedUnitNumber = Integer.parseInt(txtUnitNumber.getText().toString());
                String updatedAddress = txtAddress.getText().toString();
                LocationTypes updatedLocation = LocationTypes.valueOf(txtLocation.getText().toString());
                String updatedPostalCode = txtPostalCode.getText().toString();
                int updatedSize = Integer.parseInt(txtSize.getText().toString());
                int updatedBedrooms = Integer.parseInt(txtBedrooms.getText().toString());
                int updatedBathrooms = Integer.parseInt(txtBathrooms.getText().toString());
                int updatedStageFloor = Integer.parseInt(txtStageFloor.getText().toString());
                boolean updatedHasParking = checkHasParking.isChecked();
                boolean updatedHasHeating = checkHasHeating.isChecked();
                RentTypes updatedRentType = RentTypes.valueOf(txtRentType.getText().toString());

                // Object creation
                ApartmentModel updatedApartment = new ApartmentModel();

                updatedApartment.setId(selectedApartmentUID);
                updatedApartment.setTitle(updatedTitle);
                updatedApartment.setPrice(updatedPrice);
                updatedApartment.setUnitNumber(updatedUnitNumber);
                updatedApartment.setAddress(updatedAddress);
                updatedApartment.setLocation(updatedLocation);
                updatedApartment.setPostalCode(updatedPostalCode);
                updatedApartment.setSize(updatedSize);
                updatedApartment.setBedrooms(updatedBedrooms);
                updatedApartment.setBathrooms(updatedBathrooms);
                updatedApartment.setStageFloor(updatedStageFloor);
                updatedApartment.setHasParking(updatedHasParking);
                updatedApartment.setHasHeating(updatedHasHeating);
                updatedApartment.setRentType(updatedRentType);

                // Update data in Firebase
                reference.child(selectedApartmentUID).setValue(updatedApartment)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ApartmentEditActivity.this, "Apartment updated successfully!", Toast.LENGTH_SHORT).show();
                                intent = new Intent(ApartmentEditActivity.this, ApartmentsListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ApartmentEditActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                                intent = new Intent(ApartmentEditActivity.this, ApartmentsListActivity.class);
                                startActivity(intent);
                            }
                        });


            }

        });
    }
}
