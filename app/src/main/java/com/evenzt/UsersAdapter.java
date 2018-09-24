package com.evenzt;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import usersPOJO.Datum;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    List<Datum> list = new ArrayList<>();
    Context context;

    public UsersAdapter(Context context , List<Datum> list)
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.users_list_model , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Datum item = list.get(position);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(item.getImage() , holder.profile);
        holder.name.setText(item.getUsername());
        if (item.getIsFriend())
        {
            holder.isFriend.setTextColor(Color.GREEN);
        }
        else
        {
            holder.isFriend.setTextColor(Color.GRAY);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircularImageView profile;
        TextView name , isFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            profile = (CircularImageView)itemView.findViewById(R.id.users_list_profile);
            name = (TextView)itemView.findViewById(R.id.users_list_name);
            isFriend = (TextView)itemView.findViewById(R.id.users_list_is_friend);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context , UserDetailScreen.class);
                    i.putExtra("frien" , list.get(getAdapterPosition()).getId());
                    context.startActivity(i);

                }
            });

        }
    }

}
