package com.evenzt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.SmoothViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import loginPOJO.logo;
import retrofit2.Call;


import POJOs.Regis;
import interfaces.register;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login3 extends AppCompatActivity {

    public static Context context;
    static SmoothViewPager pager;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    static TextSwitcher title;
    SharedPreferences pref;
    static SharedPreferences.Editor edit;
    static String e = "";



    static String p = "";
    static Dialog dialog;
    static Activity activity;
    static Boolean keepLogged = true;

    static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        context = getApplicationContext();

        toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);

        activity = this;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wait_dialog);


        overridePendingTransition(0, R.anim.fade);

        pref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        edit = pref.edit();



        pager = (SmoothViewPager) findViewById(R.id.pager);
        title = (TextSwitcher) findViewById(R.id.title);


        title.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView myText = new TextView(Login3.this);
                myText.setTextSize(34);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        title.setText("Sign In");


        title.setInAnimation(getApplicationContext(), R.anim.fade_in);
        //title.setOutAnimation(getApplicationContext() , R.anim.fade_out);


        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 2);


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    title.setText("Sign In");
                } else {
                    title.setText("Sign Up");
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setAdapter(mSectionsPagerAdapter);


    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        final int noOfTabs;

        SectionsPagerAdapter(FragmentManager fm, int noOfTabs) {
            super(fm);
            this.noOfTabs = noOfTabs;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return noOfTabs;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:


                    return new FirstPage();

                case 1:

                    return new SecondPage();

            }
            return null;
        }
    }


    public static class FirstPage extends Fragment {

        EditText email, password;
        Button log;
        TextView loggedIn;

        TextView forgot;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.sign_in_page1, container, false);

            forgot = (TextView)v.findViewById(R.id.forgot);
            email = (EditText) v.findViewById(R.id.email_login);

            int maxLength = 60;
            email.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

            password = (EditText) v.findViewById(R.id.password_login);
            loggedIn = (TextView) v.findViewById(R.id.keep_logged_in);

            loggedIn.setBackgroundResource(R.drawable.logged_in_false);

            loggedIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!keepLogged) {
                        loggedIn.setBackgroundResource(R.drawable.logged_in_false);
                        keepLogged = true;
                    } else {
                        loggedIn.setBackgroundColor(Color.TRANSPARENT);
                        keepLogged = false;
                    }


                }
            });




            log = (Button) v.findViewById(R.id.signin);


            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    e = email.getText().toString();

                    p = password.getText().toString();


                    if (e.length() > 0) {

                        if (p.length() > 0) {


                            dialog.show();


                            logoEmail("email", e, p);


                        } else {
                            password.setError("Invalid Password");
                        }
                    } else {
                        email.setError("Invalid Email");
                    }


                }
            });


            TextView goToCreatePage = (TextView) v.findViewById(R.id.create_page);
            goToCreatePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pager.setCurrentItem(2, true);

                    title.setText("Sign Up");
                    // title.setInAnimation(getApplicationContext() , R.anim.fade_in);
                    ///title.setOutAnimation(getApplicationContext() , R.anim.fade_out);

                }
            });



            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.forgot_dialog);
                    dialog.show();

                    final EditText emm = (EditText)dialog.findViewById(R.id.email);
                    Button sub = (Button) dialog.findViewById(R.id.submit);
                    final ProgressBar bar = (ProgressBar)dialog.findViewById(R.id.progress);

                    sub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String ee = emm.getText().toString();

                            if (ee.length()>0)
                            {

                                bar.setVisibility(View.VISIBLE);

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://evenzt.com/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                register r = retrofit.create(register.class);

                                Call<forgotBean> get = r.forgot(ee);

                                get.enqueue(new Callback<forgotBean>() {
                                    @Override
                                    public void onResponse(Call<forgotBean> call, Response<forgotBean> response) {

                                        Toast.makeText(getContext() , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);
                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onFailure(Call<forgotBean> call, Throwable t) {
                                        bar.setVisibility(View.GONE);
                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(getActivity() , "Please Enter your Email" , Toast.LENGTH_SHORT).show();
                            }



                        }
                    });


                }
            });


            return v;

        }


        public static void logoEmail(String provider, String name, String password) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            register r = retrofit.create(register.class);

            Call<logo> get = r.loginwithemail(provider, name, password);

            get.enqueue(new Callback<logo>() {
                @Override
                public void onResponse(Call<logo> call, Response<logo> response) {


                    if (response.body() != null) {


                        if (String.valueOf(response.body().getResponseMessage()).equals("Login Successful")) {




                            if (response.body().getData().getEmailVerified().equals("1"))
                            {
                                // Verified Email, Check for Verification Key

                                if (response.body().getData().getEmailVerificationKey().equals("12345"))
                                {

                                    // Email is not Verified, Login and Show a Popup


                                    if (keepLogged) {
                                        edit.putBoolean("email", true);
                                        edit.putString("emailId", e);
                                        edit.putString("password", p);


                                    }
                                    edit.putString("id", response.body().getData().getId());

                                    edit.apply();

                                    bean b = (bean) context.getApplicationContext();
                                    b.userId = Integer.parseInt(response.body().getData().getId());
                                    b.userName = response.body().getData().getUsername();
                                    b.userProfile = response.body().getData().getAvatarUrl();

                                    b.loggedIn = true;

                                    dialog.dismiss();

                                    Intent i = new Intent(context, MapsActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra("popup" , true);
                                    context.startActivity(i);
                                    activity.finishAffinity();

                                }
                                else
                                {

                                    // Email is verified, Login user



                                    if (keepLogged) {
                                        edit.putBoolean("email", true);
                                        edit.putString("emailId", e);
                                        edit.putString("password", p);


                                    }
                                    edit.putString("id", response.body().getData().getId());

                                    edit.apply();

                                    bean b = (bean) context.getApplicationContext();
                                    b.userId = Integer.parseInt(response.body().getData().getId());
                                    b.userName = response.body().getData().getUsername();
                                    b.userProfile = response.body().getData().getAvatarUrl();

                                    b.loggedIn = true;

                                    dialog.dismiss();

                                    Intent i = new Intent(context, MapsActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra("popup" , false);
                                    context.startActivity(i);
                                    activity.finishAffinity();

                                }

                            }
                            else
                            {

                                // Unverified Email, Show a popup and Exit app



                                if (keepLogged) {
                                    edit.putBoolean("email", true);
                                    edit.putString("emailId", e);
                                    edit.putString("password", p);


                                }
                                edit.putString("id", response.body().getData().getId());

                                edit.apply();

                                bean b = (bean) context.getApplicationContext();
                                b.userId = Integer.parseInt(response.body().getData().getId());
                                b.userName = response.body().getData().getUsername();
                                b.userProfile = response.body().getData().getAvatarUrl();

                                b.loggedIn = true;

                                dialog.dismiss();

                                // Show a popup



                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.exit_popup);
                                dialog.show();


                                Button ok = dialog.findViewById(R.id.button);


                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();

                                        //activity.finishAndRemoveTask();

                                        activity.finishAffinity();
                                        //activity.finish();

                                    }
                                });



                                //Intent i = new Intent(context, MapsActivity.class);
                                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //context.startActivity(i);


                            }





                        } else {
                            toast.setText(response.body().getResponseMessage());
                            toast.show();

                            dialog.dismiss();
                        }


                    }


                }

                @Override
                public void onFailure(Call<logo> call, Throwable t) {

                    dialog.dismiss();

                    toast.setText("Some error occurred, please try again");

                    t.printStackTrace();

                }
            });
        }

        public static boolean isValidEmail(CharSequence target) {
            return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }


    }

    public static class SecondPage extends Fragment {


        static EditText username;
        static EditText email;
        static EditText password;
        static EditText retpassword;
        Button create;

        TextView terms;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.sign_in_page2, container, false);
            //  title.setText("Sign Up");
            TextView goToLoginPage = (TextView) v.findViewById(R.id.login_pager);

            terms = (TextView)v.findViewById(R.id.terms);


            ClickableSpan termsOfServicesClick = new ClickableSpan() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext() , Terms.class);
                    intent.putExtra("type" , "terms");
                    startActivity(intent);

                }
            };

            ClickableSpan privacyPolicyClick = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext() , Terms.class);
                    intent.putExtra("type" , "privacy");
                    startActivity(intent);
                }
            };

            makeLinks(terms , new String[] { "Terms of Use", "Privacy Policy" }, new ClickableSpan[] {
                    termsOfServicesClick, privacyPolicyClick
            });


            username = (EditText) v.findViewById(R.id.username);
            email = (EditText) v.findViewById(R.id.emailId);

            int maxLength = 60;
            username.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

            password = (EditText) v.findViewById(R.id.password);
            retpassword = (EditText) v.findViewById(R.id.retpassword);
            create = (Button) v.findViewById(R.id.signup);


            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user = username.getText().toString();
                    String emai = email.getText().toString();
                    String pass = password.getText().toString();
                    String retpa = retpassword.getText().toString();
                    //new register(emai , user , pass).execute();


                    if (user.length() > 0) {

                        if (emai.length() > 0) {


                            if (isValidEmail(emai)) {

                                if (pass.length() > 0 && pass.length() > 5) {

                                    if (pass.compareTo(retpa) == 0) {

                                        dialog.show();
                                        regisEmail("email", user, emai, pass);

                                    } else {
                                        retpassword.setError("Passwords did not match");
                                    }

                                } else {
                                    password.setError("Password must be at least 6 digits long");
                                }

                            } else {
                                email.setError("Invalid Email");
                            }
                        } else {
                            email.setError("Invalid Email");
                        }
                    } else {
                        username.setError("Invalid Username");
                    }


                    //new register1(emai , pass).execute();


                }
            });


            goToLoginPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pager.setCurrentItem(0, true);

                    title.setText("Sign In");
                    //  title.setInAnimation(getApplicationContext() , R.anim.fade_in);
                    //  title.setOutAnimation(getApplicationContext() , R.anim.fade_out);
                }
            });

            return v;
        }


        public static boolean isValidEmail(CharSequence target) {
            return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }


        private static void regisEmail(String provider, String name, String emaill, final String passwordd) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://evenzt.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            register r = retrofit.create(register.class);

            Call<Regis> get = r.registerWithEmail(provider, emaill, name, passwordd);

            get.enqueue(new Callback<Regis>() {
                @Override
                public void onResponse(Call<Regis> call, Response<Regis> response) {


                    if (response.body() != null) {

                        username.setText("");
                        email.setText("");
                        password.setText("");
                        retpassword.setText("");

                        dialog.dismiss();

                        if (response.body().getResponseCode() == 200) {
                            pager.setCurrentItem(0, true);

                            title.setText("Sign In");

                            toast.setText("Registered successfully, please Login to continue");
                            toast.show();

                        }
                        else if (response.body().getResponseCode() == 4)
                        {
                            Toast.makeText(context , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();
                        }
                        else if (response.body().getResponseCode() == 5)
                        {
                            Toast.makeText(context , response.body().getResponseMessage() , Toast.LENGTH_SHORT).show();
                        }


                    }


                }

                @Override
                public void onFailure(Call<Regis> call, Throwable t) {

                    dialog.dismiss();

                    toast.setText("Some error occurred, please try again");

                    t.printStackTrace();

                }
            });
        }


    }



    public static void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];

            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.fade);
    }
}
