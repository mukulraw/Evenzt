package com.evenzt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import interfaces.eventDetails;
import profilePOJO.profileBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditProfile extends AppCompatActivity {

    EditText   phone , interest , education , occupation , bio , age , organ , fullName;

    SearchableSpinner country , gender;

    TextView save;

    String userId;

    ProgressBar progress;

    String countr , gendr;


    private final String[] listSpinner1 = {"--Select country--","Afghanistan",
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


    private final String[] listSpinner2 = {"Male" , "Female" , "Unspecified"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progress = (ProgressBar)findViewById(R.id.progress);

        bean b = (bean)getApplicationContext();

        country = (SearchableSpinner) findViewById(R.id.edit_country);
        gender = (SearchableSpinner) findViewById(R.id.edit_gender);
        phone = (EditText) findViewById(R.id.edit_phone);
        interest = (EditText) findViewById(R.id.edit_interest);
        education = (EditText) findViewById(R.id.edit_education);
        occupation = (EditText) findViewById(R.id.edit_occupation);
        bio = (EditText) findViewById(R.id.edit_bio);
        age = (EditText) findViewById(R.id.edit_age);
        organ = (EditText) findViewById(R.id.edit_organ);
        fullName = (EditText) findViewById(R.id.edit_name);
        save = (TextView) findViewById(R.id.edit_edit);


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    gendr = listSpinner2[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.simple_spinner_item2, listSpinner2);
        gender.setAdapter(adapter2);





        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                {
                    countr = listSpinner1[position];
                }
                else
                {
                    countr = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.simple_spinner_item2, listSpinner1);
        country.setAdapter(adapter1);

        userId = String.valueOf(b.userId);


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




                //country.setText(response.body().getData().getCountry());

                        phone.setText(response.body().getData().getPhone());
                interest.setText(response.body().getData().getInterest());
                        education.setText(response.body().getData().getEducation());
                occupation.setText(response.body().getData().getOccupation());
                        bio.setText(response.body().getData().getBio());
                        organ.setText(response.body().getData().getOrganization());
                        age.setText(response.body().getData().getAge());
                        fullName.setText(response.body().getData().getFullName());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<profileBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progress.setVisibility(View.VISIBLE);

                Call<profileBean> call = cr.editProfile(userId , countr , interest.getText().toString() , occupation.getText().toString() , education.getText().toString() , bio.getText().toString() , phone.getText().toString() , age.getText().toString() , organ.getText().toString() , gendr , fullName.getText().toString());

                call.enqueue(new Callback<profileBean>() {
                    @Override
                    public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                        Toast.makeText(getApplicationContext() , "Updated Successfully" , Toast.LENGTH_SHORT).show();

                        countr = "";

                        progress.setVisibility(View.GONE);

                        finish();

                    }

                    @Override
                    public void onFailure(Call<profileBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(EditProfile.this , "Error" , Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });



    }
}
