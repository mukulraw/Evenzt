package com.evenzt;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.rey.material.widget.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import chatPOJO.Datum;
import chatPOJO.chatBean;
import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.evenzt.Login3.READ_TIMEOUT;

public class ChatScreen extends AppCompatActivity {

    //CircleImageView senderImage;
    //TextView senderName;

    ListView grid;

    Timer t;

    EditText messagewBox;

    FloatingActionButton send;

    int count = 0;

    ChatAdapter adapter;





    List<Datum> list;

    LinearLayoutManager manager;

    String userId , friendId;

    String url , name;


    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        list = new ArrayList<>();
        manager = new LinearLayoutManager(this);



        getSupportActionBar().setDisplayShowTitleEnabled(false);
        grid = (ListView) findViewById(R.id.chat_list);
        toolbar.setTitleTextColor(Color.WHITE);

        grid.setDividerHeight(0);

        toolbar.setNavigationIcon(R.drawable.back);

        adapter = new ChatAdapter(ChatScreen.this , list , R.layout.chat_list_model);

        grid.setAdapter(adapter);







        toolbar.setTitleTextColor(Color.WHITE);

        t = new Timer();





        messagewBox = (EditText)findViewById(R.id.type_chat);




        send = (FloatingActionButton) findViewById(R.id.send_message);



        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);

        friendId = getIntent().getExtras().getString("friend");

        name = getIntent().getExtras().getString("name");


        toolbar.setTitle("@" + name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {
                                      fetch();
                                  }

                              },

                0,

                1000);











        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new login2(messagewBox.getText().toString()).execute();

            }
        });




    }



    private class login2 extends AsyncTask<Void , Void , Void>
    {

        String username;
        String result;

        HttpURLConnection conn;
        URL url = null;


        String mess;

        public login2(String mess)
        {
            this.mess = mess;
        }








        @Override
        protected Void doInBackground(Void... params) {

            try {
                String LOG_IN = "http://evenzt.com/eventmanagement/api/sendchat";
                url = new URL(LOG_IN);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);
                //conn.connect();
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userId", userId).appendQueryParameter("friendId" , friendId).appendQueryParameter("text" , mess).appendQueryParameter("isQuery" , "0").appendQueryParameter("eventName" , "");

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));



                result = bufferedReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
            }










            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {


            messagewBox.setText("");


            //fetch();





            super.onPostExecute(aVoid);
        }
    }



    private void fetch()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<chatBean> call = cr.loadMessages(userId , friendId);

        call.enqueue(new Callback<chatBean>() {
            @Override
            public void onResponse(Call<chatBean> call, Response<chatBean> response) {

                for (int i = count ; i < response.body().getData().size() ; i++)
                {
                    list.add(response.body().getData().get(i));
                    adapter.setGridData(list);
                }

                count = response.body().getData().size();

            }

            @Override
            public void onFailure(Call<chatBean> call, Throwable t) {

            }
        });

    }



}
