package com.evenzt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import interfaces.eventDetails;
import joinedUsersPOJO.Datum;
import joinedUsersPOJO.joinedUsersBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class JoinedMembers extends AppCompatActivity {

    private RecyclerView joinedlist;

    String userId , eventId;

    GridLayoutManager manager;

    ProgressBar progress;

    JoinedAdapter adapter;
    private List<Datum> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_members);
        joinedlist = (RecyclerView) findViewById(R.id.joined_members_list);

        progress = (ProgressBar)findViewById(R.id.progress);

        list = new ArrayList<>();

        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);

        eventId = getIntent().getStringExtra("eventId");


        manager = new GridLayoutManager(this , 1);

        adapter = new JoinedAdapter(this , list);


        progress.setVisibility(View.VISIBLE);


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<joinedUsersBean> call = cr.getJoinedUsers(userId , eventId);

        call.enqueue(new Callback<joinedUsersBean>() {
            @Override
            public void onResponse(Call<joinedUsersBean> call, Response<joinedUsersBean> response) {


                list = response.body().getData();
                adapter.setGridData(list);

                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<joinedUsersBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(JoinedMembers.this , "Error" , Toast.LENGTH_SHORT).show();
            }
        });




        joinedlist.setAdapter(adapter);
        joinedlist.setLayoutManager(manager);






    }
}
