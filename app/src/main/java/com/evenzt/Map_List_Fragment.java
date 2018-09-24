package com.evenzt;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import nearbyPOJO.Datum;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class Map_List_Fragment extends Fragment implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {


    TabLayout toggleTabs;
    ViewPager togglePager;

    String provider;
    FragStatePagerAdapter adapter;
    static List<Datum> list;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    static eventListAdapter eventAdapter;
    static String userId;
    private static int[] pno = {1};

    //View marker;
    static String toggle = "normal";

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    static Location current;



    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_list_layout, container, false);





        toggleTabs = (TabLayout) view.findViewById(R.id.toggle_tabs);
        togglePager = (ViewPager) view.findViewById(R.id.toggle_view_pager);

        Button create = (Button) view.findViewById(R.id.button2);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CreateEvent1.class);
                startActivity(i);

            }
        });

        toggleTabs.addTab(toggleTabs.newTab().setText("LIST"));
        toggleTabs.addTab(toggleTabs.newTab().setText("MAP"));

        list = new ArrayList<>();


        eventAdapter = new eventListAdapter(getActivity(), list);

        adapter = new FragStatePagerAdapter(getChildFragmentManager(), toggleTabs.getTabCount());

        togglePager.setAdapter(adapter);

        togglePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toggleTabs));

        toggleTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                togglePager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        bean b = (bean) getActivity().getApplicationContext();
        userId = String.valueOf(b.userId);


        //fetch();

        return view;
    }


   /* private void fetch() {
        list.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://samplelogin.affixwebsolution.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final createEvent cr = retrofit.create(createEvent.class);


        Call<viewBean> call = cr.getAllEvents(userId, String.valueOf(pno[0]));
        //Call<nearbyBean> call = cr.nearbyEvents(userId, "4364554", "123123");

        call.enqueue(new Callback<viewBean>() {
            @Override
            public void onResponse(Call<viewBean> call, Response<viewBean> response) {

                if (response.body().getResponseMessage().equals("Event Data")) {


                    List<ViewPOJO.Datum> data = response.body().getData();

                    for (int i = 0; i < data.size(); i++) {

                        list.add(data.get(i));

                    }

                    eventAdapter.setGridData(list);

                    refreshMap(list);
                    refreshList(list);


                    pno[0]++;
                } else {
                    pno[0] = 0;
                }

            }

            @Override
            public void onFailure(Call<viewBean> call, Throwable t) {

            }
        });


    }
*/

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = ((MapsActivity)getActivity()).mGoogleApiClient;


    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d("asdasdasd" , "on location changed");

        current = location;
        //fetch();
        Log.d("asdasdasd" , String.valueOf(location.getLongitude()));


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("asdasdasd" , "1");
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {




            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(100); // Update location every second

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("asdasdasd" , "location listener 2 called");
                }
            });


            current = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);


            Log.d("asdasdasd" , String.valueOf(current.getLongitude()));


        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }



    class FragStatePagerAdapter extends FragmentStatePagerAdapter {


        private int count;


        FragStatePagerAdapter(FragmentManager fm, int count) {
            super(fm);

            this.count = count;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new ListFragment();

                case 1:
                    return new MapFragment();
            }


            return null;

        }

        @Override
        public int getCount() {
            return count;
        }


    }

    public static class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {


        static GoogleMap mMap;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.event_map, container, false);


            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.eventMapView);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    mMap = googleMap;

                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);




                }
            });

            return view;





        }



        public static void resetLocation(Location location)
        {
            LatLng current = new LatLng(location.getLatitude(),location.getLongitude());


            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,14));
        }



        public static void refreshMap(List<ViewPOJO.Datum> list)
        {
            LatLng sydney = new LatLng(-34, 151);
            LatLng NFC = new LatLng(28.561824 , 77.270743);



            // Bitmap b = Bitmap.createBitmap(50 , 50 , Bitmap.Config.ARGB_8888);





            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
            Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
            Paint color = new Paint();
            color.setTextSize(35);
            color.setColor(Color.BLACK);

// modify canvas
          //  canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
           //         R.drawable.partydreamcolorpart), 0,0, color);

// add marker to Map



            LatLng current1 = new LatLng(current.getLatitude(),current.getLongitude());


            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current1,14));
        }


        @Override
        public void onMapLongClick(LatLng latLng) {
            switch (toggle)
            {
                case "normal":mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    Log.d("asdasasd","satelite toggled");
                    toggle = "satelite";
                    break;
                case "satelite":mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Log.d("asdasasd","normal toggled");
                    toggle="normal";
                    break;
            }
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            Intent i = new Intent(getActivity() , EventDetailsScreen.class);
            startActivity(i);

            return false;
        }
    }


    public static class ListFragment extends Fragment {

        ProgressBar bar;
        static RecyclerView grid;
        //GridLayoutManager layoutManager;
        static Context context;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //layoutManager = new GridLayoutManager(getActivity() , 1);

            context = getActivity();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.event_list , container , false);


            bar = (ProgressBar)view.findViewById(R.id.bar);
            bar.setVisibility(View.GONE);

            grid = (RecyclerView)view.findViewById(R.id.eventListView);



            //grid.setVisibility(View.GONE);





         /*   grid.setOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    if (pno[0] > 1)
                    {
                        bar.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://samplelogin.affixwebsolution.com/")
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

                                        list.add(data.get(i));

                                    }


                                    eventAdapter.setGridData(list);
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
*/



            return view;

        }


        public static void refreshList(List<ViewPOJO.Datum> list)
        {
            GridLayoutManager manager = new GridLayoutManager(context , 1);


            grid.setAdapter(eventAdapter);

            if (grid.getLayoutManager() == null)
            {
                grid.setLayoutManager(manager);
            }
        }

    }

}
