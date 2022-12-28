package com.example.flashcardactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

public class SetActivity extends AppCompatActivity implements  SetAdapter.CardItemClickListener{

    private ArrayList<Card> cardset;
    private Button newcard;
    private RecyclerView.LayoutManager layoutManager;
    private SetAdapter mAdapter;
    private ActivityResultLauncher<Intent> CardLauncher;
    private Hashtable<String,Card> index = new Hashtable<String,Card>();
    private EditText search;
    private EditText title;
    private String setid;
    private Button SlideShow;
    ActivityResultLauncher<Intent> notesLauncher;
    CardDAO dao;

    Filterable filterable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        dao = new CardDbDAO(this);

        Intent intent = getIntent();
        String content = intent.getStringExtra("name");
        setid = intent.getStringExtra("id");




        CardViewModel vm = new ViewModelProvider(SetActivity.this).get(CardViewModel.class);
        vm.setDao(dao);
        vm.setSetid(setid);
        cardset = vm.getNotes(setid);

        title = (EditText) findViewById(R.id.title);
        title.setText(content);

        RecyclerView rv= (RecyclerView) findViewById(R.id.list);
        rv.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        // specify an adapter
        SetAdapter adp = new SetAdapter(cardset, this);
        filterable = adp;
        mAdapter = adp;
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setAdapter(mAdapter);







        search= (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher(){


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterable.getFilter().filter(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );



        //register launcher
        CardLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == RESULT_OK) {

                            Toast.makeText(getApplicationContext(),"SavingCard",Toast.LENGTH_SHORT).show();

                            Intent data = result.getData();

                            String id = data.getStringExtra("id");
                            String front = data.getStringExtra("front");
                            String back = data.getStringExtra("back");

                            if ( id.equals(""))
                            {
                                cardset.add(new Card(setid,front,back,dao));
                                mAdapter.notifyDataSetChanged();

                            }
                            else{

                                Card n= index.get(id);
                                if (n!=null)
                                {
                                    n.setDao(dao);
                                    n.setSetid(setid);
                                    n.setFront(front);
                                    n.setBack(back);

                                    mAdapter.notifyDataSetChanged();
                                }

                            }

                            //
                        }


                    }


                });



    }

    //}

    public void clickHandler(@NonNull View clickHandler) {

        if ( clickHandler.getId()== R.id.button_new){
            newcard();

        }
        if ( clickHandler.getId()== R.id.button_save){

            savenote();

        }
        if ( clickHandler.getId()== R.id.button_cancel){

            cancelnote();
        }
        if (clickHandler.getId()==R.id.slideshow)
        {
            slideshow();

        }


    }

    private void newcard() {

        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("id","");
        intent.putExtra("Front","");
        intent.putExtra("Back","");

        CardLauncher.launch(intent);

    }



    public void savenote() {

        Intent sendbacc = new Intent();
        sendbacc.putExtra("id", setid);
        sendbacc.putExtra("title", title.getText().toString());
        setResult(RESULT_OK,sendbacc);
        finish();
    }

    public void cancelnote() {

        Intent result = new Intent();
        setResult(RESULT_CANCELED,result);
        finish();

    }

    public void slideshow(){

        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("Cardset",cardset);
        CardLauncher.launch(intent);
    }

    // @Override
    public void onClick(Card n) {
        Intent intent = new Intent(this, CardActivity.class);

        String id = n.getId();
        String f=n.getFront();
        String g= n.getBack();
        String h= n.getFront();
        intent.putExtra("front", n.getFront());
        intent.putExtra("back", n.getBack());
        intent.putExtra("id", n.getId());

        intent.putExtra("setid",n.getsetid() );

        index.put(n.getId(),n);
        CardLauncher.launch(intent);

    }
}

