package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.model.MessageModel;
import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.source.LoginTypes;

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
    TextView textViewRentalType;
    EditText editTextMessage;
    Button buttonSendMessage;
    Button buttonBackPage;
    LinearLayout linearLayoutMessageFields;

    LoginTypes loginAs = null;
    Object loggedUser = null;
    Intent intent = null;

    ApartmentModel apartment = null;

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
        textViewRentalType = findViewById(R.id.textViewRentType);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.btnSendMessage);
        buttonBackPage = findViewById(R.id.btnPreviousPage);
        linearLayoutMessageFields = findViewById(R.id.linearLayoutMessageFields);

        intent = getIntent();

        if (intent != null){
            loginAs = (LoginTypes)intent.getSerializableExtra("loginAS");

            if(loginAs != null){
                if(loginAs.equals(LoginTypes.TENANT)){
                    loggedUser = (TenantModel) intent.getSerializableExtra("loggedUser");
                    linearLayoutMessageFields.setVisibility(View.VISIBLE);
                }else{
                    loggedUser = (OwnerModel) intent.getSerializableExtra("loggedUser");
                    linearLayoutMessageFields.setVisibility(View.INVISIBLE);
                }

                apartment = (ApartmentModel) intent.getSerializableExtra("apartment");

                if(apartment != null){

                    textViewTitle.setText(apartment.getTitle());
                    textViewPrice.setText("$" + String.format("%.2f",apartment.getPrice()));
                    textViewUnitNumber.setText(String.format(Integer.toString(apartment.getUnitNumber())));
                    textViewAddress.setText(apartment.getAddress());
                    textViewLocation.setText(apartment.getLocationTypes().getName());
                    textViewLocation.setText(apartment.getLocationTypes().toString());
                    textViewPostalCode.setText(apartment.getPostalCode());
                    textViewSize.setText(String.format(Integer.toString(apartment.getSize())));
                    textViewBedrooms.setText(String.format(Integer.toString(apartment.getBedrooms())));
                    textViewBathrooms.setText(String.format(Integer.toString(apartment.getBathrooms())));
                    textViewStageFloor.setText(String.format(Integer.toString(apartment.getStageFloor())));
                    textViewParking.setText(apartment.getHasParking() ? "YES" : "NO");
                    textViewHeating.setText(apartment.getHasHeating() ? "YES" : "NO");
                    textViewRentalType.setText(apartment.getRentTypes().getName());
                    //editTextMessage.setText("Message");
                    buttonSendMessage = findViewById(R.id.btnSendMessage);
                    //buttonBackPage = findViewById(R.id.btnPreviousPage);
                    linearLayoutMessageFields = findViewById(R.id.linearLayoutMessageFields);
                }else{
                    intent = new Intent(ViewApartmentActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }else{
                intent = new Intent(ViewApartmentActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        } else{
            intent = new Intent(ViewApartmentActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputMessage = editTextMessage.getText().toString().trim();

                if (inputMessage.isEmpty()) {
                    editTextMessage.setError("Password is required");
                    editTextMessage.requestFocus();
                    return;
                }

                List<MessageModel> listMessage = new ArrayList<MessageModel>();

                MessageModel message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                listMessage.add(message);

                /**
                 * Testing with more messages
                 */
//***************************************************************
                message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                listMessage.add(message);

                message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                listMessage.add(message);

                message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                listMessage.add(message);

                message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                listMessage.add(message);

                message = new MessageModel();
                message.setTenant((TenantModel) loggedUser);
                message.setMessage(inputMessage);
                message.setMessageDate(new Date());

                listMessage.add(message);
// **********************************************************************

                editTextMessage.setText("");

                intent.setClass(ViewApartmentActivity.this, MessageListActivity.class);
                intent.putExtra("messageList", (Serializable) listMessage);

                startActivity(intent);

            }
        });

    }
}