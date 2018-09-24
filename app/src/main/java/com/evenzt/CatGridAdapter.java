package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import searchPOJO.Datum;


public class CatGridAdapter extends RecyclerView.Adapter<CatGridAdapter.ViewHolder> {

    List<Datum> list = new ArrayList<>();
    Context context;


    public CatGridAdapter(Context context , List<Datum> list)
    {
        this.context = context;
        this.list = list;
    }


    public void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }


    public void addData(Datum item)
    {
        list.add(item);
        notifyItemInserted(list.size()-1);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cat_searh_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Datum item = list.get(position);

        holder.name.setText(item.getEventName());
        holder.host.setText(String.format("%s%s", context.getString(R.string.created_by), item.getEventDesc()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name , host;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.cat_event_title);
            host = (TextView)itemView.findViewById(R.id.cat_event_host);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context , EventDetailsScreen.class);
                    i.putExtra("eventId" , list.get(getAdapterPosition()).getEventId());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);


                }
            });

        }
    }


}

