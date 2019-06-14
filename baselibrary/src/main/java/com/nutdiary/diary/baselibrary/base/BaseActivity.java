package com.nutdiary.diary.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.nutdiary.diary.baselibrary.component.MyLoadDialog;
import com.nutdiary.diary.baselibrary.component.MyToast;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 *
 */

public class BaseActivity extends RxAppCompatActivity {
    public MyLoadDialog myLoadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLoadDialog = new MyLoadDialog(this);

    }
    public void showTip(String msg){
        MyToast.showToast(this,msg);
    }

}
