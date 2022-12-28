package com.example.flashcardactivity;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class CardFirebaseDao implements  CardDAO{


    public interface  observer {
        void update();

    }

    private observer observer;
    FirebaseDatabase database;
    DatabaseReference myRef;


    ArrayList<Hashtable<String,String>>  data;

    public CardFirebaseDao(observer obs){
        observer = obs;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cards");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    data = new ArrayList<Hashtable<String,String>>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        GenericTypeIndicator<HashMap<String,Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String,Object> map =  d.getValue(type);

                        Hashtable<String,String> obj = new Hashtable<String,String>();
                        for(String key : map.keySet()){
                            obj.put(key,map.get(key).toString());
                        }
                        data.add(obj);
                    }

                    observer.update();
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });

    }


    @Override
    public void save(Hashtable<String, String> attributes) {
        myRef.child(attributes.get("name")).setValue(attributes);
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }

    }

    @Override
    public ArrayList<Hashtable<String, String>> load() {
        if (data == null){
            data = new ArrayList<Hashtable<String,String>>();
        }
        return data;
    }

    @Override
    public Hashtable<String, String> load(String id) {
        return null;
    }
}
