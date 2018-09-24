package com.evenzt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import friendsPOJO.Datum;


public class ChooseFriendsAdapter extends RecyclerView.Adapter<ChooseFriendsAdapter.ViewHolder> {


    List<Datum> list = new ArrayList<>();
    private List<String> cList = new ArrayList<>();
    Context context;


    ChooseFriendsAdapter(Context context, List<Datum> list)
    {
        this.context = context;
        this.list = list;
    }

    public void setGridData(List<Datum> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    List<String> getCheckedIds()
    {
        return this.cList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.choose_friend_list_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Datum item = list.get(position);

        final String id = item.getFriendId();

        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(item.getFriendThumbnailUrl() , holder.image);

        holder.name.setText(String.format("@%s", item.getFriendUsername()));

        holder.cheeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked)
                {
                    cList.remove(id);
                }
                else
                {
                    cList.add(id);
                }

            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.cheeck.isChecked())
                {
                    cList.remove(id);
                    holder.cheeck.toggle();
                }
                if (!holder.cheeck.isChecked())
                {
                    cList.add(id);
                    holder.cheeck.toggle();
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

        CircularImageView image;
        TextView name;
        CheckBox cheeck;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (CircularImageView)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.name);
            cheeck = (CheckBox)itemView.findViewById(R.id.check);

        }
    }
}
