package com.example.segundoauqui.savingdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.attr.name;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    Button takeMeBack;

    private ListView list;
    private String[] Names;
    private  String [] id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        takeMeBack = (Button) findViewById(R.id.takeMeBack);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        try {
            ArrayList<MyContacs> contacts = dataBaseHelper.getContacs("-1");
            Names = new String[contacts.size()];

            id = new String[contacts.size()];


            for (int i = 0; i < contacts.size(); i++) {
                Names[i] = contacts.get(i).getEtName() + " " + contacts.get(i).getEtPhoneNumber();
                id[i] = "" + contacts.get(i).getId();
            }



        list = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Names);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(this);
        }catch (Exception e){}


    }

    @Override
    protected void onResume() {
        super.onResume();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        try {
            ArrayList<MyContacs> contacts = dataBaseHelper.getContacs("-1");
            Names = new String[contacts.size()];

            id = new String[contacts.size()];


            for (int i = 0; i < contacts.size(); i++) {
                Names[i] = contacts.get(i).getEtName() + " " + contacts.get(i).getEtPhoneNumber();
                id[i] = "" + contacts.get(i).getId();
            }



            list = (ListView)findViewById(R.id.listview);
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Names);
            list.setAdapter(adaptador);
            list.setOnItemClickListener(this);
        }catch (Exception e){}
    }

    public void takeMeBack(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getString(R.string.ID), id[i]);
        startActivity(intent);
    }
}
