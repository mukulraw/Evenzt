package com.evenzt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import PostedPOJO.Datum;
import PostedPOJO.PostedBean;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class JoinedEvents extends AppCompatActivity {

    int pno = 1;
    RecyclerView grid;
    GridLayoutManager manager;
    PostedAdapter adapter;
    List<Datum> list;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_events);
        manager = new GridLayoutManager(this , 1);

        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);

        list = new ArrayList<>();
        adapter = new PostedAdapter(this , list);

        grid = (RecyclerView)findViewById(R.id.posted_list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);







        grid.setOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {


                if (pno > 1)
                {
                    //bar.setVisibility(View.VISIBLE);

                    //list = new ArrayList<Datum>();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final eventDetails cr = retrofit.create(eventDetails.class);




                    Call<PostedBean> call2 = cr.myEvents(userId , String.valueOf(pno));

                    call2.enqueue(new Callback<PostedBean>() {
                        @Override
                        public void onResponse(Call<PostedBean> call, Response<PostedBean> response) {

                            if (response.body().getResponseMessage().equals("Event Data"))
                            {

                                List<Datum> data = response.body().getData();

                                for (int i = 0 ; i < data.size() ; i++)
                                {

                                    list.add(data.get(i));

                                }

                                adapter.setGridData(list);
                                //bar.setVisibility(View.GONE);

                                pno++;

                            }
                            else
                            {
                                pno = 1;
                                //bar.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<PostedBean> call, Throwable t) {

                        }
                    });

                }



            }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<PostedBean> call = cr.joinedEvents(userId , String.valueOf(pno));

        call.enqueue(new Callback<PostedBean>() {
            @Override
            public void onResponse(Call<PostedBean> call, Response<PostedBean> response) {

                if (response.body().getResponseMessage().equals("Event Data"))
                {

                    list = new ArrayList<Datum>();

                    List<Datum> data = response.body().getData();

                    for (int i = 0 ; i < data.size() ; i++)
                    {

                        Log.d("asdasdasdPosted" , String.valueOf(i));
                        list.add(data.get(i));

                    }

                    adapter.setGridData(list);
                    //bar.setVisibility(View.GONE);

                    pno++;

                }
                else
                {
                    pno = 1;
                    //bar.setVisibility(View.GONE);
                }





            }

            @Override
            public void onFailure(Call<PostedBean> call, Throwable t) {

            }
        });

    }
}
