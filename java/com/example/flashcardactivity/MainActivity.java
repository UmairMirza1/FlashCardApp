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

public class MainActivity extends AppCompatActivity implements MainAdapter.GroupItemClickListener {


    private RecyclerView.LayoutManager layoutManager;
    private EditText search;
    Filterable filterable;
    private ArrayList<CardGroup> dataSet = new ArrayList<CardGroup>();
    private MainAdapter mAdapter;
    private Hashtable<String,CardGroup> index = new Hashtable<String,CardGroup>();
    ActivityResultLauncher<Intent> setLauncher;
    CardDAO dao;
    SetViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String useremail = intent.getStringExtra("email");

       // dao= new SetDbdao(this);
        dao = new CardFirebaseDao(new CardFirebaseDao.observer() {
            @Override
            public void update() {
               refresh();
            }
        });


        //recycler view
        vm = new ViewModelProvider(MainActivity.this).get(SetViewModel.class);

        vm.setDao(dao);
        vm.setemail(useremail);


        dataSet = vm.getNotes(useremail);
        MainAdapter adp = new MainAdapter(dataSet,this );
        filterable = adp;
        mAdapter = adp;


        RecyclerView rv_main = (RecyclerView) findViewById(R.id.list_Set);
        rv_main.setHasFixedSize(true);
        // Layout inflater

        layoutManager = new LinearLayoutManager(this);
        rv_main.setLayoutManager(layoutManager);

        //search

        search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
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
        });



        rv_main.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_main.setAdapter(mAdapter);


        // getting result

        setLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){

                                Intent data = result.getData();
                                String title = data.getStringExtra("title");
                                String id =(String) data.getSerializableExtra("id");

                                if ( id.equals(""))
                                {
                                    CardGroup a= new CardGroup(title,dao,useremail);
                                    dataSet.add(a);
                                    mAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    CardGroup n= index.get(id);
                                    if ( n!=null) {
                                        n.setDao(dao);
                                        n.setName(title);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }

                        }
                    }
                });


    }



    @Override
    public void onClick(CardGroup n) {

        Intent intent = new Intent(this, SetActivity.class);
        String id = n.getId();
        intent.putExtra("id", id);
        intent.putExtra("name",n.getName());
        index.put(n.getId(),n);
        setLauncher.launch(intent);

    }

    public void clickHandler(View view) {

        if (view.getId() == R.id.button_new_set){

            Intent intent = new Intent(this, SetActivity.class);
            intent.putExtra("id", "");
            intent.putExtra("name","(noName)");
            setLauncher.launch(intent);

        }




    }

    public void refresh () {

        ///SetViewModel vm = new ViewModelProvider(MainActivity.this).get(SetViewModel.class);
        dataSet = vm.update();
        if (dataSet != null){

            mAdapter.updateData(dataSet);

        }
    }


}


