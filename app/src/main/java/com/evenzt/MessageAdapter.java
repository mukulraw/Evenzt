package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Objects;


import de.hdodenhof.circleimageview.CircleImageView;
import messagePOJO.Datum;

class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    Context context;
    List<Datum> list;


    public MessageAdapter(Context context , List<Datum> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.message_list_model , parent , false);
        return new MessageAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Datum item = list.get(position);

        bean b = (bean)context.getApplicationContext();

        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(item.getFriendThumbnailUrl() , holder.userImage);

        holder.senderName.setText(item.getFriendUsername());

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
        TextView senderName , senderMessage;


        public ViewHolder(View itemView) {
            super(itemView);
            userImage = (CircleImageView) itemView.findViewById(R.id.sender_image);
            senderMessage = (TextView)itemView.findViewById(R.id.sendermessage);
            senderName = (TextView)itemView.findViewById(R.id.sender_name);


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
