package com.nutdiary.diary.baselibrary.component;

import android.support.design.widget.Snackbar;
import android.view.View;

public class MySnackBar {
    public static void show(View view, String content, String actionText, View.OnClickListener onClickListener){
          Snackbar.make(view, content, Snackbar.LENGTH_LONG)
                        .setAction(actionText, onClickListener).show();
    }
}
