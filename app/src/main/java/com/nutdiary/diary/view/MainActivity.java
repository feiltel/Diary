package com.nutdiary.diary.view;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.nutdiary.diary.R;
import com.nutdiary.diary.base.BaseActivity;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.component.MyToast;
import com.nutdiary.diary.contract.MainContract;
import com.nutdiary.diary.presenter.MainPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.MainView {

    private MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this, this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.getListData("123");
            }
        });
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainListItem mainListItem = new MainListItem();
                mainListItem.setType(1);
                mainListItem.setContent("这是我的第一篇日记");
                mainListItem.setDateStr("2019.1.31");
                mainListItem.setWeather("小雪");
                mainPresenter.saveItemData(mainListItem);
            }
        });

        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory() + File.separator + "test3.png";
                mainPresenter.uploadFile(new File(path));
            }
        });
        findViewById(R.id.upload_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory() + File.separator + "test3.png";
                String path1 = Environment.getExternalStorageDirectory() + File.separator + "test1.png";
                String path2 = Environment.getExternalStorageDirectory() + File.separator + "test2.png";
                String path3 = Environment.getExternalStorageDirectory() + File.separator + "test3.png";

                List<File> fileList = new ArrayList<>();
                fileList.add(new File(path));
                fileList.add(new File(path1));
                fileList.add(new File(path2));
                fileList.add(new File(path3));

                mainPresenter.uploadFiles(fileList);
            }
        });
    }

    @Override
    public void showToast(String msg) {
        MyToast.showToast(msg);
    }

    @Override
    public void showDialog() {
        myLoadDialog.show();
    }

    @Override
    public void dismissDialog() {
        myLoadDialog.hide();
    }

    @Override
    public void showProgress(int progress) {

    }

    @Override
    public void setDialogMsg(String msg) {

    }

}
