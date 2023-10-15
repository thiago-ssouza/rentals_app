package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ApartmentsListActivity extends AppCompatActivity {

    Button createApartmentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

        createApartmentButton = findViewById(R.id.createApartmentButton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApartmentsListActivity.this, ApartmentAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Class destination = ApartmentsListActivity.class;

        if (R.id.menuAccount == id) {
            destination = MyAccountActivity.class;
        }
        if (R.id.menuApartments == id) {
            destination = ApartmentsListActivity.class;
        }
        if (R.id.menuLogOut == id) {
            destination = LoginActivity.class;
        }

        Intent intent = new Intent(ApartmentsListActivity.this, destination);
        startActivity(intent);

        return true;
    }
}