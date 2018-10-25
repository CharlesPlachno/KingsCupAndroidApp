package com.example.charles.kingcup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Charles on 7/20/2017.
 */

public class CardDB {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public CardDB(Context context){
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }
    private void openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }
    private void openWritableDB(){
        db = dbHelper.getWritableDatabase();
    }
    private void closeDb(){
        if(db != null){
            db.close();
        }
    }

    //database constants
    public  static final String DB_NAME = "cards.db";
    public  static final int    DB_VERSION = 1;

    //table constants
    public static final String DEFAULT_TABLE = "default_table";
    public static final String PLAYER_TABLE = "player_table";
    /*
    _id | suite | face_value | rule
     0      1         2         3
     */

    public static final String ID = "_id";
    public static final int    ID_COL = 0;

    public static final String SUITE = "suite";
    public static final int    SUITE_ID = 1;

    public static final String FACE_VALUE = "face_value";
    public static final int    FACE_VALUE_ID =2;

    public static final String RULE = "rule";
    public static final int    RULE_ID =3;

    public static final String CREATE_DEFAULT_TABLE = "CREATE TABLE "+DEFAULT_TABLE+ " (" +
            ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUITE      + " TEXT, "                              +
            FACE_VALUE + " TEXT, "                              +
            RULE       + " TEXT, "                                +
            "UNIQUE("+FACE_VALUE+", "+ SUITE+"));";

    public static final String CREATE_PLAYER_TABLE = "CREATE TABLE "+PLAYER_TABLE+ " (" +
            ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUITE      + " TEXT, "                              +
            FACE_VALUE + " TEXT, "                              +
            RULE       + " TEXT, "                                +
            "UNIQUE("+FACE_VALUE+", "+ SUITE+") ON CONFLICT REPLACE);";
    public static final String DROP_DEFUALT_TABLE = "DROP TABLE IF EXISTS " + DEFAULT_TABLE;
    public static final String DROP_PLAYER_TABLE = "DROP TABLE IF EXISTS " + PLAYER_TABLE;

    private static class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PLAYER_TABLE);
            db.execSQL(CREATE_DEFAULT_TABLE);

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Card Database", "Upgrading database from version "+ oldVersion + " to version " + newVersion);
            db.execSQL(CardDB.DROP_DEFUALT_TABLE);
            db.execSQL(CardDB.DROP_PLAYER_TABLE);
            onCreate(db);
        }
    }
    public boolean dataExists(String table){
        this.openReadableDB();
        Cursor cursor = db.rawQuery("select * from " +  table,null);
        if( cursor !=null && cursor.getCount()>0){
            Log.e("dataExists", "Data did exist");
            cursor.close();
            this.closeDb();
            return true;
        }
        cursor.close();
        this.closeDb();
        Log.e("dataExists", "Data did not exist");
        return false;
    }

    public void gameSetUp(){
        if(!dataExists(DEFAULT_TABLE)){
            for(int i =0; i<4; i++){
                String suite = Defaults.SUITES[i];
                for (int j = 0; j<13; j++){
                    Card card = new Card(suite, Defaults.FACE_VALUES[j], Defaults.RULES[j]);
                    insertCard(card, DEFAULT_TABLE);
                    insertCard(card, PLAYER_TABLE);
                }
            }
        }

    }
    public long insertCard(Card card, String table){
        ContentValues cv = new ContentValues();
        cv.put(SUITE, card.getSuite());
        cv.put(FACE_VALUE, card.getFace_value());
        cv.put(RULE, card.getRule());

        this.openWritableDB();
        long rowID = db.insert(table, null, cv);
        this.closeDb();

        return rowID;
    }
    //get playerdeck
    public ArrayList<Card> getPlayerDeck(){
        ArrayList<Card> deck = new ArrayList<Card>();
        this.openReadableDB();
        Cursor  cursor = db.rawQuery("select * from "+ PLAYER_TABLE,null);
        while(cursor.moveToNext()){
            deck.add(getCardFromCursor(cursor));
        }
        if(cursor != null) {
            cursor.close();
        }
        this.closeDb();
        return deck;
    }

    private Card getCardFromCursor(Cursor cursor){
        if (cursor==null || cursor.getCount() == 0){
            return null;
        }
        else{
            try{
                Card card = new Card(cursor.getString(SUITE_ID), cursor.getString(FACE_VALUE_ID), cursor.getString(RULE_ID));
                return card;
            }catch(Exception e){
                return null;
            }
        }
    }
    public Card getCardFromName(String cardName, String table){
        String[] parts = cardName.split(" ");
        String face_value = parts[0];
        String suite = parts[1];
        this.openReadableDB();
        Cursor cursor = db.query(table, null, SUITE+" like '%" + suite + "%' AND " + FACE_VALUE + " like '%" + face_value + "%'", null, null, null, null);
        Card card = null;
        if(cursor.moveToFirst()){
            card = getCardFromCursor(cursor);
        }
        if(cursor!=null){
            cursor.close();
        }
        this.closeDb();
        return card;
    }
}
