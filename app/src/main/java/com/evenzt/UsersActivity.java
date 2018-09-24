package com.evenzt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import usersPOJO.Datum;
import usersPOJO.usersBean;

public class UsersActivity extends AppCompatActivity {



    List<Datum> list;
    RecyclerView grid;
    GridLayoutManager manager;
    UsersAdapter adapter;
    ProgressBar progress;
    int pno = 1;
    Toolbar toolbar;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress = (ProgressBar)findViewById(R.id.progress);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        toolbar.setTitle("Users");
        toolbar.setTitleTextColor(Color.WHITE);
        grid = (RecyclerView)findViewById(R.id.users_list);

        list = new ArrayList<>();

        adapter = new UsersAdapter(this , list);


        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);


        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu , menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search)
        {
            Intent intent = new Intent(getApplicationContext() , UserSearchResult.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }



        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        list = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<usersBean> call = cr.getUsers(userId , String.valueOf(pno));

        call.enqueue(new Callback<usersBean>() {
            @Override
            public void onResponse(Call<usersBean> call, Response<usersBean> response) {


                List<Datum> data = response.body().getData();

                if (data.size()>0)
                {

                    for (int i = 0 ; i < data.size() ; i++)
                    {

                        if (!Objects.equals(userId, data.get(i).getId()))
                        {
                            list.add(data.get(i));
                        }

                    }

                    adapter.setGridData(list);
                    //bar.setVisibility(View.GONE);

                    pno++;


                    progress.setVisibility(View.GONE);


                }
                else
                {progress.setVisibility(View.GONE);
                    pno = 1;
                }







            }

            @Override
            public void onFailure(Call<usersBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        grid.setOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {


                if (pno > 1)
                {
                    //bar.setVisibility(View.VISIBLE);

                    //list = new ArrayList<Datum>();
                    progress.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final eventDetails cr = retrofit.create(eventDetails.class);




                    Call<usersBean> call2 = cr.getUsers(userId , String.valueOf(pno));

                    call2.enqueue(new Callback<usersBean>() {
                        @Override
                        public void onResponse(Call<usersBean> call, Response<usersBean> response) {

                            if (response.body().getData().size()>0)
                            {

                                List<Datum> data = response.body().getData();

                                for (int i = 0 ; i < data.size() ; i++)
                                {

                                    if (!Objects.equals(userId, data.get(i).getId()))
                                    {
                                        list.add(data.get(i));
                                    }

                                }

                                adapter.setGridData(list);
                                //bar.setVisibility(View.GONE);
                                progress.setVisibility(View.GONE);
                                pno++;

                            }
                            else
                            {
                                progress.setVisibility(View.GONE);
                                pno = 1;
                                //bar.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<usersBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }



            }
        });




    }





    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

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
        public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.users_list_model , null);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.setIsRecyclable(false);

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






}
