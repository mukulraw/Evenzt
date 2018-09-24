package com.evenzt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import PostedPOJO.Datum;
import PostedPOJO.PostedBean;
import blockPOJO.blockBean;
import commentPOJO.commentBean;
import friendsPOJO.friendsBean;
import interfaces.eventDetails;
import profilePOJO.profileBean;
import reportUserPOJO.reportUserBean;
import requestPOJO.requestBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import reviewPOJO.reviewBean;

public class UserDetailScreen extends AppCompatActivity {


    TextView profileName , profileAge;

    TabLayout tabs;

    //TextView addFriend;

    static Dialog dialog;

    TextView report;

    MyViewPager pager;
    static String userId;
    static String friendId;
    static String friendName;
    TextView edit;
    FragStatePagerAdapter adapter;

    CircularImageView profilePic;

    ImageView cover;
    SharedPreferences pref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_screen);


        dialog = new Dialog(UserDetailScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_layout);

        pref = getSharedPreferences("myPref" , Activity.MODE_PRIVATE);
        //addFriend = (TextView)findViewById(R.id.add_friend);


        report = (TextView)findViewById(R.id.report);




        profileName = (TextView)findViewById(R.id.profile_name);
        //profileEmail = (TextView)findViewById(R.id.profile_email);
        profileAge = (TextView)findViewById(R.id.profile_age);

        cover = (ImageView)findViewById(R.id.profile_cover_image);


        friendId = getIntent().getStringExtra("frien");

        profilePic = (CircularImageView)findViewById(R.id.profile_photo);

        bean b = (bean)getApplicationContext();
        userId = pref.getString("id" , "0");


        edit = (TextView)findViewById(R.id.edit_pic);

        tabs = (TabLayout)findViewById(R.id.profile_tabs);


        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.about_icon)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.posted)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.joined)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.rating)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.friend)));


        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        pager = (MyViewPager) findViewById(R.id.profile_view_pager);

//        pager.setSwipeable(false);

        adapter = new FragStatePagerAdapter(getSupportFragmentManager(), 5);



        pager.setAdapter(adapter);



        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;








        final ImageLoader imageLoader = ImageLoader.getInstance();











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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<profileBean> call = cr.userDetail(friendId , userId);

        call.enqueue(new Callback<profileBean>() {
            @Override
            public void onResponse(Call<profileBean> call, Response<profileBean> response) {



                profileName.setText(String.format("@%s", response.body().getData().getUsername()));



                //profileEmail.setText(response.body().getData().getEmail());

                if (response.body().getData().getAge().length() == 0)
                {
                    profileAge.setVisibility(View.GONE);
                }
                else
                {
                    profileAge.setText(String.format("%s%s", getString(R.string.age3), response.body().getData().getAge()));
                }



                friendName = response.body().getData().getUsername();

                if (response.body().getData().getIsFriendBy())
                {
                    //addFriend.setText("MESSAGE");
                }
                else
                {
                    if (response.body().getData().getIsFriendRequested())
                    {
                        //addFriend.setText("REQUESTED");
                    }
                    else
                    {
                        //addFriend.setText("ADD FRIEND");
                    }

                }


                if (response.body().getData().getAvatarUrl().length()>0)
                {
                    imageLoader.displayImage(response.body().getData().getAvatarUrl() , profilePic);
                }

                if (response.body().getData().getCoverUrl().length()>0)
                {
                    imageLoader.displayImage(response.body().getData().getCoverUrl() , cover);
                }







            }

            @Override
            public void onFailure(Call<profileBean> call, Throwable t) {

            }
        });



     /*   addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (addFriend.getText().equals("ADD FRIEND"))
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://evenzt.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    final eventDetails cr = retrofit.create(eventDetails.class);

                    Call<requestBean> call = cr.addFriend(userId , friendId);

                    call.enqueue(new Callback<requestBean>() {
                        @Override
                        public void onResponse(Call<requestBean> call, Response<requestBean> response) {

                            Toast.makeText(getApplicationContext() , "Request sent successfully" , Toast.LENGTH_SHORT).show();
                            addFriend.setText("REQUESTED");

                        }

                        @Override
                        public void onFailure(Call<requestBean> call, Throwable t) {

                        }
                    });
                }
                else if (addFriend.getText().equals("MESSAGE"))
                {
                   //Toast.makeText(getApplicationContext() , "Already a friend" , Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getApplicationContext() , ChatScreen.class);
                    Bundle b = new Bundle();
                    b.putString("friend" , friendId);
                    b.putString("name" , friendName);
                    intent.putExtras(b);
                    startActivity(intent);


                }
                else
                {

                }




            }
        });

*/


     report.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             final Dialog dialog = new Dialog(UserDetailScreen.this);
             dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
             dialog.setCancelable(true);
             dialog.setContentView(R.layout.report_dialog);

             dialog.show();

             TextView title = (TextView)dialog.findViewById(R.id.title);
             final EditText reason = (EditText)dialog.findViewById(R.id.reason);
             Button submit = (Button)dialog.findViewById(R.id.submit);
             final ProgressBar bar = (ProgressBar)dialog.findViewById(R.id.progress);



             title.setText("Report this User");
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

                         Call<reportUserBean> call = cr.reportUser(userId , userId , r);

                         call.enqueue(new Callback<reportUserBean>() {
                             @Override
                             public void onResponse(Call<reportUserBean> call, Response<reportUserBean> response) {

                                 try {
                                     Toast.makeText(UserDetailScreen.this , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                     dialog.dismiss();
                                 }catch (Exception e)
                                 {
                                     e.printStackTrace();
                                 }


                                 bar.setVisibility(View.GONE);
                             }

                             @Override
                             public void onFailure(Call<reportUserBean> call, Throwable t) {
                                 bar.setVisibility(View.GONE);
                             }
                         });


                     }
                     else
                     {
                         Toast.makeText(UserDetailScreen.this , "Please Enter a Reason" , Toast.LENGTH_SHORT).show();
                     }

                 }
             });

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
                    return new about();
                case 1:
                    return new posted();
                case 2:
                    return new joined();
                case 3:
                    return new rating();
                case 4:
                    return new friends();
            }


            return null;

        }

        @Override
        public int getCount() {
            return count;
        }


    }








    public static class about extends Fragment{

        TextView country , phone , interest , education , occupation , bio , gender , fullName;
        TextView countryLabel , phoneLabel , interestLabel , educationLabel , occupationLabel , bioLabel , genderLabel , fullNameLabel;

        Button addFriend , block;

        @Override
        public void onResume() {
            super.onResume();

            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<profileBean> call = cr.userDetail(friendId , userId);

            call.enqueue(new Callback<profileBean>() {
                @Override
                public void onResponse(Call<profileBean> call, Response<profileBean> response) {


                    if (response.body().getData().getCountry().length() == 0)
                    {
                        countryLabel.setVisibility(View.GONE);
                        country.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getPhone().length() == 0)
                    {
                        phoneLabel.setVisibility(View.GONE);
                        phone.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getInterest().length() == 0)
                    {
                        interest.setVisibility(View.GONE);
                        interestLabel.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getEducation().length() == 0)
                    {
                        education.setVisibility(View.GONE);
                        educationLabel.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getOccupation().length() == 0)
                    {
                        occupation.setVisibility(View.GONE);
                        occupationLabel.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getBio().length() == 0)
                    {
                        bioLabel.setVisibility(View.GONE);
                        bio.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getGender().length() == 0)
                    {
                        genderLabel.setVisibility(View.GONE);
                        gender.setVisibility(View.GONE);
                    }
                    if (response.body().getData().getFullName().length() == 0)
                    {
                        fullNameLabel.setVisibility(View.GONE);
                        fullName.setVisibility(View.GONE);
                    }


                    country.setText(response.body().getData().getCountry());
                    phone.setText(response.body().getData().getPhone());
                    interest.setText(response.body().getData().getInterest());
                    education.setText(response.body().getData().getEducation());
                    occupation.setText(response.body().getData().getOccupation());
                    bio.setText(response.body().getData().getBio());
                    gender.setText(response.body().getData().getGender());
                    fullName.setText(response.body().getData().getFullName());

                    if (response.body().getData().getIsFriendBy())
                    {
                        addFriend.setText(R.string.messag);
                    }
                    else
                    {
                        if (response.body().getData().getIsFriendRequested())
                        {
                            addFriend.setText(R.string.requeste);
                        }
                        else
                        {
                            addFriend.setText(R.string.add_frien);
                        }

                    }


                    if (response.body().getData().getIsBlocked())
                    {
                        block.setText(R.string.blocke);
                    }
                    else
                    {
                        block.setText(R.string.bloc);
                    }







                    dialog.dismiss();


                }

                @Override
                public void onFailure(Call<profileBean> call, Throwable t) {

                    dialog.dismiss();
                }
            });




        }

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.about_me_layout2 , container , false);


            country = (TextView)view.findViewById(R.id.country);
            countryLabel = (TextView)view.findViewById(R.id.country_label);
            phone = (TextView)view.findViewById(R.id.phone);
            phoneLabel = (TextView)view.findViewById(R.id.phone_label);
            interest = (TextView)view.findViewById(R.id.interest);
            interestLabel = (TextView)view.findViewById(R.id.interest_label);
            education = (TextView)view.findViewById(R.id.education);
            educationLabel = (TextView)view.findViewById(R.id.education_label);
            occupation = (TextView)view.findViewById(R.id.occupation);
            occupationLabel = (TextView)view.findViewById(R.id.occupation_label);
            bio = (TextView)view.findViewById(R.id.bio);
            bioLabel = (TextView)view.findViewById(R.id.bio_label);
            gender = (TextView)view.findViewById(R.id.gender);
            genderLabel = (TextView)view.findViewById(R.id.gender_label);
            fullNameLabel = (TextView)view.findViewById(R.id.full_name_label);
            fullName = (TextView)view.findViewById(R.id.full_name);
            addFriend = (Button)view.findViewById(R.id.add_friend);
            block = (Button)view.findViewById(R.id.block);





            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if (addFriend.getText().equals("ADD FRIEND"))
                    {

                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<requestBean> call = cr.addFriend(userId , friendId);

                        call.enqueue(new Callback<requestBean>() {
                            @Override
                            public void onResponse(Call<requestBean> call, Response<requestBean> response) {

                                Toast.makeText(getContext() , "Request sent successfully" , Toast.LENGTH_SHORT).show();
                                addFriend.setText(getString(R.string.requeste));

                                dialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<requestBean> call, Throwable t) {

                            }
                        });
                    }
                    else if (addFriend.getText().equals("FRIENDS"))
                    {
                        //Toast.makeText(getApplicationContext() , "Already a friend" , Toast.LENGTH_SHORT).show();

                        dialog.dismiss();


                        final Dialog dialog1 = new Dialog(getActivity());
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setCancelable(true);
                        dialog1.setContentView(R.layout.unfriend_dialog);

                        dialog1.show();

                        TextView unfriend = (TextView)dialog1.findViewById(R.id.unfriend);
                        TextView messag = (TextView)dialog1.findViewById(R.id.message);



                        unfriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.show();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();


                                final eventDetails cr = retrofit.create(eventDetails.class);

                                Call<requestBean> call = cr.addFriend(userId , friendId);

                                call.enqueue(new Callback<requestBean>() {
                                    @Override
                                    public void onResponse(Call<requestBean> call, Response<requestBean> response) {

                                        Toast.makeText(getContext() , "Friend Removed successfully" , Toast.LENGTH_SHORT).show();
                                        addFriend.setText("ADD FRIEND");

                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onFailure(Call<requestBean> call, Throwable t) {

                                    }
                                });

                            }
                        });





                        messag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog1.dismiss();

                                Intent intent = new Intent(getContext() , ChatScreen.class);
                                Bundle b = new Bundle();
                                b.putString("friend" , friendId);
                                b.putString("name" , friendName);
                                intent.putExtras(b);
                                startActivity(intent);

                            }
                        });




                    }





                }
            });



            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if (block.getText().equals("BLOCK"))
                    {

                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<blockBean> call = cr.blockUser(userId , friendId);


                        call.enqueue(new Callback<blockBean>() {
                            @Override
                            public void onResponse(Call<blockBean> call, Response<blockBean> response) {


                                Toast.makeText(getActivity() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                block.setText(getString(R.string.blocke));

                                dialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<blockBean> call, Throwable t) {

                                dialog.dismiss();

                            }
                        });
                    }
                    else if (block.getText().equals("BLOCKED"))
                    {

                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<blockBean> call = cr.unblockUser(userId , friendId);


                        call.enqueue(new Callback<blockBean>() {
                            @Override
                            public void onResponse(Call<blockBean> call, Response<blockBean> response) {

                                block.setText(getString(R.string.bloc));
                                Toast.makeText(getActivity() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                dialog.dismiss();


                            }

                            @Override
                            public void onFailure(Call<blockBean> call, Throwable t) {

                                dialog.dismiss();

                            }
                        });
                    }








                }
            });









            return view;
        }
    }


    public static class posted extends Fragment{



        int pno = 1;
        RecyclerView grid;
        GridLayoutManager manager;
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

            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<PostedBean> call = cr.myEvents(friendId , String.valueOf(pno));

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
                        //bar.setVisibility(View.GONE);

                        pno++;

                    }
                    else
                    {
                        pno = 1;
                        //bar.setVisibility(View.GONE);
                    }



                    dialog.dismiss();


                }

                @Override
                public void onFailure(Call<PostedBean> call, Throwable t) {

                    dialog.dismiss();

                }
            });





        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.posted_events , container , false);
            list = new ArrayList<>();
            adapter = new PostedAdapter(getActivity() , list);

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

                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final eventDetails cr = retrofit.create(eventDetails.class);




                        Call<PostedBean> call2 = cr.myEvents(friendId , String.valueOf(pno));

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

                                    pno++;

                                }
                                else
                                {
                                    pno = 1;
                                    //bar.setVisibility(View.GONE);
                                }
dialog.dismiss();


                            }

                            @Override
                            public void onFailure(Call<PostedBean> call, Throwable t) {

                                dialog.dismiss();

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
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View v = inflater.inflate(R.layout.posted_list_model , parent , false);
                return new ViewHolder(v);

            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                Datum item = list.get(position);

                try {

                    String currentString = item.getStartTime();

                    String[] separate1 = currentString.split("\\s");

                    String asd = separate1[0];

                    String[] sepa2  = asd.split("-");

                    holder.name.setText(item.getEventName());
                    holder.date.setText(sepa2[2]);
                    holder.month.setText(mon[Integer.parseInt(sepa2[1]) - 1]);
                    holder.venue.setText(item.getCategoryName());



                }catch (IndexOutOfBoundsException e)
                {

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



    public static class joined extends Fragment{
        int pno = 1;
        RecyclerView grid;
        GridLayoutManager manager;
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

            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);

            Call<PostedBean> call = cr.joinedEvents(friendId , String.valueOf(pno));

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
                        //bar.setVisibility(View.GONE);

                        pno++;

                    }
                    else
                    {
                        pno = 1;
                        //bar.setVisibility(View.GONE);
                    }



                    dialog.dismiss();


                }

                @Override
                public void onFailure(Call<PostedBean> call, Throwable t) {

                    dialog.dismiss();

                }
            });


        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.joined_events , container , false);
            list = new ArrayList<>();
            adapter = new PostedAdapter(getActivity() , list);

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

                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final eventDetails cr = retrofit.create(eventDetails.class);




                        Call<PostedBean> call2 = cr.myEvents(friendId , String.valueOf(pno));

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

                                    pno++;

                                }
                                else
                                {
                                    pno = 1;
                                    //bar.setVisibility(View.GONE);
                                }

                                dialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<PostedBean> call, Throwable t) {

                                dialog.dismiss();

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
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View v = inflater.inflate(R.layout.posted_list_model , parent , false);
                return new ViewHolder(v);

            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                Datum item = list.get(position);


                try {
                    String currentString = item.getStartTime();

                    String[] separate1 = currentString.split("\\s");

                    String asd = separate1[0];

                    String[] sepa2  = asd.split("-");

                    holder.name.setText(item.getEventName());
                    holder.date.setText(sepa2[2]);
                    holder.month.setText(mon[Integer.parseInt(sepa2[1]) - 1]);
                    holder.venue.setText(item.getCategoryName());

                    Log.d("asdasdasdasdadasdasd" , sepa2[0]);


                }catch (NullPointerException e)
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



    public static class friends extends Fragment{


        RecyclerView grid;
        GridLayoutManager manager;
        FriendsAdapter adapter;
        Dialog dialog;
        List<friendsPOJO.Datum> list;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            list = new ArrayList<>();

            manager = new GridLayoutManager(getContext() , 1);
            adapter = new FriendsAdapter(getContext() , list);

        }


        @Override
        public void onResume() {
            super.onResume();

            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);


            Call<friendsBean> call = cr.getFriends(friendId);

            call.enqueue(new Callback<friendsBean>() {
                @Override
                public void onResponse(Call<friendsBean> call, Response<friendsBean> response) {


                    if (response.body().getData()!=null)
                    {
                        adapter.setGridData(response.body().getData());
                    }


                    dialog.dismiss();


                }

                @Override
                public void onFailure(Call<friendsBean> call, Throwable t) {

                    dialog.dismiss();

                }
            });


        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.friend_list2 , container , false);



            grid = (RecyclerView)view.findViewById(R.id.friends_grid);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);

            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.progress_layout);



            return view;
        }
    }



    public static class rating extends Fragment{



        TextView addRating;
        RecyclerView grid;
        GridLayoutManager manager;

        List<reviewPOJO.Datum> list;

        RatingAdapter adapter;
        Dialog dialog;





        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.rating_screen , container , false);

            grid = (RecyclerView)view.findViewById(R.id.review_list);
            manager = new GridLayoutManager(getContext() , 1);
            list = new ArrayList<>();

            adapter = new RatingAdapter(getContext() , list);

            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.progress_layout);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);

            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);


            Call<reviewBean> call = cr.getReviews(friendId);

            call.enqueue(new Callback<reviewBean>() {
                @Override
                public void onResponse(Call<reviewBean> call, Response<reviewBean> response) {


                    //if (response.body().getData()!=null)
                    //{

                        adapter.setGridData(response.body().getData());

                    //}


                    dialog.dismiss();


                }

                @Override
                public void onFailure(Call<reviewBean> call, Throwable t) {

                    dialog.dismiss();

                }
            });





            addRating = (TextView)view.findViewById(R.id.add_rating);


            addRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.rating_dialog);
                    dialog.show();

                    final RatingBar rater = (RatingBar)dialog.findViewById(R.id.rating);
                    LayerDrawable stars = (LayerDrawable) rater.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.SRC_ATOP);
                    //rater.setVisibility(View.GONE);



                    final EditText review = (EditText)dialog.findViewById(R.id.review);
                    ImageButton check = (ImageButton) dialog.findViewById(R.id.confirm_rating);

                    dialog.show();

                    check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            float rat = rater.getRating();

                            if (rat > 0)
                            {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();


                                final eventDetails cr = retrofit.create(eventDetails.class);


                                Call<commentBean> call = cr.comment(friendId , userId , review.getText().toString() , Float.toString(rater.getRating()));

                                Log.d("asdasdf" , Float.toString(rater.getRating()));

                                call.enqueue(new Callback<commentBean>() {
                                    @Override
                                    public void onResponse(Call<commentBean> call, Response<commentBean> response) {

                                        Call<reviewBean> call2 = cr.getReviews(friendId);

                                        call2.enqueue(new Callback<reviewBean>() {
                                            @Override
                                            public void onResponse(Call<reviewBean> call, Response<reviewBean> response) {


                                                if (response.body().getData()!=null)
                                                {

                                                    adapter.setGridData(response.body().getData());

                                                }


                                                dialog.dismiss();


                                            }

                                            @Override
                                            public void onFailure(Call<reviewBean> call, Throwable t) {

                                                dialog.dismiss();

                                            }
                                        });




                                    }

                                    @Override
                                    public void onFailure(Call<commentBean> call, Throwable t) {

                                    }
                                });

                                //dialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(getContext() , "Please add a rating" , Toast.LENGTH_SHORT).show();
                            }






                        }
                    });






                }
            });




            return view;
        }
    }




}
