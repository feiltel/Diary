package com.nutdiary.diary.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nutdiary.diary.R;
import com.nutdiary.diary.base.BaseActivity;
import com.nutdiary.diary.base.BaseRecyclerView.CommonAdapter;
import com.nutdiary.diary.base.BaseRecyclerView.MultiItemTypeAdapter;
import com.nutdiary.diary.base.BaseRecyclerView.baseIn.ViewHolder;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.component.MyToast;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.presenter.HomePresenter;
import com.nutdiary.diary.utils.MyPermissionUtils;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.refactor.lib.colordialog.PromptDialog;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.HomeView {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private HomePresenter homePresenter;
    private CommonAdapter<DiaryBean> commonAdapter;
    private List<DiaryBean> mainListItemList = new ArrayList<>();
    private int nowPageNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //权限请求
        MyPermissionUtils.checkAndRequests(this, 12);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
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
        commonAdapter = new CommonAdapter<DiaryBean>(this, R.layout.main_list_item, mainListItemList) {
            @Override
            protected void convert(ViewHolder holder, DiaryBean mainListItem, int position) {
                holder.setText(R.id.content_tv, mainListItem.getContent());
                holder.setText(R.id.date_tv, mainListItem.getLocationName());
                holder.setText(R.id.weather_tv, mainListItem.getMood());
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                final DiaryBean diaryBean = mainListItemList.get(position);
                new PromptDialog(HomeActivity.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                        .setAnimationEnable(true)
                        .setTitleText("删除")
                        .setContentText("确定要删除这条记录吗？")
                        .setPositiveListener("确定", new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                homePresenter.deleteItem(diaryBean.getId(), position);
                            }
                        }).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(commonAdapter);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            nowPageNumber = 1;
            homePresenter.getFirstListData(nowPageNumber);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {

            homePresenter.getListData(nowPageNumber);
        });

        refreshLayout.setRefreshHeader(new DeliveryHeader(this));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
    }

    private void initView() {
        initToolBar();
        initNavigation();
        initRecyclerView();
    }

    private void initEvent() {
        fab.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, LoginActivity.class)));
    }

    private void initNavigation() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_center) {
            // Handle the camera action
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showToast(String msg) {
        MyToast.showToast(msg);
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
    public void finishLoadMore() {
        refreshLayout.finishLoadMore();
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
    public void removeItem(int pos) {
        mainListItemList.remove(pos);
        commonAdapter.notifyItemRemoved(pos);
    }
}
