package com.evenzt;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TrapeziumFrameLayout extends View{
    private Paint bgPaint;


    public TrapeziumFrameLayout(Context context) {
        super(context);
        init();
    }

    public TrapeziumFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrapeziumFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        bgPaint= new Paint();
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int h = getMeasuredHeight();
        int w = getMeasuredWidth();

        Path path = new Path();
        path.moveTo(w,0);
        path.lineTo(w , (2*h)/3);
        path.lineTo(0, h);
        path.lineTo(0, h/3);
        path.lineTo(w, 0);
        path.close();
        canvas.drawPath(path,bgPaint);
        canvas.clipPath(path);

    }
}
