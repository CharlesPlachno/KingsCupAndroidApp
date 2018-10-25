package com.example.charles.kingcup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.zip.Inflater;

public class GameActivity extends AppCompatActivity {
    Button nextButton;
    ImageView cardImageView;
    TextView rulesText, kingsPulled, cardsRemaining;
    Game game;
    SharedPreferences sharedPref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPref.edit();
        //put kings discardlist currentcard-name
        String storedName = "";
        if(game.getCurrentCard()!=null){
            storedName = game.getCurrentCard().getCardName();
        }
        editor.putInt("kings", game.getKings());
        editor.putStringSet("discard",game.getDiscard());
        editor.putString("currentCard", storedName);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newGame:
                //trigger new game
                game = new Game(getApplicationContext());
                updateGameFields();
                break;
            case R.id.editorLaunch:
                Intent editorActivity = new Intent(getApplicationContext(), EditorActivity.class);
                startActivity(editorActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HashSet<String> discard = new HashSet<String>();
        discard.addAll(sharedPref.getStringSet("discard", new HashSet<String>()));
        game.resumeGame(discard, sharedPref.getInt("kings", 0), sharedPref.getString("currentCard", ""));
        updateGameFields();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE);

        nextButton = (Button) findViewById(R.id.nextButton);
        cardImageView = (ImageView) findViewById(R.id.imageView);
        rulesText = (TextView) findViewById(R.id.cardRulesTextView);
        kingsPulled = (TextView) findViewById((R.id.kingsPulledTextView)) ;
        cardsRemaining = (TextView) findViewById(R.id.cardsRemainingTextView);

        game = new Game(getApplicationContext());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.pullCard();
                updateGameFields();
            }
        });
    }
    public void updateGameFields(){
        updateCard();
        updateText();
    }
    public void updateText(){
        if(game.currentCard!=null){
            rulesText.setText(game.getCurrentCard().getRule());
        }else{
            rulesText.setText("");
        }

        kingsPulled.setText(Integer.toString(game.getKings()));
        cardsRemaining.setText(Integer.toString(game.getCardCount()));

    }
    public void updateCard(){
        if(game.currentCard != null) {
            switch (game.getCurrentCard().getCardName()) {
                case "Ace Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.ace_of_clubs));
                    break;
                case "2 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs2));
                    break;
                case "3 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs3));
                    break;
                case "4 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs4));
                    break;
                case "5 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs5));
                    break;
                case "6 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs6));
                    break;
                case "7 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs7));
                    break;
                case "8 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs8));
                    break;
                case "9 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs9));
                    break;
                case "10 Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubs10));
                    break;
                case "Jack Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubsj));
                    break;
                case "Queen Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubsq));
                    break;
                case "King Clubs":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.clubsk));
                    break;
                case "Ace Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.ace_of_spades));
                    break;
                case "2 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades2));
                    break;
                case "3 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades3));
                    break;
                case "4 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades4));
                    break;
                case "5 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades5));
                    break;
                case "6 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades6));
                    break;
                case "7 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades7));
                    break;
                case "8 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades8));
                    break;
                case "9 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades9));
                    break;
                case "10 Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spades10));
                    break;
                case "Jack Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spadesj));
                    break;
                case "Queen Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spadesq));
                    break;
                case "King Spades":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.spadesk));
                    break;
                case "Ace Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.ace_of_hearts));
                    break;
                case "2 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts2));
                    break;
                case "3 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts3));
                    break;
                case "4 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts4));
                    break;
                case "5 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts5));
                    break;
                case "6 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts6));
                    break;
                case "7 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts7));
                    break;
                case "8 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts8));
                    break;
                case "9 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts9));
                    break;
                case "10 Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.hearts10));
                    break;
                case "Jack Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.heartsj));
                    break;
                case "Queen Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.heartsq));
                    break;
                case "King Hearts":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.heartsk));
                    break;
                case "Ace Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.ace_of_diamonds));
                    break;
                case "2 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds2));
                    break;
                case "3 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds3));
                    break;
                case "4 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds4));
                    break;
                case "5 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds5));
                    break;
                case "6 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds6));
                    break;
                case "7 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds7));
                    break;
                case "8 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds8));
                    break;
                case "9 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds9));
                    break;
                case "10 Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamonds10));
                    break;
                case "Jack Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamondsj));
                    break;
                case "Queen Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamondsq));
                    break;
                case "King Diamonds":
                    cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.diamondsk));
                    break;
            }
        }else{
            cardImageView.setImageDrawable(getResources().getDrawable(R.drawable.back));
        }
    }

}
