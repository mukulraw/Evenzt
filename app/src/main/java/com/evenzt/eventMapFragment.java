package com.evenzt;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class eventMapFragment extends Fragment implements OnMapReadyCallback {

    private Marker marker;
    private GoogleMap mMap;
    private String toggle = "normal";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_map , null);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.eventMapView);

        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
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
        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.partydreamcolorpart), 0,0, color);

// add marker to Map



        mMap.addMarker(new MarkerOptions().position(NFC).title("Party in the Batcave"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NFC));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent i = new Intent(getActivity() , EventDetailsScreen.class);
                startActivity(i);

                return false;

            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
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
        });


    }
}
