package com.nutdiary.diary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nutdiary.diary.component.MyLoadDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 *
 */

public class BaseActivity extends RxAppCompatActivity {
    public MyLoadDialog myLoadDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLoadDialog=new MyLoadDialog(this);
    }
}
