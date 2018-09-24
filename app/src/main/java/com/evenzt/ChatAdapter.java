package com.evenzt;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import chatPOJO.Datum;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sendMessagePOJO.sendMessageBean;


class ChatAdapter extends BaseAdapter {

    Context context;
    List<Datum> list;
    LayoutInflater inflater;
    private int res;


    public ChatAdapter(Context context , List<Datum> list , int layoutResId)
    {
        this.context = context;
        this.list = list;
        this.res = layoutResId;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }





    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        View vi = view;
        ViewHolder holder;
        if(view==null){



            vi = inflater.inflate(res, null);



            holder = new ViewHolder();
            holder.layoutToAdd = (LinearLayout)vi.findViewById(R.id.add_bubble);
            holder.container = (LinearLayout)vi.findViewById(R.id.containrer);
            holder.bubble = (TextView)vi.findViewById(R.id.bubble);
            holder.eventName = (TextView)vi.findViewById(R.id.event_name);
            holder.time = (TextView)vi.findViewById(R.id.time);
            holder.reply = (TextView) vi.findViewById(R.id.reply);


            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();


        final Datum item = list.get(i);
        final bean b = (bean)context.getApplicationContext();



        if (Objects.equals(String.valueOf(b.userId), item.getSenderId()))
        {


            if (Objects.equals(item.getIsQuery(), "1"))
            {
                holder.reply.setVisibility(View.GONE);
                holder.eventName.setVisibility(View.VISIBLE);
                holder.eventName.setText(String.format("%s%s", context.getString(R.string.event1), item.getEventName()));






            }
            else if (Objects.equals(item.getIsQuery(), "0"))
            {
                holder.reply.setVisibility(View.GONE);
                holder.eventName.setVisibility(View.GONE);
            }


            holder.bubble.setText(item.getMessageText());
            holder.layoutToAdd.setBackground(context.getResources().getDrawable(R.drawable.back_me));
            //holder.sender.setBackground(context.getResources().getDrawable(R.drawable.back_me));
            holder.container.setGravity(Gravity.END);
        }
        else
        {

            if (Objects.equals(item.getIsQuery(), "1"))
            {
                holder.reply.setVisibility(View.VISIBLE);
                holder.eventName.setVisibility(View.VISIBLE);
                holder.eventName.setText(String.format("%s%s", context.getString(R.string.event1), item.getEventName()));



                holder.reply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.query_dialog);

                        dialog.show();

                        final EditText queryText = (EditText)dialog.findViewById(R.id.query);
                        Button send = (Button)dialog.findViewById(R.id.send);

                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();


                                final eventDetails cr = retrofit.create(eventDetails.class);

                                Call<sendMessageBean> call = cr.sendMessage(String.valueOf(b.userId), item.getSenderId() , queryText.getText().toString() , "1" , item.getEventName());

                                call.enqueue(new Callback<sendMessageBean>() {
                                    @Override
                                    public void onResponse(Call<sendMessageBean> call, Response<sendMessageBean> response) {



                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onFailure(Call<sendMessageBean> call, Throwable throwable) {

                                        dialog.dismiss();

                                    }
                                });


                            }
                        });


                    }
                });


            }
            else if (Objects.equals(item.getIsQuery(), "0"))
            {
                holder.reply.setVisibility(View.GONE);
                holder.eventName.setVisibility(View.GONE);
            }


            holder.time.setText(item.getMessageTime());

            holder.bubble.setText(item.getMessageText());
            holder.layoutToAdd.setBackground(context.getResources().getDrawable(R.drawable.back_you));
            //holder.sender.setBackground(context.getResources().getDrawable(R.drawable.back_you));
            holder.container.setGravity(Gravity.START);
        }





        return vi;




















    }

    public class ViewHolder{

        LinearLayout layoutToAdd , container;
        TextView bubble , eventName , time;
        TextView reply;


}

}
