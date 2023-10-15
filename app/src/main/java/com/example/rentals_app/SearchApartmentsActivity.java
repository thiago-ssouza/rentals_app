package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SearchApartmentsActivity extends AppCompatActivity {
    private SearchView searchView;
    private LinearLayout filters;
    private Button filterButton, applyFiltersButton, clearFiltersButton, cancelFiltersButton;

    private boolean filtersState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appartments);

        searchView = findViewById(R.id.searchApartments);
        filters = findViewById(R.id.filters);

        filterButton = findViewById(R.id.filterButton);
        applyFiltersButton = findViewById(R.id.applyFiltersButton);
        clearFiltersButton = findViewById(R.id.clearFiltersButton);
        cancelFiltersButton = findViewById(R.id.cancelFiltersButton);

        filters.setVisibility(View.INVISIBLE);


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filtersState) {
                    filters.setVisibility(View.INVISIBLE);
                } else {
                    filters.setVisibility(View.VISIBLE);
                }
                filtersState = !filtersState;
            }
        });

        cancelFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filters.setVisibility(View.INVISIBLE);
            }
        });
    }
}