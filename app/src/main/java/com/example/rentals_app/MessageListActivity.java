package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rentals_app.model.MessageModel;

import java.util.ArrayList;

public class MessageListActivity extends AppCompatActivity {
    ListView listViewMessageList;

    ArrayList<MessageModel> messageList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);


        listViewMessageList = findViewById(R.id.listViewMessageList);

        intent = getIntent();

        //if(intent.hasExtra("loginAs")){
        if(intent != null){

            messageList = (ArrayList<MessageModel>) intent.getSerializableExtra("messageList");

            MessageAdapter adapter = new MessageAdapter(messageList, this);

            listViewMessageList.setAdapter(adapter);

        }else{
            intent = new Intent(MessageListActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }
}