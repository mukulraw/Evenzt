package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import categoryPOJO.Datum;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder>{


    List<Datum> list = new ArrayList<>();
    Context context;


    public CatAdapter(Context context , List<Datum> list)
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
        View view = inflater.inflate(R.layout.cat_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Datum item = list.get(position);

        holder.name.setText(item.getName());

        if (position == list.size()-1)
        {
            holder.line.setVisibility(View.GONE);
        }

        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(item.getImage() , holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name , line;
        ImageView image;

        MaterialRippleLayout click;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.cat_list_item);
            line = (TextView)itemView.findViewById(R.id.line);
            image = (ImageView)itemView.findViewById(R.id.image);
            click = (MaterialRippleLayout)itemView.findViewById(R.id.click);

            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context , CreateEvent2.class);
                    Bundle b = new Bundle();
                    b.putInt("id" , Integer.parseInt(list.get(getAdapterPosition()).getId()));
                    b.putString("name" , list.get(getAdapterPosition()).getName());
                    i.putExtras(b);
                    context.startActivity(i);
                    ((CreateEvent1)context).finish();

                }
            });

        }
    }

}
