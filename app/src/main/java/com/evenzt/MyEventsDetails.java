package com.evenzt;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jsibbold.zoomage.ZoomageView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acceptPOJO.acceptBean;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import eventDetailPOJO.ImageUrl;
import eventDetailPOJO.eventDetailBean;
import eventRequestPOJO.erBean;
import interfaces.createEvent;
import interfaces.eventDetails;
import joinedUsersPOJO.joinedUsersBean;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import removeImagePOJO.removeImageBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyEventsDetails extends AppCompatActivity {

   // TextView toolbarTitle;
    TabLayout tabs;
    SmoothViewPager pager;
    static String userId;
    static FragStatePagerAdapter adapter;
    static String nam;
    static String eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events_details);



        eventId = getIntent().getExtras().getString("eventId");


        Log.d("asasdasdasdasd" , eventId);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        toolbar.setTitleTextColor(Color.WHITE);


        toolbar.setTitle(getIntent().getExtras().getString("name"));

        nam = getIntent().getExtras().getString("name");

        tabs = (TabLayout)findViewById(R.id.my_events_tabs);
        pager = (SmoothViewPager) findViewById(R.id.my_events_pager);

        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);


        adapter = new FragStatePagerAdapter(getSupportFragmentManager() , 3);
        pager.setAdapter(adapter);


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));



        tabs.addTab(tabs.newTab().setText("EVENT DETAILS"));
        tabs.addTab(tabs.newTab().setText("ALL REQUESTS"));
        tabs.addTab(tabs.newTab().setText("PEOPLE JOINED"));


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
                    return new eventDetail();
                case 1:
                    return new posted();
                case 2:
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
        erAdapter adapter;
        List<eventRequestPOJO.Datum> list;
        TextView title;

        ProgressBar progress;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            manager = new GridLayoutManager(getContext() , 1);


        }


        @Override
        public void onResume() {
            super.onResume();




            callAPI();



        }


        public void callAPI()
        {
            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<erBean> call = cr.getEventRequests(userId , eventId);



            call.enqueue(new Callback<erBean>() {
                @Override
                public void onResponse(Call<erBean> call, Response<erBean> response) {

                    //if (response.body().getResponseMessage().equals("Event Data"))
                    //{

                        List<eventRequestPOJO.Datum> data = response.body().getData();

                        adapter.setGridData(data);

                        progress.setVisibility(View.GONE);

                        //bar.setVisibility(View.GONE);

                        pno++;

                    //}
                    //else
                    //{
                    //    pno = 1;
                    //    progress.setVisibility(View.GONE);
                        //bar.setVisibility(View.GONE);
                    //}





                }

                @Override
                public void onFailure(Call<erBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.posted_events , container , false);

            progress = (ProgressBar)view.findViewById(R.id.progress);

            title = (TextView)view.findViewById(R.id.title);

            title.setVisibility(View.GONE);

            list = new ArrayList<>();
            adapter = new erAdapter(getActivity() , list , eventId);



            grid = (RecyclerView)view.findViewById(R.id.posted_list);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);







            return view;
        }





        class erAdapter extends RecyclerView.Adapter<erAdapter.ViewHolder> {

            Context context;
            List<eventRequestPOJO.Datum> list = new ArrayList<>();
            String eventId;

            public erAdapter(Context context , List<eventRequestPOJO.Datum> list , String eventId)
            {
                this.context = context;
                this.list = list;
                this.eventId = eventId;
            }


            public void setGridData(List<eventRequestPOJO.Datum> list)
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
            public void onBindViewHolder(final ViewHolder holder, final int position) {

                eventRequestPOJO.Datum item = list.get(position);

                holder.name.setText(String.format("@%s", item.getUsername()));

                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getImage() , holder.image);

                holder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progress.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<acceptBean> call = cr.acceptEvent(list.get(position).getId() , eventId);

                        call.enqueue(new Callback<acceptBean>() {
                            @Override
                            public void onResponse(Call<acceptBean> call, Response<acceptBean> response) {

                                Log.d("asdasdasd" , "response");

                                callAPI();


                            }

                            @Override
                            public void onFailure(Call<acceptBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                                Log.d("error" , t.toString());
                            }
                        });


                    }
                });



                holder.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progress.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<acceptBean> call = cr.rejectEvent(list.get(position).getId() , eventId);



                        call.enqueue(new Callback<acceptBean>() {
                            @Override
                            public void onResponse(Call<acceptBean> call, Response<acceptBean> response) {

                                callAPI();

                            }

                            @Override
                            public void onFailure(Call<acceptBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });


                    }
                });



            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{


                CircularImageView image;
                TextView name;
                TextView accept , reject;


                public ViewHolder(View itemView) {
                    super(itemView);
                    image = (CircularImageView)itemView.findViewById(R.id.image);
                    name = (TextView)itemView.findViewById(R.id.name);

                    accept = (TextView)itemView.findViewById(R.id.accept);
                    reject = (TextView)itemView.findViewById(R.id.reject);













                }
            }
        }







    }



    public static class joined extends Fragment{
        int pno = 1;
        RecyclerView grid;
        GridLayoutManager manager;
        JoinedAdapter adapter;
        private List<joinedUsersPOJO.Datum> list;
        TextView title;
        ProgressBar progress;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            manager = new GridLayoutManager(getContext() , 1);


        }


        @Override
        public void onResume() {
            super.onResume();

            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<joinedUsersBean> call = cr.getJoinedUsers(userId , eventId);

            call.enqueue(new Callback<joinedUsersBean>() {
                @Override
                public void onResponse(Call<joinedUsersBean> call, Response<joinedUsersBean> response) {

                    list = response.body().getData();
                    adapter.setGridData(list);
                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<joinedUsersBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });




        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.posted_events , container , false);

            progress = (ProgressBar)view.findViewById(R.id.progress);

            title = (TextView)view.findViewById(R.id.title);

            title.setVisibility(View.GONE);

            list = new ArrayList<>();
            adapter = new JoinedAdapter(getContext() , list);



            grid = (RecyclerView)view.findViewById(R.id.posted_list);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);







            return view;
        }
    }



    public static class eventDetail extends Fragment
    {

        static TextView additionalHide;

        static AutoScrollViewPager eventImage;
        static TextView eventName;

        static CircleIndicator indicator;
        static TextView address;
        private String mCurrentPhotoPath;

        LinearLayout tags;

        static String addr1 = "";

        LinearLayout main;
        static TextView sTime;

        private final int PICK_IMAGE_REQUEST = 2;

        static ProgressBar bar;
        TextView lastDate , startDate;
        Button join;

        TextView host;
        Bitmap bmp;


        static String hostId;

        static String hostName;

        static PagerAdapter1 adapter;


        String[] months = new String[]{"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};





        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.my_event_detail , container , false);


            tags = (LinearLayout)view.findViewById(R.id.tags);



            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
            ImageLoader.getInstance().init(config);


            sTime = (TextView)view.findViewById(R.id.start_time);






            bar = (ProgressBar)view.findViewById(R.id.progress_bar2);

            address = (TextView)view.findViewById(R.id.address);

            main = (LinearLayout)view.findViewById(R.id.activity_main);

            main.setVisibility(View.GONE);


            host = (TextView)view.findViewById(R.id.user);

            startDate = (TextView)view.findViewById(R.id.date);

            eventName = (TextView)view.findViewById(R.id.name);

            lastDate = (TextView)view.findViewById(R.id.lastDate);

            join = (Button)view.findViewById(R.id.join);


            indicator = (CircleIndicator)view.findViewById(R.id.indicator);

            eventImage = (AutoScrollViewPager) view.findViewById(R.id.event_image);

            additionalHide = (TextView)view.findViewById(R.id.additional_hide);

            List<ImageUrl> blist = new ArrayList<ImageUrl>();

            adapter = new PagerAdapter1(getChildFragmentManager() , blist);

            //eventImage.startAutoScroll();

            bar.setVisibility(View.VISIBLE);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<eventDetailBean> call = cr.getAllEvents(eventId , userId);

            call.enqueue(new Callback<eventDetailBean>() {
                @Override
                public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {




                    if (response.body().getData().getImageUrl().size()>0)
                    {
                        if (response.body().getData().getImageUrl().size()>0)
                        {

                            List<ImageUrl> blist = new ArrayList<ImageUrl>();

                            blist = response.body().getData().getImageUrl();

                            adapter.setData(blist);

                            eventImage.setAdapter(adapter);

                            eventImage.setOffscreenPageLimit(blist.size()- 1);

                            indicator.setViewPager(eventImage);

                        }
                    }

                    String t = response.body().getData().getKeywords();

                    List<String> tl = Arrays.asList(t.split(","));

                    if (t.length() > 0)
                    {
                        for (int i = 0 ; i < tl.size() ; i++)
                        {
                            View v = inflater.inflate(R.layout.tag , null);

                            TextView tv = (TextView)v.findViewById(R.id.tag);

                            tv.setText(tl.get(i));

                            tags.addView(v);

                        }
                    }
                    else
                    {
                        tags.setVisibility(View.GONE);
                    }


                    hostId = String.valueOf(response.body().getData().getEventTimelineId());

                    hostName = String.valueOf(response.body().getData().getEventTimelineUsername());


                    additionalHide.setText(response.body().getData().getEventDesc());
                    eventName.setText(response.body().getData().getEventName());


                    try
                    {
                        String ss1 = response.body().getData().getEventTime();
                        String ss2 = response.body().getData().getEventEnd();

                        String[] spl1 = ss1.split(":");
                        String[] spl2 = ss2.split(":");

                        int t1 = Integer.parseInt(spl1[0]);
                        int t2 = Integer.parseInt(spl2[0]);

                        String stime = "";
                        String etime = "";

                        if (t1 >= 12)
                        {
                            stime = String.valueOf(t1-12) + ":" + spl1[1] + " PM";
                        }
                        else
                        {
                            stime = String.valueOf(12-t1) + ":" + spl1[1] + " AM";
                        }

                        if (t2 >= 12)
                        {
                            etime = String.valueOf(t2-12) + ":" + spl2[1] + " PM";
                        }
                        else
                        {
                            etime = String.valueOf(12-t2) + ":" + spl2[1] + " AM";
                        }

                        sTime.setText(String.format("%s - %s", stime, etime));
                    }
                    catch (NumberFormatException e)
                    {
                        e.printStackTrace();
                    }


                    addr1 = response.body().getData().getVenue();

                    String addr = response.body().getData().getVenue();

                    String[] separate2 = addr.split(",");




                    try {
                        address.setText(String.format("%s\n%s%s", response.body().getData().getVenue(), getString(R.string.city3), response.body().getData().getCity()));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }



                    //address.setText(response.body().getData().getVenue());

                    String currentString = response.body().getData().getEndTime();

                    String[] separate1 = currentString.split("\\s");


                    String[] s1 = separate1[0].split("-");

                    int n = Integer.parseInt(s1[1]);

                    lastDate.setText(String.format("%s%s %s %s )", getString(R.string.event_end_date1), s1[2], months[n - 1], s1[0]));

                    String currentString1 = response.body().getData().getStartTime();

                    String[] separate3 = currentString1.split("\\s");

                    String[] s2 = separate3[0].split("-");

                    int m = Integer.parseInt(s2[1]);

                    startDate.setText(String.format("%s %s %s", s2[2], months[m - 1], s2[0]));

                    host.setText(String.format("%s@%s", getString(R.string.created_by_2), response.body().getData().getEventTimelineUsername()));

                    host.setText(String.format("@%s", Html.fromHtml(String.format("%s%s</u>", getString(R.string.created_by3), response.body().getData().getEventTimelineUsername()))));

                    Log.d("asdasdasd" , response.body().getData().getEventNumJoines());


                    if (userId.equals(response.body().getData().getEventTimelineId()))
                    {
                        join.setText(R.string.delete_edit1);
                    }




                    bar.setVisibility(View.GONE);
                    main.setVisibility(View.VISIBLE);






                }

                @Override
                public void onFailure(Call<eventDetailBean> call, Throwable t) {

                }
            });

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.delete_dialog);

                    dialog.show();

                    TextView delete = (TextView)dialog.findViewById(R.id.delete);
                    TextView edit = (TextView)dialog.findViewById(R.id.edit);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            Intent intent = new Intent();

                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);

                            dialog.dismiss();

                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://evenzt.com/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            final eventDetails cr = retrofit.create(eventDetails.class);

                            Call<deleteBean> call = cr.deleteEvent(userId , eventId);

                            call.enqueue(new Callback<deleteBean>() {
                                @Override
                                public void onResponse(Call<deleteBean> call, Response<deleteBean> response) {

                                    dialog.dismiss();

                                    ((MyEventsDetails)getContext()).finish();

                                    Toast.makeText(getContext() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<deleteBean> call, Throwable throwable) {

                                }
                            });


                        }
                    });

                }
            });


            return view;
        }

        private class PagerAdapter1 extends FragmentStatePagerAdapter
        {

            List<ImageUrl> list = new ArrayList<>();

            public void setData(List<ImageUrl> list)
            {
                this.list = list;
                notifyDataSetChanged();
            }

            PagerAdapter1(FragmentManager fm , List<ImageUrl> list) {
                super(fm);
                this.list = list;
            }

            @Override
            public Fragment getItem(int position) {

                pages p = new pages();
                Bundle b = new Bundle();
                b.putString("image" , list.get(position).getUrl());
                b.putString("id" , list.get(position).getId());
                p.setArguments(b);
                return p;

            }

            @Override
            public int getCount() {
                return list.size();
            }
        }


        public static class pages extends Fragment
        {

            String url = "";
            String id = "";
            Bitmap bmp;
            ImageView image;
            TextView remove;
            ProgressBar progress;

            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.pager_images , container , false);
                url = getArguments().getString("image");
                id = getArguments().getString("id");

                image = (ImageView)view.findViewById(R.id.image);
                remove = (TextView)view.findViewById(R.id.remove);
                progress = (ProgressBar)view.findViewById(R.id.progress);

                ImageLoader loader = ImageLoader.getInstance();

                loader.loadImage(url, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        bmp = loadedImage;
                        image.setImageBitmap(bmp);

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });


                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.remove_dialog);
                        dialog.show();


                        TextView yes = (TextView)dialog.findViewById(R.id.yes);
                        TextView no = (TextView)dialog.findViewById(R.id.no);

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                progress.setVisibility(View.VISIBLE);

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                final eventDetails cr = retrofit.create(eventDetails.class);


                                Call<removeImageBean> call = cr.removeImage(userId , id);

                                call.enqueue(new Callback<removeImageBean>() {
                                    @Override
                                    public void onResponse(Call<removeImageBean> call, Response<removeImageBean> response) {


                                        getActivity().finish();


                                        Intent i = new Intent(getContext() , MyEventsDetails.class);
                                        Bundle b = new Bundle();
                                        b.putString("eventId" , eventId);
                                        b.putString("name" , nam);
                                        i.putExtras(b);
                                        startActivity(i);


                                        //refresh();


                                        progress.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<removeImageBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });

                            }
                        });

                    }
                });


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Dialog dialog = new Dialog(getActivity() , R.style.DialogTheme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.image_dialog);

                        dialog.show();

                        ZoomageView imagev = (ZoomageView) dialog.findViewById(R.id.image);

                        imagev.setImageBitmap(bmp);

                    }
                });

                //loader.displayImage(url , image);

                return view;
            }
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {


            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(String.valueOf(data.getData())));

                    //browse_image.setImageBitmap(bitmap);
                    Uri selectedImageUri = data.getData();

                    //bean.setBrowse(bitmap);





                    mCurrentPhotoPath = getPath(getContext() , selectedImageUri);



                    //path = selectedImageUri.getPath();

                    //path = String.valueOf(selectedImageUri);
                    Log.d("asdasdasd" , String.valueOf(selectedImageUri));


                    Log.d("asdasdasd" , mCurrentPhotoPath);



                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.edit_image_layout);

                    TextView no = (TextView)dialog.findViewById(R.id.no);
                    TextView yes = (TextView)dialog.findViewById(R.id.yes);

                    dialog.show();

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //editImage();

                            bar.setVisibility(View.VISIBLE);

                            final Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://evenzt.com/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            createEvent cr = retrofit.create(createEvent.class);

                            File file = null;



                            MultipartBody.Part body = null;
                            try {

                                if (mCurrentPhotoPath.length()>0)
                                {
                                    file = new File(mCurrentPhotoPath);
                                }

                                RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                                body = MultipartBody.Part.createFormData("video" , file.getName(), reqFile);

                            }catch (NullPointerException e)
                            {
                                e.printStackTrace();
                            }

                            Call<eventDetailBean> call = cr.editEvent(userId , body , eventId);

                            Log.d("add Image" , userId + "," + eventId);

                            call.enqueue(new Callback<eventDetailBean>() {
                                @Override
                                public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {



                                    final eventDetails cr = retrofit.create(eventDetails.class);

                                    Call<eventDetailBean> call2 = cr.getAllEvents(eventId , userId);

                                    call2.enqueue(new Callback<eventDetailBean>() {
                                        @Override
                                        public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {


                                            if (response.body().getData().getImageUrl().size()>0)
                                            {

                                                List<ImageUrl> blist;

                                                blist = response.body().getData().getImageUrl();

                                                PagerAdapter1 adapter = new PagerAdapter1(getChildFragmentManager() , blist);

                                                eventImage.setAdapter(adapter);

                                                eventImage.setOffscreenPageLimit(blist.size() - 1);

                                                indicator.setViewPager(eventImage);

                                            }



                                            bar.setVisibility(View.GONE);
                                            main.setVisibility(View.VISIBLE);






                                        }

                                        @Override
                                        public void onFailure(Call<eventDetailBean> call, Throwable t) {

                                        }
                                    });


                                }

                                @Override
                                public void onFailure(Call<eventDetailBean> call, Throwable t) {

                                }
                            });

                            dialog.dismiss();

                        }
                    });






                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else
            {
                super.onActivityResult(requestCode, resultCode, data);
            }


        }


        public void editImage()
        {
            bar.setVisibility(View.VISIBLE);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            createEvent cr = retrofit.create(createEvent.class);

            File file = null;



            MultipartBody.Part body = null;
            try {

                if (mCurrentPhotoPath.length()>0)
                {
                    file = new File(mCurrentPhotoPath);
                }

                RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                body = MultipartBody.Part.createFormData("video" , file.getName(), reqFile);

            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            Call<eventDetailBean> call = cr.editEvent(userId , body , eventId);

            Log.d("add Image" , userId + "," + eventId);

            call.enqueue(new Callback<eventDetailBean>() {
                @Override
                public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {



                    final eventDetails cr = retrofit.create(eventDetails.class);

                    Call<eventDetailBean> call2 = cr.getAllEvents(eventId , userId);

                    call2.enqueue(new Callback<eventDetailBean>() {
                        @Override
                        public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {


                            if (response.body().getData().getImageUrl().size()>0)
                            {

                                List<ImageUrl> blist;

                                blist = response.body().getData().getImageUrl();

                                PagerAdapter1 adapter = new PagerAdapter1(getChildFragmentManager() , blist);

                                eventImage.setAdapter(adapter);

                                eventImage.setOffscreenPageLimit(blist.size() - 1);

                                indicator.setViewPager(eventImage);

                            }



                            bar.setVisibility(View.GONE);
                            main.setVisibility(View.VISIBLE);






                        }

                        @Override
                        public void onFailure(Call<eventDetailBean> call, Throwable t) {

                        }
                    });


                }

                @Override
                public void onFailure(Call<eventDetailBean> call, Throwable t) {

                }
            });



        }

        private static String getPath(final Context context, final Uri uri)
        {
            final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
                    // ExternalStorageProvider
                    if (isExternalStorageDocument(uri)) {
                        final String docId = DocumentsContract.getDocumentId(uri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        if ("primary".equalsIgnoreCase(type)) {
                            return Environment.getExternalStorageDirectory() + "/" + split[1];
                        }

                        // TODO handle non-primary volumes
                    }
                    // DownloadsProvider
                    else if (isDownloadsDocument(uri)) {

                        final String id = DocumentsContract.getDocumentId(uri);
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                        return getDataColumn(context, contentUri, null, null);
                    }
                    // MediaProvider
                    else if (isMediaDocument(uri)) {
                        final String docId = DocumentsContract.getDocumentId(uri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        final String selection = "_id=?";
                        final String[] selectionArgs = new String[] {
                                split[1]
                        };

                        return getDataColumn(context, contentUri, selection, selectionArgs);
                    }
                }
                // MediaStore (and general)
                else if ("content".equalsIgnoreCase(uri.getScheme())) {
                    return getDataColumn(context, uri, null, null);
                }
                // File
                else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
            }

            return null;
        }

        private static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        private static String getDataColumn(Context context, Uri uri, String selection,
                                            String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }


        public static void refresh()
        {
            bar.setVisibility(View.VISIBLE);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<eventDetailBean> call = cr.getAllEvents(eventId , userId);

            call.enqueue(new Callback<eventDetailBean>() {
                @Override
                public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {




                    if (response.body().getData().getImageUrl().size()>0)
                    {
                        if (response.body().getData().getImageUrl().size()>0)
                        {

                            List<ImageUrl> blist = new ArrayList<ImageUrl>();

                            blist = response.body().getData().getImageUrl();

                            //PagerAdapter1 adapter = new PagerAdapter1( , blist);

                            //eventImage.setAdapter(adapter);

                            //eventImage.setOffscreenPageLimit(blist.size()- 1);

                            adapter.setData(blist);

                            //indicator.setViewPager(eventImage);

                        }
                    }

                    bar.setVisibility(View.GONE);


                }

                @Override
                public void onFailure(Call<eventDetailBean> call, Throwable t) {
                    bar.setVisibility(View.GONE);
                }
            });
        }


    }





}
