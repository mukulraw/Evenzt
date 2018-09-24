package com.evenzt;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;

import android.annotation.TargetApi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;


import android.graphics.Canvas;


import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Build;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;


import com.balysv.materialripple.MaterialRippleLayout;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Set;


import interfaces.createEvent;
import interfaces.eventDetails;
import messCountPOJO.messCountBean;
import nearbyPOJO.Datum;
import nearbyPOJO.nearbyBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.evenzt.MapsActivity.ListFragment.context;
import static com.evenzt.MapsActivity.ListFragment.refreshList;
import static com.evenzt.MapsActivity.MapFragment.refreshMap;
import static com.evenzt.MapsActivity.MapFragment.resetLocation;


public class MapsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener, ConnectionCallbacks, OnConnectionFailedListener{


    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    SearchableSpinner spin;

    String lat , lng;

    static ProgressBar progress;





    static int TOOLBAR_ELEVATION = 3;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static Location mLastLocation;



    TabLayout toggleTabs;
    ViewPager togglePager;


    FragStatePagerAdapter adapter;
    static List<Datum> list;
    static List<Datum> subList;


    static Hashtable<String , Datum> markers;

    ImageLoader imageLoader;

    static String userId;
    private static int[] pno = {1};



    static String toggle = "normal";

    DrawerLayout drawer;
    static eventListAdapter eventAdapter;

    TextView titleName;
    SharedPreferences pref;

    CircularImageView titleImage;

    // ImageView coverImage;

    GoogleApiClient mGoogleApiClient;
    SharedPreferences.Editor edit;
    private LocationRequest mLocationRequest;


    String query = "";


    List<String> checkedList;





    static Boolean isNearby = true;

    FragmentManager fm;

    boolean isFilter = false;

    private final String[] listSpinner1 = {"Nearby",
            "Afghanistan",
            "Aland Islands",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "British Indian Ocean Territory",
            "British Virgin Islands",
            "Brunei Darussalam",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Cape Verde",
            "Cayman Islands",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos Islands",
            "Colombia",
            "Comoros",
            "Cook Islands",
            "Costa Rica",
            "Cote d’Ivoire",
            "Croatia",
            "Cuba",
            "CuraÃ§ao",
            "Cyprus",
            "Czech Republic",
            "Democratic Republic of the Congo",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Ethiopia",
            "Falkland Islands",
            "Faroe Islands",
            "Federated States of Micronesia",
            "Fiji",
            "Finland",
            "France",
            "French Polynesia",
            "French Southern and Antarctic Lands",
            "Gabon",
            "Georgia",
            "Germany",
            "Ghana",
            "Gibraltar",
            "Greece",
            "Greenland",
            "Grenada",
            "Guam",
            "Guatemala",
            "Guernsey",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Heard Island and McDonald Islands",
            "Honduras",
            "Hong Kong",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Isle of Man",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jersey",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Kosovo",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macau",
            "Macedonia",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Mauritania",
            "Mauritius",
            "Mexico",
            "Moldova",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Montserrat",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "New Caledonia",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "Niue",
            "Norfolk Island",
            "North Korea",
            "Northern Cyprus",
            "Northern Mariana Islands",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Pitcairn Islands",
            "Poland",
            "Portugal",
            "Puerto Rico",
            "Qatar",
            "Republic of Congo",
            "Romania",
            "Russia",
            "Rwanda",
            "Saint Barthelemy",
            "Saint Helena",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Martin",
            "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Sint Maarten",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "Somaliland",
            "South Africa",
            "South Georgia and South Sandwich Islands",
            "South Korea",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Svalbard",
            "Swaziland",
            "Sweden",
            "Switzerland",
            "Syria",
            "Taiwan",
            "Tajikistan",
            "Tanzania",
            "Thailand",
            "The Gambia",
            "Timor-Leste",
            "Togo",
            "Tokelau",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Turks and Caicos Islands",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United States",
            "Uruguay",
            "US Minor Outlying Islands",
            "US Virgin Islands",
            "Uzbekistan",
            "Vanuatu",
            "Vatican City",
            "Venezuela",
            "Vietnam",
            "Wallis and Futuna",
            "Western Sahara",
            "Yemen",
            "Zambia",
            "Zimbabwe"};

    // SwitchCompat switchView;




    String[] lats = {"34.517",
            "60.117",
            "41.317",
            "36.75",
            "-14.27",
            "42.5",
            "-8.833",
            "18.217",
            "0",
            "17.117",
            "-34.58",
            "40.167",
            "12.517",
            "-35.27",
            "48.2",
            "40.383",
            "25.083",
            "26.233",
            "23.717",
            "13.1",
            "53.9",
            "50.833",
            "17.25",
            "6.4833",
            "32.283",
            "27.467",
            "-16.5",
            "43.867",
            "-24.63",
            "-15.78",
            "-7.3",
            "18.417",
            "4.8833",
            "42.683",
            "12.367",
            "-3.367",
            "11.55",
            "3.8667",
            "45.417",
            "14.917",
            "19.3",
            "4.3667",
            "12.1",
            "-33.45",
            "39.917",
            "-10.42",
            "-12.17",
            "4.6",
            "-11.7",
            "-21.2",
            "9.9333",
            "6.8167",
            "45.8",
            "23.117",
            "12.1",
            "35.167",
            "50.083",
            "-4.317",
            "55.667",
            "11.583",
            "15.3",
            "18.467",
            "-0.217",
            "30.05",
            "13.7",
            "3.75",
            "15.333",
            "59.433",
            "9.0333",
            "-51.7",
            "62",
            "6.9167",
            "-18.13",
            "60.167",
            "48.867",
            "-17.53",
            "-49.35",
            "0.3833",
            "41.683",
            "52.517",
            "5.55",
            "36.133",
            "37.983",
            "64.183",
            "12.05",
            "13.467",
            "14.617",
            "49.45",
            "9.5",
            "11.85",
            "6.8",
            "18.533",
            "0",
            "14.1",
            "0",
            "47.5",
            "64.15",
            "28.6",
            "-6.167",
            "35.7",
            "33.333",
            "53.317",
            "54.15",
            "31.767",
            "41.9",
            "18",
            "35.683",
            "49.183",
            "31.95",
            "51.167",
            "-1.283",
            "-0.883",
            "42.667",
            "29.367",
            "42.867",
            "17.967",
            "56.95",
            "33.867",
            "-29.32",
            "6.3",
            "32.883",
            "47.133",
            "54.683",
            "49.6",
            "0",
            "42",
            "-18.92",
            "-13.97",
            "3.1667",
            "4.1667",
            "12.65",
            "35.883",
            "7.1",
            "18.067",
            "-20.15",
            "19.433",
            "47",
            "43.733",
            "47.917",
            "42.433",
            "16.7",
            "34.017",
            "-25.95",
            "16.8",
            "-22.57",
            "-0.548",
            "27.717",
            "52.35",
            "-22.27",
            "-41.3",
            "12.133",
            "13.517",
            "9.0833",
            "-19.02",
            "-29.05",
            "39.017",
            "35.183",
            "15.2",
            "59.917",
            "23.617",
            "33.683",
            "7.4833",
            "31.767",
            "8.9667",
            "-9.45",
            "-25.27",
            "-12.05",
            "14.6",
            "-25.07",
            "52.25",
            "38.717",
            "18.467",
            "25.283",
            "-4.25",
            "44.433",
            "55.75",
            "-1.95",
            "17.883",
            "-15.93",
            "17.3",
            "14",
            "18.073",
            "46.767",
            "13.133",
            "-13.82",
            "43.933",
            "0.3333",
            "24.65",
            "14.733",
            "44.833",
            "-4.617",
            "8.4833",
            "1.2833",
            "18.017",
            "48.15",
            "46.05",
            "-9.433",
            "2.0667",
            "9.55",
            "-25.7",
            "-54.28",
            "37.55",
            "4.85",
            "40.4",
            "6.9167",
            "15.6",
            "5.8333",
            "78.217",
            "-26.32",
            "59.333",
            "46.917",
            "33.5",
            "25.033",
            "38.55",
            "-6.8",
            "13.75",
            "13.45",
            "-8.583",
            "6.1167",
            "-9.167",
            "-21.13",
            "10.65",
            "36.8",
            "39.933",
            "37.95",
            "21.467",
            "-8.517",
            "0.3167",
            "50.433",
            "24.467",
            "51.5",
            "38.883",
            "-34.85",
            "38.883",
            "18.35",
            "41.317",
            "-17.73",
            "41.9",
            "10.483",
            "21.033",
            "-13.95",
            "27.154",
            "15.35",
            "-15.42",
            "-17.82"};

    String[] longs = {"69.183",
            "19.9",
            "19.817",
            "3.05",
            "-170.7",
            "1.5167",
            "13.217",
            "-63.05",
            "0",
            "-61.85",
            "-58.67",
            "44.5",
            "-70.03",
            "149.13",
            "16.367",
            "49.867",
            "-77.35",
            "50.567",
            "90.4",
            "-59.62",
            "27.567",
            "4.3333",
            "-88.77",
            "2.6167",
            "-64.78",
            "89.633",
            "-68.15",
            "18.417",
            "25.9",
            "-47.92",
            "72.4",
            "-64.62",
            "114.93",
            "23.317",
            "-1.517",
            "29.35",
            "104.92",
            "11.517",
            "-75.7",
            "-23.52",
            "-81.38",
            "18.583",
            "15.033",
            "-70.67",
            "116.38",
            "105.72",
            "96.833",
            "-74.08",
            "43.233",
            "-159.8",
            "-84.08",
            "-5.267",
            "16",
            "-82.35",
            "-68.92",
            "33.367",
            "14.467",
            "15.3",
            "12.583",
            "43.15",
            "-61.4",
            "-69.9",
            "-78.5",
            "31.25",
            "-89.2",
            "8.7833",
            "38.933",
            "24.717",
            "38.7",
            "-57.85",
            "-6.767",
            "158.15",
            "178.42",
            "24.933",
            "2.3333",
            "-149.6",
            "70.217",
            "9.45",
            "44.833",
            "13.4",
            "-0.217",
            "-5.35",
            "23.733",
            "-51.75",
            "-61.75",
            "144.73",
            "-90.52",
            "-2.533",
            "-13.7",
            "-15.58",
            "-58.15",
            "-72.33",
            "0",
            "-87.22",
            "0",
            "19.083",
            "-21.95",
            "77.2",
            "106.82",
            "51.417",
            "44.4",
            "-6.233",
            "-4.483",
            "35.233",
            "12.483",
            "-76.8",
            "139.75",
            "-2.1",
            "35.933",
            "71.417",
            "36.817",
            "169.53",
            "21.167",
            "47.967",
            "74.6",
            "102.6",
            "24.1",
            "35.5",
            "27.483",
            "-10.8",
            "13.167",
            "9.5167",
            "25.317",
            "6.1167",
            "0",
            "21.433",
            "47.517",
            "33.783",
            "101.7",
            "73.5",
            "-8",
            "14.5",
            "171.38",
            "-15.97",
            "57.483",
            "-99.13",
            "28.85",
            "7.4167",
            "106.92",
            "19.267",
            "-62.22",
            "-6.817",
            "32.583",
            "96.15",
            "17.083",
            "166.92",
            "85.317",
            "4.9167",
            "166.45",
            "174.78",
            "-86.25",
            "2.1167",
            "7.5333",
            "-169.9",
            "167.97",
            "125.75",
            "33.367",
            "145.75",
            "10.75",
            "58.583",
            "73.05",
            "134.63",
            "35.233",
            "-79.53",
            "147.18",
            "-57.67",
            "-77.05",
            "120.97",
            "-130.1",
            "21",
            "-9.133",
            "-66.12",
            "51.533",
            "15.283",
            "26.1",
            "37.6",
            "30.05",
            "-62.85",
            "-5.717",
            "-62.72",
            "-61",
            "-63.08",
            "-56.18",
            "-61.22",
            "-171.8",
            "12.417",
            "6.7333",
            "46.7",
            "-17.63",
            "20.5",
            "55.45",
            "-13.23",
            "103.85",
            "-63.03",
            "17.117",
            "14.517",
            "159.95",
            "45.333",
            "44.05",
            "28.217",
            "-36.5",
            "126.98",
            "31.617",
            "-3.683",
            "79.833",
            "32.533",
            "-55.17"
            ,
            "15.633",
            "31.133",
            "18.05",
            "7.4667",
            "36.3",
            "121.52",
            "68.767",
            "39.283",
            "100.52",
            "-16.57",
            "125.6",
            "1.2167",
            "-171.8",
            "-175.2",
            "-61.52",
            "10.183",
            "32.867",
            "58.383",
            "-71.13",
            "179.22",
            "32.55",
            "30.517",
            "54.367",
            "-0.083",
            "-77",
            "-56.17",
            "-77",
            "-64.93",
            "69.25",
            "168.32",
            "12.45",
            "-66.87",
            "105.85",
            "-171.9",
            "-13.2",
            "44.2",
            "28.283",
            "31.033"};

    static Toolbar toolbar;

    TextView count;

    TextView messageCount;

    ImageButton message;

    static LinearLayout scrollId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        imageLoader = ImageLoader.getInstance();


        progress = (ProgressBar)findViewById(R.id.progress);

        scrollId = (LinearLayout)findViewById(R.id.scroll_id);


        spin = (SearchableSpinner) findViewById(R.id.spinner);


        spin.setTitle("Search country");



        //checkedList = new ArrayList<>();

        markers = new Hashtable<String , Datum>();

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();



            //  tutorial here

        }



        toggleTabs = (TabLayout) findViewById(R.id.toggle_tabs);
        togglePager = (ViewPager) findViewById(R.id.toggle_view_pager);

        MaterialRippleLayout create = (MaterialRippleLayout) findViewById(R.id.button2);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), CreateEvent1.class);
                startActivity(i);

            }
        });

        toggleTabs.addTab(toggleTabs.newTab().setText("MAP"));
        toggleTabs.addTab(toggleTabs.newTab().setText("LIST"));

        list = new ArrayList<>();
        subList = new ArrayList<>();


        eventAdapter = new eventListAdapter(this, list);

        adapter = new FragStatePagerAdapter(getSupportFragmentManager(), toggleTabs.getTabCount());

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


        final bean b = (bean) getApplicationContext();
        userId = String.valueOf(b.userId);


        //fetch();



        pref = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
        edit = pref.edit();








        // layoutToReplace = (LinearLayout) findViewById(R.id.layout_to_replace);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        toolbar.inflateMenu(R.menu.menu_activity_maps);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.action_search) {

                    Intent i = new Intent(getApplicationContext() , SearchActivity.class);
                    startActivity(i);
                    overridePendingTransition(0,0);
                    return true;
                }

                else if (id == R.id.filter) {

                    Log.d("asdasasasdasd" , "Fiklter");

                    if (list.size()>0) {

                        final List<String> chooseList = new ArrayList<String>();
                        final Set<String> s = new HashSet<String>();

                        final Dialog dialog = new Dialog(MapsActivity.this);
                        dialog.setContentView(R.layout.filter_dialog);
                        dialog.setCancelable(true);


                        dialog.show();


                        RecyclerView filterList = (RecyclerView) dialog.findViewById(R.id.filter_list);

                        TextView filterClick = (TextView) dialog.findViewById(R.id.filter_click);
                        TextView filterClear = (TextView) dialog.findViewById(R.id.filter_clear);

                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

                        final ChooseCategoryAdapter adap = new ChooseCategoryAdapter(getApplicationContext(), chooseList, subList);
                        filterList.setAdapter(adap);
                        filterList.setLayoutManager(manager);


                        //s.clear();
                        //chooseList.clear();

                        for (int i = 0; i < list.size(); i++) {
                            chooseList.add(list.get(i).getCategoryName().toLowerCase());
                        }

                        s.addAll(chooseList);
                        chooseList.clear();
                        chooseList.addAll(s);

                        adap.setGridData(chooseList);


                        filterClick.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                checkedList = new ArrayList<String>();

                                checkedList = adap.getSelectedId();


                                Log.d("asdasdasdchecked", String.valueOf(checkedList.size()));


                                //subList.clear();
                                subList = new ArrayList<Datum>();

                                for (int i = 0; i < list.size(); i++) {
                                    for (int j = 0; j < checkedList.size(); j++) {
                                        if (Objects.equals(list.get(i).getCategoryName().toLowerCase(), checkedList.get(j).toLowerCase())) {
                                            Log.d("asdasdasdasdasd", "true");
                                            subList.add(list.get(i));
                                        }
                                    }
                                }

                                isFilter = true;

                                Log.d("asdasdasdasdfiltered", String.valueOf(subList.size()));

                                //eventAdapter.setGridData(subList);
                                progress.setVisibility(View.VISIBLE);
                                refreshList(subList);
                                refreshMap(subList);

                                dialog.dismiss();


                            }
                        });


                        filterClear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                subList.clear();
                                isFilter = false;
                                progress.setVisibility(View.VISIBLE);
                                eventAdapter.setGridData(list);

                                refreshList(list);
                                refreshMap(list);

                                dialog.dismiss();


                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(context , "No events to filter" , Toast.LENGTH_SHORT).show();
                    }



                    return true;
                }




                return true;
            }
        });


        fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        Map_List_Fragment frag1 = new Map_List_Fragment();



        //ft.remove(frag1);
        ft.add(R.id.layout_to_replace , frag1);
        //ft.addToBackStack(null);
        //ft.commit();









        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer);
        View header = navigationView.getHeaderView(0);




        navigationView.setNavigationItemSelectedListener(this);

        titleName = (TextView)header.findViewById(R.id.title_name);
        titleImage = (CircularImageView) header.findViewById(R.id.title_Image);
        count = (TextView)header.findViewById(R.id.noti_count);



        message = (ImageButton) header.findViewById(R.id.message);
        messageCount = (TextView)header.findViewById(R.id.mess_count);


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext() , MessageList.class);
                startActivity(intent);

            }
        });


        ImageButton notification = (ImageButton)header.findViewById(R.id.notification);


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MapsActivity.this , NotificationActivity.class);
                startActivity(intent);

            }
        });

        //coverImage = (ImageView)header.findViewById(R.id.header_cover1);





        setProfileDetails();







        // Log.d("titile" , b.userName);



        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.simple_spinner_item, listSpinner1);
        spin.setAdapter(adapter1);

        spin.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);



        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                {


                    query = listSpinner1[position];
                    isNearby = false;



                    progress.setVisibility(View.VISIBLE);


                    lat = lats[position-1];
                    lng = longs[position-1];

//                    resetLocation(lats[position-1] , longs[position-1]);

                    fetchSearch(query);



                }
                else
                {
                    if (mLastLocation!=null)
                    {

                        progress.setVisibility(View.VISIBLE);
                        isNearby = true;
                        query = "";
                        fetchNearby(mLastLocation);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        if(checkPlayServices()) {

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }

        }

        Log.d("asdasdasdNor" , "1");

        Intent intent = new Intent(MapsActivity.this, NotifyService.class);

        MapsActivity.this.startService(intent);

    }



    public void fetchSearch(final String query)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<nearbyBean> call = cr.stateFilter(userId , query);

        call.enqueue(new Callback<nearbyBean>() {
            @Override
            public void onResponse(Call<nearbyBean> call, Response<nearbyBean> response) {

                try {
                    list = response.body().getData();

                    List<Datum> l2 = new ArrayList<Datum>();

                    for (int i = 0 ; i < list.size() ; i++)
                    {
                        if (Objects.equals(list.get(i).getCountry().toLowerCase(), query.toLowerCase()))
                        {
                            l2.add(list.get(i));
                        }
                    }

                    //eventAdapter.setGridData(list);
                    progress.setVisibility(View.VISIBLE);
                    refreshMap(l2);
                    refreshList(l2);
                    resetLocation(lat , lng);
                }catch (NullPointerException e)
                {progress.setVisibility(View.GONE);
                    e.printStackTrace();
                }





            }

            @Override
            public void onFailure(Call<nearbyBean> call, Throwable t) {

            }
        });

    }



    public void fetchNearby(Location location)
    {
        //final List<Datum> list2 = new ArrayList<>();

        //list3 = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final createEvent cr = retrofit.create(createEvent.class);


        Call<nearbyBean> call = cr.nearbyEvents(userId, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        //Call<nearbyBean> call = cr.nearbyEvents(userId, "28.684684684684683", "77.06094706317053");

        //Call<viewBean> call = cr.getAllEvents(userId , String.valueOf(pno[0]));

        call.enqueue(new Callback<nearbyBean>() {
            @Override
            public void onResponse(Call<nearbyBean> call, Response<nearbyBean> response) {

                if (response.body().getResponseMessage().equals("Event Data")) {





                    list = response.body().getData();

                    //eventAdapter.setGridData(list);



                    progress.setVisibility(View.VISIBLE);

                    if (list.size() == 0)
                    {
                        progress.setVisibility(View.GONE);
                    }

                    refreshMap(list);
                    refreshList(list);
                    resetLocation(String.valueOf(mLastLocation.getLatitude()) , String.valueOf(mLastLocation.getLongitude()));


                    //pno[0]++;
                }

            }

            @Override
            public void onFailure(Call<nearbyBean> call, Throwable t) {

            }
        });


    }


    public void fetchWithFilter(Location location , List<String> clist)
    {
        //final List<Datum> list2 = new ArrayList<>();

        //list3 = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final createEvent cr = retrofit.create(createEvent.class);


        Call<nearbyBean> call = cr.nearbyEvents(userId, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        //Call<nearbyBean> call = cr.nearbyEvents(userId, "28.684684684684683", "77.06094706317053");

        //Call<viewBean> call = cr.getAllEvents(userId , String.valueOf(pno[0]));

        call.enqueue(new Callback<nearbyBean>() {
            @Override
            public void onResponse(Call<nearbyBean> call, Response<nearbyBean> response) {

                if (response.body().getResponseMessage().equals("Event Data")) {



               /*     list = response.body().getData();

                    subList = new ArrayList<Datum>();

                    for (int i = 0 ; i < list.size() ; i++)
                    {
                        for (int j = 0 ; j < checkedList.size() ; j++)
                        {
                            if (Objects.equals(list.get(i).getCategoryName(), checkedList.get(j)))
                            {
                                subList.add(list.get(i));
                            }
                        }
                    }

                    //isFilter = true;
*/


                    //eventAdapter.setGridData(subList);
                    progress.setVisibility(View.VISIBLE);
                    refreshList(subList);
                    refreshMap(subList);


                    //pno[0]++;
                }

            }

            @Override
            public void onFailure(Call<nearbyBean> call, Throwable t) {

            }
        });


    }


    /* public void fetch() {
        list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final createEvent cr = retrofit.create(createEvent.class);


        //Call<nearbyBean> call = cr.nearbyEvents(userId, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        //Call<nearbyBean> call = cr.nearbyEvents(userId, "28.684684684684683", "77.06094706317053");

        Call<viewBean> call = cr.getAllEvents(userId , String.valueOf(pno[0]));

        call.enqueue(new Callback<viewBean>() {
            @Override
            public void onResponse(Call<viewBean> call, Response<viewBean> response) {

                if (response.body().getResponseMessage().equals("Event Data")) {


                    List<ViewPOJO.Datum> data = response.body().getData();

                    /*for (int i = 0; i < data.size(); i++) {

                        list.add(data.get(i));
                        //Log.i("asdasdasdas" , data.get(i).getEventId());

                    }
*/
           /*         eventAdapter.setGridData(response.body().getData());

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
                    return new MapFragment();

                case 1:
                    return new ListFragment();
            }


            return null;

        }

        @Override
        public int getCount() {
            return count;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_activity_maps , menu);


        getMenuInflater().inflate(R.menu.menu_activity_maps , menu);

        return true;
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer id = item.getItemId();

        if (id == R.id.action_search) {

            Intent i = new Intent(getApplicationContext() , SearchActivity.class);
            startActivity(i);
            overridePendingTransition(0,0);
            return true;
        }

        else if (id == R.id.filter) {

            Log.d("asdasasasdasd" , "Fiklter");

            if (list.size()>0) {

                final List<String> chooseList = new ArrayList<String>();
                final Set<String> s = new HashSet<String>();

                final Dialog dialog = new Dialog(MapsActivity.this);
                dialog.setContentView(R.layout.filter_dialog);
                dialog.setCancelable(true);


                dialog.show();


                RecyclerView filterList = (RecyclerView) dialog.findViewById(R.id.filter_list);

                TextView filterClick = (TextView) dialog.findViewById(R.id.filter_click);
                TextView filterClear = (TextView) dialog.findViewById(R.id.filter_clear);

                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

                final ChooseCategoryAdapter adap = new ChooseCategoryAdapter(getApplicationContext(), chooseList, subList);
                filterList.setAdapter(adap);
                filterList.setLayoutManager(manager);


                //s.clear();
                //chooseList.clear();

                for (int i = 0; i < list.size(); i++) {
                    chooseList.add(list.get(i).getCategoryName().toLowerCase());
                }

                s.addAll(chooseList);
                chooseList.clear();
                chooseList.addAll(s);

                adap.setGridData(chooseList);


                filterClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        checkedList = new ArrayList<String>();

                        checkedList = adap.getSelectedId();


                        Log.d("asdasdasdchecked", String.valueOf(checkedList.size()));


                        //subList.clear();
                        subList = new ArrayList<Datum>();

                        for (int i = 0; i < list.size(); i++) {
                            for (int j = 0; j < checkedList.size(); j++) {
                                if (Objects.equals(list.get(i).getCategoryName().toLowerCase(), checkedList.get(j).toLowerCase())) {
                                    Log.d("asdasdasdasdasd", "true");
                                    subList.add(list.get(i));
                                }
                            }
                        }

                        isFilter = true;

                        Log.d("asdasdasdasdfiltered", String.valueOf(subList.size()));

                        //eventAdapter.setGridData(subList);
                        progress.setVisibility(View.VISIBLE);
                        refreshList(subList);
                        refreshMap(subList);

                        dialog.dismiss();


                    }
                });


                filterClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        subList.clear();
                        isFilter = false;
                        progress.setVisibility(View.VISIBLE);
                        eventAdapter.setGridData(list);

                        refreshList(list);
                        refreshMap(list);

                        dialog.dismiss();


                    }
                });


            }
            else
            {
                Toast.makeText(context , "No events to filter" , Toast.LENGTH_SHORT).show();
            }



            return true;
        }




        return super.onOptionsItemSelected(item);
    }*/

    public static class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback {


        static GoogleMap mMap;
        static Context context;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.event_map, container, false);

            context = getActivity();

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.eventMapView);

            mapFragment.getMapAsync(this);


         /*   mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Datum item = list.get(Integer.parseInt(marker.getSnippet()));

                    Log.d("asdasdasd" , item.getEventId());



                }
            });


*/

//

            return view;





        }



        public static void resetLocation(String lat , String lon)
        {
            LatLng current = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));


            if (!isNearby)
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,4));
            }
            else
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,12));
            }

        }



        public static void refreshMap(List<Datum> list)
        {



            mMap.clear();

            // Bitmap b = Bitmap.createBitmap(50 , 50 , Bitmap.Config.ARGB_8888);


            //ImageLoader loader = ImageLoader.getInstance();

            LayoutInflater inflater = (LayoutInflater)ListFragment.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            ImageLoader loader = ImageLoader.getInstance();



            for(int i = 0 ; i < list.size() ; i++)
            {

                //Log.d("markerasdasd" , list.get(i).getEventId());

                try {
                    final Datum item = list.get(i);

                    final LatLng mark = new LatLng(Double.parseDouble(item.getLatitude()) , Double.parseDouble(item.getLongitude()));



                    final View pin = inflater.inflate(R.layout.marker , null);
                    pin.layout(0 , 0 , 100 , 100);

                    final ImageView image = (ImageView) pin.findViewById(R.id.marker_image);

                    //loader.displayImage(list.get(i).getImageUrl() , image);

                    //Log.d("asdasdimage" , list.get(i).getImageUrl());


                    //Bitmap mm1 = loader.loadImageSync(item.getImageUrl());

                    //Bitmap mm1 = null;


















                    //image.setImageBitmap(mm1);


                    //if (item.getImageUrl().length()>0)
                    //{
                    //    Picasso.with(context).load(item.getImageUrl()).into(image);
                    //}



                    //loader.displayImage(item.getImageUrl() , image);




                    //= mMap.addMarker(new MarkerOptions().position(mark).title(item.getEventName()).snippet(item.getEventId()).icon(BitmapDescriptorFactory.fromBitmap(mker)));


                    //tmap bmImg = null;









                    Ion.with(context).load(item.getCatImage()).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {

                            try {

                                image.setImageBitmap(result);

                                final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(item.getEventName()).snippet(item.getEventId())
                                        .icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(pin))));

                                markers.put(mar.getId() , item);


                            }catch (NullPointerException e1)
                            {
                                e1.printStackTrace();
                            }


                        }
                    });


               /* try
                {

                    final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(item.getEventName()).snippet(item.getEventId()));

                    markers.put(mar.getId() , item);

                }
                catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }

*/

                    //Bitmap bmImg = loader.loadImageSync(item.getImageUrl() , new ImageSize(70 , 70));






                    //image.setImageBitmap(bmImg);


                    //Bitmap mker = getViewBitmap(pin);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }






            }






            progress.setVisibility(View.GONE);

            //LatLng current1 = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());

            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current1,10));
        }



        public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
            // GET CURRENT SIZE
            int width = bm.getWidth();
            int height = bm.getHeight();
            // GET SCALE SIZE
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            return resizedBitmap;
        }



        public static Bitmap getViewBitmap(View view)
        {
            //Get the dimensions of the view so we can re-layout the view at its current size
            //and create a bitmap of the same size
            int width = view.getWidth();
            int height = view.getHeight();

            int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

            //Cause the view to re-layout
            view.measure(measuredWidth, measuredHeight);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

            //Create a bitmap backed Canvas to draw the view into
            Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);

            //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
            view.draw(c);

            return b;
        }






        @Override
        public void onMapLongClick(LatLng latLng) {
            switch (toggle)
            {
                case "normal":mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    //Log.d("asdasasd","satelite toggled");
                    toggle = "satelite";
                    break;
                case "satelite":mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    // Log.d("asdasasd","normal toggled");
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
            //mMap.setMyLocationEnabled(true);

            mMap.setOnMapLongClickListener(this);

            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent i = new Intent(getActivity() , EventDetailsScreen.class);
                    i.putExtra("eventId" , marker.getSnippet());
                    startActivity(i);
                }
            });

            //mMap.setOnMarkerClickListener(this);
        }


        class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {





            @Override
            public View getInfoContents(Marker marker) {

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_info, null);

                TextView tvTitle = ((TextView)view.findViewById(R.id.title));
                tvTitle.setText(marker.getTitle());
                final TextView tvSnippet = ((TextView)view.findViewById(R.id.id));
                tvSnippet.setText(marker.getSnippet());

                return view;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                // TODO Auto-generated method stub
                return null;
            }


        }




    }



    public static class ListFragment extends Fragment {

        ProgressBar bar;
        static RecyclerView grid;
        //GridLayoutManager layoutManager;
        static Context context;
        static TextView isData;


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

            isData = (TextView)view.findViewById(R.id.is_data);

            bar = (ProgressBar)view.findViewById(R.id.bar);
            bar.setVisibility(View.GONE);

            grid = (RecyclerView)view.findViewById(R.id.eventListView);


            GridLayoutManager manager = new GridLayoutManager(context , 1);


            eventAdapter = new eventListAdapter(context , list);


            grid.setAdapter(eventAdapter);


            grid.setLayoutManager(manager);


            return view;

        }




        public static void refreshList(List<Datum> list)
        {

            if (list.size()>0)
            {
                isData.setVisibility(View.GONE);
            }
            else
            {
                isData.setVisibility(View.VISIBLE);
            }
            eventAdapter.setGridData(list);

            progress.setVisibility(View.GONE);


        }

    }


    protected void onPause() {
        super.onPause();
        if(checkPlayServices()) {
            stopLocationUpdates();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient!=null&&mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void toolbarSetElevation(float elevation) {
        // setElevation() only works on Lollipop
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            scrollId.setElevation(elevation);
        }
    }

    private static void toolbarAnimateShow(final int verticalOffset) {
        scrollId.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbarSetElevation(verticalOffset == 0 ? 0 : TOOLBAR_ELEVATION);
                    }
                });
    }

    private static void toolbarAnimateHide() {
        scrollId.animate()
                .translationY(-scrollId.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
    }


    private void setProfileDetails()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);

        Call<messCountBean> call = cr.getCount(userId);

        call.enqueue(new Callback<messCountBean>() {
            @Override
            public void onResponse(Call<messCountBean> call, Response<messCountBean> response) {

                messageCount.setText(response.body().getData().getCount());

            }

            @Override
            public void onFailure(Call<messCountBean> call, Throwable t) {

            }
        });



        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(false).build();


        bean b = (bean)getApplicationContext();

        imageLoader.displayImage(b.userProfile , titleImage);
        //imageLoader.displayImage(b.userCover , coverImage , options);

        titleName.setText(getString(R.string.hi) + "@" + b.userName);

        int c = pref.getInt("count" , 0);

        count.setText(String.valueOf(c));

    }


    @Override
    protected void onResume() {
        super.onResume();

        setProfileDetails();




        boolean isPopup = getIntent().getBooleanExtra("popup" , false);

        if (isPopup)
        {


            final Dialog dialog = new Dialog(MapsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.exit_popup2);
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

                    Call<String> call1 = cr.resendVerification(userId);

                    call1.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            Toast.makeText(MapsActivity.this , "The verification mail has been sent to your registered email" , Toast.LENGTH_SHORT).show();

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

                }
            });



        }




    }

    @Override
    public void onBackPressed() {



        /*int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }*/

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    private synchronized void buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean checkPlayServices() {

        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        //   Log.i("DEBUG_TAG",
        //          "checkGooglePlayServicesAvailable, connectionStatusCode="
        //                  + checkGooglePlayServices);

        if (GooglePlayServicesUtil.isUserRecoverableError(checkGooglePlayServices)) {
            showGooglePlayServicesAvailabilityErrorDialog(checkGooglePlayServices);
            return false;
        }


        return true;

    }


    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode,MapsActivity.this,
                        PLAY_SERVICES_RESOLUTION_REQUEST);
                if (dialog == null) {
                    //Log.e("DEBUG_TAG",
                    //        "couldn't get GooglePlayServicesUtil.getErrorDialog");
                    Toast.makeText(getBaseContext(),
                            "incompatible version of Google Play Services",
                            Toast.LENGTH_LONG).show();

                    Button button = (Button)findViewById(R.id.button);
                    button.setVisibility(View.GONE);

                    dialog.show();
                }
                //this was wrong here -->dialog.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext() , "Google Play Services must be installed.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (requestCode == 11)
        {

            if (data.getStringExtra("status").equals("success"))
            {
                setProfileDetails();
            }

        }
    }


    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    private void stopLocationUpdates() {

        if(mGoogleApiClient.isConnected()) {
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);
            }
        }
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onResult(@NonNull Status status) {


                        if (status.isSuccess())
                        {
                            bean b = (bean)getApplicationContext();
                            titleImage.setImageResource(R.drawable.user_profile_pic);
                            b.userName = "guest";
                            b.userId = -1;
                            b.loggedIn = false;
                            b.userProfile = "";
                            edit.remove("google");
                            edit.apply();
                            Intent i = new Intent(getApplicationContext() , LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            overridePendingTransition(0,0);
                            finish();

                        }


                    }
                });
    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_log_out)
        {



            if (pref.getBoolean("google" , false)) {
                if (mGoogleApiClient.isConnected()) {
                    signOut();
                }
            } else if (pref.getBoolean("email" , false)) {


                bean b = (bean)getApplicationContext();
                b.userId = -1;
                b.userName = "guest";
                b.loggedIn = false;
                b.userProfile = "";
                b.userCover = "";
                titleImage.setImageResource(R.drawable.user_profile_pic);
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
                finish();
            }else if (pref.getBoolean("facebook" , false))
            {
                LoginManager.getInstance().logOut();
                bean b = (bean)getApplicationContext();
                titleImage.setImageResource(R.drawable.user_profile_pic);
                b.userName = "guest";
                b.userId = -1;
                b.loggedIn = false;
                b.userProfile = "";
                //edit.remove("facebook");
                edit.putInt("count" , 0);
                edit.remove("id");
                edit.putBoolean("facebook" , false);
                edit.apply();
                Intent i = new Intent(getApplicationContext() , LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();

            } else {
                bean b = (bean)getApplicationContext();
                b.loggedIn = false;
                edit.remove("id");
                edit.putInt("count" , 0);
                edit.apply();
                titleImage.setImageResource(R.drawable.user_profile_pic);
                Intent i = new Intent(getApplicationContext() , LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }





        }

        if (id == R.id.view_profile)
        {
            Intent i = new Intent(getApplicationContext() , UserProfileActivity.class);
            startActivity(i);

           /* UserProfileFragment frag1 = new UserProfileFragment();

            //FragmentManager fm = ((MapsActivity)this).getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();


            ft.replace(R.id.layout_to_replace , frag1);
            ft.addToBackStack(null);

            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            }

            ft.commit();
            */

        }
        if (id == R.id.all_request_page)
        {


            Intent i = new Intent(getApplicationContext() , Requests.class);
            startActivity(i);
        }

       /* if (id == R.id.joined_evemts)
        {
            Intent intent = new Intent(getApplicationContext() , JoinedEvents.class);
            startActivity(intent);
        }
        */

        if (id == R.id.posted_events)
        {
            Intent intent = new Intent(getApplicationContext() , PostedEvents.class);
            startActivity(intent);
        }

        if (id == R.id.friends)
        {
            Intent intent = new Intent(getApplicationContext() , Friends.class);
            startActivity(intent);
        }

        if (id == R.id.rating)
        {
            Intent intent = new Intent(getApplicationContext() , ReviewScreen.class);
            startActivity(intent);
        }

        /*if (id == R.id.notification)
        {
            Intent intent = new Intent(getApplicationContext() , NotificationActivity.class);
            startActivity(intent);
        }*/

        /*if (id == R.id.block_list)
        {
            Intent intent = new Intent(getApplicationContext() , BlockedListActivity.class);
            startActivity(intent);
        }*/

        if (id == R.id.users)
        {
            Intent i = new Intent(getApplicationContext() , UsersActivity.class);
            startActivity(i);
        }

        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {


            // Log.d("Asdasdasd" , String.valueOf(mLastLocation.getLatitude())+","+String.valueOf(mLastLocation.getLongitude()));


        /*    if (isFilter)
            {
                fetchWithFilter(mLastLocation , checkedList);
            }
            else
            {
                fetchNearby(mLastLocation);
            }
*/

            if (isNearby)
            {


                if (isFilter)
                {
                    //progress.setVisibility(View.VISIBLE);
                    fetchWithFilter(mLastLocation , checkedList);
                }
                else
                {
                    //progress.setVisibility(View.VISIBLE);
                    lat = String.valueOf(mLastLocation.getLatitude());
                    lng = String.valueOf(mLastLocation.getLongitude());
                    fetchNearby(mLastLocation);
                    resetLocation(String.valueOf(mLastLocation.getLatitude()) , String.valueOf(mLastLocation.getLongitude()));
                }

            }
            else
            {
                if (isFilter)
                {
                    fetchWithFilter(mLastLocation , checkedList);
                }
                else
                {
                    fetchSearch(query);
                }

            }



        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;


/*
        if (isNearby)
        {
            if (isFilter)
            {
                fetchWithFilter(mLastLocation , checkedList);
            }
            else
            {
                lat = String.valueOf(mLastLocation.getLatitude());
                lng = String.valueOf(mLastLocation.getLongitude());
                fetchNearby(mLastLocation);
                resetLocation(String.valueOf(mLastLocation.getLatitude()) , String.valueOf(mLastLocation.getLongitude()));
            }
        }
        else
        {
            //if (isFilter)
            //{
            //    fetchWithFilter(mLastLocation , checkedList);
            //}
            //else
            //{
            //    fetchSearch(query);
            //}

        }

*/







        //Toast.makeText(this, "Latitude:" + mLastLocation.getLatitude()+", Longitude:"+mLastLocation.getLongitude(),Toast.LENGTH_LONG).show();


    }
}