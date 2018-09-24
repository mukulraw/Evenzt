package com.evenzt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.SmoothViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import PostedPOJO.Datum;
import PostedPOJO.PostedBean;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PostedEvents extends AppCompatActivity {


    TabLayout tabs;
    SmoothViewPager pager;
    static String userId;
    FragStatePagerAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_events);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        toolbar.setTitle("My Events");
        toolbar.setTitleTextColor(Color.WHITE);


        tabs = (TabLayout)findViewById(R.id.my_events_tabs);
        pager = (SmoothViewPager) findViewById(R.id.my_events_pager);

        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);


        adapter = new FragStatePagerAdapter(getSupportFragmentManager() , 2);
        pager.setAdapter(adapter);


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));




        tabs.addTab(tabs.newTab().setText("POSTED"));
        tabs.addTab(tabs.newTab().setText("JOINED"));

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());







            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }



    class FragStatePagerAdapter extends FragmentStatePagerAdapter {


        private int count;


        FragStatePagerAdapter(FragmentManager fm, int count) {
            super(fm);

            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new posted();
                case 1:
                    return new joined();
            }


            return null;

        }

        @Override
        public int getCount() {
            return count;
        }


    }


    public static class posted extends Fragment{



        int pno = 1;
        RecyclerView grid;
        GridLayoutManager manager;
        PostedAdapter adapter;
        ProgressBar progress;
        List<Datum> list;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            manager = new GridLayoutManager(getContext() , 1);


        }


        @Override
        public void onResume() {
            super.onResume();

            pno = 1;

            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<PostedBean> call = cr.myEvents(userId , String.valueOf(pno));

            call.enqueue(new Callback<PostedBean>() {
                @Override
                public void onResponse(Call<PostedBean> call, Response<PostedBean> response) {

                    if (response.body().getResponseMessage().equals("Event Data"))
                    {

                        list = new ArrayList<Datum>();

                        List<Datum> data = response.body().getData();

                        for (int i = 0 ; i < data.size() ; i++)
                        {
                            list.add(data.get(i));
                        }

                        adapter.setGridData(list);
                        //bar.setVisibility(View.GONE);

                        progress.setVisibility(View.GONE);

                        pno++;

                    }
                    else
                    {
                        pno = 1;
                        progress.setVisibility(View.GONE);
                        //bar.setVisibility(View.GONE);
                    }





                }

                @Override
                public void onFailure(Call<PostedBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });





        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.posted_events , container , false);
            list = new ArrayList<>();
            adapter = new PostedAdapter(getActivity() , list);

            progress = (ProgressBar)view.findViewById(R.id.progress);

            grid = (RecyclerView)view.findViewById(R.id.posted_list);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);





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




                        Call<PostedBean> call2 = cr.myEvents(userId , String.valueOf(pno));

                        call2.enqueue(new Callback<PostedBean>() {
                            @Override
                            public void onResponse(Call<PostedBean> call, Response<PostedBean> response) {

                                if (response.body().getResponseMessage().equals("Event Data"))
                                {

                                    List<Datum> data = response.body().getData();

                                    for (int i = 0 ; i < data.size() ; i++)
                                    {

                                        list.add(data.get(i));

                                    }

                                    adapter.setGridData(list);
                                    //bar.setVisibility(View.GONE);
                                    progress.setVisibility(View.GONE);
                                    pno++;

                                }
                                else
                                {progress.setVisibility(View.GONE);
                                    pno = 1;
                                    //bar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<PostedBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });

                    }



                }
            });




            return view;
        }






        class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.ViewHolder>{

            Context context;
            List<Datum> list = new ArrayList<>();



            String[] mon = {"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};



            PostedAdapter(Context context, List<Datum> list)
            {
                this.context = context;
                this.list = list;
            }

            void setGridData(List<Datum> list)
            {
                this.list = list;
                notifyDataSetChanged();
            }


            @Override
            public PostedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View v = inflater.inflate(R.layout.posted_list_model , parent , false);
                return new PostedAdapter.ViewHolder(v);

            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                try {


                    Datum item = list.get(position);

                    String currentString = item.getStartTime();

                    String[] separate1 = currentString.split("\\s");

                    String asd = separate1[0];

                    String[] sepa2  = asd.split("-");

                    holder.name.setText(item.getEventName());
                    holder.date.setText(sepa2[2]);
                    holder.month.setText(mon[Integer.parseInt(sepa2[1]) - 1]);
                    holder.venue.setText(item.getCategoryName());

                    Log.d("asdasdasdasdadasdasd" , sepa2[0]);


                }catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }



            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{


                TextView date , month , name , venue;


                ViewHolder(View itemView) {
                    super(itemView);

                    date = (TextView)itemView.findViewById(R.id.posted_date);
                    month = (TextView)itemView.findViewById(R.id.posted_month);
                    name = (TextView)itemView.findViewById(R.id.posted_name);
                    venue = (TextView)itemView.findViewById(R.id.posted_venue);


                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(context , MyEventsDetails.class);
                            Bundle b = new Bundle();
                            b.putString("eventId" , list.get(getAdapterPosition()).getEventId());
                            b.putString("name" , list.get(getAdapterPosition()).getEventName());
                            i.putExtras(b);
                            context.startActivity(i);

                        }
                    });



                }
            }
        }






    }



    public static class joined extends Fragment{
        int pno = 1;
        RecyclerView grid;
        GridLayoutManager manager;
        ProgressBar progress;
        PostedAdapter adapter;
        List<Datum> list;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            manager = new GridLayoutManager(getContext() , 1);


        }


        @Override
        public void onResume() {
            super.onResume();

            pno = 1;

            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<PostedBean> call = cr.joinedEvents(userId , String.valueOf(pno));

            call.enqueue(new Callback<PostedBean>() {
                @Override
                public void onResponse(Call<PostedBean> call, Response<PostedBean> response) {

                    if (response.body().getResponseMessage().equals("Event Data"))
                    {

                        list = new ArrayList<Datum>();

                        List<Datum> data = response.body().getData();

                        for (int i = 0 ; i < data.size() ; i++)
                        {

                            Log.d("asdasdasdPosted" , String.valueOf(i));
                            list.add(data.get(i));

                        }

                        adapter.setGridData(list);

                        progress.setVisibility(View.GONE);
                        //bar.setVisibility(View.GONE);

                        pno++;

                    }
                    else
                    {progress.setVisibility(View.GONE);
                        pno = 1;
                        //bar.setVisibility(View.GONE);
                    }





                }

                @Override
                public void onFailure(Call<PostedBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });


        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.joined_events , container , false);
            list = new ArrayList<>();
            adapter = new PostedAdapter(getActivity() , list);

            progress = (ProgressBar)view.findViewById(R.id.progress);

            grid = (RecyclerView)view.findViewById(R.id.posted_list);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);







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




                        Call<PostedBean> call2 = cr.myEvents(userId , String.valueOf(pno));

                        call2.enqueue(new Callback<PostedBean>() {
                            @Override
                            public void onResponse(Call<PostedBean> call, Response<PostedBean> response) {

                                if (response.body().getResponseMessage().equals("Event Data"))
                                {

                                    List<Datum> data = response.body().getData();

                                    for (int i = 0 ; i < data.size() ; i++)
                                    {

                                        list.add(data.get(i));

                                    }

                                    adapter.setGridData(list);
                                    //bar.setVisibility(View.GONE);
                                    progress.setVisibility(View.GONE);
                                    pno++;

                                }
                                else
                                {progress.setVisibility(View.GONE);
                                    pno = 1;
                                    //bar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<PostedBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });

                    }



                }
            });




            return view;
        }








        class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.ViewHolder>{

            Context context;
            List<Datum> list = new ArrayList<>();



            String[] mon = {"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};



            PostedAdapter(Context context, List<Datum> list)
            {
                this.context = context;
                this.list = list;
            }

            void setGridData(List<Datum> list)
            {
                this.list = list;
                notifyDataSetChanged();
            }


            @Override
            public PostedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View v = inflater.inflate(R.layout.posted_list_model , parent , false);
                return new ViewHolder(v);

            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                try{


                    Datum item = list.get(position);

                    String currentString = item.getStartTime();

                    String[] separate1 = currentString.split("\\s");

                    String asd = separate1[0];

                    String[] sepa2  = asd.split("-");

                    holder.name.setText(item.getEventName());
                    holder.date.setText(sepa2[2]);
                    holder.month.setText(mon[Integer.parseInt(sepa2[1]) - 1]);
                    holder.venue.setText(item.getCategoryName());

                    Log.d("asdasdasdasdadasdasd" , sepa2[0]);


                }catch (Exception e)
                {
                    e.printStackTrace();
                }



            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{


                TextView date , month , name , venue;


                ViewHolder(View itemView) {
                    super(itemView);

                    date = (TextView)itemView.findViewById(R.id.posted_date);
                    month = (TextView)itemView.findViewById(R.id.posted_month);
                    name = (TextView)itemView.findViewById(R.id.posted_name);
                    venue = (TextView)itemView.findViewById(R.id.posted_venue);


                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Activity activity = (Activity)context;

                            Intent i = new Intent(context , EventDetailsScreen.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("eventId" , list.get(getAdapterPosition()).getEventId());
                            activity.startActivity(i);

                        }
                    });



                }
            }
        }








    }




}
