package com.evenzt;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import friendsPOJO.Datum;
import friendsPOJO.friendsBean;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Friends extends AppCompatActivity {

    RecyclerView grid;
    GridLayoutManager manager;
    FriendsAdapter adapter;
    List<Datum> list;
    TextView addFriend;
    Toolbar toolbar;

    ProgressBar progress;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        progress = (ProgressBar)findViewById(R.id.progress);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        toolbar.setTitle("My Friends");
        toolbar.setTitleTextColor(Color.WHITE);

        bean b = (bean)getApplicationContext();
        userId = String.valueOf(b.userId);


        list = new ArrayList<>();

        manager = new GridLayoutManager(this , 1);
        adapter = new FriendsAdapter(this , list);

        addFriend = (TextView)findViewById(R.id.add_friend);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext() , UsersActivity.class);
                startActivity(i);

            }
        });

        grid = (RecyclerView)findViewById(R.id.friends_grid);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);





    }


    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);


        Call<friendsBean> call = cr.getFriends(userId);

        call.enqueue(new Callback<friendsBean>() {
            @Override
            public void onResponse(Call<friendsBean> call, Response<friendsBean> response) {


                if (response.body().getData()!=null)
                {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<friendsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(Friends.this, "Error" , Toast.LENGTH_SHORT).show();
            }
        });


    }
}
