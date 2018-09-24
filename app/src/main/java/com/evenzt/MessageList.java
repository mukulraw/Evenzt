package com.evenzt;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import interfaces.eventDetails;
import messagePOJO.Datum;
import messagePOJO.messageBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MessageList extends AppCompatActivity {


    RecyclerView grid;
    MessageAdapter adapter;
    Toolbar toolbar;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);


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

        toolbar.setTitle("Messages");
        toolbar.setTitleTextColor(Color.WHITE);



        grid = (RecyclerView)findViewById(R.id.message_list);









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

        bean b = (bean)getApplicationContext();

        Call<messageBean> call = cr.getAllMessages(String.valueOf(b.userId));

        call.enqueue(new Callback<messageBean>() {
            @Override
            public void onResponse(Call<messageBean> call, Response<messageBean> response) {


                LinearLayoutManager manager = new LinearLayoutManager(MessageList.this);
                manager.setReverseLayout(true);
                adapter = new MessageAdapter(getApplicationContext() , response.body().getData());
                grid.setLayoutManager(manager);
                grid.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<messageBean> call, Throwable t) {

            }
        });



    }

    class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


        Context context;
        List<Datum> list;


        public MessageAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        @Override
        public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.message_list_model , parent , false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
            Datum item = list.get(position);

            bean b = (bean)context.getApplicationContext();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getFriendThumbnailUrl() , holder.userImage);

            holder.senderName.setText(String.format("@%s", item.getFriendUsername()));

            if (position == list.size()-1)
            {
                holder.line.setVisibility(View.GONE);
            }
            else
            {
                holder.line.setVisibility(View.VISIBLE);
            }

            if (Objects.equals(String.valueOf(b.userId), item.getFriendId()))
            {
                holder.senderMessage.setText(item.getMsg());
            }
            else
            {
                holder.senderMessage.setText(item.getMsg());
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {

            CircleImageView userImage;
            TextView senderName , senderMessage , line;


            public ViewHolder(View itemView) {
                super(itemView);
                userImage = (CircleImageView) itemView.findViewById(R.id.sender_image);
                senderMessage = (TextView)itemView.findViewById(R.id.sendermessage);
                senderName = (TextView)itemView.findViewById(R.id.sender_name);
                line = (TextView)itemView.findViewById(R.id.line);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context , ChatScreen.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("friend" , list.get(getAdapterPosition()).getFriendId());
                        //bundle.putString("url" , list.get(getAdapterPosition()).getFriendThumbnailUrl());
                        bundle.putString("name" , list.get(getAdapterPosition()).getFriendUsername());

                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

            }
        }

    }



}
