package com.example.flashcardactivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

public class CardViewModel extends ViewModel {

    private ArrayList<Card> notes;
    CardDAO dao;
    String setid;


    public ArrayList<Card> getNotes(String id ){
        if (notes == null){

                if (dao != null) {

                    notes = Card.Load(dao, id );

                }
                else notes = new ArrayList<Card>();
            }


        return notes;
    }


    public void setDao(CardDAO d){
        dao = d;
    }

    public void setSetid(String id ) { this.setid= id ;}

    public ArrayList<Card> update(String setid){

        if (dao != null){
            notes = Card.Load(dao,setid);
        }
        else notes = new ArrayList<Card>();
        return notes;
    }
}
