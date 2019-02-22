package com.nutdiary.diary.view;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.nutdiary.diary.R;
import com.nutdiary.diary.base.BaseActivity;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.component.MyToast;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.presenter.AddDiaryPresenter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addDiaryPresenter = new AddDiaryPresenter(this, this);
        ButterKnife.bind(this);
        initView();
        InputMethodManager imm = (InputMethodManager) contentEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            contentEt.requestFocus();
            imm.showSoftInput(contentEt, 0);
        }
        if (MyPermissionUtils.checkAndRequest(this, Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION_REQUEST_CODE)) {
            addDiaryPresenter.getAddress(LocationUtil.getLocation(this));
        }
    }


    private void initView() {
        initToolBar();
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("保存");
        // LocationUtil.getLocation(this);
        smileRating.setSelectedSmile(BaseRating.OKAY);
        phoneNameTv.setText(PhoneUtil.getDeviceName());

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

    @OnClick(R.id.right_tv)
    public void onViewClicked() {

    }

    @OnClick({R.id.right_tv, R.id.location_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                String contentStr = TextViewUtil.getString(contentEt);
                int userId = 21;
                String locationName = TextViewUtil.getString(locationTv);
                double lat = 108.953708; //纬度
                double lng = 34.323827;//经度
                String mood = smileRating.getSmileName(smileRating.getSelectedSmile());
                DiaryBean diaryBean = new DiaryBean(contentStr, userId, locationName, lat, lng, mood);
                addDiaryPresenter.saveItemData(diaryBean);
                break;
            case R.id.location_tv:
                if (MyPermissionUtils.checkAndRequest(this, Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION_REQUEST_CODE)) {
                    if (!isOPen(this)) {
                        openGPS(this);
                    }
                    addDiaryPresenter.getAddress(LocationUtil.getLocation(this));
                }


                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int perCode = MyPermissionUtils.getRequestPermissionsResult(this, requestCode, permissions, grantResults, ACCESS_COARSE_LOCATION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("Permissions", perCode + ">>");
        if (perCode==1){
            addDiaryPresenter.getAddress(LocationUtil.getLocation(this));
        }
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return gps;
    }

    @Override
    public void setAddress(String address) {
        locationTv.setText(address);
    }

    @Override
    public void finishSave() {
        super.finish();
    }
}
