package com.nutdiary.diary.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nutdiary.diary.R;
import com.nutdiary.diary.app.MyApp;

/**
 * Created by admin on 2016/3/15.
 */
public class MyToast {
    public static void showToast(String text) {
        showToast1(MyApp.getContext(), text, Toast.LENGTH_SHORT);
    }

    //系统原生toast
    public static void showToast(Context context, String text, final int duration) {
        Toast mToast = Toast.makeText(context, text, duration);
        //单独设置为了解决miui显示app名称的问题
        mToast.setText(text);
        mToast.show();
    }

    //自定义布局的toast
    public static void showToast1(Context context, String text, final int duration) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_root
                , null);
        TextView  textView =  view.findViewById(R.id.text);
        Toast   mToast = Toast.makeText(context, text, duration);
        mToast.setView(view);
        textView.setText(text);
        mToast.show();
    }
}
