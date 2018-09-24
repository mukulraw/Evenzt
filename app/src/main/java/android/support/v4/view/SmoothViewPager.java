package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;

public class SmoothViewPager extends ViewPager {
    private int mVelocity = 1;

    public SmoothViewPager(Context context) {
        super(context);
    }

    public SmoothViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void smoothScrollTo(int x, int y, int velocity) {
        //ignore passed velocity, use one defined here
        super.smoothScrollTo(x, y, mVelocity);
    }





}