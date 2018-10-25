package com.example.charles.kingcup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadScreen extends AppCompatActivity {
    CardDB cardDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        cardDB = new CardDB(getApplicationContext());
        cardDB.gameSetUp();
        //check if the constants are already in the database
        //if not put them in the database
        //otherwise send intent to base activity
        Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(gameActivity);
        finish();
    }
}
