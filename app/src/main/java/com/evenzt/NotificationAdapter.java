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
import java.util.Objects;

import notificationPOJO.Datum;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    List<Datum> list = new ArrayList<>();
    Context context;


    public NotificationAdapter(Context context , List<Datum> list)
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
        View view = inflater.inflate(R.layout.notify_list_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Datum item = list.get(position);

        holder.name.setText(item.getNotifText());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Objects.equals(item.getType(), "friend"))
                {

                    Intent intent = new Intent(context , Friends.class);
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


            }
        });




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

        }
    }
}
