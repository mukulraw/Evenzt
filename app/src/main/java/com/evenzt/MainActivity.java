package com.evenzt;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    ScrollView scroll;
    TextView additional , additionalHide , membersJoined;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = (TabLayout)findViewById(R.id.tabs);

        scroll = (ScrollView)findViewById(R.id.scroll);

        LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);
        LinearLayout view1 = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);
        LinearLayout view2 = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);
        LinearLayout view3 = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);
        LinearLayout view4 = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);
        LinearLayout view5 = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);
        LinearLayout view6 = (LinearLayout)getLayoutInflater().inflate(R.layout.tab_view , null);

        tabs.addTab(tabs.newTab().setCustomView(view1));
        tabs.addTab(tabs.newTab().setCustomView(view2));
        tabs.addTab(tabs.newTab().setCustomView(view3));
        tabs.addTab(tabs.newTab().setCustomView(view4));
        tabs.addTab(tabs.newTab().setCustomView(view5));
        tabs.addTab(tabs.newTab().setCustomView(view6));


        additional = (TextView)findViewById(R.id.additional);
        additionalHide = (TextView)findViewById(R.id.additional_hide);
        membersJoined = (TextView)findViewById(R.id.members_joined);

        additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (additionalHide.getVisibility() == View.GONE)
                {
                    additionalHide.setVisibility(View.VISIBLE);
                    scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            scroll.post(new Runnable() {
                                public void run() {
                                    scroll.fullScroll(View.FOCUS_DOWN);
                                }
                            });
                        }
                    });
                }
                else
                {
                    additionalHide.setVisibility(View.GONE);
                }

            }
        });

        tabs.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);


        membersJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext() , JoinedMembers.class);
                startActivity(i);

            }
        });


        //tabs.setBackgroundResource(R.drawable.tab_background);

    }
}
