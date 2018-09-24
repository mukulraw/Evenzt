package com.evenzt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import allRequestsPOJO.Datum;
import interfaces.eventDetails;
import profilePOJO.profileBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class AllRequestAdapter extends RecyclerView.Adapter<AllRequestAdapter.ViewHolder> {


    List<Datum> list = new ArrayList<>();
    Context context;


    public AllRequestAdapter(Context context , List<Datum> list)
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
        View view = inflater.inflate(R.layout.friend_list_model , null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(list.get(position).getThumbnailUrl() , holder.image);
        holder.name.setText("@" + list.get(position).getRequestUsername());





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        CircularImageView image;
        TextView name;
        Button accept , reject;


        public ViewHolder(View itemView) {
            super(itemView);

            image = (CircularImageView)itemView.findViewById(R.id.friend_list_profile);
            name = (TextView)itemView.findViewById(R.id.friend_list_name);
            accept = (Button)itemView.findViewById(R.id.accept);
            reject = (Button)itemView.findViewById(R.id.reject);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {











                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    final eventDetails cr = retrofit.create(eventDetails.class);

                    bean b = (bean)context.getApplicationContext();

                    Call<profileBean> call = cr.acceptRequest(String.valueOf(b.userId) , list.get(getAdapterPosition()).getFriendId());



                    call.enqueue(new Callback<profileBean>() {
                        @Override
                        public void onResponse(Call<profileBean> call, Response<profileBean> response) {


                            Toast.makeText(context , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();





                        }

                        @Override
                        public void onFailure(Call<profileBean> call, Throwable t) {

                        }
                    });







                }
            });



            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    final eventDetails cr = retrofit.create(eventDetails.class);

                    bean b = (bean)context.getApplicationContext();

                    Call<profileBean> call = cr.declineRequest(String.valueOf(b.userId) , list.get(getAdapterPosition()).getFriendId());



                    call.enqueue(new Callback<profileBean>() {
                        @Override
                        public void onResponse(Call<profileBean> call, Response<profileBean> response) {


                            Toast.makeText(context , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();





                        }

                        @Override
                        public void onFailure(Call<profileBean> call, Throwable t) {

                        }
                    });


                }
            });


        }
    }
}
