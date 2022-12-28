package com.example.flashcardactivity;


import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Hashtable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// I've split code into two helpers to make DB operations easier .
// As in one helper and one db it was hard to find the error
public class SetDbHelper extends SQLiteOpenHelper{
    public static final int DBversion=5;
    public static final String Db_name= "Set.db";

    Context context;

    public SetDbHelper(Context context){

        super(context,Db_name,null,DBversion);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        String Create_Set_table = "CREATE TABLE "
                + "Set_" + "(" + "Setid" + " TEXT PRIMARY KEY ," + "Name" + " TEXT );" ;

        db.execSQL(Create_Set_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Set_");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
