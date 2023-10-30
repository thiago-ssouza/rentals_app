package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.rentals_app.model.FilterModel;
import com.example.rentals_app.source.LoginTypes;

import java.util.ArrayList;
import java.util.Arrays;

public class FiltersActivity extends AppCompatActivity {

    private Button applyFiltersButton, clearFiltersButton, cancelFiltersButton;
    EditText filterTitle, filterRooms, filterBathrooms;
    Spinner filterParking, filterHeating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        applyFiltersButton = findViewById(R.id.applyFiltersButton);
        clearFiltersButton = findViewById(R.id.clearFiltersButton);
        cancelFiltersButton = findViewById(R.id.cancelFiltersButton);

        filterTitle = findViewById(R.id.filterTitle);
        filterRooms = findViewById(R.id.filterRooms);
        filterBathrooms = findViewById(R.id.filterBathrooms);
        filterParking = findViewById(R.id.filterParking);
        filterHeating = findViewById(R.id.filterHeating);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(
                Arrays.asList("", "Yes", "No")));
        filterParking.setAdapter(adapter);
        filterHeating.setAdapter(adapter);

        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fParking = filterParking.getSelectedItem().toString().trim();
                String fHeating = filterHeating.getSelectedItem().toString().trim();
                FilterModel filterModel = new FilterModel(
                        filterTitle.getText().toString(),
                        Integer.parseInt(filterRooms.getText().toString().length() > 0 ? filterRooms.getText().toString() : "0"),
                        Integer.parseInt(filterBathrooms.getText().toString().length() > 0 ? filterBathrooms.getText().toString() : "0"),
                        (fParking == "Yes" ? 1 : (fParking == "No" ? 2 : 0)),
                        (fHeating == "Yes" ? 1 : (fHeating == "No" ? 2 : 0))
                );

                Intent intent = new Intent(FiltersActivity.this, SearchApartmentsActivity.class);
                intent.putExtra("filterModel", filterModel);
                startActivity(intent);
                finish();
            }
        });

        clearFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterTitle.setText("");
                filterRooms.setText("");
                filterBathrooms.setText("");
                filterParking.setSelection(0);
                filterHeating.setSelection(0);
            }
        });

        cancelFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiltersActivity.this, SearchApartmentsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}