package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.rentals_app.model.UserModel;

public class MainActivity extends AppCompatActivity {

    private TextView mainText;
    private UserModel loggedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = findViewById(R.id.mainText);

        loggedUser = UserModel.getSession();

        mainText.setText("Hello " + loggedUser.getFirstName() + " " + loggedUser.getLastName());
    }
}