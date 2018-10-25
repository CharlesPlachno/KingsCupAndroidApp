package com.example.charles.kingcup;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Charles on 7/21/2017.
 */

public class Game {
    ArrayList<Card> deck;
    HashSet<String> discard;
    int kings, cardCount;
    CardDB cardDB;
    Random r = new Random();
    Card currentCard;

    public Game(Context context){
        cardDB = new CardDB(context);
        fillDeck();
        //amount of kings that have been pulled
        kings =0;
        cardCount = deck.size();
        discard = new HashSet<String>();
    }

    public void fillDeck(){
        deck = cardDB.getPlayerDeck();
    }

    public void pullCard(){
        if(cardCount!=0) {

            int nextCardIndex = r.nextInt(cardCount);
            currentCard = deck.get(nextCardIndex);
            if (currentCard.getFace_value().equals("King")) {
                kings++;
            }
            discard.add(deck.get(nextCardIndex).getCardName());
            deck.remove(nextCardIndex);
            cardCount = deck.size();
        }else{
            currentCard = null;
        }
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getKings() {
        return kings;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public HashSet<String> getDiscard() {
        return discard;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public void setKings(int kings) {
        this.kings = kings;
    }

    public void setDiscard(HashSet<String> discard) {
        this.discard = discard;
    }

    public void resumeGame(HashSet<String> discard, int kings, String currentCardName){
        //for use after new game

        this.discard = discard;
        fillDeck();
        ArrayList<Card> newDeck = new ArrayList<Card>();
        this.kings = kings;
        if(discard.isEmpty())Log.e("resume game", "Discard empty");

        //recover currentcard and normal deck from discarded deck
        for(int i =0; i<deck.size();i++){
            if(deck.get(i).getCardName().equals(currentCardName)){
                currentCard = deck.get(i);
            }
            if(!discard.contains(deck.get(i).getCardName())){
                newDeck.add(deck.get(i));
            }
        }
        deck = newDeck;
        cardCount = newDeck.size();
    }
}
