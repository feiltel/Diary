package com.nutdiary.diary.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

import com.nutdiary.diary.R;
import com.nutdiary.diary.bean.ColorTheme;
import com.nutdiary.diary.bean.CommitsBase;
import com.nutdiary.diary.bean.Day;
import com.scwang.smartrefresh.header.waveswipe.DropBounceInterpolator;

import java.util.ArrayList;
import java.util.Date;

public class TaskView extends View implements View.OnClickListener {

    private Paint bgPaint;
    private Paint fgPaint;
    private float mRadius;
    private float ringWidth;
    private int startAngle;
    private int endAngle;

    private int viewEndAngle;
    private RingAnimation ringAnimation;


    private int mRingColor = 0;



    public TaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TaskView);

        mRingColor = typedArray.getColor(R.styleable.TaskView_ring_color, Color.parseColor("#711CFC"));
        ringWidth = typedArray.getDimension(R.styleable.TaskView_ring_width, 0);
        startAngle = typedArray.getInteger(R.styleable.TaskView_start_angle, 0);
        endAngle = startAngle;
        viewEndAngle = typedArray.getInteger(R.styleable.TaskView_end_angle, 90);



        init();
        setOnClickListener(this);
        ringAnimation = new RingAnimation();

        int duration = 10000;
        ringAnimation.setDuration(duration);
        ringAnimation.setInterpolator(new DecelerateInterpolator());
        startAnimation(ringAnimation);
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);// 抗锯齿效果
        bgPaint.setStyle(Paint.Style.STROKE);

        fgPaint = new Paint();
        fgPaint.setAntiAlias(true);// 抗锯齿效果
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
    }

    @Override
    public void onClick(View view) {
      /*  endAngle+=10;
        invalidate();*/


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        mRadius = Math.min(getWidth() / 2, getHeight() / 2);

        ringWidth = ringWidth == 0 ? mRadius / 5 : ringWidth;

        mRadius = mRadius - ringWidth / 2;


        bgPaint.setColor(Color.rgb(234, 234, 234));
        bgPaint.setStrokeWidth(ringWidth);
        canvas.drawCircle(centerX, centerY, mRadius, bgPaint);
        canvas.save();


        fgPaint.setColor(mRingColor);
        fgPaint.setStrokeWidth(ringWidth);
        float left = centerX - mRadius;
        float top = centerY - mRadius;
        float right = centerX + mRadius;
        float bottom = centerY + mRadius;
        canvas.drawArc(new RectF(left, top, right, bottom), startAngle - 90, endAngle - startAngle, false, fgPaint);
        canvas.restore();



    }

    private double radianToAngle(float radios) {
        double aa = ringWidth / 2 / radios;
        double asin = Math.asin(aa);
        double radian = Math.toDegrees(asin);
        return radian;
    }



    public class RingAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (endAngle<viewEndAngle){
                endAngle += 10*interpolatedTime;
                postInvalidate();
            }else {
                ringAnimation.cancel();
            }
            Log.d("rotate",""+endAngle);

        }

    }

    public static  Bitmap createBitmap(CommitsBase base, int weeksNumber, Point size, String theme){
        boolean showDaysLabel=true;
        boolean startOnMonday=true;
        float SPACE_RATIO = 0.1f;
        int TEXT_GRAPH_SPACE = 7;

        float daysLabelSpaceRatio = showDaysLabel ? 0.8f : 0;


        float side = size.x/(weeksNumber+daysLabelSpaceRatio) * (1-SPACE_RATIO);
        float space = size.x/(weeksNumber+daysLabelSpaceRatio) - side;
        float textSize = side*0.87f;

        int height = (int)(7*(side+space)+textSize+TEXT_GRAPH_SPACE);

        ColorTheme colorTheme = new ColorTheme();

        Bitmap bitmap = Bitmap.createBitmap(size.x, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paintText.setTextSize(textSize);
        paintText.setColor(Color.GRAY);

        if(base!=null){
            float x = 0, y;

            // Draw days labels.
            if(showDaysLabel){
                y = startOnMonday ? textSize*2+TEXT_GRAPH_SPACE : textSize*2+TEXT_GRAPH_SPACE+side;
               canvas.drawText("m", 0, y, paintText);
                canvas.drawText("w", 0, y+2*(side+space), paintText);
                canvas.drawText("f", textSize*0.1f, y+4*(side+space), paintText);
                if(startOnMonday)
                    canvas.drawText("s", textSize*0.1f, y+6*(side+space), paintText);

                x = textSize;
            }

            y = textSize+TEXT_GRAPH_SPACE;

            ArrayList<ArrayList<Day>> weeks = base.getWeeks();

            int firstWeek = base.getFirstWeekOfMonth(weeksNumber); //Number of the week above which there will be the first month name.

            for(int i = weeks.size() - weeksNumber; i<weeks.size(); i++){

                // Set the position and draw a month name.
                if( (firstWeek!=-1 && (i-weeks.size()+firstWeek)%4 == 0 && i!=weeks.size()-1) || firstWeek==i){
                    canvas.drawText(weeks.get(i).get(1).getMonthName(), x, textSize, paintText);
                }

                for (Day day : weeks.get(i)){

                    if(startOnMonday && weeks.get(i).indexOf(day)==0)
                        continue;

                    paint.setColor(colorTheme.getColor(theme, day.getLevel()));
                    canvas.drawRect(x, y, x+side, y+side, paint);
                    y = y + side + space;
                }
                if(startOnMonday){
                    try {
                        paint.setColor(colorTheme.getColor(theme, weeks.get(i + 1).get(0).getLevel()));
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                    canvas.drawRect(x, y, x+side, y+side, paint);
                    y = y + side + space;
                }
                y = textSize+TEXT_GRAPH_SPACE;
                x = x + side + space;
            }
        }

        return bitmap;
    }
}
