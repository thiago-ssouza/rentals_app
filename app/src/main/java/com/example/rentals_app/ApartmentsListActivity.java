package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private UserModel getLoggedUser;
    private List<ApartmentModel> apartmentsList = new ArrayList<>();
    Button createApartmentButton, updateApartmentButton;
    private UserModel loggedUser;
    FirebaseDatabase database;
    DatabaseReference reference;
    ListView apartmentsListView;
    ApartmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

        createApartmentButton = findViewById(R.id.btnCreate);
        updateApartmentButton = findViewById(R.id.btnUpdate);
        apartmentsListView = findViewById(R.id.apartmentsList);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");


        Query apartmentsQuery = reference.orderByChild("title");
        apartmentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserModel loggedUser;

                    for (DataSnapshot messageSpapshot: snapshot.getChildren()) {
                        System.out.println("--------------1------------------------");
                        System.out.println(messageSpapshot.getValue());
                        System.out.println(messageSpapshot);
                        System.out.println("--------------2------------------------");
                        apartments.add(messageSpapshot.getValue(ApartmentModel.class));
                    }

                    adapter = new ApartmentAdapter(apartments, ApartmentsListActivity.this);
                    apartmentsListView.setAdapter(adapter);



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

        updateApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApartmentsListActivity.this, ApartmentEditActivity.class);
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
