package com.example.flashcardactivity;

import java.util.ArrayList;
import java.util.Hashtable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CardDbhelper extends SQLiteOpenHelper {

    public static final int DBversion=4;
    public static final String Db_name= "Card.db";

    public CardDbhelper(Context context){
        super(context,Db_name,null,DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Create_cards_table = "CREATE TABLE "
                + "Cards " + "(" + "Cardid " + " TEXT PRIMARY KEY ," + "Front " + " TEXT ," + "Back " + "TEXT, "+" Setid " +" TEXT );";

      /* String Create_Set_table = "CREATE TABLE "
                + "Set_" + "(" + "id"
                + " TEXT PRIMARY KEY ," + "Name" + " TEXT );" ; */


        db.execSQL(Create_cards_table);
       // db.execSQL(Create_Set_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS Cards");
      //  db.execSQL("DROP TABLE IF EXISTS Set_");
        onCreate(db);

    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

}