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

import java.util.List;
import java.util.Objects;

import joinedUsersPOJO.Datum;


class JoinedAdapter extends RecyclerView.Adapter<JoinedAdapter.ViewHolder> {

    private Context context;
    private List<Datum> list;



    public void setGridData(List<Datum> list)
    {

        this.list = list;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        CircularImageView image;

        ViewHolder(View itemView) {
            super(itemView);


            name = (TextView)itemView.findViewById(R.id.joined_name);
            image = (CircularImageView)itemView.findViewById(R.id.joined_image);


            itemView.setOnClickListener(this);


        }




        @Override
        public void onClick(View view) {

            bean b = (bean)context.getApplicationContext();

            if (Objects.equals(String.valueOf(b.userId), list.get(getAdapterPosition()).getId()))
            {
                Intent i = new Intent(context , UserProfileActivity.class);
                context.startActivity(i);
            }
            else
            {
                Intent i = new Intent(context , UserDetailScreen.class);
                i.putExtra("frien" , list.get(getAdapterPosition()).getId());
                context.startActivity(i);

            }

        }
    }


    JoinedAdapter(Context context , List<Datum> list)
    {

        this.context = context;
        this.list = list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.joined_list_model , null);









        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Datum item = list.get(position);

        holder.name.setText(String.format("@%s", item.getUsername()));

        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(item.getImage() , holder.image);



    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
