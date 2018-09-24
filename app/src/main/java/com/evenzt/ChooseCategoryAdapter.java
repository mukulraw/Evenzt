package com.evenzt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


class ChooseCategoryAdapter extends RecyclerView.Adapter<ChooseCategoryAdapter.ViewHolder> {


    private List<String> list = new ArrayList<>();
    List<String> cList = new ArrayList<>();
    List<nearbyPOJO.Datum> l2 = new ArrayList<>();
    private Context context;
    private int selectedid = -1;
    private String selectedName = "";
    String iidd;

    ChooseCategoryAdapter(Context context , List<String> list , List<nearbyPOJO.Datum> l2)
    {
        this.context = context;
        this.list = list;
        this.l2 = l2;
    }


    public void setGridData(List<String> list)
    {
        cList = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }


    List<String> getSelectedId()
    {
        return this.cList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.choose_model , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final String item = list.get(position);

        holder.cat.setText(item);

        for (int i = 0 ; i < l2.size() ; i++)
        {
            if (Objects.equals(l2.get(i).getCategoryName().toLowerCase(), item.toLowerCase()))
            {
                holder.check.setChecked(true);
                cList.add(list.get(holder.getAdapterPosition()).toLowerCase());
            }
        }


        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (holder.check.isChecked())
                {
                    int flag = 0;

                    for (int i = 0 ; i < cList.size() ; i++)
                    {
                        if (Objects.equals(list.get(holder.getAdapterPosition()).toLowerCase(), cList.get(i).toLowerCase()))
                        {
                            flag++;
                        }
                    }

                    if (flag == 0)
                    {
                        cList.add(list.get(holder.getAdapterPosition()).toLowerCase());
                    }



                    //compoundButton.toggle();
                }
                else
                {
                    cList.remove(list.get(holder.getAdapterPosition()).toLowerCase());
                    //compoundButton.toggle();
                }


            }
        });



/*
        holder.cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.check.isChecked())
                {
                    cList.remove(item);
                    holder.check.toggle();
                }
                if (!holder.check.isChecked())
                {
                    cList.add(item);
                    holder.check.toggle();
                }

            }
        });
*/



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView cat;
        CheckBox check;
        //ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            cat = (TextView)itemView.findViewById(R.id.filter_name);
            check = (CheckBox)itemView.findViewById(R.id.filter_check);

        }


    }

}
