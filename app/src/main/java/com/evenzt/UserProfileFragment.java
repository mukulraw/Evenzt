package com.evenzt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;


public class UserProfileFragment extends Fragment {

    ImageView profileCoverImage;
    CircularImageView profilePhotoImage;
    TextView name , email , age;
    TabLayout tabs;
    ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_layout , container , false);


        profileCoverImage = (ImageView)view.findViewById(R.id.profile_cover_image);
        profilePhotoImage = (CircularImageView)view.findViewById(R.id.profile_photo);

        name = (TextView)view.findViewById(R.id.profile_name);
        email = (TextView)view.findViewById(R.id.profile_email);
        age = (TextView)view.findViewById(R.id.profile_age);

        tabs = (TabLayout)view.findViewById(R.id.profile_tabs);

        pager = (ViewPager)view.findViewById(R.id.profile_view_pager);





        return view;
    }



}
