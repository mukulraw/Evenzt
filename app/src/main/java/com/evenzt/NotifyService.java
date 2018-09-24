package com.evenzt;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.eventDetails;
import notificationPOJO.Datum;
import notificationPOJO.notificationBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NotifyService extends Service {

    Timer timer;
    ConnectionDetector cd;

    private void doSomethingRepeatedly() {
        timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {



                try{






                    if (cd.isConnectingToInternet())
                    {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://evenzt.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();



                        final eventDetails cr = retrofit.create(eventDetails.class);

                        bean b = (bean)getApplicationContext();


                        Call<notificationBean> call = cr.getNotification(pref.getString("id" , "0") , "1");

                        call.enqueue(new Callback<notificationBean>() {
                            @Override
                            public void onResponse(Call<notificationBean> call, Response<notificationBean> response) {





                                try{
                                    List<Datum> list = response.body().getData();

                                    if (list.size() > 0) {

                                        if (list.size() == 1)
                                        {
                                            int c = pref.getInt("count" , 0);

                                            edit.putInt("count" , c + 1);

                                            Log.d("notification" , list.get(0).getNotifText());

                                            addNotification("Evenzt", list.get(0).getNotifText());
                                        }
                                        else
                                        {
                                            int c = pref.getInt("count" , 0);

                                            edit.putInt("count" , c + list.size());
                                            Log.d("notification" , "You have " + String.valueOf(list.size()) + " new Notifications");
                                            addNotification2("You have " + String.valueOf(list.size()) + " new Notifications");
                                        }

                                        edit.apply();

                                    }
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                                //}



                            }

                            @Override
                            public void onFailure(Call<notificationBean> call, Throwable t) {
                                Log.d("notification" , "failure");
                            }
                        });











                    }






                }
                catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }

            }
        }, 0, 1000);



    }


    private void addNotification(String message , String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(message)
                        .setAutoCancel(true)
                        .setContentText(Html.fromHtml(text));

        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private void addNotification2(String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setAutoCancel(true)
                        .setContentTitle(message);

        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }




    SharedPreferences pref;
    SharedPreferences.Editor edit;


    @Override
    public void onCreate() {



        pref = getApplicationContext().getSharedPreferences("myPref" , Activity.MODE_PRIVATE);
        edit = pref.edit();
        cd = new ConnectionDetector(getApplicationContext());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        doSomethingRepeatedly();

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        receive r = new receive();



        if (r!=null) {
            try {

                unregisterReceiver(r);
                r=null;

            }catch (Exception e)
            {

            }
        }







    }

}
