package com.example.rentals_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rentals_app.model.MessageModel;
import com.example.rentals_app.model.TenantModel;
import com.example.rentals_app.model.UserModel;
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
    TextView textViewListViewApartmentTitle;
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
        textViewListViewApartmentTitle = findViewById(R.id.textViewListViewApartmentTitle);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("apartments");

        if (loggedUser instanceof TenantModel) {
            destination = SearchApartmentsActivity.class;
        } else {
            destination = ApartmentsListActivity.class;
        }


        if(intent != null){
            selectedApartmentUID = intent.getStringExtra("selectedApartmentUID");

            if(selectedApartmentUID != null){

                getMessageListAndDisplayListView();

            }else{
                Toast.makeText(MessageListActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                intent = new Intent(MessageListActivity.this, destination);
                startActivity(intent);
            }
        }else{
            intent = new Intent(MessageListActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getMessageListAndDisplayListView(){
        //Query checkApartmentData = reference.child(selectedApartmentUID).child("messages");
        Query checkApartmentData = reference.child(selectedApartmentUID);

        checkApartmentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    textViewListViewApartmentTitle.setText(snapshot.child("title").getValue(String.class));
                    messageList = new ArrayList<MessageModel>();

//                            for (DataSnapshot messageSpapshot: snapshot.getChildren()) {
                    for (DataSnapshot messageSpapshot: snapshot.child("messages").getChildren()) {

                        messageList.add(messageSpapshot.getValue(MessageModel.class));
                    }

                    adapter = new MessageAdapter(messageList, MessageListActivity.this);
                    listViewMessageList.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MessageListActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                intent = new Intent(MessageListActivity.this, destination);
                startActivity(intent);
                finish();
            }
        });
    }
}