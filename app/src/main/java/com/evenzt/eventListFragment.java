package com.evenzt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ViewPOJO.Datum;
import ViewPOJO.viewBean;
import interfaces.createEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class eventListFragment extends Fragment {

    RecyclerView grid;
    GridLayoutManager layoutManager;
    eventListAdapter adapter;
    List<eventListBean> list;
    ProgressBar bar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutManager = new GridLayoutManager(getActivity() , 1);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list , null);

        bean b = (bean)getActivity().getApplicationContext();
        final String userId = String.valueOf(b.userId);

        list = new ArrayList<>();

       // adapter = new eventListAdapter(getActivity() , list);

        bar = (ProgressBar)view.findViewById(R.id.bar);
        grid = (RecyclerView)view.findViewById(R.id.eventListView);
        grid.setAdapter(adapter);
        grid.setLayoutManager(layoutManager);

        grid.setVisibility(View.GONE);


        final int[] pno = {1};






            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final createEvent cr = retrofit.create(createEvent.class);


            Call<viewBean> call = cr.getAllEvents(userId, String.valueOf(pno[0]));

            call.enqueue(new Callback<viewBean>() {
                @Override
                public void onResponse(Call<viewBean> call, Response<viewBean> response) {

                    if (response.body().getResponseMessage().equals("Event Data")) {


                        List<Datum> data = response.body().getData();

                        for (int i = 0; i < data.size(); i++) {
                            eventListBean item = new eventListBean();
                            item.setName("Name: " + data.get(i).getEventName());
                            item.setDate("Date: "+data.get(i).getStartTime());
                            item.setAddress("Venue: "+data.get(i).getVenue());
                            list.add(item);

                        }

                       // adapter.setGridData(list);


                        grid.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);

                        pno[0]++;
                    }
                    else {
                        pno[0] = 0;
                    }

                }

                @Override
                public void onFailure(Call<viewBean> call, Throwable t) {

                }
            });




        grid.setOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (pno[0] > 1)
                {
                    bar.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final createEvent cr = retrofit.create(createEvent.class);




                    Call<viewBean> call2 = cr.getAllEvents(userId , String.valueOf(pno[0]));

                    call2.enqueue(new Callback<viewBean>() {
                        @Override
                        public void onResponse(Call<viewBean> call, Response<viewBean> response) {

                            if (response.body().getResponseMessage().equals("Event Data"))
                            {

                                List<Datum> data = response.body().getData();

                                for (int i = 0 ; i < data.size() ; i++)
                                {
                                    eventListBean item = new eventListBean();
                                    item.setName("Name: "+data.get(i).getEventName());
                                    item.setDate("Date: "+data.get(i).getStartTime());
                                    item.setAddress("Venue: "+data.get(i).getVenue());
                                    list.add(item);

                                }


                              //  adapter.setGridData(list);
                                bar.setVisibility(View.GONE);

                                pno[0]++;

                            }
                            else
                            {
                                pno[0] = 1;
                                bar.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<viewBean> call, Throwable t) {

                        }
                    });

                }

            }
        });









        return view;
    }
}
