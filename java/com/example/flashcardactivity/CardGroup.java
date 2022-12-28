package com.example.flashcardactivity;
//import android.support.v7.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

public class CardGroup implements Serializable {
    private String useremail;
    private String name;
    private ArrayList<Card> Set;
    private String id;
    CardDAO dao;

    public CardGroup() {

        init();
    }
    public CardGroup(String n){
        this.name= n;
    }

    public CardGroup(String name  , CardDAO dao , String email) {
        this.useremail = email;
        this.name = name;
        this.id= UUID.randomUUID().toString();
        this.dao= dao;
        save();
    }

    public void checkifexists(String name ){




    }


    public void update(String name) {
       // this.Set=set;
        this.name=name;
    }

    public void update(String name, ArrayList <Card> set) {
            this.Set=set;
            this.name=name;
    }

    public void init(){

        this.id= UUID.randomUUID().toString();


    }
    public String getName() {
        return name;
    }

    public String getId(){return this.id;}
    public void setName(String name) {
        this.name = name;
            // calling save here to update the name in DB too
        // Updation of same Item is handled in DBDao File
        save();
    }
    public void setDao(CardDAO dao)
    {
        this.dao=dao;
    }
    public void setSet(ArrayList<Card> set) {
        Set = set;
    }

    public ArrayList<Card> getSet() {
        return Set;
    }

    public void setContent(String content) {
        this.name= content;
    }

    public void save(){

        if (dao != null){

            Hashtable<String,String> data = new Hashtable<String, String>();


            data.put("Setid",id);
            data.put("name", this.getName());
            data.put("email", this.useremail);

            dao.save(data);
        }



    }
    public CardGroup ( CardDAO dao ){
        init () ;


    }
    public String getsetid( ){

        return this.id;

    }

    public void load(Hashtable<String,String> data){
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

        name = data.get("name");
        id= data.get("Setid");
        useremail= data.get("email");
    }


    public static ArrayList<CardGroup> load(CardDAO dao, String email)
    {
        ArrayList<CardGroup> notes = new ArrayList<CardGroup>();
        if(dao != null){
            // load it from DB through the card database
            // This is where the call goes to third layer
            ArrayList<Hashtable<String,String>> objects = dao.load();

            for(Hashtable<String,String> obj : objects){
                    if (obj.get("email").equals(email)){
                CardGroup card = new CardGroup(dao);
                card.load(obj);
                notes.add(card);}
              }
        }
        return notes;


    }


    public static ArrayList<CardGroup> load(CardDAO dao ){

        ArrayList<CardGroup> notes = new ArrayList<CardGroup>();
        if(dao != null){
            // load it from DB through the card database
            // This is where the call goes to third layer
            ArrayList<Hashtable<String,String>> objects = dao.load();

            for(Hashtable<String,String> obj : objects){
            //    if (obj.get("email").equals(this.useremail){
                CardGroup card = new CardGroup(dao);
                card.load(obj);
                notes.add(card);}
          //  }
        }
        return notes;


    }
}
