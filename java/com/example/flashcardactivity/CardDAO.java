package com.example.flashcardactivity;

import java.util.Hashtable;
import java.util.ArrayList;

public interface CardDAO {

    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public Hashtable<String,String> load(String id);



}
