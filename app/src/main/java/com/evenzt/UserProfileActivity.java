package com.evenzt;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import PostedPOJO.Datum;
import PostedPOJO.PostedBean;
import friendsPOJO.friendsBean;
import interfaces.createEvent;
import interfaces.eventDetails;
import interfaces.register;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import profilePOJO.profileBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserProfileActivity extends AppCompatActivity {

    TextView profileName , profileAge;

    //TabLayout tabs;

    MyViewPager pager;

    ProgressBar CoverProgress , ProfileProgress;

    static TextView facebook;

    static String userId;
    CallbackManager callbackManager;

    TextView edit;
    FragStatePagerAdapter adapter;

    int CHANGE_COVER = 567;

    ProgressBar progress;

    int CHANGE_PROFILE = 789;

    CircularImageView profilePic;

    ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

                //regisGoogle("google"  , acct.getDisplayName() , acct.getEmail() , acct.getId());


                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        final eventDetails cr = retrofit.create(eventDetails.class);

                        Call<profileBean> call = cr.syncUser(userId , bFacebookData.getString("idFacebook") , bFacebookData.getString("email") , "facebook");

                        Log.d("asdIdlSync" , bFacebookData.getString("idFacebook"));
                        Log.d("asdEmailSync" , bFacebookData.getString("email"));

                        call.enqueue(new Callback<profileBean>() {
                            @Override
                            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                            }

                            @Override
                            public void onFailure(Call<profileBean> call, Throwable t) {

                            }
                        });


                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        setContentView(R.layout.activity_user_profile);

        progress = (ProgressBar)findViewById(R.id.progress);

        facebook = (TextView)findViewById(R.id.facebook);

        profileName = (TextView)findViewById(R.id.profile_name);
        //profileEmail = (TextView)findViewById(R.id.profile_email);
        profileAge = (TextView)findViewById(R.id.profile_age);


        CoverProgress = (ProgressBar)findViewById(R.id.progress_cover);
        ProfileProgress = (ProgressBar)findViewById(R.id.progress_profile);


        CoverProgress.setVisibility(View.VISIBLE);
        ProfileProgress.setVisibility(View.VISIBLE);

        cover = (ImageView)findViewById(R.id.profile_cover_image);

        profilePic = (CircularImageView)findViewById(R.id.profile_photo);

        bean b = (bean)getApplicationContext();
        userId = String.valueOf(b.userId);


        Log.d("asdasdasd" , userId);

        edit = (TextView)findViewById(R.id.edit_pic);

//        tabs = (TabLayout)findViewById(R.id.profile_tabs);


  /*      tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.about_icon)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.posted)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.joined)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.rating)));
        tabs.addTab(tabs.newTab().setIcon(getResources().getDrawable(R.drawable.friend)));


        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);
*/
        pager = (MyViewPager) findViewById(R.id.profile_view_pager);

//        pager.setSwipeable(false);

        adapter = new FragStatePagerAdapter(getSupportFragmentManager(), 1);



        pager.setAdapter(adapter);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(UserProfileActivity.this , Arrays.asList("email"));

            }
        });

        //pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;







        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(UserProfileActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.change_profile_picture_dialog);
                dialog.show();


                TextView profile = (TextView)dialog.findViewById(R.id.change_profile_photo);
                TextView cover = (TextView)dialog.findViewById(R.id.change_cover_photo);
                final TextView name = (TextView)dialog.findViewById(R.id.name);

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        final Dialog dialog1 = new Dialog(UserProfileActivity.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setCancelable(true);
                        dialog1.setContentView(R.layout.update_username_dialog);

                        dialog1.show();

                        final EditText updatedName = (EditText)dialog1.findViewById(R.id.name);

                        int maxLength = 20;
                        updatedName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});


                        Button update = (Button)dialog1.findViewById(R.id.update);

                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                createEvent cr = retrofit.create(createEvent.class);



                                Call<profileBean> call = cr.updateUserName(userId , updatedName.getText().toString());

                                call.enqueue(new Callback<profileBean>() {
                                    @Override
                                    public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                                        Toast.makeText(UserProfileActivity.this , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();

                                        loadHeader();
                                        dialog1.dismiss();


                                    }

                                    @Override
                                    public void onFailure(Call<profileBean> call, Throwable throwable) {
                                        dialog1.dismiss();
                                    }
                                });


                            }
                        });



                        dialog.dismiss();






                    }
                });

                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), CHANGE_PROFILE);



                        dialog.dismiss();

                    }
                });

                cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), CHANGE_COVER);

                        dialog.dismiss();

                    }
                });




            }
        });














        /*tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
*/


        loadHeader();


    }



    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=200");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            Log.d("TAG","Error parsing JSON");
            return null;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_COVER&& resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri selectedImageUri = data.getData();
            String mCurrentPhotoPath = getPath(getApplicationContext() , selectedImageUri);
            Log.d("asdasdasd" , String.valueOf(selectedImageUri));
            Log.d("asdasdasd" , mCurrentPhotoPath);

            CoverProgress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            createEvent cr = retrofit.create(createEvent.class);

            File file = null;
            if (mCurrentPhotoPath != null) {
                file = new File(mCurrentPhotoPath);
            }

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), reqFile);

            Call<profileBean> call = cr.updateCoverImage(userId , body);

            call.enqueue(new Callback<profileBean>() {
                @Override
                public void onResponse(Call<profileBean> call, Response<profileBean> response) {


                    loadHeader();


                }

                @Override
                public void onFailure(Call<profileBean> call, Throwable t) {

                }
            });


        }
        if (requestCode == CHANGE_PROFILE&& resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri selectedImageUri = data.getData();
            String mCurrentPhotoPath = getPath(getApplicationContext() , selectedImageUri);
            Log.d("asdasdasd" , String.valueOf(selectedImageUri));
            Log.d("asdasdasd" , mCurrentPhotoPath);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ProfileProgress.setVisibility(View.VISIBLE);

            createEvent cr = retrofit.create(createEvent.class);

            File file = null;
            if (mCurrentPhotoPath != null) {
                file = new File(mCurrentPhotoPath);
            }

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), reqFile);

            Call<profileBean> call = cr.updateProfileImage(userId , body);

            call.enqueue(new Callback<profileBean>() {
                @Override
                public void onResponse(Call<profileBean> call, Response<profileBean> response) {


//                    Log.d("asdasdasdasdasd" , response.body().getResponseMessage());


                    loadHeader();


                }

                @Override
                public void onFailure(Call<profileBean> call, Throwable t) {

                }
            });




        }



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




    private void loadHeader()
    {

        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        final ImageLoader imageLoader = ImageLoader.getInstance();
        eventDetails cr = retrofit.create(eventDetails.class);



        Call<profileBean> call = cr.userDetail(userId , userId);



        call.enqueue(new Callback<profileBean>() {
            @Override
            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                profileName.setText(String.format("@%s", response.body().getData().getUsername()));
                //profileEmail.setText(response.body().getData().getEmail());
                profileAge.setText(response.body().getData().getAge());


                if (response.body().getData().getAvatarUrl().length()>0)
                {
                    imageLoader.displayImage(response.body().getData().getAvatarUrl() , profilePic);
                    ProfileProgress.setVisibility(View.GONE);
                }

                if (response.body().getData().getCoverUrl().length()>0)
                {
                    imageLoader.displayImage(response.body().getData().getCoverUrl() , cover);
                    CoverProgress.setVisibility(View.GONE);
                }




                progress.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<profileBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
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
            }


            return null;

        }

        @Override
        public int getCount() {
            return count;
        }


    }








    public static class about extends Fragment{

        TextView edit , country , phone , interest , education , occupation , bio , gender , fullName;

        ProgressBar progress;

        TextView reset;

        String emai = "";

        CallbackManager callbackManager;

        SharedPreferences.Editor edi;
        SharedPreferences pref;

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

            Call<profileBean> call = cr.userDetail(userId , userId);

            call.enqueue(new Callback<profileBean>() {
                @Override
                public void onResponse(Call<profileBean> call, Response<profileBean> response) {


                    country.setText(response.body().getData().getCountry());
                    phone.setText(response.body().getData().getPhone());
                    interest.setText(response.body().getData().getInterest());
                    education.setText(response.body().getData().getEducation());
                    occupation.setText(response.body().getData().getOccupation());
                    bio.setText(response.body().getData().getBio());
                    gender.setText(response.body().getData().getGender());
                    fullName.setText(response.body().getData().getFullName());


                    emai = response.body().getData().getEmail();




                    if (Objects.equals(response.body().getData().getFbConnect(), "Y"))
                    {
                        facebook.setVisibility(View.GONE);
                    }


                    progress.setVisibility(View.GONE);






                }

                @Override
                public void onFailure(Call<profileBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });




        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.about_me_layout , container , false);
            progress = (ProgressBar)view.findViewById(R.id.progress);

            pref = getContext().getSharedPreferences("myPref" , Context.MODE_PRIVATE);
            edi = pref.edit();

            reset = (TextView)view.findViewById(R.id.reset);

            edit = (TextView)view.findViewById(R.id.edit_profile);
            country = (TextView)view.findViewById(R.id.country);
            phone = (TextView)view.findViewById(R.id.phone);
            interest = (TextView)view.findViewById(R.id.interest);
            education = (TextView)view.findViewById(R.id.education);
            occupation = (TextView)view.findViewById(R.id.occupation);
            bio = (TextView)view.findViewById(R.id.bio);
            gender = (TextView)view.findViewById(R.id.gender);
            fullName = (TextView)view.findViewById(R.id.full_name);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(getContext() , EditProfile.class);
                    startActivity(i);

                }
            });



            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.reset_dialog);
                    dialog.setCancelable(false);
                    dialog.show();

                    TextView yes = (TextView)dialog.findViewById(R.id.yes);
                    TextView no = (TextView)dialog.findViewById(R.id.no);
                    final ProgressBar bar = (ProgressBar)dialog.findViewById(R.id.progress);

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            bar.setVisibility(View.VISIBLE);

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://evenzt.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            register r = retrofit.create(register.class);

                            final bean b = (bean)getContext().getApplicationContext();

                            Call<forgotBean> get = r.forgot(emai);

                            get.enqueue(new Callback<forgotBean>() {
                                @Override
                                public void onResponse(Call<forgotBean> call, Response<forgotBean> response) {

                                    Toast.makeText(getContext() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                    dialog.dismiss();




                                    if (pref.getBoolean("email" , false)) {



                                        b.userId = -1;
                                        b.userName = "guest";
                                        b.loggedIn = false;
                                        b.userProfile = "";
                                        b.userCover = "";
                                        edi.remove("emailId");
                                        edi.remove("id");
                                        edi.putInt("count" , 0);
                                        edi.remove("password");
                                        edi.putBoolean("email" , false);
                                        edi.apply();
                                        Intent i = new Intent(getContext() , LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        //overridePendingTransition(0,0);
                                        getActivity().finish();
                                    }else if (pref.getBoolean("facebook" , false))
                                    {
                                        LoginManager.getInstance().logOut();

                                        b.userName = "guest";
                                        b.userId = -1;
                                        b.loggedIn = false;
                                        b.userProfile = "";
                                        //edi.remove("facebook");
                                        edi.putInt("count" , 0);
                                        edi.remove("id");
                                        edi.putBoolean("facebook" , false);
                                        edi.apply();
                                        Intent i = new Intent(getContext() , LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        //overridePendingTransition(0,0);
                                        getActivity().finish();

                                    } else {

                                        b.loggedIn = false;
                                        edi.remove("id");
                                        edi.putInt("count" , 0);
                                        edi.apply();
                                        Intent i = new Intent(getContext() , LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        //overridePendingTransition(0,0);
                                        getActivity().finish();
                                    }


                                }

                                @Override
                                public void onFailure(Call<forgotBean> call, Throwable t) {
                                    bar.setVisibility(View.GONE);
                                }
                            });

                        }
                    });



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





                }

                @Override
                public void onFailure(Call<PostedBean> call, Throwable t) {

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

                                    pno++;

                                }
                                else
                                {
                                    pno = 1;
                                    //bar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<PostedBean> call, Throwable t) {

                            }
                        });

                    }



                }
            });




            return view;
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
                        //bar.setVisibility(View.GONE);

                        pno++;

                    }
                    else
                    {
                        pno = 1;
                        //bar.setVisibility(View.GONE);
                    }





                }

                @Override
                public void onFailure(Call<PostedBean> call, Throwable t) {

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

                                    pno++;

                                }
                                else
                                {
                                    pno = 1;
                                    //bar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<PostedBean> call, Throwable t) {

                            }
                        });

                    }



                }
            });




            return view;
        }
    }



    public static class friends extends Fragment{


        RecyclerView grid;
        GridLayoutManager manager;
        FriendsAdapter adapter;
        List<friendsPOJO.Datum> list;
        TextView addFriend;

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


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            final eventDetails cr = retrofit.create(eventDetails.class);


            Call<friendsBean> call = cr.getFriends(userId);

            call.enqueue(new Callback<friendsBean>() {
                @Override
                public void onResponse(Call<friendsBean> call, Response<friendsBean> response) {


                    if (response.body().getData()!=null)
                    {
                        adapter.setGridData(response.body().getData());
                    }




                }

                @Override
                public void onFailure(Call<friendsBean> call, Throwable t) {

                }
            });


        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.friends_list , container , false);

            addFriend = (TextView)view.findViewById(R.id.add_friend);

            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(getActivity() , UsersActivity.class);
                    getContext().startActivity(i);

                }
            });

            grid = (RecyclerView)view.findViewById(R.id.friends_grid);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);



            return view;
        }
    }



}
