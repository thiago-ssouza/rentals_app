package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.model.MessageModel;
import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApartmentsListActivity extends AppCompatActivity {

    ArrayList<ApartmentModel> apartments = new ArrayList<ApartmentModel>();
    private List<ApartmentModel> apartmentsList = new ArrayList<>();
    Button createApartmentButton;
    private UserModel loggedUser;
    FirebaseDatabase database;
    DatabaseReference reference;
    ListView apartmentsListView;
    ApartmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

        loggedUser = UserModel.getSession();

        createApartmentButton = findViewById(R.id.btnCreate);
        apartmentsListView = findViewById(R.id.apartmentsList);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("owners");


        Query apartmentsQuery = reference.child(loggedUser.getId()).child("apartments").orderByChild("title");
        apartmentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot messageSpapshot: snapshot.getChildren()) {
                        ApartmentModel apt = messageSpapshot.getValue(ApartmentModel.class);
                        apt.setId(messageSpapshot.getKey());
                        apartments.add(apt);
                    }

                    adapter = new ApartmentAdapter(apartments, ApartmentsListActivity.this);
                    apartmentsListView.setAdapter(adapter);
                    apartmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ApartmentModel apt = apartments.get(position);

                            Intent intent = new Intent(ApartmentsListActivity.this, ApartmentEditActivity.class);
                            intent.putExtra("selectedApartmentUID", apt.getId());
                            startActivity(intent);
                        }
                    });



                } else {
                    Toast.makeText(ApartmentsListActivity.this, "Apartments not found. Please try again", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        loggedUser = UserModel.getSession();
        if (loggedUser != null) {

        }

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
