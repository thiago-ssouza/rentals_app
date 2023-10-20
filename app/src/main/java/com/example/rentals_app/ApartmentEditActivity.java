package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.rentals_app.model.ApartmentModel;

public class ApartmentEditActivity extends AppCompatActivity {

    EditText txtTitle, txtPrice, txtUnitNumber, txtAddress, txtLocation, txtPostalCode, txtSize, txtBedrooms, txtBathrooms, txtStageFloor, txtRentType;
    CheckBox checkHasParking, checkHasHeating;
    Button saveButton;
    private DatabaseReference reference;
    private String apartmentId;

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

        reference = FirebaseDatabase.getInstance().getReference().child("apartments");
        apartmentId = getIntent().getStringExtra("apartmentId");

        reference.child(apartmentId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                ApartmentModel apartment = snapshot.getValue(ApartmentModel.class);
                if (apartment != null) {
                    updateUIWithApartmentData(apartment);
                }
            }
        });

        saveButton.setOnClickListener(view -> {

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


        });
    }

    private void updateUIWithApartmentData(ApartmentModel apartment) {
        txtTitle.setText(apartment.getTitle());
        txtPrice.setText(String.valueOf(apartment.getPrice()));
        txtUnitNumber.setText(String.valueOf(apartment.getUnitNumber()));
        txtAddress.setText(apartment.getAddress());
        txtLocation.setText(apartment.getLocation().toString());
        txtPostalCode.setText(apartment.getPostalCode());
        txtSize.setText(String.valueOf(apartment.getSize()));
        txtBedrooms.setText(String.valueOf(apartment.getBedrooms()));
        txtBathrooms.setText(String.valueOf(apartment.getBathrooms()));
        txtStageFloor.setText(String.valueOf(apartment.getStageFloor()));
        txtRentType.setText(apartment.getRentType().toString());
        checkHasParking.setChecked(apartment.getHasParking());
        checkHasHeating.setChecked(apartment.getHasHeating());
    }
}
