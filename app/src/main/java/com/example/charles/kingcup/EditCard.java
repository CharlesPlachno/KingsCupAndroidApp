package com.example.charles.kingcup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EditCard extends AppCompatActivity {
    Card card;
    CardDB cardDB;
    EditText ruleEdit;
    TextView title;
    Button done, restore;
    RadioGroup options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        cardDB = new CardDB(getApplicationContext());
        ruleEdit = (EditText) findViewById(R.id.ruleEditText);
        done = (Button) findViewById(R.id.doneButton) ;
        restore = (Button) findViewById(R.id.restoreButton);
        options = (RadioGroup) findViewById(R.id.optionRadioGroup);

        //pull up database information based off of intents name
        String cardName = getIntent().getStringExtra("name");
        card = cardDB.getCardFromName(cardName, "player_table");
        title = (TextView) findViewById(R.id.cardNameTextView);

        title.setText(card.getCardName());
        ruleEdit.setText(card.getRule());

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card defaultCard = cardDB.getCardFromName(card.getCardName(), "default_table");
                ruleEdit.setText(defaultCard.getRule());
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newRule = ruleEdit.getText().toString();
                //suite facevalue rule
                Card newCard = null;
                switch(options.getCheckedRadioButtonId()){
                    case R.id.oneCardRadioButton:
                        newCard = new Card(card.getSuite(), card.getFace_value(), newRule);
                        cardDB.insertCard(newCard, "player_table");
                        break;
                    case R.id.allCardRadioButton:
                        for(int i = 0; i<4; i++){
                            newCard = new Card(Defaults.SUITES[i], card.getFace_value(), newRule);
                            cardDB.insertCard(newCard, "player_table");
                        }
                        break;
                }
                finish();
            }
        });
    }
}
