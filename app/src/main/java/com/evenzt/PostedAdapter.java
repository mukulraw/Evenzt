package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import PostedPOJO.Datum;


class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.ViewHolder>{

    Context context;
    List<Datum> list = new ArrayList<>();



    String[] mon = {"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};



    PostedAdapter(Context context, List<Datum> list)
    {
        this.context = context;
        this.list = list;
    }

    void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.posted_list_model , parent , false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Datum item = list.get(position);

        String currentString = item.getStartTime();

        String[] separate1 = currentString.split("\\s");

        String asd = separate1[0];

        String[] sepa2  = asd.split("-");

        holder.name.setText(item.getEventName());
        holder.date.setText(sepa2[2]);
        holder.month.setText(mon[Integer.parseInt(sepa2[1]) - 1]);
        holder.venue.setText(item.getCategoryName());

        Log.d("asdasdasdasdadasdasd" , sepa2[0]);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        TextView date , month , name , venue;


        ViewHolder(View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.posted_date);
            month = (TextView)itemView.findViewById(R.id.posted_month);
            name = (TextView)itemView.findViewById(R.id.posted_name);
            venue = (TextView)itemView.findViewById(R.id.posted_venue);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context , MyEventsDetails.class);
                    Bundle b = new Bundle();
                    b.putString("eventId" , list.get(getAdapterPosition()).getEventId());
                    b.putString("name" , list.get(getAdapterPosition()).getEventName());
                    i.putExtras(b);
                    context.startActivity(i);

                }
            });



        }
    }
}
