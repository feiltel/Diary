package com.nutdiary.diary.baselibrary.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nutdiary.diary.baselibrary.R;

/**
 * Created by admin on 2016/3/15.
 */
public class MyToast {
    public static void showToast(Context context, String text) {
        showToast1(context, text, Toast.LENGTH_LONG);
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
        TextView textView = view.findViewById(R.id.text);
        Toast mToast = Toast.makeText(context, text, duration);
        mToast.setGravity(Gravity.TOP,0,0);
        mToast.setView(view);
        textView.setText(text);
        mToast.show();
    }
}
