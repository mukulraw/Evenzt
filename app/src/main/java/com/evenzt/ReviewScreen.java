package com.evenzt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import reviewPOJO.Datum;
import reviewPOJO.reviewBean;

public class ReviewScreen extends AppCompatActivity {


    RecyclerView grid;
    GridLayoutManager manager;
    Toolbar toolbar;

    List<Datum> list;

    RatingAdapter adapter;

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_screen);

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

        toolbar.setTitle("My Reviews");
        toolbar.setTitleTextColor(Color.WHITE);
        bean b = (bean)getApplicationContext();

        grid = (RecyclerView)findViewById(R.id.review_list);
        manager = new GridLayoutManager(this , 1);
        list = new ArrayList<>();

        adapter = new RatingAdapter(this , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);


        Call<reviewBean> call = cr.getReviews(String.valueOf(b.userId));

        call.enqueue(new Callback<reviewBean>() {
            @Override
            public void onResponse(Call<reviewBean> call, Response<reviewBean> response) {


                //if (response.body().getData()!=null)
                //{

                adapter.setGridData(response.body().getData());

                //}
                progress.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<reviewBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }
}
