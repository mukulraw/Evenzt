package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import friendsPOJO.Datum;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    List<Datum> list = new ArrayList<>();
    Context context;



    public void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }



    public FriendsAdapter(Context context , List<Datum> list)
    {
        this.context = context;
        this.list = list;
    }


    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.friend_list_model , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {

        final Datum item = list.get(position);


        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(item.getFriendThumbnailUrl() , holder.profile);

        holder.username.setText(String.format("@%s", item.getFriendUsername()));


        holder.accept.setVisibility(View.GONE);
        holder.reject.setVisibility(View.GONE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bean b = (bean)context.getApplicationContext();

                if (Objects.equals(String.valueOf(b.userId), item.getFriendId()))
                {
                    Intent i = new Intent(context , UserProfileActivity.class);
                    context.startActivity(i);
                }
                else
                {
                    Intent i = new Intent(context , UserDetailScreen.class);
                    i.putExtra("frien" , item.getFriendId());
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

        CircularImageView profile;
        TextView username;
        TextView accept , reject;


    public ViewHolder(View itemView) {
        super(itemView);

        profile = (CircularImageView)itemView.findViewById(R.id.friend_list_profile);
        username = (TextView)itemView.findViewById(R.id.friend_list_name);
        accept = (TextView) itemView.findViewById(R.id.accept);
        reject = (TextView) itemView.findViewById(R.id.reject);

      /*  itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bean b = (bean)context.getApplicationContext();

                if (!Objects.equals(String.valueOf(b.userId), list.get(getAdapterPosition()).getFriendId()))
                {
                    Intent intent = new Intent(context , UserDetailScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("frien" , list.get(getAdapterPosition()).getFriendId());
                    context.startActivity(intent);
                }



            }
        });
*/

    }
}
}
