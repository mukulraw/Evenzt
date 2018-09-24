package com.evenzt;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsibbold.zoomage.ZoomageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import eventDetailPOJO.ImageUrl;
import eventDetailPOJO.eventDetailBean;
import interfaces.createEvent;
import interfaces.eventDetails;
import joinPOJO.joinBean;
import leavePOJO.leaveBean;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import reportPOJO.reportBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sendMessagePOJO.sendMessageBean;

public class EventDetailsScreen extends AppCompatActivity {

    ScrollView scroll;
    TextView additional , additionalHide , membersJoined;

    AutoScrollViewPager eventImage;
    TextView eventName;

    CircleIndicator indicator;

    TextView likeCount;

    TextView address;
    private String mCurrentPhotoPath;

    LinearLayout tags;

    String addr1 = "";

    LinearLayout main;

    TextView submitQuery;

    TextView sTime;

    ImageButton like;

    private final int PICK_IMAGE_REQUEST = 2;

    String eventId;
    ProgressBar bar;
    TextView lastDate , startDate;
    Button join;

    TextView host;



    Boolean isLiked = false;

    Bitmap bmp;


    String hostId;

    String hostName;


    Toolbar toolbar;
    TextView title;

    SharedPreferences pref;

    ImageButton share;

    TextView report;

    String userId;

    String lat = "";
    String lng = "";

    String[] months = new String[]{"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_screen);

        pref = getSharedPreferences("myPref" , Activity.MODE_PRIVATE);

        tags = (LinearLayout)findViewById(R.id.tags);

        likeCount = (TextView)findViewById(R.id.like_count);

        share = (ImageButton)findViewById(R.id.share);
        report = (TextView)findViewById(R.id.report);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);


        sTime = (TextView)findViewById(R.id.start_time);



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


        like = (ImageButton)findViewById(R.id.like);


        submitQuery = (TextView)findViewById(R.id.submit_query);





        bean b = (bean)getApplicationContext();

        userId = pref.getString("id" , "0");
        eventId = getIntent().getStringExtra("eventId");

        bar = (ProgressBar)findViewById(R.id.progress_bar2);

        address = (TextView)findViewById(R.id.address);

        main = (LinearLayout)findViewById(R.id.activity_main);

        main.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);

        host = (TextView)findViewById(R.id.user);

        startDate = (TextView)findViewById(R.id.date);

        eventName = (TextView)findViewById(R.id.name);

        lastDate = (TextView)findViewById(R.id.lastDate);

        join = (Button)findViewById(R.id.join);


        indicator = (CircleIndicator)findViewById(R.id.indicator);

        eventImage = (AutoScrollViewPager) findViewById(R.id.event_image);


        scroll = (ScrollView)findViewById(R.id.scroll);





        additional = (TextView)findViewById(R.id.additional);
        additionalHide = (TextView)findViewById(R.id.additional_hide);
        membersJoined = (TextView)findViewById(R.id.members_joined);



        eventImage.startAutoScroll();



        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Log.d("eventId" , eventId);


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

                        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager() , blist);

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
                        View v = LayoutInflater.from(EventDetailsScreen.this).inflate(R.layout.tag , null);

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

                hostName = "@" + String.valueOf(response.body().getData().getEventTimelineUsername());


                if (response.body().getData().getIsLiked()!=null)
                {
                    if (response.body().getData().getIsLiked())
                    {
                        like.setBackground(getResources().getDrawable(R.drawable.like_blue));
                        isLiked = true;
                    }
                    else
                    {
                        like.setBackground(getResources().getDrawable(R.drawable.like));
                        isLiked = false;
                    }

                }


                lat = response.body().getData().getLatitude();
                lng = response.body().getData().getLongitude();


                likeCount.setText(response.body().getData().getEventNumLikes());



                

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



                toolbar.setTitle(response.body().getData().getEventName());


                addr1 = response.body().getData().getVenue();

                String addr = response.body().getData().getVenue();

                String[] separate2 = addr.split(",");




                try {
                    address.setText(String.format("%s\n%s%s", response.body().getData().getVenue(), getString(R.string.city1), response.body().getData().getCity()));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }



                //address.setText(response.body().getData().getVenue());

                String currentString = response.body().getData().getEndTime();

                String[] separate1 = currentString.split("\\s");


                String[] s1 = separate1[0].split("-");

                int n = Integer.parseInt(s1[1]);

                lastDate.setText(String.format("%s%s %s %s )", getString(R.string.event_end_date), s1[2], months[n - 1], s1[0]));

                String currentString1 = response.body().getData().getStartTime();

                String[] separate3 = currentString1.split("\\s");

                String[] s2 = separate3[0].split("-");

                int m = Integer.parseInt(s2[1]);

                startDate.setText(String.format("%s %s %s", s2[2], months[m - 1], s2[0]));

                host.setText(String.format("%s%s", getString(R.string.created_by2), response.body().getData().getEventTimelineUsername()));

                host.setText(Html.fromHtml("<u>created by "+response.body().getData().getEventTimelineUsername() + "</u>"));

                Log.d("asdasdasd" , response.body().getData().getEventNumJoines());

                membersJoined.setText(String.format("%s%s", response.body().getData().getEventNumJoines(), " " + getString(R.string.people_joined)));

                if (userId.equals(response.body().getData().getEventTimelineId()))
                {
                    join.setText(R.string.delete_edit);
                    submitQuery.setVisibility(View.GONE);
                }
                else
                {
                    if (response.body().getData().getIsJoined()!=null)
                    {
                        if (response.body().getData().getIsJoined())
                        {
                            join.setText(R.string.leave);
                            submitQuery.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        join.setText(R.string.join1);
                        submitQuery.setVisibility(View.VISIBLE);
                    }

                    if (response.body().getData().getIsRequested()!=null)
                    {
                        if (response.body().getData().getIsRequested())
                        {
                            join.setText(R.string.requested);
                            submitQuery.setVisibility(View.VISIBLE);
                        }
                    }
                    if (response.body().getData().getIsRejected()!=null)
                    {
                        if (response.body().getData().getIsRejected())
                        {
                            join.setText(R.string.rejected);
                            submitQuery.setVisibility(View.VISIBLE);
                        }
                    }




                }





                bar.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);






            }

            @Override
            public void onFailure(Call<eventDetailBean> call, Throwable t) {

            }
        });




        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                bean b = (bean)getApplicationContext();

                if (Objects.equals(hostId, String.valueOf(b.userId)))
                {
                    Intent intent = new Intent(getApplicationContext() , UserProfileActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent i = new Intent(getApplicationContext() , UserDetailScreen.class);
                    i.putExtra("frien" , hostId);
                    startActivity(i);
                }


            }
        });


        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*

                Uri gmmIntentUri = Uri.parse("geo:" + lat +"," + lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
*/

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng));
                startActivity(intent);

                /*
                String map = "http://maps.google.co.in/maps?q=" + addr1;

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(i);
*/
            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                    if (isLiked) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<eventDetailBean> call = cr.likeEvent(eventId, userId);
                        call.enqueue(new Callback<eventDetailBean>() {
                            @Override
                            public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {


                                    like.setBackground(getResources().getDrawable(R.drawable.like));
                                    isLiked = false;

                                likeCount.setText(response.body().getData().getEventNumLikes());



                            }

                            @Override
                            public void onFailure(Call<eventDetailBean> call, Throwable t) {

                            }
                        });
                    }
                    if (!isLiked)
                    {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<eventDetailBean> call = cr.likeEvent(eventId, userId);
                        call.enqueue(new Callback<eventDetailBean>() {
                            @Override
                            public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {


                                    like.setBackground(getResources().getDrawable(R.drawable.like_blue));
                                    isLiked = true;
                                likeCount.setText(response.body().getData().getEventNumLikes());




                            }

                            @Override
                            public void onFailure(Call<eventDetailBean> call, Throwable t) {

                            }
                        });
                    }


            }
        });







        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Evenzt");
                    String sAux = "\nShare this Event\n\n";
                    sAux = sAux + "http://evenzt.com/event-single.php?evtId=" + eventId + " \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    e.toString();
                }

            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(EventDetailsScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.report_dialog);

                dialog.show();

                TextView title = (TextView)dialog.findViewById(R.id.title);
                final EditText reason = (EditText)dialog.findViewById(R.id.reason);
                Button submit = (Button)dialog.findViewById(R.id.submit);
                final ProgressBar bar = (ProgressBar)dialog.findViewById(R.id.progress);



                title.setText("Report this Event");
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String r = reason.getText().toString();

                        if (r.length() > 0)
                        {

                            bar.setVisibility(View.VISIBLE);

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://evenzt.com/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            final eventDetails cr = retrofit.create(eventDetails.class);

                            Call<reportBean> call = cr.reportEvent(userId , eventId , r);

                            call.enqueue(new Callback<reportBean>() {
                                @Override
                                public void onResponse(Call<reportBean> call, Response<reportBean> response) {

                                    try {
                                        Toast.makeText(EventDetailsScreen.this , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }


                                    bar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<reportBean> call, Throwable t) {
                                    bar.setVisibility(View.GONE);
                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(EventDetailsScreen.this , "Please Enter a Reason" , Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });







        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(EventDetailsScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.query_dialog);

                dialog.show();

                final EditText queryText = (EditText)dialog.findViewById(R.id.query);
                Button send = (Button)dialog.findViewById(R.id.send);

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String text = queryText.getText().toString();

                        if (text.length() > 0)
                        {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://evenzt.com/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();


                            final eventDetails cr = retrofit.create(eventDetails.class);

                            Call<sendMessageBean> call = cr.sendMessage(userId, hostId , queryText.getText().toString() , "1" , eventName.getText().toString());

                            call.enqueue(new Callback<sendMessageBean>() {
                                @Override
                                public void onResponse(Call<sendMessageBean> call, Response<sendMessageBean> response) {

                                    Toast.makeText(getApplicationContext() , "Your query submitted successfully" , Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                }

                                @Override
                                public void onFailure(Call<sendMessageBean> call, Throwable throwable) {

                                    dialog.dismiss();

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(EventDetailsScreen.this , "Please Enter a Text" , Toast.LENGTH_SHORT).show();
                        }




                    }
                });



            }
        });


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (join.getText().toString().toLowerCase().equals("delete/edit"))
                {
                    //Toast.makeText(getBaseContext() , "You are the host" , Toast.LENGTH_SHORT).show();


                    final Dialog dialog = new Dialog(EventDetailsScreen.this);
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
                                    finish();
                                    Toast.makeText(getApplicationContext() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<deleteBean> call, Throwable throwable) {

                                }
                            });


                        }
                    });



                }
                else if (join.getText().toString().toLowerCase().equals("leave"))
                {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    final eventDetails cr = retrofit.create(eventDetails.class);

                    Call<leaveBean> call = cr.leaveEvent(eventId , userId);

                    call.enqueue(new Callback<leaveBean>() {
                        @Override
                        public void onResponse(Call<leaveBean> call, Response<leaveBean> response) {


                            join.setText(getString(R.string.join1));


                            /*final Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://evenzt.com/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            final eventDetails cr = retrofit.create(eventDetails.class);

                            Call<eventDetailBean> call2 = cr.getAllEvents(eventId , userId);

                            call2.enqueue(new Callback<eventDetailBean>() {
                                @Override
                                public void onResponse(Call<eventDetailBean> call, Response<eventDetailBean> response) {


                                    if (userId.equals(response.body().getData().getEventTimelineId()))
                                    {
                                        join.setText("delete/edit");
                                    }
                                    else
                                    {
                                        if (response.body().getData().getIsJoined()!=null)
                                        {
                                            if (response.body().getData().getIsJoined())
                                            {
                                                join.setText("Leave");
                                            }
                                        }
                                        else
                                        {
                                            join.setText("join");
                                        }

                                        if (response.body().getData().getIsRequested()!=null)
                                        {
                                            if (response.body().getData().getIsRequested())
                                            {
                                                join.setText("Requested");
                                            }
                                        }
                                        if (response.body().getData().getIsRejected()!=null)
                                        {
                                            if (response.body().getData().getIsRejected())
                                            {
                                                join.setText("Rejected");
                                            }
                                        }


                                    }


                                    bar.setVisibility(View.GONE);
                                    main.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onFailure(Call<eventDetailBean> call, Throwable t) {

                                }
                            });*/


                        }

                        @Override
                        public void onFailure(Call<leaveBean> call, Throwable t) {

                        }
                    });


                }
                if (join.getText().toString().toLowerCase().equals("join"))
                {


                    final eventDetails cr = retrofit.create(eventDetails.class);

                    Call<joinBean> call = cr.joinEvent(eventId , userId);

                    call.enqueue(new Callback<joinBean>() {
                        @Override
                        public void onResponse(Call<joinBean> call, Response<joinBean> response) {

                            join.setText(getString(R.string.requested));

                            Toast.makeText(getApplicationContext() , "Success" , Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<joinBean> call, Throwable t) {

                        }
                    });








                }

            }
        });




        additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (additionalHide.getVisibility() == View.GONE)
                {


                    additional.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.minus,0);


                    additionalHide.setVisibility(View.VISIBLE);
                    scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            scroll.post(new Runnable() {
                                public void run() {
                                    scroll.fullScroll(View.FOCUS_DOWN);
                                }
                            });
                        }
                    });
                }
                else
                {
                    additional.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.plus,0);
                    additionalHide.setVisibility(View.GONE);
                }

            }
        });


        membersJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext() , JoinedMembers.class);
                i.putExtra("eventId" , eventId);
                startActivity(i);



            }
        });



        if (isLiked!=null)
        {
            if (isLiked)
            {
                like.setBackground(getResources().getDrawable(R.drawable.like_blue));
            }
            else
            {
                like.setBackground(getResources().getDrawable(R.drawable.like));
            }
        }


        //tabs.setBackgroundResource(R.drawable.tab_background);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(String.valueOf(data.getData())));

                Uri selectedImageUri = data.getData();

                mCurrentPhotoPath = getPath(getApplicationContext() , selectedImageUri);


                final Dialog dialog = new Dialog(EventDetailsScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.edit_image_layout);

                dialog.show();

                TextView no = (TextView)dialog.findViewById(R.id.no);
                TextView yes = (TextView)dialog.findViewById(R.id.yes);


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

                                            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager() , blist);

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

                            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager() , blist);

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

    private class PagerAdapter extends FragmentStatePagerAdapter
    {

        List<ImageUrl> list = new ArrayList<>();


        PagerAdapter(FragmentManager fm , List<ImageUrl> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {

            pages p = new pages();
            Bundle b = new Bundle();
            b.putString("image" , list.get(position).getUrl());
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
        Bitmap bmp;
        ImageView image;
        TextView remo;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.pager_images , container , false);
            url = getArguments().getString("image");

            remo = (TextView)view.findViewById(R.id.remove);
            image = (ImageView)view.findViewById(R.id.image);

            remo.setVisibility(View.GONE);

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
    protected void onPause() {
        super.onPause();
       // overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
}
