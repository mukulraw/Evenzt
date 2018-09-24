package com.evenzt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import acceptPOJO.acceptBean;
import eventRequestPOJO.Datum;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class erAdapter extends RecyclerView.Adapter<erAdapter.ViewHolder> {

    Context context;
    List<Datum> list = new ArrayList<>();
    String eventId;

    public erAdapter(Context context , List<Datum> list , String eventId)
    {
        this.context = context;
        this.list = list;
        this.eventId = eventId;
    }


    public void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.er_list_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Datum item = list.get(position);

        holder.name.setText(item.getUsername());

        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(item.getImage() , holder.image);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        CircularImageView image;
        TextView name;
        Button accept , reject;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (CircularImageView)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.name);

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

                    Call<acceptBean> call = cr.acceptEvent(list.get(getAdapterPosition()).getId() , eventId);



                    call.enqueue(new Callback<acceptBean>() {
                        @Override
                        public void onResponse(Call<acceptBean> call, Response<acceptBean> response) {








                        }

                        @Override
                        public void onFailure(Call<acceptBean> call, Throwable t) {

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

                    Call<acceptBean> call = cr.rejectEvent(list.get(getAdapterPosition()).getId() , eventId);



                    call.enqueue(new Callback<acceptBean>() {
                        @Override
                        public void onResponse(Call<acceptBean> call, Response<acceptBean> response) {








                        }

                        @Override
                        public void onFailure(Call<acceptBean> call, Throwable t) {

                        }
                    });


                }
            });





        }
    }
}
