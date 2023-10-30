package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.model.FilterModel;
import com.example.rentals_app.model.UserModel;
import com.example.rentals_app.source.LocationTypes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchApartmentsActivity extends AppCompatActivity {
    private SearchView searchView;
    private Button filterButton;
    ArrayList<ApartmentModel> apartments = new ArrayList<ApartmentModel>();
    private List<ApartmentModel> apartmentsList = new ArrayList<>();
    private UserModel loggedUser;
    FirebaseDatabase database;
    DatabaseReference reference;
    ListView apartmentsListView;
    ApartmentAdapter adapter;
    FilterModel filterModel;

    private boolean filtersState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appartments);

        loggedUser = UserModel.getSession();
        filterButton = findViewById(R.id.filterButton);
        apartmentsListView = findViewById(R.id.apartmentsList);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filterModel = (FilterModel) getIntent().getSerializableExtra("filterModel");

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchApartmentsActivity.this, FiltersActivity.class);
                startActivity(intent);
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");


        Query apartmentsQuery = reference.orderByChild("title");

        if (filterModel != null) {
            if (filterModel.getFilterTitle().trim().length() > 0) {
                apartmentsQuery = apartmentsQuery.startAt(filterModel.getFilterTitle(), "title");
            }
            if (filterModel.getFilterRooms() > 0) {
                apartmentsQuery = apartmentsQuery.equalTo(filterModel.getFilterRooms(), "bedrooms");
            }
            if (filterModel.getFilterBathRooms() > 0) {
                apartmentsQuery = apartmentsQuery.equalTo(filterModel.getFilterBathRooms(), "bathrooms");
            }
            if (filterModel.isFilterHeating() > 0) {
                int filter = filterModel.isFilterHeating();
                apartmentsQuery = apartmentsQuery.equalTo((filter == 1 ? true : false), "hasHeating");
            }
            if (filterModel.isFilterParking() > 0) {
                int filter = filterModel.isFilterParking();
                apartmentsQuery = apartmentsQuery.equalTo((filter == 1 ? true : false), "hasParking");
            }
        }
        apartmentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot messageSpapshot: snapshot.getChildren()) {
                        ApartmentModel apt = new ApartmentModel(
                                messageSpapshot.getKey(),
                                messageSpapshot.child("address").getValue(String.class),
                                messageSpapshot.child("location").getValue(LocationTypes.class),
                                messageSpapshot.child("price").getValue(Double.class),
                                messageSpapshot.child("title").getValue(String.class)
                        );
                        apartments.add(apt);
                    }

                    adapter = new ApartmentAdapter(apartments, SearchApartmentsActivity.this, 2);
                    apartmentsListView.setAdapter(adapter);
                    apartmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ApartmentModel apt = apartments.get(position);

                            Intent intent = new Intent(SearchApartmentsActivity.this, ViewApartmentActivity.class);
                            intent.putExtra("selectedApartmentUID", apt.getId());
                            startActivity(intent);
                        }
                    });



                } else {
                    Toast.makeText(SearchApartmentsActivity.this, "Apartments not found. Please try again", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        /*if (R.id.menuApartments == id) {
            destination = ApartmentsListActivity.class;
        }*/
        if (R.id.menuLogOut == id) {
            destination = LoginActivity.class;
        }

        Intent intent = new Intent(SearchApartmentsActivity.this, destination);
        startActivity(intent);

        return true;
    }
}