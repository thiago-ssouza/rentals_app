package com.example.rentals_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.model.MessageModel;
import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.model.UserModel;
import com.example.rentals_app.source.LocationTypes;
import com.example.rentals_app.source.LoginTypes;
import com.example.rentals_app.source.RentTypes;
import com.example.rentals_app.source.StatusTypes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Date;

public class ViewApartmentActivity extends AppCompatActivity {
    TextView textViewTitle;
    TextView textViewPrice;
    TextView textViewUnitNumber;
    TextView textViewAddress;
    TextView textViewLocation;
    TextView textViewPostalCode;
    TextView textViewSize;
    TextView textViewBedrooms;
    TextView textViewBathrooms;
    TextView textViewStageFloor;
    TextView textViewParking;
    TextView textViewHeating;
    TextView textViewRentType;
    TextView textViewViewApartmentDescription;
    EditText editTextViewApartmentMessage;
    Button buttonSendMessage;
    Button buttonViewApartmentViewMessage;
    LinearLayout linearLayoutMessageField;
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
        setContentView(R.layout.activity_view_apartment);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewUnitNumber = findViewById(R.id.textViewUnit);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewPostalCode = findViewById(R.id.textViewPostalCode);
        textViewSize = findViewById(R.id.textViewSize);
        textViewBedrooms = findViewById(R.id.textViewBedrooms);
        textViewBathrooms = findViewById(R.id.textViewBathrooms);
        textViewStageFloor = findViewById(R.id.textViewStageFloor);
        textViewParking = findViewById(R.id.textViewParking);
        textViewHeating = findViewById(R.id.textViewHeating);
        textViewRentType = findViewById(R.id.textViewRentType);
        editTextViewApartmentMessage = findViewById(R.id.editTextViewApartmentMessage);
        textViewViewApartmentDescription = findViewById(R.id.textViewViewApartmentDescription);
        buttonSendMessage = findViewById(R.id.btnSendMessage);
        buttonViewApartmentViewMessage = findViewById(R.id.btnViewApartmentViewMessages);
        linearLayoutMessageField = findViewById(R.id.linearLayoutMessageField);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");

        loggedUser = UserModel.getSession();
        intent = getIntent();

        if (loggedUser instanceof TenantModel) {
            linearLayoutMessageField.setVisibility(View.VISIBLE);
            buttonSendMessage.setVisibility(View.VISIBLE);
            buttonViewApartmentViewMessage.setVisibility(View.INVISIBLE);
            destination = SearchApartmentsActivity.class;
        } else {
            linearLayoutMessageField.setVisibility(View.INVISIBLE);
            buttonViewApartmentViewMessage.setVisibility(View.VISIBLE);
            buttonSendMessage.setVisibility(View.INVISIBLE);
            destination = ApartmentsListActivity.class;
        }

        if (intent != null) {
            selectedApartmentUID = intent.getStringExtra("selectedApartmentUID");

            if(selectedApartmentUID != null){

                getApartmentAndSetActivityFields();

            }else{
                Toast.makeText(ViewApartmentActivity.this, "Something wrong! Apartment ID not found!", Toast.LENGTH_SHORT).show();
                intent = new Intent(ViewApartmentActivity.this, destination);
                startActivity(intent);
                finish();
            }

        }else{
            Toast.makeText(ViewApartmentActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            intent = new Intent(ViewApartmentActivity.this, destination);
            startActivity(intent);
            finish();
        }

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputMessage = editTextViewApartmentMessage.getText().toString().trim();

                if (inputMessage.isEmpty()) {
                    editTextViewApartmentMessage.setError("Message is required");
                    editTextViewApartmentMessage.requestFocus();
                    return;
                }

                MessageModel message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                reference.child(selectedApartmentUID).child("messages").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ViewApartmentActivity.this, "Message successful sent!", Toast.LENGTH_SHORT).show();
                        editTextViewApartmentMessage.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewApartmentActivity.this, "Something wrong! Message NOT sent! Try again later!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        buttonViewApartmentViewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ViewApartmentActivity.this, MessageListActivity.class);
                //intent.setClass(ViewApartmentActivity.this, MessageListActivity.class);
                intent.putExtra("selectedApartmentUID", selectedApartmentUID);
                startActivity(intent);
            }
        });
    }

    private void getApartmentAndSetActivityFields(){
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

                    textViewTitle.setText(apartment.getTitle());
                    textViewPrice.setText("$" + String.format("%.2f",apartment.getPrice()));
                    textViewUnitNumber.setText(String.format(Integer.toString(apartment.getUnitNumber())));
                    textViewAddress.setText(apartment.getAddress());
                    textViewLocation.setText(apartment.getLocation().getName());
                    textViewPostalCode.setText(apartment.getPostalCode());
                    textViewSize.setText(String.format(Integer.toString(apartment.getSize())));
                    textViewBedrooms.setText(String.format(Integer.toString(apartment.getBedrooms())));
                    textViewBathrooms.setText(String.format(Integer.toString(apartment.getBathrooms())));
                    textViewStageFloor.setText(String.format(Integer.toString(apartment.getStageFloor())));
                    textViewParking.setText(apartment.getHasParking() ? "YES" : "NO");
                    textViewHeating.setText(apartment.getHasHeating() ? "YES" : "NO");
                    textViewRentType.setText(apartment.getRentType().getName());
                    textViewViewApartmentDescription.setText(apartment.getDescription());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewApartmentActivity.this, "Something wrong! Apartment not found! Try again later!", Toast.LENGTH_SHORT).show();
                intent = new Intent(ViewApartmentActivity.this, destination);
                startActivity(intent);
                finish();
            }
        });
    }
}