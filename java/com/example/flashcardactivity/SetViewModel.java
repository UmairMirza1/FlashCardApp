package com.example.flashcardactivity;

import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

public class SetViewModel extends ViewModel {

    private ArrayList<CardGroup> sets;
    CardDAO dao;
    private String email;

    public ArrayList<CardGroup> getNotes(String email ){
        if (sets == null){

                if (dao != null){
                    sets = CardGroup.load(dao,email);
                }
                else sets = new ArrayList<CardGroup>();
            }
            else{
                //sets = (ArrayList<CardGroup>) savedInstanceState.get(key);
            }

        return sets;
    }

    public void setDao(CardDAO d){
        dao = d;
    }
    public void setemail ( String email ){  this.email=email;}

    public ArrayList<CardGroup> update(){
        if (dao != null){
            sets = CardGroup.load(dao,email);
        }
        else sets = new ArrayList<CardGroup>();
        return sets;
    }


}
