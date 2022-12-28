package com.example.flashcardactivity;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class CardActivity extends AppCompatActivity {

    private Button save;
    private Button cancel;
    public EditText front;
    public EditText back;
    String CardId;
    String Front;
    String Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        front = (EditText) findViewById(R.id.text_front);
        back = (EditText) findViewById(R.id.text_back);
        save = (Button) findViewById(R.id.button_save);
        cancel = (Button) findViewById(R.id.button_cancel);

        Intent intent = getIntent();

        String frontt = intent.getStringExtra("front");
        String backk = intent.getStringExtra("back");
        CardId = intent.getStringExtra("id");

        front.setText(frontt);
        back.setText(backk);

        Front= frontt;
        Back= backk;



    }

    public void buttonClick(View v) {

        if (v.getId() == R.id.button_save) {
           savenote();

        }


        else if (v.getId() == R.id.button_cancel) {
            cancelnote();


        }


    }


    public void savenote() {

        Intent sendbacc = new Intent();

       /* String a = CardId;
        String b = Front;
        String c = Back; */

        sendbacc.putExtra("front",front.getText().toString());
        sendbacc.putExtra("back",back.getText().toString());
        sendbacc.putExtra("id",CardId);;
        //sendbacc.putExtra("setid",)
        Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK,sendbacc);
        finish();
    }

    public void cancelnote() {

        Intent result = new Intent();
        setResult(RESULT_CANCELED,result);
        finish();

    }


}