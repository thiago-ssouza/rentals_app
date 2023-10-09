package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MessageListActivity extends AppCompatActivity {
    ListView listViewMessageList;

    /**
     * Needs to uncomment
     */
//    ArrayList<MessageModel> messageList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);


        listViewMessageList = findViewById(R.id.listViewMessageList);

        intent = getIntent();

        //if(intent.hasExtra("loginAs")){
        if(intent != null){
            /**
             * Needs to uncomment
             */
//            messageList = (ArrayList<MessageModel>) intent.getSerializableExtra("messageList");

            /**
             * Needs to uncomment
             */
//            MessageAdapter adapter = new MessageAdapter(messageList, this);

            /**
             * Needs to uncomment
             */
//            listViewMessageList.setAdapter(adapter);

        }else{
            /**
             * Needs to uncomment
             */
//            intent = new Intent(MessageListActivity.this, LoginActivity.class);
//            startActivity(intent);
        }


    }
}