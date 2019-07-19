package com.nutdiary.diary.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import com.nutdiary.diary.R;
import com.nutdiary.diary.baselibrary.component.MyToast;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyToast.showToast(this,"她哦哦哦她哦哦哦她哦哦哦她哦哦哦");
      /*  ImageView imageView=findViewById(R.id.img_im);
        CommitsBase commitsBase=new CommitsBase();
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=textFormat.parse("2019.3.21");
            Day day= new Day(date,3,"#ebedf0");
            commitsBase.addDay(day);
            imageView.setImageBitmap(TaskView.createBitmap(commitsBase,10,getScreenSize(this),"#ebedf0"));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        new Handler().postDelayed(() -> jumAndFinish(), 1000);

    }

    private Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private void jumAndFinish() {
        startActivity(new Intent(this, HomeActivity.class));
        super.finish();
    }
}
