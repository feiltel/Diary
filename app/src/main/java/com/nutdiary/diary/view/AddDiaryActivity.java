package com.nutdiary.diary.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.nutdiary.diary.R;
import com.nutdiary.diary.base.BaseActivity;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.component.MyToast;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.presenter.AddDiaryPresenter;
import com.nutdiary.diary.utils.LocationUtil;
import com.nutdiary.diary.utils.TextViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private AddDiaryPresenter addDiaryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addDiaryPresenter = new AddDiaryPresenter(this, this);
        ButterKnife.bind(this);
        initView();
        delayShowKeyBoard(contentEt);
    }

    public static void delayShowKeyBoard(final EditText editText){

        if (Build.HOST.contains("google")|| Build.HOST.contains("sony") ||Build.HOST.contains("siajk043cna")){
            editText.setFocusable(true);
        }else {
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    if (editText!=null){
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        InputMethodManager inputManager =
                                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }

                }

            }, 500);



        }

    }
    private void initView() {
        initToolBar();
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("保存");
        LocationUtil.getLocation(this);

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
                String locationName = "陕西省西安市凤城五路";
                double lat = 108.953708; //纬度
                double lng = 34.323827;//经度
                String mood = "高兴";
                DiaryBean diaryBean = new DiaryBean(contentStr, userId, locationName, lat, lng, mood);
                addDiaryPresenter.saveItemData(diaryBean);
                break;
            case R.id.location_tv:
                addDiaryPresenter.getAddress();
                break;
        }
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
