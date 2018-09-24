package com.evenzt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import interfaces.eventDetails;
import notificationPOJO.Datum;
import notificationPOJO.notificationBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView grid;
    LinearLayoutManager manager;

    List<Datum> list;
    NotificationAdapter adapter;
    Toolbar toolbar;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        pref = getSharedPreferences("myPref" , Activity.MODE_PRIVATE);

        edit = pref.edit();

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

        toolbar.setTitle("My Notifications");
        toolbar.setTitleTextColor(Color.WHITE);


        grid = (RecyclerView)findViewById(R.id.notification_list);

        list = new ArrayList<>();

        adapter = new NotificationAdapter(this , list);

        manager = new LinearLayoutManager(NotificationActivity.this);

        manager.setReverseLayout(true);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);



        edit.putInt("count" , 0);
        edit.apply();






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

        bean b = (bean)getApplicationContext();

        Call<notificationBean> call = cr.getNotification(pref.getString("id" , "0") , "0");

        call.enqueue(new Callback<notificationBean>() {
            @Override
            public void onResponse(Call<notificationBean> call, Response<notificationBean> response) {



                list = response.body().getData();




                adapter.setGridData(list);

                progress.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<notificationBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



    }

    class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


        List<Datum> list = new ArrayList<>();
        Context context;


        NotificationAdapter(Context context, List<Datum> list)
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
        public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.notify_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Datum item = list.get(position);

            holder.name.setText(item.getNotifText());

            if (position == list.size() - 1)
            {
                holder.hide.setVisibility(View.GONE);
            }
            else
            {
                holder.hide.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Objects.equals(item.getType(), "friend"))
                    {
                        Intent intent = new Intent(context , Requests.class);
                        context.startActivity(intent);
                    }
                    if (Objects.equals(item.getType(), "eventinvite"))
                    {
                        Intent i = new Intent(context , EventDetailsScreen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("eventId" , item.getEventId());
                        context.startActivity(i);
                    }
                    if (Objects.equals(item.getType(), "eventaccept"))
                    {
                        Intent i = new Intent(context , EventDetailsScreen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("eventId" , item.getEventId());
                        context.startActivity(i);

                    }
                    if (Objects.equals(item.getType(), "eventreject"))
                    {
                        Intent i = new Intent(context , EventDetailsScreen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("eventId" , item.getEventId());
                        context.startActivity(i);
                    }
                    if (Objects.equals(item.getType(), "accept_request"))
                    {
                        Intent i = new Intent(context , UserDetailScreen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("frien" , item.getNotifId());
                        context.startActivity(i);
                    }
                    if (Objects.equals(item.getType(), "joinrequest"))
                    {
                        Intent i = new Intent(context , MyEventsDetails.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle b = new Bundle();
                        b.putString("eventId" , item.getEventId());
                        i.putExtras(b);
                        context.startActivity(i);
                    }

                }
            });




        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView name , hide;

            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView)itemView.findViewById(R.id.name);
                hide = (TextView)itemView.findViewById(R.id.hide);

            }
        }
    }









}
