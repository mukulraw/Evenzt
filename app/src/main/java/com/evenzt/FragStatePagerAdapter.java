package com.evenzt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



class FragStatePagerAdapter extends FragmentStatePagerAdapter {


    FragStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new Frag1();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
