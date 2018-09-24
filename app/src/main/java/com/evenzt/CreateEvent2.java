package com.evenzt;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import createPOJO.createBean;
import friendsPOJO.Datum;
import friendsPOJO.friendsBean;
import interfaces.createEvent;


import interfaces.eventDetails;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateEvent2 extends AppCompatActivity implements OnMapReadyCallback {

    TextView title;
    private GoogleMap mMap;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    TextView startDate, endDate, eventTime, end_Time;
    String start_date = "", end_date = "", event_time = "", end_time = "";
    EditText aboutEvent, eventTitle, state;
    SearchableSpinner country;
    EditText address;
    TextView makePublic, makePrivate;
    ImageView preview;
    String lat = "", lng = "";
    Boolean isPublic = true;
    String venue = "";
    FloatingActionButton create;
    TextView pickLocation, pickImage;
    private BottomSheetBehavior mBottomSheetBehavior;

    String iidd;

    ChooseFriendsAdapter adapter;

    CoordinatorLayout coordinatorLayout;

    GridLayoutManager manager;

    RecyclerView chooseFriends;

    List<String> friends;

    Dialog progress;

    EditText city, keywords;

    List<Datum> list;

    String date1 = "", date2 = "";

    String h1 = "", m1 = "", h2 = "", m2 = "";

    Date d1, d2;

    SimpleDateFormat sdf;

    TextView head;
    String countr = "";
    LinearLayout footer;


    private final String[] listSpinner1 = {"--Select country--", "Afghanistan",
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


    Bitmap bmp;
    private String mCurrentPhotoPath;
    private final int PICK_IMAGE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        //progress = (ProgressBar)findViewById(R.id.progress);

        progress = new Dialog(CreateEvent2.this);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setCancelable(true);
        progress.setContentView(R.layout.progress_layout);

        sdf = new SimpleDateFormat("MM-dd-yyyy");


        title = (TextView) findViewById(R.id.party_title);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        address = (EditText) findViewById(R.id.venue);
        eventTime = (TextView) findViewById(R.id.event_time);
        end_Time = (TextView) findViewById(R.id.event_end_time);
        eventTitle = (EditText) findViewById(R.id.title);
        country = (SearchableSpinner) findViewById(R.id.country);
        state = (EditText) findViewById(R.id.state);
        city = (EditText) findViewById(R.id.city);
        keywords = (EditText) findViewById(R.id.keywords);
        aboutEvent = (EditText) findViewById(R.id.about_event);

        makePublic = (TextView) findViewById(R.id.make_piblic);
        makePrivate = (TextView) findViewById(R.id.make_private);
        create = (FloatingActionButton) findViewById(R.id.create);
        pickLocation = (TextView) findViewById(R.id.pick_location);
        pickImage = (TextView) findViewById(R.id.pick_image);
        preview = (ImageView) findViewById(R.id.preview_image);

        footer = (LinearLayout) findViewById(R.id.footer);


        final bean b = (bean) getApplicationContext();
        eventTitle.setText(b.title);
        aboutEvent.setText(b.details);
        keywords.setText(b.keywords);

        keywords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.keywords = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        state.setText(b.state);

        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                b.state = String.valueOf(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        city.setText(b.city);

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.city = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        address.setText(b.address);

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.address = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        eventTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                b.title = String.valueOf(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        aboutEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.details = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    countr = listSpinner1[position];
                } else {
                    countr = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.simple_spinner_item1, listSpinner1);
        country.setAdapter(adapter1);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate);


        friends = new ArrayList<>();

        list = new ArrayList<>();

        chooseFriends = (RecyclerView) findViewById(R.id.choose_friends);

        manager = new GridLayoutManager(this, 1);

        adapter = new ChooseFriendsAdapter(this, list);

        chooseFriends.setAdapter(adapter);

        chooseFriends.setLayoutManager(manager);

        head = (TextView) findViewById(R.id.head);


        View bottom = (View) coordinatorLayout.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });


        ViewTreeObserver vto = head.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                mBottomSheetBehavior.setPeekHeight(head.getHeight());
                footer.setPadding(0, 0, 0, head.getHeight());

                head.getViewTreeObserver().removeGlobalOnLayoutListener(this);


            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final eventDetails cr = retrofit.create(eventDetails.class);


        Call<friendsBean> call = cr.getFriends(String.valueOf(b.userId));

        call.enqueue(new Callback<friendsBean>() {
            @Override
            public void onResponse(Call<friendsBean> call, Response<friendsBean> response) {


                if (response.body().getData() != null) {
                    adapter.setGridData(response.body().getData());
                }


            }

            @Override
            public void onFailure(Call<friendsBean> call, Throwable t) {

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);


        int id = getIntent().getExtras().getInt("id");


        iidd = String.valueOf(id);


        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);


            }
        });


        pickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(CreateEvent2.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });

        if (isPublic) {
            makePublic.setTextColor(Color.WHITE);
            makePublic.setBackgroundColor(Color.RED);
        }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createYourEvent();
            }
        });


        makePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPublic = true;
                makePublic.setTextColor(Color.WHITE);
                makePublic.setBackgroundColor(Color.RED);
                makePrivate.setTextColor(Color.BLACK);
                makePrivate.setBackgroundColor(Color.WHITE);
            }
        });

        makePrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPublic = false;
                makePrivate.setTextColor(Color.WHITE);
                makePrivate.setBackgroundColor(Color.RED);
                makePublic.setTextColor(Color.BLACK);
                makePublic.setBackgroundColor(Color.WHITE);

            }
        });


        title.setText(getIntent().getExtras().getString("name"));
        //title.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(b.list.get(id).getResId()) , null , null , null);
        //title.setCompoundDrawablePadding(10);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(CreateEvent2.this);
                dialog.setContentView(R.layout.date_picker_dialog);
                dialog.setCancelable(true);
                dialog.show();
                final DatePicker picker = (DatePicker) dialog.findViewById(R.id.pick_date);

                picker.setMinDate(System.currentTimeMillis());

                final TextView pick = (TextView) dialog.findViewById(R.id.select_date);

                pick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start_date = String.valueOf(picker.getYear()) + ":" + String.valueOf(picker.getMonth() + 1) + ":" + String.valueOf(picker.getDayOfMonth());
                        startDate.setText(start_date);
                        date1 = String.valueOf(picker.getMonth() + 1) + "-" + String.valueOf(picker.getDayOfMonth()) + "-" + String.valueOf(picker.getYear());

                        try {
                            d1 = sdf.parse(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });

            }
        });


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(CreateEvent2.this);
                dialog.setContentView(R.layout.date_picker_dialog);
                dialog.setCancelable(true);

                dialog.show();

                final DatePicker picker = (DatePicker) dialog.findViewById(R.id.pick_date);

                picker.setMinDate(System.currentTimeMillis());

                TextView pick = (TextView) dialog.findViewById(R.id.select_date);

                pick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        end_date = String.valueOf(picker.getYear()) + ":" + String.valueOf(picker.getMonth() + 1) + ":" + String.valueOf(picker.getDayOfMonth());
                        endDate.setText(end_date);
                        date2 = String.valueOf(picker.getMonth() + 1) + "-" + String.valueOf(picker.getDayOfMonth()) + "-" + String.valueOf(picker.getYear());

                        try {
                            d2 = sdf.parse(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });

            }
        });


        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(CreateEvent2.this);
                dialog.setContentView(R.layout.time_picker_dialog);
                dialog.setCancelable(true);
                dialog.show();
                final TimePicker picker = (TimePicker) dialog.findViewById(R.id.pick_time);


                TextView pick = (TextView) dialog.findViewById(R.id.select_time);

                pick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        event_time = String.valueOf(picker.getCurrentHour()) + ":" + String.valueOf(picker.getCurrentMinute());
                        eventTime.setText(event_time);
                        h1 = String.valueOf(picker.getCurrentHour());
                        m1 = String.valueOf(picker.getCurrentMinute());
                        dialog.dismiss();
                    }
                });

            }
        });


        end_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(CreateEvent2.this);
                dialog.setContentView(R.layout.time_picker_dialog);
                dialog.setCancelable(true);
                dialog.show();
                final TimePicker picker = (TimePicker) dialog.findViewById(R.id.pick_time);
                TextView pick = (TextView) dialog.findViewById(R.id.select_time);

                pick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        end_time = String.valueOf(picker.getCurrentHour()) + ":" + String.valueOf(picker.getCurrentMinute());
                        end_Time.setText(end_time);
                        h2 = String.valueOf(picker.getCurrentHour());
                        m2 = String.valueOf(picker.getCurrentMinute());
                        dialog.dismiss();
                    }
                });

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(String.valueOf(data.getData())));

                //browse_image.setImageBitmap(bitmap);
                Uri selectedImageUri = data.getData();

                //bean.setBrowse(bitmap);


                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                preview.setImageBitmap(bitmap);

                mCurrentPhotoPath = getPath(getApplicationContext(), selectedImageUri);


                //path = selectedImageUri.getPath();

                //path = String.valueOf(selectedImageUri);
                Log.d("asdasdasd", String.valueOf(selectedImageUri));


                Log.d("asdasdasd", mCurrentPhotoPath);
                //ph = new File(path);


                //uri = getImageUri(getApplicationContext() , bitmap);


                //String bit = getStringImage(bitmap);
                //bean.setBrowse(bit);
                //bo = bit;
                //Log.d("asdasdasd" , bit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();


            lat = String.valueOf(place.getLatLng().latitude);
            lng = String.valueOf(place.getLatLng().longitude);

            LatLng NFC = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);


            mMap.clear();


            mMap.addMarker(new MarkerOptions().position(NFC).title("Your event").anchor(0.5f, 1));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(NFC));


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    private static String getPath(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

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
                    final String[] selectionArgs = new String[]{
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


    private void createYourEvent() {
        progress.show();

        bean b = (bean) getApplicationContext();
        String userId = String.valueOf(b.userId);
        String catId = iidd;
        String eventName = eventTitle.getText().toString();
        String eventDesc = aboutEvent.getText().toString();
        String startTime = start_date;
        String endTime = end_date;
        String eventtime = event_time;
        String eventend = end_time;
        String v = address.getText().toString();
        String video = "";
        String eventType = "";
        if (isPublic) {
            eventType = "public";
        } else {
            eventType = "private";
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://evenzt.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        createEvent cr = retrofit.create(createEvent.class);

        File file = null;


        MultipartBody.Part body = null;
        try {

            if (mCurrentPhotoPath.length() > 0) {
                file = new File(mCurrentPhotoPath);
            }

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            body = MultipartBody.Part.createFormData("video", file.getName(), reqFile);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        if (eventName.length() > 0) {

            if (startTime.length() > 0) {

                if (endTime.length() > 0) {

                    if (eventtime.length() > 0) {


                        if (eventend.length() > 0) {
                            if (eventDesc.length() > 0) {

                                if (v.length() > 0) {
                                    if (lat.length() > 0 && lng.length() > 0) {

                                        Calendar calendar = Calendar.getInstance();


                                        if (d1.compareTo(d2) < 0) {
                                            if (isPublic) {

                                                friends = new ArrayList<>();
                                                friends = adapter.getCheckedIds();
                                                try {
                                                    Call<createBean> call = cr.createEventWithImage(userId, catId, eventName, eventDesc, startTime, endTime, eventtime, v, lat, lng, "", body, eventType, eventend, state.getText().toString(), countr, city.getText().toString(), keywords.getText().toString());

                                                    call.enqueue(new Callback<createBean>() {
                                                        @Override
                                                        public void onResponse(Call<createBean> call, Response<createBean> response) {
                                                            Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                                                            eventTitle.setText("");
                                                            aboutEvent.setText("");
                                                            start_date = "";
                                                            end_date = "";
                                                            event_time = "";
                                                            keywords.setText("");
                                                            city.setText("");
                                                            isPublic = true;
                                                            startDate.setText(R.string.start_date1);
                                                            endDate.setText(R.string.end_date1);
                                                            eventTime.setText(R.string.start_time1);
                                                            end_Time.setText(R.string.end_time1);
                                                            state.setText("");
                                                            address.setText("");
                                                            venue = "";
                                                            lat = "";
                                                            lng = "";
                                                            mCurrentPhotoPath = "";

                                                            bean b = (bean) getApplicationContext();

                                                            b.title = "";
                                                            b.details = "";
                                                            b.keywords = "";
                                                            b.state = "";
                                                            b.city = "";
                                                            b.address = "";


                                                            if (friends.size() > 0) {

                                                                for (int i = 0; i < friends.size(); i++) {

                                                                    Retrofit retrofit = new Retrofit.Builder()
                                                                            .baseUrl("http://evenzt.com/")
                                                                            .addConverterFactory(ScalarsConverterFactory.create())
                                                                            .addConverterFactory(GsonConverterFactory.create())
                                                                            .build();

                                                                    final eventDetails cr = retrofit.create(eventDetails.class);


                                                                    Call<String> call2 = cr.sendInvite(friends.get(i), response.body().getData().getEventId());
                                                                    final int finalI = i;
                                                                    call2.enqueue(new Callback<String>() {
                                                                        @Override
                                                                        public void onResponse(Call<String> call, Response<String> response) {
                                                                            if (finalI == friends.size() - 1) {
                                                                                progress.dismiss();
                                                                                finish();
                                                                            }


                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<String> call, Throwable t) {
                                                                            progress.dismiss();
                                                                            t.printStackTrace();

                                                                        }
                                                                    });

                                                                }
                                                            } else {
                                                                progress.dismiss();
                                                                finish();
                                                            }


                                                        }

                                                        @Override
                                                        public void onFailure(Call<createBean> call, Throwable t) {
                                                            progress.dismiss();
                                                        }
                                                    });

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                friends = new ArrayList<>();
                                                friends = adapter.getCheckedIds();

                                                Log.d("asdasdasdasdasdadasd", String.valueOf(friends.size()));

                                                if (friends.size() > 0) {
                                                    try {
                                                        Call<createBean> call = cr.createEventWithImage(userId, catId, eventName, eventDesc, startTime, endTime, eventtime, v, lat, lng, "", body, eventType, eventend, state.getText().toString(), countr, city.getText().toString(), keywords.getText().toString());

                                                        Log.d("asdasdasd", startTime + " , " + endTime);

                                                        call.enqueue(new Callback<createBean>() {
                                                            @Override
                                                            public void onResponse(Call<createBean> call, Response<createBean> response) {
                                                                Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                                                                eventTitle.setText("");
                                                                aboutEvent.setText("");
                                                                start_date = "";
                                                                end_date = "";
                                                                event_time = "";
                                                                state.setText("");
                                                                address.setText("");
                                                                venue = "";
                                                                keywords.setText("");
                                                                city.setText("");
                                                                isPublic = true;
                                                                startDate.setText(R.string.start_date1);
                                                                endDate.setText(R.string.end_date1);
                                                                eventTime.setText(R.string.start_time1);
                                                                end_Time.setText(R.string.end_time1);
                                                                lat = "";
                                                                lng = "";
                                                                mCurrentPhotoPath = "";

                                                                bean b = (bean) getApplicationContext();


                                                                b.title = "";
                                                                b.details = "";
                                                                b.keywords = "";
                                                                b.state = "";
                                                                b.city = "";
                                                                b.address = "";


                                                                if (friends.size() > 0) {
                                                                    for (int i = 0; i < friends.size(); i++) {

                                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                                .baseUrl("http://evenzt.com/")
                                                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                                                .addConverterFactory(GsonConverterFactory.create())
                                                                                .build();

                                                                        final eventDetails cr = retrofit.create(eventDetails.class);


                                                                        Call<String> call2 = cr.sendInvite(friends.get(i), response.body().getData().getEventId());
                                                                        final int finalI = i;
                                                                        call2.enqueue(new Callback<String>() {
                                                                            @Override
                                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                                if (finalI == friends.size() - 1) {
                                                                                    progress.dismiss();
                                                                                    finish();
                                                                                }

                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                                progress.dismiss();
                                                                                t.printStackTrace();
                                                                            }
                                                                        });
                                                                    }
                                                                } else {
                                                                    progress.dismiss();
                                                                    finish();
                                                                }


                                                            }

                                                            @Override
                                                            public void onFailure(Call<createBean> call, Throwable t) {
                                                                progress.dismiss();
                                                            }
                                                        });

                                                    } catch (Exception e) {
                                                        progress.dismiss();
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    progress.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Please select friends to invite", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else if (d1.compareTo(d2) == 0) {
                                            if (Integer.parseInt(h1) < Integer.parseInt(h2)) {
                                                if (isPublic) {

                                                    friends = new ArrayList<>();
                                                    friends = adapter.getCheckedIds();
                                                    try {
                                                        Call<createBean> call = cr.createEventWithImage(userId, catId, eventName, eventDesc, startTime, endTime, eventtime, v, lat, lng, "", body, eventType, eventend, state.getText().toString(), countr, city.getText().toString(), keywords.getText().toString());

                                                        Log.d("asdasdasd", startTime + " , " + endTime);

                                                        call.enqueue(new Callback<createBean>() {
                                                            @Override
                                                            public void onResponse(Call<createBean> call, Response<createBean> response) {
                                                                Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                                                                eventTitle.setText("");
                                                                aboutEvent.setText("");
                                                                start_date = "";
                                                                end_date = "";
                                                                event_time = "";
                                                                state.setText("");
                                                                address.setText("");
                                                                venue = "";
                                                                keywords.setText("");
                                                                city.setText("");
                                                                isPublic = true;
                                                                startDate.setText(R.string.start_date1);
                                                                endDate.setText(R.string.end_date1);
                                                                eventTime.setText(R.string.start_time1);
                                                                end_Time.setText(R.string.end_time1);
                                                                lat = "";
                                                                lng = "";
                                                                mCurrentPhotoPath = "";

                                                                bean b = (bean) getApplicationContext();

                                                                b.title = "";
                                                                b.details = "";
                                                                b.keywords = "";
                                                                b.state = "";
                                                                b.city = "";
                                                                b.address = "";


                                                                if (friends.size() > 0) {

                                                                    for (int i = 0; i < friends.size(); i++) {

                                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                                .baseUrl("http://evenzt.com/")
                                                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                                                .addConverterFactory(GsonConverterFactory.create())
                                                                                .build();

                                                                        final eventDetails cr = retrofit.create(eventDetails.class);


                                                                        Call<String> call2 = cr.sendInvite(friends.get(i), response.body().getData().getEventId());
                                                                        final int finalI = i;
                                                                        call2.enqueue(new Callback<String>() {
                                                                            @Override
                                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                                if (finalI == friends.size() - 1) {
                                                                                    progress.dismiss();
                                                                                    finish();
                                                                                }

                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                                progress.dismiss();
                                                                                t.printStackTrace();
                                                                            }
                                                                        });

                                                                    }
                                                                } else {
                                                                    progress.dismiss();
                                                                    finish();
                                                                }


                                                            }

                                                            @Override
                                                            public void onFailure(Call<createBean> call, Throwable t) {
                                                                progress.dismiss();
                                                            }
                                                        });

                                                    } catch (Exception e) {
                                                        progress.dismiss();
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    friends = new ArrayList<>();
                                                    friends = adapter.getCheckedIds();

                                                    Log.d("asdasdasdasdasdadasd", String.valueOf(friends.size()));

                                                    if (friends.size() > 0) {
                                                        try {
                                                            Call<createBean> call = cr.createEventWithImage(userId, catId, eventName, eventDesc, startTime, endTime, eventtime, v, lat, lng, "", body, eventType, eventend, state.getText().toString(), countr, city.getText().toString(), keywords.getText().toString());

                                                            Log.d("asdasdasd", startTime + " , " + endTime);

                                                            call.enqueue(new Callback<createBean>() {
                                                                @Override
                                                                public void onResponse(Call<createBean> call, Response<createBean> response) {
                                                                    Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                                                                    eventTitle.setText("");
                                                                    aboutEvent.setText("");
                                                                    start_date = "";
                                                                    keywords.setText("");
                                                                    city.setText("");
                                                                    end_date = "";
                                                                    event_time = "";
                                                                    state.setText("");
                                                                    address.setText("");
                                                                    venue = "";
                                                                    isPublic = true;
                                                                    startDate.setText(R.string.start_date1);
                                                                    endDate.setText(R.string.end_date1);
                                                                    eventTime.setText(R.string.start_time1);
                                                                    end_Time.setText(R.string.end_time1);
                                                                    lat = "";
                                                                    lng = "";
                                                                    mCurrentPhotoPath = "";

                                                                    bean b = (bean) getApplicationContext();

                                                                    b.title = "";
                                                                    b.details = "";
                                                                    b.keywords = "";
                                                                    b.state = "";
                                                                    b.city = "";
                                                                    b.address = "";


                                                                    if (friends.size() > 0) {
                                                                        for (int i = 0; i < friends.size(); i++) {

                                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                                    .baseUrl("http://evenzt.com/")
                                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                                    .build();

                                                                            final eventDetails cr = retrofit.create(eventDetails.class);


                                                                            Call<String> call2 = cr.sendInvite(friends.get(i), response.body().getData().getEventId());
                                                                            final int finalI = i;
                                                                            call2.enqueue(new Callback<String>() {
                                                                                @Override
                                                                                public void onResponse(Call<String> call, Response<String> response) {
                                                                                    if (finalI == friends.size() - 1) {
                                                                                        progress.dismiss();
                                                                                        finish();
                                                                                    }

                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<String> call, Throwable t) {
                                                                                    progress.dismiss();
                                                                                    t.printStackTrace();
                                                                                }
                                                                            });
                                                                        }
                                                                    } else {
                                                                        progress.dismiss();
                                                                        finish();
                                                                    }


                                                                }

                                                                @Override
                                                                public void onFailure(Call<createBean> call, Throwable t) {
                                                                    progress.dismiss();
                                                                }
                                                            });

                                                        } catch (Exception e) {
                                                            progress.dismiss();
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        progress.dismiss();
                                                        Toast.makeText(getApplicationContext(), "Please select friends to invite", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            } else if (Integer.parseInt(h1) == Integer.parseInt(h2)) {

                                                if (Integer.parseInt(m1) < Integer.parseInt(m2)) {
                                                    if (isPublic) {

                                                        friends = new ArrayList<>();
                                                        friends = adapter.getCheckedIds();
                                                        try {
                                                            Call<createBean> call = cr.createEventWithImage(userId, catId, eventName, eventDesc, startTime, endTime, eventtime, v, lat, lng, "", body, eventType, eventend, state.getText().toString(), countr, city.getText().toString(), keywords.getText().toString());

                                                            Log.d("asdasdasd", startTime + " , " + endTime);

                                                            call.enqueue(new Callback<createBean>() {
                                                                @Override
                                                                public void onResponse(Call<createBean> call, Response<createBean> response) {
                                                                    Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                                                                    eventTitle.setText("");
                                                                    aboutEvent.setText("");
                                                                    start_date = "";
                                                                    end_date = "";
                                                                    keywords.setText("");
                                                                    city.setText("");
                                                                    event_time = "";
                                                                    state.setText("");
                                                                    address.setText("");
                                                                    venue = "";
                                                                    isPublic = true;
                                                                    startDate.setText(R.string.start_date1);
                                                                    endDate.setText(R.string.end_date1);
                                                                    eventTime.setText(R.string.start_time1);
                                                                    end_Time.setText(R.string.end_time1);
                                                                    lat = "";
                                                                    lng = "";
                                                                    mCurrentPhotoPath = "";

                                                                    bean b = (bean) getApplicationContext();

                                                                    b.title = "";
                                                                    b.details = "";
                                                                    b.keywords = "";
                                                                    b.state = "";
                                                                    b.city = "";
                                                                    b.address = "";


                                                                    if (friends.size() > 0) {

                                                                        for (int i = 0; i < friends.size(); i++) {

                                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                                    .baseUrl("http://evenzt.com/")
                                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                                    .build();

                                                                            final eventDetails cr = retrofit.create(eventDetails.class);


                                                                            Call<String> call2 = cr.sendInvite(friends.get(i), response.body().getData().getEventId());
                                                                            final int finalI = i;
                                                                            call2.enqueue(new Callback<String>() {
                                                                                @Override
                                                                                public void onResponse(Call<String> call, Response<String> response) {
                                                                                    if (finalI == friends.size() - 1) {
                                                                                        progress.dismiss();
                                                                                        finish();
                                                                                    }

                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<String> call, Throwable t) {
                                                                                    progress.dismiss();
                                                                                    t.printStackTrace();
                                                                                }
                                                                            });

                                                                        }
                                                                    } else {
                                                                        progress.dismiss();
                                                                        finish();
                                                                    }

                                                                }

                                                                @Override
                                                                public void onFailure(Call<createBean> call, Throwable t) {
                                                                    progress.dismiss();
                                                                }
                                                            });

                                                        } catch (Exception e) {
                                                            progress.dismiss();
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        friends = new ArrayList<>();
                                                        friends = adapter.getCheckedIds();

                                                        Log.d("asdasdasdasdasdadasd", String.valueOf(friends.size()));

                                                        if (friends.size() > 0) {
                                                            try {
                                                                Call<createBean> call = cr.createEventWithImage(userId, catId, eventName, eventDesc, startTime, endTime, eventtime, v, lat, lng, "", body, eventType, eventend, state.getText().toString(), countr, city.getText().toString(), keywords.getText().toString());

                                                                Log.d("asdasdasd", startTime + " , " + endTime);

                                                                call.enqueue(new Callback<createBean>() {
                                                                    @Override
                                                                    public void onResponse(Call<createBean> call, Response<createBean> response) {
                                                                        Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                                                                        eventTitle.setText("");
                                                                        aboutEvent.setText("");
                                                                        start_date = "";
                                                                        end_date = "";
                                                                        event_time = "";
                                                                        state.setText("");
                                                                        address.setText("");
                                                                        venue = "";
                                                                        isPublic = true;
                                                                        startDate.setText(R.string.start_date1);
                                                                        endDate.setText(R.string.end_date1);
                                                                        eventTime.setText(R.string.start_time1);
                                                                        end_Time.setText(R.string.end_time1);
                                                                        keywords.setText("");
                                                                        city.setText("");
                                                                        lat = "";
                                                                        lng = "";
                                                                        mCurrentPhotoPath = "";

                                                                        bean b = (bean) getApplicationContext();

                                                                        b.title = "";
                                                                        b.details = "";
                                                                        b.keywords = "";
                                                                        b.state = "";
                                                                        b.city = "";
                                                                        b.address = "";


                                                                        if (friends.size() > 0) {
                                                                            for (int i = 0; i < friends.size(); i++) {

                                                                                Retrofit retrofit = new Retrofit.Builder()
                                                                                        .baseUrl("http://evenzt.com/")
                                                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                                                        .addConverterFactory(GsonConverterFactory.create())
                                                                                        .build();

                                                                                final eventDetails cr = retrofit.create(eventDetails.class);


                                                                                Call<String> call2 = cr.sendInvite(friends.get(i), response.body().getData().getEventId());

                                                                                final int finalI = i;
                                                                                call2.enqueue(new Callback<String>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<String> call, Response<String> response) {
                                                                                        if (finalI == friends.size() - 1) {
                                                                                            progress.dismiss();
                                                                                            finish();
                                                                                        }


                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<String> call, Throwable t) {
                                                                                        progress.dismiss();
                                                                                        t.printStackTrace();
                                                                                    }
                                                                                });
                                                                            }
                                                                        } else {
                                                                            progress.dismiss();
                                                                            finish();
                                                                        }


                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<createBean> call, Throwable t) {
                                                                        progress.dismiss();
                                                                    }
                                                                });

                                                            } catch (Exception e) {
                                                                progress.dismiss();
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            progress.dismiss();
                                                            Toast.makeText(getApplicationContext(), "Please select friends to invite", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } else {
                                                    progress.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Invalid event time", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        } else {
                                            progress.dismiss();
                                            Toast.makeText(getApplicationContext(), "Invalid event date", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                        progress.dismiss();
                                        Toast.makeText(getApplicationContext(), "Please select a location", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Please enter an address", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(), "Please enter a short description about event", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Please select event start time", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Please select event start time", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), "Please select event end date", Toast.LENGTH_SHORT).show();
                }

            } else {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Please select event start date", Toast.LENGTH_SHORT).show();
            }

        } else {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), "Please enter event title", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng NFC = new LatLng(28.561824, 77.270743);


        mMap.addMarker(new MarkerOptions().position(NFC).title("Party in the Batcave").anchor(0.5f, 1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NFC));
    }


    @Override
    public void onBackPressed() {


        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }

    }
}
