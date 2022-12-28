package com.example.flashcardactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Filterable;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Hashtable;

public class SlideshowActivity extends AppCompatActivity {
    private ArrayList<Card> cardset= new ArrayList<Card>();

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);

        RecyclerView rv_main = (RecyclerView) findViewById(R.id.list_Set);
        rv_main.setHasFixedSize(true);
        // Layout inflater

        layoutManager = new LinearLayoutManager(this);
        rv_main.setLayoutManager(layoutManager);

        Intent intent= getIntent();
        cardset= (ArrayList<Card>) intent.getSerializableExtra("cardset");

        SlideShowAdapter adp = new SlideShowAdapter(cardset );
         mAdapter = adp;

        rv_main.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_main.setAdapter(mAdapter);



    }

    public void clickHandler(View view) {

        if (view.getId() == R.id.back) {

            Intent result = new Intent();
            setResult(RESULT_CANCELED,result);
            finish();

        }
    }


}
