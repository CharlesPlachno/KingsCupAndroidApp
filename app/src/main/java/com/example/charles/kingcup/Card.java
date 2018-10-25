package com.example.charles.kingcup;

/**
 * Created by Charles on 7/21/2017.
 */

public class Card {
    private String suite;
    private String face_value;
    private String rule;

    public Card(String suite, String face_value, String rule){
        this.suite = suite;
        this.face_value = face_value;
        this.rule = rule;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public void setFace_value(String face_value) {
        this.face_value = face_value;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getFace_value() {
        return face_value;
    }

    public String getRule() {
        return rule;
    }

    public String getSuite() {
        return suite;
    }
    public String getCardName(){
        return(face_value + " " + suite);
    }
}
