package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import userSearchPOJO.Datum;
import userSearchPOJO.userSearchBean;

public class UserSearchResult extends AppCompatActivity {


    EditText searchBox;
    RecyclerView grid;
    GridLayoutManager manager;
    UsersAdapter adapter;
    List<Datum> list;
    String userId;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_result);

        progress = (ProgressBar)findViewById(R.id.progress);

        searchBox = (EditText)findViewById(R.id.search_text);
        grid = (RecyclerView)findViewById(R.id.search_list);

        list = new ArrayList<>();

        adapter = new UsersAdapter(this , list);

        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);

        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                progress.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://evenzt.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final eventDetails cr = retrofit.create(eventDetails.class);

                Call<userSearchBean> call = cr.searchUser(userId , charSequence.toString());

                call.enqueue(new Callback<userSearchBean>() {
                    @Override
                    public void onResponse(Call<userSearchBean> call, Response<userSearchBean> response) {


                        List<Datum> data = response.body().getData();

                        list.clear();

                        for (int i = 0 ; i < data.size() ; i++)
                        {

                            if (!Objects.equals(userId, data.get(i).getId()))
                            {
                                list.add(data.get(i));
                            }

                        }


                        adapter.setGridData(list);

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<userSearchBean> call, Throwable throwable) {
                        progress.setVisibility(View.GONE);
                    }
                });



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
    {

        List<Datum> list = new ArrayList<>();
        Context context;

        public UsersAdapter(Context context , List<Datum> list)
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
            View v = inflater.inflate(R.layout.users_list_model , null);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Datum item = list.get(position);

            if (!Objects.equals(userId, item.getId()))
            {
                ImageLoader imageLoader = ImageLoader.getInstance();

                imageLoader.displayImage(item.getImage() , holder.profile);
                holder.name.setText(String.format("@%s", item.getUsername()));
                if (!item.getIsFriend())
                {
                    holder.isFriend.setVisibility(View.GONE);
                }
                else
                {
                    holder.isFriend.setVisibility(View.VISIBLE);
                }

                if (position == list.size()-1)
                {
                    holder.line.setVisibility(View.GONE);
                }
                else
                {
                    holder.line.setVisibility(View.VISIBLE);
                }
            }




        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            CircularImageView profile;
            TextView name , line;

            ImageView isFriend;

            public ViewHolder(View itemView) {
                super(itemView);
                profile = (CircularImageView)itemView.findViewById(R.id.users_list_profile);
                name = (TextView)itemView.findViewById(R.id.users_list_name);
                isFriend = (ImageView) itemView.findViewById(R.id.users_list_is_friend);
                line = (TextView)itemView.findViewById(R.id.line);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(context , UserDetailScreen.class);
                        i.putExtra("frien" , list.get(getAdapterPosition()).getId());
                        context.startActivity(i);

                    }
                });

            }
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(0,0);
    }
}
