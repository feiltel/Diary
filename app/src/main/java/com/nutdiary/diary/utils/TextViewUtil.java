package com.nutdiary.diary.utils;

import android.widget.TextView;

public class TextViewUtil {
    public static String getString(TextView textView){
        if (textView!=null){
            return textView.getText().toString();
        }
        return "";
    }
}
