package com.evenzt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import googleLoginPOJO.googleSignin;
import googlePOJO.googleLogin;
import interfaces.google;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;


public class Login2 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient googleApiClient;
    private int RC_SIGN_IN = 123;
    CallbackManager callbackManager;
    ProgressBar bar;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String imageURL = "";
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {

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
                parameters.putString("fields", "id, first_name, last_name, email , gender , birthday , location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();





                /*if(Profile.getCurrentProfile() == null) {
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



        setContentView(R.layout.activity_login2);
        overridePendingTransition(0 , R.anim.fade);

        pref = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        bar = (ProgressBar)findViewById(R.id.progress);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        TextView fb = (TextView)findViewById(R.id.fb);
        //TextView google = (TextView)findViewById(R.id.google);
        TextView email = (TextView)findViewById(R.id.email);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login2.this , Arrays.asList("email"));
            }
        });

        /*google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
*/

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext() , Login3.class);
                startActivity(i);
            }
        });


    }


    private void googleSignIn()
    {
        bar.setVisibility(View.VISIBLE);

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }



    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();


            if (acct != null) {
                imageURL = String.valueOf(acct.getPhotoUrl());
                //regisGoogle("google"  , acct.getDisplayName() , acct.getEmail() , acct.getId());
            }


        }
    }



    /*private void regisGoogle(String provider , String name , final String email , final String password)
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
                    loginGoogle("google" , email , password);

                }



            }

            @Override
            public void onFailure(Call<googleLogin> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }
*/
    private void regisFacebook(String provider , String pass , final String email , final String password , String username)
    {

        bar.setVisibility(View.VISIBLE);



        Log.d("asdasdasdasdasd" , password);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        google r = retrofit.create(google.class);

        Call<googleLogin> get = r.registerWithgoogle(provider , email , username , pass , password);

        get.enqueue(new Callback<googleLogin>() {
            @Override
            public void onResponse(Call<googleLogin> call, Response<googleLogin> response) {



                if (response.body()!=null)
                {
                    if (response.body().getData()!=null)
                    {
                        Log.d("asdasdasd" , String.valueOf(response.body().getResponseMessage()));
                        //userId = response.body().getData().getId();
                        loginFacebook("facebook" , email , password);
                        bar.setVisibility(View.GONE);
                    }
                    else
                    {
                        Log.d("asdasdLogin" , response.body().getResponseMessage());
                        Toast.makeText(Login2.this , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                    }
                }



            }

            @Override
            public void onFailure(Call<googleLogin> call, Throwable t) {

                t.printStackTrace();
                bar.setVisibility(View.GONE);

            }
        });
    }


    private void loginFacebook(String provider , final String email , final String password)
    {

        bar.setVisibility(View.VISIBLE);

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

                    if (response.body().getData() != null)
                    {

                        edit.putString("id" , response.body().getData().getId());
                        edit.putBoolean("facebook" , true);
                        edit.apply();

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
                    else
                    {

                        final Dialog dialog = new Dialog(Login2.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.regis_dialog);
                        dialog.show();


                        final EditText user = (EditText)dialog.findViewById(R.id.username);
                        final EditText pass = (EditText)dialog.findViewById(R.id.password);
                        final EditText cpass = (EditText)dialog.findViewById(R.id.confirm);

                        TextView set = (TextView)dialog.findViewById(R.id.set);

                        set.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String u = user.getText().toString();
                                String p = pass.getText().toString();
                                String c = cpass.getText().toString();


                                if (u.length()>0)
                                {

                                    if (p.length()>0)
                                    {

                                        if (Objects.equals(p, c))
                                        {

                                            regisFacebook("facebook" , p , email , password , u);
                                            dialog.dismiss();

                                        }
                                        else
                                        {
                                            Toast.makeText(Login2.this , "Passwords did not match" , Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else
                                    {
                                        Toast.makeText(Login2.this , "Invalid password" , Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(Login2.this , "Invalid username" , Toast.LENGTH_SHORT).show();
                                }

                            }
                        });






                    }

                    bar.setVisibility(View.GONE);

                }



            }

            @Override
            public void onFailure(Call<googleSignin> call, Throwable t) {

                bar.setVisibility(View.GONE);

                imageURL = "";
                t.printStackTrace();

            }
        });
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



    private void loginGoogle(String provider , String email , String password)
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

                        edit.putBoolean("google" , true);
                        edit.apply();

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





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0 , R.anim.fade);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
