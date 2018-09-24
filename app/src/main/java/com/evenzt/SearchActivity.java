package com.evenzt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import interfaces.eventDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import searchPOJO.Datum;
import searchPOJO.searchBean;

public class SearchActivity extends AppCompatActivity {

    EditText search;
    String userId;
    List<Datum> list;
    RecyclerView grid;

    TextView advSearchToggle;

    GridLayoutManager manager;

    ProgressBar progress;

    SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        overridePendingTransition(0,0);

        progress = (ProgressBar)findViewById(R.id.progress);

        advSearchToggle = (TextView)findViewById(R.id.adv_search);

        grid = (RecyclerView)findViewById(R.id.search_list);

        manager = new GridLayoutManager(this , 1);






        advSearchToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchActivity.this , AdvanceSearch.class);
                startActivity(intent);

            }
        });








        bean b = (bean)getApplicationContext();

        userId = String.valueOf(b.userId);





        search = (EditText)findViewById(R.id.search_box);


        list = new ArrayList<>();
        adapter = new SearchAdapter(this , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        RecyclerView.ItemDecoration itemDecoration = new
                SimpleDividerItemDecoration(this, SimpleDividerItemDecoration.VERTICAL_LIST);

        grid.addItemDecoration(itemDecoration);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {

                final String query = charSequence.toString();

                Log.d("asdasdasd" , query);

                progress.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://evenzt.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                final eventDetails cr = retrofit.create(eventDetails.class);


                Call<searchBean> call = cr.searchQuery(userId , query);

                if (query.length()>0)
                {
                    call.enqueue(new Callback<searchBean>() {
                        @Override
                        public void onResponse(Call<searchBean> call, Response<searchBean> response) {
                            list = response.body().getData();

                            adapter.setGridData(response.body().getData());

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<searchBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        overridePendingTransition(0,0);

    }
}
