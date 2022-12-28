package com.example.flashcardactivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Hashtable;
import com.example.flashcardactivity.CardDbhelper;


public class CardDbDAO implements  CardDAO {

    private Context context;

    public CardDbDAO (Context ctx){
        this.context= ctx;

    }


    @Override
    public void save(Hashtable<String, String> attributes) {

        CardDbhelper carddb = new CardDbhelper(context) ;
        SQLiteDatabase  db = carddb.getWritableDatabase();
        ContentValues content= new ContentValues();
        Enumeration<String> keys = attributes.keys();

        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key, attributes.get(key));
        }

        String [] arguments = new String[1];
        arguments[0] = attributes.get("Cardid");
        Hashtable obj = load(arguments[0]);

        if (obj.get("cardid") != null && obj.get("cardid").equals(arguments[0])){
            db.update("Cards",content,"Cardid = ?",arguments);
        }
        else{
            long res=db.insert("Cards",null,content);
            if (res>=1){

                Toast.makeText(context.getApplicationContext(),"Card Saved",Toast.LENGTH_SHORT).show();

            };
        }


    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {

        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @SuppressLint("Range")
    @Override
    public ArrayList<Hashtable<String, String>> load() {

        CardDbhelper dbHelper = new CardDbhelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Cards ";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext()){

            Hashtable<String,String> obj = new Hashtable<String, String>();

            String [] columns = cursor.getColumnNames();

            for(String col : columns){

                obj.put(col.toLowerCase(),cursor.getString(cursor.getColumnIndex(col)));}

            objects.add(obj);
        }

        return objects;

    }
    @SuppressLint("Range")
    @Override
    public Hashtable<String, String> load(String id) {

        CardDbhelper dbHelper = new CardDbhelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Cards WHERE Cardid = ?";
        String[] arguments = new String[1];
        arguments[0] = id;
        Cursor cursor = db.rawQuery(query,arguments);


        Hashtable<String,String> obj = new Hashtable<String, String>();
        while(cursor.moveToNext()){
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                obj.put(col.toLowerCase(),cursor.getString(cursor.getColumnIndex(col)));
            }
        }

        return obj;
    }
    }

