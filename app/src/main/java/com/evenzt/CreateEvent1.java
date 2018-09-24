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

import categoryPOJO.Datum;
import categoryPOJO.categoryBean;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateEvent1 extends AppCompatActivity {

    List<Datum> list;

    ProgressBar progress;

    CatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        progress = (ProgressBar)findViewById(R.id.progress);

        RecyclerView chooselist = (RecyclerView) findViewById(R.id.choose_list);



        list = new ArrayList<>();



        bean b = (bean)this.getApplicationContext();

        b.list = new ArrayList<>();


        adapter = new CatAdapter(this , list);


        GridLayoutManager manager = new GridLayoutManager(this , 1);

        chooselist.setAdapter(adapter);
        chooselist.setLayoutManager(manager);


        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);


        Call<categoryBean> call = cr.getAllCategories(String.valueOf(b.userId));

        call.enqueue(new Callback<categoryBean>() {
            @Override
            public void onResponse(Call<categoryBean> call, Response<categoryBean> response) {




                adapter.setGridData(response.body().getData());
                progress.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<categoryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(CreateEvent1.this , "Error" , Toast.LENGTH_SHORT).show();
            }
        });





       //










    }
}
