package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewApartmentActivity extends AppCompatActivity {

    //ImageView imageViewApartment;
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
    EditText editTextMessage;
    Button buttonSendMessage;
    Button buttonBackPage;
    LinearLayout linearLayoutMessageFields;

    LoginTypes loginAs = null;
    Object loggedUser = null;
    Intent intent = null;

    ApartmentModel apartment = null;

    FirebaseDatabase database;
    DatabaseReference reference;

    UserModel loggedUserModel;

    String selectedApartmentUID;



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
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.btnSendMessage);
        buttonBackPage = findViewById(R.id.btnPreviousPage);
        linearLayoutMessageFields = findViewById(R.id.linearLayoutMessageFields);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");

        loggedUserModel = UserModel.getSession();
        intent = getIntent();
        Class destination;

        loggedUserModel = new TenantModel();

        if (loggedUserModel instanceof TenantModel) {
            linearLayoutMessageFields.setVisibility(View.VISIBLE);
            destination = SearchApartmentsActivity.class;
        } else {
            linearLayoutMessageFields.setVisibility(View.INVISIBLE);
            destination = ApartmentsListActivity.class;
        }


        //intent = new Intent();

        if (intent != null) {
            selectedApartmentUID = intent.getStringExtra("selectedApartmentUID");

//
//            apartment = new ApartmentModel();
//            apartment.setTitle("2 Bedroom Apartment for Rent in Brampton!");
//            apartment.setPrice(2490.00);
//            apartment.setUnitNumber(1234);
//            apartment.setAddress("Saint-Catherine St W");
//            apartment.setLocation(LocationTypes.CITY_OF_MONTREAL);
//            apartment.setPostalCode("H3H 2T2");
//            apartment.setSize(4);
//            apartment.setBedrooms(3);
//            apartment.setBathrooms(2);
//            apartment.setStageFloor(4);
//            apartment.setHasParking(true);
//            apartment.setHasHeating(true);
//            apartment.setRentType(RentTypes.ROOM_RENTALS_AND_ROOMMATES);
//
//
//
//
//            UserModel owner = new OwnerModel();
//            owner.setFirstName("Thiago");
//            owner.setLastName("Soares de Souza");
//            owner.setEmail("thiago@gmail.com");
//            owner.setPhone("3284959346");
//
//            apartment.setOwner((OwnerModel) owner);
//            apartment.setStatus(StatusTypes.AVAILABLE);

//            database = FirebaseDatabase.getInstance();
//            reference = database.getReference("apartments");
//
//            reference.child("apartments").setValue(apartment);


            if(selectedApartmentUID != null){

                //Query checkUserData = reference.orderByChild("username").equalTo(userUsername);
                // get an apartment by uid
                Query checkApartmentData = reference.child("apartments").child(selectedApartmentUID);

                checkApartmentData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {

                            apartment = new ApartmentModel();

                            apartment.setTitle(snapshot.child("apartments").child("title").getValue(String.class));
                            apartment.setPrice(snapshot.child("apartments").child("price").getValue(Double.class));
                            apartment.setUnitNumber(snapshot.child("apartments").child("unitNumber").getValue(Integer.class));
                            apartment.setAddress(snapshot.child("apartments").child("address").getValue(String.class));
                            apartment.setLocation(snapshot.child("apartments").child("location").getValue(LocationTypes.class));
                            apartment.setPostalCode(snapshot.child("apartments").child("postalCode").getValue(String.class));
                            apartment.setSize(snapshot.child("apartments").child("size").getValue(Integer.class));
                            apartment.setBedrooms(snapshot.child("apartments").child("bedrooms").getValue(Integer.class));
                            apartment.setBathrooms(snapshot.child("apartments").child("bathrooms").getValue(Integer.class));
                            apartment.setStageFloor(snapshot.child("apartments").child("stageFloor").getValue(Integer.class));
                            apartment.setHasParking(snapshot.child("apartments").child("hasParking").getValue(Boolean.class));
                            apartment.setHasHeating(snapshot.child("apartments").child("hasHeating").getValue(Boolean.class));
                            apartment.setRentType(snapshot.child("apartments").child("rentType").getValue(RentTypes.class));
                            apartment.setOwner(snapshot.child("apartments").child("owner").getValue(OwnerModel.class));
                            apartment.setStatus(snapshot.child("apartments").child("status").getValue(StatusTypes.class));

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
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
//                        Class destination;
//
//                        if (loggedUserModel instanceof TenantModel) {
//                            destination = SearchApartmentsActivity.class;
//                        } else {
//                            destination = ApartmentsListActivity.class;
//                        }

                        Toast.makeText(ViewApartmentActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(ViewApartmentActivity.this, destination);
                        startActivity(intent);
                    }
                });

            }else{
                Toast.makeText(ViewApartmentActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                intent = new Intent(ViewApartmentActivity.this, destination);
                startActivity(intent);
            }

        }else{
            Toast.makeText(ViewApartmentActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            intent = new Intent(ViewApartmentActivity.this, destination);
            startActivity(intent);
        }



        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputMessage = editTextMessage.getText().toString().trim();

                if (inputMessage.isEmpty()) {
                    editTextMessage.setError("Message is required");
                    editTextMessage.requestFocus();
                    return;
                }

                List<MessageModel> listMessage = new ArrayList<>();

                MessageModel message = new MessageModel();
                message.setApartment(apartment);
                message.setTenant((TenantModel) loggedUserModel);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                reference = database.getReference("messages");

                reference.push().setValue(message);

                listMessage.add(message);

//                /**
//                 * Testing with more messages
//                 */
////***************************************************************
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//// **********************************************************************

                editTextMessage.setText("");

                intent.setClass(ViewApartmentActivity.this, MessageListActivity.class);
                intent.putExtra("messageList", (Serializable) listMessage);

                startActivity(intent);

            }
        });






        //****************************************************************

//        if (intent != null){
//            loginAs = (LoginTypes)intent.getSerializableExtra("loginAS");
//
//            if(loginAs != null){
//                if(loginAs.equals(LoginTypes.TENANT)){
//                    loggedUser = (TenantModel) intent.getSerializableExtra("loggedUser");
//                    linearLayoutMessageFields.setVisibility(View.VISIBLE);
//                }else{
//                    loggedUser = (OwnerModel) intent.getSerializableExtra("loggedUser");
//                    linearLayoutMessageFields.setVisibility(View.INVISIBLE);
//                }
//
//                apartment = (ApartmentModel) intent.getSerializableExtra("apartment");
//
//                if(apartment != null){
//
//                    textViewTitle.setText(apartment.getTitle());
//                    textViewPrice.setText("$" + String.format("%.2f",apartment.getPrice()));
//                    textViewUnitNumber.setText(String.format(Integer.toString(apartment.getUnitNumber())));
//                    textViewAddress.setText(apartment.getAddress());
//                    textViewLocation.setText(apartment.getLocation().getName());
//                    textViewLocation.setText(apartment.getLocation().toString());
//                    textViewPostalCode.setText(apartment.getPostalCode());
//                    textViewSize.setText(String.format(Integer.toString(apartment.getSize())));
//                    textViewBedrooms.setText(String.format(Integer.toString(apartment.getBedrooms())));
//                    textViewBathrooms.setText(String.format(Integer.toString(apartment.getBathrooms())));
//                    textViewStageFloor.setText(String.format(Integer.toString(apartment.getStageFloor())));
//                    textViewParking.setText(apartment.getHasParking() ? "YES" : "NO");
//                    textViewHeating.setText(apartment.getHasHeating() ? "YES" : "NO");
//                    textViewRentType.setText(apartment.getRentType().getName());
//                    //editTextMessage.setText("Message");
//                    //buttonSendMessage = findViewById(R.id.btnSendMessage);
//                    //buttonBackPage = findViewById(R.id.btnPreviousPage);
//                    //linearLayoutMessageFields = findViewById(R.id.linearLayoutMessageFields);
//                }else{
//                    intent = new Intent(ViewApartmentActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//
//            }else{
//                intent = new Intent(ViewApartmentActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//
//        } else{
//            intent = new Intent(ViewApartmentActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }

//        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String inputMessage = editTextMessage.getText().toString().trim();
//
//                if (inputMessage.isEmpty()) {
//                    editTextMessage.setError("Password is required");
//                    editTextMessage.requestFocus();
//                    return;
//                }
//
//                List<MessageModel> listMessage = new ArrayList<MessageModel>();
//
//                MessageModel message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                /**
//                 * Testing with more messages
//                 */
////***************************************************************
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//
//                message = new MessageModel();
//                message.setTenant((TenantModel) loggedUser);
//                message.setMessage(inputMessage);
//                message.setMessageDate(new Date());
//
//                listMessage.add(message);
//// **********************************************************************
//
//                editTextMessage.setText("");
//
//                intent.setClass(ViewApartmentActivity.this, MessageListActivity.class);
//                intent.putExtra("messageList", (Serializable) listMessage);
//
//                startActivity(intent);
//
//            }
//        });

    }
}