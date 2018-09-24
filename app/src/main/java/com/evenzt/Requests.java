package com.evenzt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import allRequestsPOJO.Datum;
import allRequestsPOJO.allRequestBean;
import interfaces.eventDetails;
import profilePOJO.profileBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Requests extends AppCompatActivity {


    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    AllRequestAdapter adapter;
    Toolbar toolbar;
    SharedPreferences pref;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        pref = getSharedPreferences("myPref" , Activity.MODE_PRIVATE);

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

        toolbar.setTitle("All Requests");
        toolbar.setTitleTextColor(Color.WHITE);


        manager = new GridLayoutManager(this , 1);

        list = new ArrayList<>();

        adapter = new AllRequestAdapter(this , list);

        grid = (RecyclerView)findViewById(R.id.all_request_list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);


        bean b = (bean)getApplicationContext();


        Call<allRequestBean> call = cr.getAllRequests(pref.getString("id" , "0"));
        call.enqueue(new Callback<allRequestBean>() {
            @Override
            public void onResponse(Call<allRequestBean> call, Response<allRequestBean> response) {

                list = response.body().getData();

                adapter.setGridData(list);


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<allRequestBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });









    }








    class AllRequestAdapter extends RecyclerView.Adapter<AllRequestAdapter.ViewHolder> {


        List<Datum> list = new ArrayList<>();
        Context context;


        public AllRequestAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }


        public void setGridData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }


        @Override
        public AllRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.friend_list_model , null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(list.get(position).getThumbnailUrl() , holder.image);
            holder.name.setText(String.format("@%s", list.get(position).getRequestUsername()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context , UserDetailScreen.class);
                    i.putExtra("frien" , list.get(position).getFriendId());
                    context.startActivity(i);

                }
            });



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {

            CircularImageView image;
            TextView name;
            TextView accept , reject;


            public ViewHolder(View itemView) {
                super(itemView);

                image = (CircularImageView)itemView.findViewById(R.id.friend_list_profile);
                name = (TextView)itemView.findViewById(R.id.friend_list_name);
                accept = (TextView)itemView.findViewById(R.id.accept);
                reject = (TextView)itemView.findViewById(R.id.reject);

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progress.setVisibility(View.VISIBLE);


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        bean b = (bean)context.getApplicationContext();

                        Call<profileBean> call = cr.acceptRequest(pref.getString("id" , "0") , list.get(getAdapterPosition()).getFriendId());



                        call.enqueue(new Callback<profileBean>() {
                            @Override
                            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();


                                final eventDetails cr = retrofit.create(eventDetails.class);


                                bean b = (bean)getApplicationContext();


                                Call<allRequestBean> call2 = cr.getAllRequests(pref.getString("id" , "0"));
                                call2.enqueue(new Callback<allRequestBean>() {
                                    @Override
                                    public void onResponse(Call<allRequestBean> call, Response<allRequestBean> response) {

                                        list = response.body().getData();

                                        adapter.setGridData(list);

                                        progress.setVisibility(View.GONE);


                                    }

                                    @Override
                                    public void onFailure(Call<allRequestBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });


                                Toast.makeText(context , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();





                            }

                            @Override
                            public void onFailure(Call<profileBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });







                    }
                });



                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progress.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        bean b = (bean)context.getApplicationContext();

                        Call<profileBean> call = cr.declineRequest(pref.getString("id" , "0") , list.get(getAdapterPosition()).getFriendId());



                        call.enqueue(new Callback<profileBean>() {
                            @Override
                            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();


                                final eventDetails cr = retrofit.create(eventDetails.class);


                                bean b = (bean)getApplicationContext();


                                Call<allRequestBean> call2 = cr.getAllRequests(pref.getString("id" , "0"));
                                call2.enqueue(new Callback<allRequestBean>() {
                                    @Override
                                    public void onResponse(Call<allRequestBean> call, Response<allRequestBean> response) {

                                        list = response.body().getData();

                                        adapter.setGridData(list);

                                        progress.setVisibility(View.GONE);


                                    }

                                    @Override
                                    public void onFailure(Call<allRequestBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });



                                try {
                                    Toast.makeText(context , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }






                            }

                            @Override
                            public void onFailure(Call<profileBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });


                    }
                });


            }
        }
    }










}
