package com.evenzt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import reviewPOJO.Datum;

class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder>{


    Context context;
    List<Datum> list = new ArrayList<>();


    RatingAdapter(Context context, List<Datum> list)
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
        View view = inflater.inflate(R.layout.review_model , parent , false);
        return new RatingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LayerDrawable stars = (LayerDrawable) holder.rater.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.SRC_ATOP);

        Datum item = list.get(position);



        holder.name.setText(String.format("@%s", Html.fromHtml(String.format("<b>%s</b>.%s", item.getCommentTimelineUsername(), item.getCommentTime()))));
        //holder.rater.setRating(Float.parseFloat(item.get));

        holder.rater.setRating(Float.parseFloat(item.getRating()));

        //holder.rater.setVisibility(View.GONE);

        holder.comment.setText(item.getCommentText());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RatingBar rater;
        TextView name , comment;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.comment_name);
            comment = (TextView)itemView.findViewById(R.id.review_comment);
            rater = (RatingBar)itemView.findViewById(R.id.comment_rating);

        }
    }
}
