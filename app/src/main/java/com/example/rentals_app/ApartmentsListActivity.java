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
import com.example.rentals_app.model.UserModel;
import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.ApartmentEditActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;

public class ApartmentsListActivity extends AppCompatActivity {

    private UserModel loggedUser;
    private List<ApartmentModel> apartmentsList = new ArrayList<>();
    private ArrayAdapter<ApartmentModel> apartmentListAdapter;
    private DatabaseReference databaseReference;
    Button btnCreate, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

        btnCreate = findViewById(R.id.btnCreate);
        btnUpdate = findViewById(R.id.btnUpdate);

        ListView apartmentsListView = findViewById(R.id.apartmentsList);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("apartments");

        apartmentListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, apartmentsList);

        apartmentsListView.setAdapter(apartmentListAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apartmentsList.clear();
                for (DataSnapshot apartmentSnapshot : dataSnapshot.getChildren()) {
                    ApartmentModel apartment = apartmentSnapshot.getValue(ApartmentModel.class);
                    if (apartment != null) {
                        apartmentsList.add(apartment);
                    }
                }

                apartmentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApartmentsListActivity.this, ApartmentAddActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
