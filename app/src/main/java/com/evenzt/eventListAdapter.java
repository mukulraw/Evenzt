package com.evenzt;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


import nearbyPOJO.Datum;

class eventListAdapter extends RecyclerView.Adapter<eventListAdapter.ViewHolder>{
    Context context;
    List<Datum> list = new ArrayList<>();
    private String[] months = new String[]{"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};

    eventListAdapter(Context context, List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.event_list_model , null);
        return new ViewHolder(view);
    }

    void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        Datum item = list.get(position);



        holder.name.setText(item.getEventName());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(false).build();


        ImageLoader loader = ImageLoader.getInstance();
        try
        {
            loader.displayImage(item.getImageUrl().get(0).getUrl() , holder.imageView , options);
        }catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }



        String currentString = item.getStartTime();

        String[] separate1 = currentString.split("\\s");


        holder.likeCount.setText(item.getEventNumLikes());

        holder.cat.setText(item.getCategoryName());

        String[] s2 = separate1[0].split("-");

        int m = Integer.parseInt(s2[1]);

        try {
            holder.date.setText(String.format("%s %s %s", s2[2], months[m - 1], s2[0]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }







try {
    holder.address.setText(String.format("%s\n%s%s", item.getVenue(), context.getString(R.string.city2), item.getCity()));
}
catch (Exception e)
{
    e.printStackTrace();
}

        bean b = (bean)context.getApplicationContext();


        if (String.valueOf(b.userId).equals(item.getEventTimelineId()))
        {

            holder.isCheck.setVisibility(View.VISIBLE);
            holder.isCheck.setBackground(context.getResources().getDrawable(R.drawable.is_check_red));
            holder.isCheck.setText(R.string.host);
        }
        else
        {
            if (item.getIsJoined()!=null)
            {
                if (item.getIsJoined())
                {
                    holder.isCheck.setVisibility(View.VISIBLE);
                    holder.isCheck.setBackground(context.getResources().getDrawable(R.drawable.is_check_green));
                    holder.isCheck.setText(R.string.joined);
                }
            }
            else
            {
                //holder.isCheck.setVisibility(View.VISIBLE);
                //holder.isCheck.setBackground(context.getResources().getDrawable(R.drawable.is_check_green));
                //holder.isCheck.setText("join");
            }

            if (item.getIsRequested()!=null)
            {
                if (item.getIsRequested())
                {
                    holder.isCheck.setVisibility(View.VISIBLE);
                    holder.isCheck.setBackground(context.getResources().getDrawable(R.drawable.is_check_bavk));
                    holder.isCheck.setText(context.getString(R.string.requested));
                }
            }
            if (item.getIsRejected()!=null)
            {
                if (item.getIsRejected())
                {
                    holder.isCheck.setVisibility(View.VISIBLE);
                    holder.isCheck.setBackground(context.getResources().getDrawable(R.drawable.is_check_red));
                    holder.isCheck.setText(context.getString(R.string.rejected));
                }
            }




        }


        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Datum item = list.get(position);
                Activity activity = (Activity)context;
                Intent i = new Intent(context , EventDetailsScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("eventId" , item.getEventId());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name , date , address;
        ImageView imageView;
        TextView isCheck , likeCount , cat;
        MaterialRippleLayout click;

        private ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.event_name);
            date = (TextView)itemView.findViewById(R.id.event_date);
            address = (TextView)itemView.findViewById(R.id.event_address);
            imageView = (ImageView)itemView.findViewById(R.id.image);
            isCheck = (TextView)itemView.findViewById(R.id.isCheck);
            likeCount = (TextView)itemView.findViewById(R.id.like_count);
            click = (MaterialRippleLayout)itemView.findViewById(R.id.click);
            cat = (TextView)itemView.findViewById(R.id.cat);
            //itemView.setOnClickListener(this);

            //name.setText(list.get(getAdapterPosition()).getName());

        }



    }

}
