package com.nutdiary.diary.view;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.nutdiary.diary.R;
import com.nutdiary.diary.baselibrary.base.BaseActivity;
import com.nutdiary.diary.baselibrary.base.UserData;
import com.nutdiary.diary.baselibrary.utils.InputUtil;
import com.nutdiary.diary.baselibrary.utils.LocationUtil;
import com.nutdiary.diary.baselibrary.utils.MyPermissionUtils;
import com.nutdiary.diary.baselibrary.utils.PhoneUtil;
import com.nutdiary.diary.baselibrary.utils.TextViewUtil;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.presenter.AddDiaryPresenter;

import static com.nutdiary.diary.baselibrary.utils.MyPermissionUtils.ACCESS_COARSE_LOCATION_REQUEST_CODE;

public class AddDiaryActivity extends BaseActivity implements AddDiaryContract.AddDiaryView, View.OnClickListener {

    private TextView titleTv;
    private Toolbar toolbar;
    private TextView rightTv;

    private EditText contentEt;
    private SmileRating smileRating;
    private TextView locationTv;
    private TextView phoneNameTv;

    private AddDiaryPresenter addDiaryPresenter;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addDiaryPresenter = new AddDiaryPresenter(this, this);
        initView();
        initEvent();
        getAddressInfo();

    }

    private void initEvent() {
        locationTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
    }


    private void initView() {
        this.titleTv = findViewById(R.id.title_tv);
        this.toolbar = findViewById(R.id.toolbar);
        this.rightTv = findViewById(R.id.right_tv);

        this.phoneNameTv = findViewById(R.id.phone_name_tv);
        this.locationTv = findViewById(R.id.location_tv);
        this.smileRating = findViewById(R.id.smile_rating);
        this.contentEt = findViewById(R.id.content_et);


        initToolBar();
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("保存");
        smileRating.setSelectedSmile(BaseRating.OKAY);
        phoneNameTv.setText(PhoneUtil.getDeviceName());
        InputUtil.showKeyBoard(contentEt);

    }

    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        titleTv.setText("新增");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String msg) {
        showTip(msg);
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限返回结果
        int perCode = MyPermissionUtils.getRequestPermissionsResult(this, requestCode, permissions, grantResults, ACCESS_COARSE_LOCATION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
        if (perCode == 1) {
            getAddressInfo();
        }
    }

    private void getAddressInfo() {
        if (MyPermissionUtils.checkAndRequest(this, Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION_REQUEST_CODE)) {
            addDiaryPresenter.getAddress(LocationUtil.getLocation(this));
        }
    }


    @Override
    public void setAddress(String address, double lat, double lng) {
        locationTv.setText(address);
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void finishSave() {
        super.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                //保存按钮
                String contentStr = TextViewUtil.getString(contentEt); //日记内容
                String locationName = TextViewUtil.getString(locationTv); //地点名称
                String mood = smileRating.getSmileName(smileRating.getSelectedSmile()); //心情
                //保存
                DiaryBean diaryBean = new DiaryBean(contentStr, UserData.getUserUUID(this), locationName, lat, lng, mood);
                addDiaryPresenter.saveItemData(diaryBean);
                break;
            case R.id.location_tv:
                //获取位置信息
                getAddressInfo();
                break;
        }
    }
}
