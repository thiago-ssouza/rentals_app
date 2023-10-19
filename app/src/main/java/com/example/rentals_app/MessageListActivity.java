package com.example.rentals_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.model.MessageModel;
import com.example.rentals_app.model.OwnerModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.model.UserModel;
import com.example.rentals_app.source.LocationTypes;
import com.example.rentals_app.source.RentTypes;
import com.example.rentals_app.source.StatusTypes;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageListActivity extends AppCompatActivity {
    ListView listViewMessageList;
    ArrayList<MessageModel> messageList;
    Intent intent;
    UserModel loggedUser;
    String selectedApartmentUID;
    ApartmentModel apartment;
    Class destination;
    FirebaseDatabase database;
    DatabaseReference reference;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        listViewMessageList = findViewById(R.id.listViewMessageList);
        intent = getIntent();
        loggedUser = UserModel.getSession();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");

        if (loggedUser instanceof TenantModel) {
            destination = SearchApartmentsActivity.class;
        } else {
            destination = ApartmentsListActivity.class;
        }


        //if(intent.hasExtra("loginAs")){
        if(intent != null){
            selectedApartmentUID = intent.getStringExtra("selectedApartmentUID");

            if(selectedApartmentUID != null){


                Query checkApartmentData = reference.child(selectedApartmentUID).child("messages");

                System.out.println(reference.child(selectedApartmentUID).child("messages"));
                checkApartmentData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        System.out.println("Entrou");
                        System.out.println(selectedApartmentUID);
                        System.out.println(snapshot.toString());
                        if(snapshot.exists()) {



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

                        System.out.println("oncanceled");
                        Toast.makeText(MessageListActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(MessageListActivity.this, destination);
//                        startActivity(intent);
//                        finish();
                    }
                });

            }else{
//                Toast.makeText(ViewApartmentActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
//                intent = new Intent(ViewApartmentActivity.this, destination);
//                startActivity(intent);
                System.out.println("Else de abjo");

                /// TODO SHOW ALL MESSAGES OF THE ONWER
            }

//            MessageAdapter adapter = new MessageAdapter(messageList, this);
//
//            listViewMessageList.setAdapter(adapter);

        }else{
//            intent = new Intent(MessageListActivity.this, LoginActivity.class);
//            startActivity(intent);
        }


    }
}