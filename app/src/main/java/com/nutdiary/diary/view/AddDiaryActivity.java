package com.nutdiary.diary.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nutdiary.diary.R;
import com.nutdiary.diary.base.BaseActivity;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.presenter.AddDiaryPresenter;

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
    private AddDiaryPresenter addDiaryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addDiaryPresenter = new AddDiaryPresenter(this, this);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        initToolBar();
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("保存");
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

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }

    @OnClick(R.id.right_tv)
    public void onViewClicked() {

        addDiaryPresenter.saveItemData(new MainListItem("2019.2.12", contentEt.getText().toString(), 1, "多云"));
    }
}
