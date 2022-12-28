package com.example.flashcardactivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

public class Card  implements Serializable {


     private String front;
     private String back;
     private String name;
     private String id;
     private String setid;
     private Date creationDateTime;
     CardDAO dao;


    public Card(String setid , String front, String back, CardDAO dao){

        init();
        this.dao=dao;
        this.name= front;
        this.front= front;
        this.back = back;
        this.setid= setid;
        // now from business layer the call goes to third layer from inside the save function
        save();
    }

    public Card(Card a) {
        init();
        this.front=a.getFront();
        this.back= a.getBack();
        //this.id=a.getId();
    }

    public Card ( CardDAO dao ){
        init () ;


    }
  

    public void init(){
         this.id= UUID.randomUUID().toString();
        this.creationDateTime = new Date();
    }

    public String getFront(){

        //final String content = this.content;
        return this.front;
    }

    public String getBack() {
        return back;
    }

    public String getId() {
        return this.id;
    }
    public void setSetid( String n ){

        this.setid=n;
    }

    public void setFront(String front)
    {
        this.front= front;
        // calling save here to update the back in DB too
        // Updation of same Item is handled in DBDao File
        save();
    }

    public void SetBack( String back)
    {
        this.back=back;
    }

    public void save(){

        if (dao != null){

            Hashtable<String,String> data = new Hashtable<String, String>();
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

            data.put("Cardid",id);
            data.put("Front",this.getFront());
            data.put("Back", this.getBack());
            data.put("Setid", this.getsetid());
            dao.save(data);
        }



    }
    public String getsetid( ){

        return this.setid;

    }

    public void setDao(CardDAO dao) {
        this.dao = dao;
    }

    public void setBack(String s)
    {
        this.back=s;
        // calling save here to update the back in DB too
        // Updation of same Item is handled in DBDao File
        save();
    }

    // loading a single record obtained from DB

    public void load(Hashtable<String,String> data){


        this.id = data.get("cardid");
        this.front= data.get("front");
        this.back= data.get("back");
        this.setid= data.get("setid");

    }

// First this business layer contacts the DB to get the data . Three tier architecture gets completed.



    /// Loading only those cards that have setid passed through main activity
    public static ArrayList<Card> Load(CardDAO dao, String id ){

        ArrayList<Card> notes = new ArrayList<Card>();
        if(dao != null){

            // load it from DB through the card database
            // Every hashtable is a card
//--- This is the DAO call
            ArrayList<Hashtable<String,String>> objects = dao.load();

            for(Hashtable<String,String> obj : objects){
                 if(obj.get("setid").equals(id)) {

                Card card = new Card(dao);
                card.load(obj);
                notes.add(card);

                }
            }
        }
        return notes;




    }


}