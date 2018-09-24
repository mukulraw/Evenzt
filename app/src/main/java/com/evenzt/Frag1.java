package com.evenzt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Frag1 extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.pager_fragment, container, false);

        RecyclerView joinedlist = (RecyclerView) v.findViewById(R.id.posted_events_list);

        List<String> list = new ArrayList<>();
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");
        list.add("asd");


        PostedEventsAdapter adapter = new PostedEventsAdapter(getActivity() , list);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity() ,1);

        joinedlist.setAdapter(adapter);
        joinedlist.setLayoutManager(layoutManager);

        return v;

    }
}
