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
import com.nutdiary.diary.base.BaseActivity;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.component.MyToast;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.localData.UserData;
import com.nutdiary.diary.presenter.AddDiaryPresenter;
import com.nutdiary.diary.utils.InputUtil;
import com.nutdiary.diary.utils.LocationUtil;
import com.nutdiary.diary.utils.MyPermissionUtils;
import com.nutdiary.diary.utils.PhoneUtil;
import com.nutdiary.diary.utils.TextViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nutdiary.diary.utils.MyPermissionUtils.ACCESS_COARSE_LOCATION_REQUEST_CODE;

public class AddDiaryActivity extends BaseActivity implements AddDiaryContract.AddDiaryView {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.smile_rating)
    SmileRating smileRating;
    @BindView(R.id.phone_name_tv)
    TextView phoneNameTv;

    private AddDiaryPresenter addDiaryPresenter;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addDiaryPresenter = new AddDiaryPresenter(this, this);
        ButterKnife.bind(this);
        initView();
        getAddressInfo();

    }


    private void initView() {
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
        MyToast.showToast(msg);
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }


    @OnClick({R.id.right_tv, R.id.location_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                //保存按钮
                String contentStr = TextViewUtil.getString(contentEt); //日记内容
                String locationName = TextViewUtil.getString(locationTv); //地点名称
                String mood = smileRating.getSmileName(smileRating.getSelectedSmile()); //心情
                //保存
                DiaryBean diaryBean = new DiaryBean(contentStr, UserData.getUserUUID(), locationName, lat, lng, mood);
                addDiaryPresenter.saveItemData(diaryBean);
                break;
            case R.id.location_tv:
                //获取位置信息
                getAddressInfo();
                break;
        }
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
}
