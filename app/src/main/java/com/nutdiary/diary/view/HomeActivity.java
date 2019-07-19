package com.nutdiary.diary.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nutdiary.diary.R;
import com.nutdiary.diary.baselibrary.base.BaseActivity;
import com.nutdiary.diary.baselibrary.base.UserData;
import com.nutdiary.diary.baselibrary.utils.MyPermissionUtils;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.presenter.HomePresenter;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.refactor.lib.colordialog.PromptDialog;

public class HomeActivity extends BaseActivity implements HomeContract.HomeView {
    private static final int jump2loginRequestCode = 21;

    private TextView titleTv;
    private Toolbar toolbar;
    private TextView rightTv;


    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private SmartRefreshLayout refreshLayout;

    private HomePresenter homePresenter;
    private MainListAdapter commonAdapter;
    private List<DiaryBean> mainListItemList = new ArrayList<>();
    private int nowPageNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //权限请求
        MyPermissionUtils.checkAndRequests(this, 12);
        setContentView(R.layout.app_bar_home);
        homePresenter = new HomePresenter(this, this);
        initView();
        initEvent();
        homePresenter.getListData(1);
    }


    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        titleTv.setText("坚果手记");
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new MainListAdapter(R.layout.main_list_item, mainListItemList);
        commonAdapter.setOnItemClickListener((adapter, view, position) -> {

        });

        commonAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DiaryBean diaryBean = mainListItemList.get(position);
            new PromptDialog(HomeActivity.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                    .setAnimationEnable(true)
                    .setTitleText("删除")
                    .setContentText("确定要删除这条记录吗？")
                    .setPositiveListener("确定", dialog -> {
                        dialog.dismiss();
                        homePresenter.deleteItem(diaryBean.getId(), position);
                    }).show();
        });

        recyclerView.setAdapter(commonAdapter);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            nowPageNumber = 1;
            homePresenter.getFirstListData(nowPageNumber);
        });
        commonAdapter.setOnLoadMoreListener(() -> homePresenter.getListData(nowPageNumber));
        refreshLayout.setRefreshHeader(new DeliveryHeader(this));
    }

    private void initView() {
        this.titleTv = findViewById(R.id.title_tv);
        this.toolbar = findViewById(R.id.toolbar);
        this.rightTv = findViewById(R.id.right_tv);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.fab = findViewById(R.id.fab);
        this.refreshLayout = findViewById(R.id.refreshLayout);


        initToolBar();
        initRecyclerView();
    }

    private void initEvent() {
        fab.setOnClickListener(view -> {
            if (UserData.isLogin(this)) {
                startActivity(new Intent(HomeActivity.this, AddDiaryActivity.class));
            } else {
                ARouter.getInstance().build("/login/LoginMainActivity").navigation();
            }
        });
    }


    @Override
    public void showToast(String msg) {
        showTip(msg);
    }

    @Override
    public void showLoadDialog() {
        myLoadDialog.show();
    }

    @Override
    public void hideLoadDialog() {
        myLoadDialog.hide();
    }

    @Override
    public void loadMoreComplete() {
        commonAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        commonAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreEnd() {
        commonAdapter.loadMoreEnd();
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void clearDataAndRefresh() {
        nowPageNumber = 1;
        mainListItemList.clear();
        commonAdapter.notifyItemRangeChanged(0, mainListItemList.size());
    }

    @Override
    public void addDataAndRefresh(List<DiaryBean> mainListItems) {
        if (mainListItems.size() > 0) {
            mainListItemList.addAll(mainListItems);
            commonAdapter.notifyItemRangeInserted(mainListItemList.size(), mainListItems.size());
            nowPageNumber++;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == jump2loginRequestCode && resultCode == RESULT_OK) {
            startActivity(new Intent(HomeActivity.this, AddDiaryActivity.class));
        }
    }

    @Override
    public void removeItem(int pos) {
        mainListItemList.remove(pos);
        commonAdapter.notifyItemRemoved(pos);
    }
}
