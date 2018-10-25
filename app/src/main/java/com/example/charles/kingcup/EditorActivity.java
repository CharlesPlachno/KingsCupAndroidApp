package com.example.charles.kingcup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class EditorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<Card> playerDeck;
    CardDB cardDB;
    ListView editorList;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backButton:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editorList = (ListView) findViewById(R.id.editorListView);
        editorList.setOnItemClickListener(this);
        cardDB = new CardDB(getApplicationContext());
        playerDeck = cardDB.getPlayerDeck();
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for(Card card: playerDeck){
            HashMap<String, String> map = new HashMap<String, String>();
            String outputName = card.getSuite() + " " + card.getFace_value();
            map.put("name", outputName);
            data.add(map);
        }
        int resource = R.layout.list_item;
        String[] from = {"name"};
        int[] to = {R.id.card_name};
        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        editorList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Card card = playerDeck.get(position);
        Intent editCardActivity = new Intent(getApplicationContext(), EditCard.class);
        editCardActivity.putExtra("name", card.getCardName());
        startActivity(editCardActivity);
    }

}
