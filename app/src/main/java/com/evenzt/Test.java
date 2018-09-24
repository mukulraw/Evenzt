package com.evenzt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

public class Test extends AppCompatActivity {

    RecyclerView grid;
    Button footer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        grid = (RecyclerView)findViewById(R.id.recycler);
        footer = (Button)findViewById(R.id.footer);


        LinearLayoutManager manager = new LinearLayoutManager(this );

        grid.setLayoutManager(manager);




    }
}
