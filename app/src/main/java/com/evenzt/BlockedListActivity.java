package com.evenzt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import blockPOJO.blockBean;
import blockUserListPOJO.blockListBean;
import interfaces.eventDetails;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BlockedListActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;
    String userId;
    List<blockUserListPOJO.Datum> list;
    GridLayoutManager manager;
    ProgressBar progress;
    FriendsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_list);

        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);

        progress = (ProgressBar)findViewById(R.id.progress);

        manager = new GridLayoutManager(this , 1);

        list = new ArrayList<>();


        adapter = new FriendsAdapter(this , list);


        grid = (RecyclerView)findViewById(R.id.blocked_grid);

        grid.setLayoutManager(manager);
        grid.setAdapter(adapter);


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

        toolbar.setTitle("Blocked users list");
        toolbar.setTitleTextColor(Color.WHITE);


        progress.setVisibility(View.VISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);


        retrofit2.Call<blockListBean> call = cr.blockList(userId);

        call.enqueue(new Callback<blockListBean>() {
            @Override
            public void onResponse(retrofit2.Call<blockListBean> call, Response<blockListBean> response) {

                list = response.body().getData();
                adapter.setGridData(list);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(retrofit2.Call<blockListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(BlockedListActivity.this , "Error" , Toast.LENGTH_SHORT).show();
            }
        });



    }


    class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

        List<blockUserListPOJO.Datum> list = new ArrayList<>();
        Context context;



        public void setGridData(List<blockUserListPOJO.Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }



        public FriendsAdapter(Context context , List<blockUserListPOJO.Datum> list)
        {
            this.context = context;
            this.list = list;
        }


        @Override
        public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.blocked_list_model , null);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            blockUserListPOJO.Datum item = list.get(position);

            holder.name.setText("@" + item.getUsername());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView name;


            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView)itemView.findViewById(R.id.name);

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(BlockedListActivity.this);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.unblock_dialog);

                        dialog.show();

                        Button unBlock = (Button)dialog.findViewById(R.id.unblock);
                        Button cancel = (Button)dialog.findViewById(R.id.cancel);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });


                        unBlock.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();



                                final eventDetails cr = retrofit.create(eventDetails.class);

                                retrofit2.Call<blockBean> call = cr.unblockUser(list.get(getAdapterPosition()).getId() , userId);

                                call.enqueue(new Callback<blockBean>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<blockBean> call, Response<blockBean> response) {

                                        Toast.makeText(getApplicationContext() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();


                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("http://evenzt.com/")
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();


                                        final eventDetails cr = retrofit.create(eventDetails.class);


                                        retrofit2.Call<blockListBean> call2 = cr.blockList(userId);

                                        call2.enqueue(new Callback<blockListBean>() {
                                            @Override
                                            public void onResponse(retrofit2.Call<blockListBean> call, Response<blockListBean> response) {

                                                list = response.body().getData();
                                                adapter.setGridData(list);




                                            }

                                            @Override
                                            public void onFailure(retrofit2.Call<blockListBean> call, Throwable t) {

                                            }
                                        });







                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<blockBean> call, Throwable t) {

                                    }
                                });

                                dialog.dismiss();

                            }
                        });


                    }
                });



            }
        }
    }



}
