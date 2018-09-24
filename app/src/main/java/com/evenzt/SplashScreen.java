package com.evenzt;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import googleLoginPOJO.googleSignin;
import interfaces.eventDetails;
import interfaces.google;
import interfaces.register;
import loginPOJO.logo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;


public class SplashScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "hash";
    //SharedPreferences.Editor edit;
    //GoogleApiClient googleApiClient;
    private int RC_SIGN_IN = 123;
    ProgressBar bar;
    String imageURL = "";
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    CallbackManager callbackManager;
    String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.WRITE_EXTERNAL_STORAGE};
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
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



                        loginFacebook("facebook" , bFacebookData.getString("email") , bFacebookData.getString("idFacebook"));

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();




                /*
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            //Log.v("facebook - profile1", profile2.getFirstName());
                            String p = profile2.getId();
                            String e = profile2.getName();
                            //regisFacebook("facebook" , profile2.getName() , profile2.getId() , profile2.getId());
                            loginFacebook("facebook" , profile2.getId() , profile2.getId());
                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    //Log.v("facebook - profile2", profile.getFirstName());
                    String p = profile.getId();
                    String e = profile.getName();
                    //regisFacebook("facebook" , profile.getName() , profile.getId() , profile.getId());
                    loginFacebook("facebook" , profile.getId() , profile.getId());
                }
*/


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        setContentView(R.layout.activity_splash_screen);




        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }







        pref = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
        edit = pref.edit();



        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).resetViewBeforeLoading(false).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);





        if(hasPermissions(this , PERMISSIONS))
        {
           startApp();
        }
        else
        {
            ActivityCompat.requestPermissions(this , PERMISSIONS , REQUEST_CODE_ASK_PERMISSIONS);
        }








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

    /*private void regisFacebook(String provider , String name , final String email , final String password)
    {


        Log.d("asdasdasdasdasd" , password);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        google r = retrofit.create(google.class);

        Call<googleLogin> get = r.registerWithgoogle(provider , email , name , password);

        get.enqueue(new Callback<googleLogin>() {
            @Override
            public void onResponse(Call<googleLogin> call, Response<googleLogin> response) {



                if (response.body()!=null)
                {
                    Log.d("asdasdasd" , String.valueOf(response.body().getResponseMessage()));
                    //userId = response.body().getData().getId();
                    loginFacebook("facebook" , email , password);

                }



            }

            @Override
            public void onFailure(Call<googleLogin> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }
*/

    private void loginFacebook(String provider , String email , String password)
    {

        Log.d("asdasdpassword" , password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        google r = retrofit.create(google.class);

        Call<googleSignin> get = r.loginwithgoogle(provider , email , password);

        get.enqueue(new Callback<googleSignin>() {
            @Override
            public void onResponse(Call<googleSignin> call, Response<googleSignin> response) {


                if (response.body()!=null)
                {
                    Log.d("asdasdasd" , String.valueOf(response.body().getResponseMessage()));

                    if (String.valueOf(response.body().getResponseMessage()).equals("Login Successful"))
                    {

                        //edit.putBoolean("facebook" , true);
                        //edit.apply();


                        edit.putString("id" , response.body().getData().getId());
                        edit.putBoolean("facebook" , true);
                        edit.apply();

                        Log.d("asdasdid" , response.body().getData().getId());

                        bean b = (bean)getApplicationContext();
                        b.userId = Integer.parseInt(response.body().getData().getId());
                        b.userName = response.body().getData().getUsername();
                        b.userProfile = response.body().getData().getAvatarUrl();
                        b.loggedIn = true;
                        bar.setVisibility(View.GONE);


                        Intent i = new Intent(getBaseContext() , MapsActivity.class);
                        startActivity(i);
                        finishAffinity();



                    }


                }



            }

            @Override
            public void onFailure(Call<googleSignin> call, Throwable t) {

                imageURL = "";
                t.printStackTrace();

            }
        });
    }


    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent , 12);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Toast.makeText(getApplicationContext() , "GPS is required for this app", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }





    private void startApp()
    {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            pref = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
            //edit = pref.edit();

            bar = (ProgressBar)findViewById(R.id.bar);

            //Boolean checkGoogle = pref.getBoolean("google" , false);
            Boolean checkEmail = pref.getBoolean("email" , false);
            Boolean checkFacebook = pref.getBoolean("facebook" , false);



                if (checkEmail)
                {
                    bar.setVisibility(View.VISIBLE);
                    String email = pref.getString("emailId" , "");
                    String password = pref.getString("password" , "");
                    if (email.length()>0 && password.length()>0)
                    {
                        logoEmail("email" , email , password);
                    }
                }
                else
                {
                    if (checkFacebook)
                    {
                        LoginManager.getInstance().logInWithReadPermissions(SplashScreen.this , Arrays.asList("email"));
                    }
                    else
                    {
                        Timer timer = new Timer();

                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                Intent i = new Intent(getBaseContext() , LoginActivity.class);

                                startActivity(i);

                                finish();

                            }
                        } , 1500);
                    }

                }



        }else{
            showGPSDisabledAlertToUser();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS)
        {
            if (ActivityCompat.checkSelfPermission(getApplicationContext() , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {

                startApp();

            }
            else
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Toast.makeText(getApplicationContext() , "Permissions are required for this app" , Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }

        }


    }

    private void googleSignIn()
    {
        bar.setVisibility(View.VISIBLE);

        //Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        //startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


        if(requestCode == 12)
        {
            startApp();
        }

    }



    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }




    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();

            if (acct != null) {
                imageURL = String.valueOf(acct.getPhotoUrl());
                loginGoogle("google" , acct.getEmail() , acct.getId());
            }


        }
    }



    private void logoEmail(String provider , final String name, final String password)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        register r = retrofit.create(register.class);

        Call<logo> get = r.loginwithemail(provider ,name , password);

        get.enqueue(new Callback<logo>() {
            @Override
            public void onResponse(Call<logo> call, Response<logo> response) {


                if (response.body()!=null)
                {

                    Log.d("asasdasdasdasd" , String.valueOf(response.body().getResponseMessage()));

                    if (String.valueOf(response.body().getResponseMessage()).equals("Login Successful"))
                    {


                        /*edit.putString("id" , response.body().getData().getId());
                        edit.putBoolean("email" , true);
                        edit.apply();


                        bar.setVisibility(View.GONE);
                        bean b = (bean)getApplicationContext();
                        b.userId = Integer.parseInt(response.body().getData().getId());
                        b.userName = response.body().getData().getUsername();
                        b.userProfile = response.body().getData().getAvatarUrl();
                        b.loggedIn = true;


                        Intent i = new Intent(getBaseContext() , MapsActivity.class);
                        startActivity(i);
                        finishAffinity();*/








                        if (response.body().getData().getEmailVerified().equals("1"))
                        {
                            // Verified Email, Check for Verification Key

                            if (response.body().getData().getEmailVerificationKey().equals("12345"))
                            {

                                // Email is not Verified, Login and Show a Popup



                                    edit.putBoolean("email", true);
                                    edit.putString("emailId", name);
                                    edit.putString("password", password);



                                edit.putString("id", response.body().getData().getId());

                                edit.apply();

                                bean b = (bean) getApplicationContext();
                                b.userId = Integer.parseInt(response.body().getData().getId());
                                b.userName = response.body().getData().getUsername();
                                b.userProfile = response.body().getData().getAvatarUrl();

                                b.loggedIn = true;
                                bar.setVisibility(View.GONE);
                                //dialog.dismiss();

                                Intent i = new Intent(SplashScreen.this , MapsActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("popup" , true);
                                startActivity(i);
                                finishAffinity();

                            }
                            else
                            {

                                // Email is verified, Login user




                                    edit.putBoolean("email", true);
                                    edit.putString("emailId", name);
                                    edit.putString("password", password);



                                edit.putString("id", response.body().getData().getId());

                                edit.apply();

                                bean b = (bean) getApplicationContext();
                                b.userId = Integer.parseInt(response.body().getData().getId());
                                b.userName = response.body().getData().getUsername();
                                b.userProfile = response.body().getData().getAvatarUrl();

                                b.loggedIn = true;


                                bar.setVisibility(View.GONE);
                                Intent i = new Intent(SplashScreen.this, MapsActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("popup" , false);
                                startActivity(i);
                                finishAffinity();

                            }

                        }
                        else
                        {

                            // Unverified Email, Show a popup and Exit app




                                edit.putBoolean("email", true);
                                edit.putString("emailId", name);
                                edit.putString("password", password);



                            edit.putString("id", response.body().getData().getId());

                            edit.apply();

                            final bean b = (bean) getApplicationContext();
                            b.userId = Integer.parseInt(response.body().getData().getId());
                            b.userName = response.body().getData().getUsername();
                            b.userProfile = response.body().getData().getAvatarUrl();

                            b.loggedIn = true;



                            // Show a popup
                            bar.setVisibility(View.GONE);


                            final Dialog dialog = new Dialog(SplashScreen.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.exit_popup);
                            dialog.show();


                            Button ok = dialog.findViewById(R.id.button);
                            TextView resend = dialog.findViewById(R.id.resend);

                            resend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://evenzt.com/")
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    final eventDetails cr = retrofit.create(eventDetails.class);

                                    Call<String> call1 = cr.resendVerification(String.valueOf(b.userId));

                                    call1.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {

                                            Toast.makeText(SplashScreen.this , "The verification mail has been sent to your registered email" , Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });


                                }
                            });

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.dismiss();

                                    //activity.finishAndRemoveTask();


                                    bean b = (bean)getApplicationContext();
                                    b.userId = -1;
                                    b.userName = "guest";
                                    b.loggedIn = false;
                                    b.userProfile = "";
                                    b.userCover = "";
                                    edit.remove("emailId");
                                    edit.remove("id");
                                    edit.putInt("count" , 0);
                                    edit.remove("password");
                                    edit.putBoolean("email" , false);
                                    edit.apply();
                                    Intent i = new Intent(getApplicationContext() , LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    overridePendingTransition(0,0);
                                    finish();                                    //activity.finish();

                                }
                            });



                            //Intent i = new Intent(context, MapsActivity.class);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);


                        }






                    }


                }



            }

            @Override
            public void onFailure(Call<logo> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }



    private void loginGoogle(String provider , String email , String password)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        google r = retrofit.create(google.class);

        Call<googleSignin> get = r.loginwithgoogle(provider , email , password);

        get.enqueue(new Callback<googleSignin>() {
            @Override
            public void onResponse(Call<googleSignin> call, Response<googleSignin> response) {


                if (response.body()!=null)
                {
                    Log.d("asdasdasd" , String.valueOf(response.body().getResponseMessage()));

                    if (String.valueOf(response.body().getResponseMessage()).equals("Login Successful"))
                    {



                        bean b = (bean)getApplicationContext();
                        b.userId = Integer.parseInt(response.body().getData().getId());
                        b.userName = response.body().getData().getUsername();
                        //b.userProfile = imageURL;
                        b.userProfile = response.body().getData().getAvatarUrl();
                        b.loggedIn = true;
                        bar.setVisibility(View.GONE);


                        Intent i = new Intent(getBaseContext() , MapsActivity.class);
                        startActivity(i);
                        finishAffinity();



                    }


                }



            }

            @Override
            public void onFailure(Call<googleSignin> call, Throwable t) {

                imageURL = "";

                t.printStackTrace();

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(0 , R.anim.fade);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
