package com.nutdiary.diary.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.nutdiary.diary.base.BaseRecyclerView.baseIn.ViewHolder;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.component.MyToast;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.presenter.HomePresenter;
import com.nutdiary.diary.utils.MyPermissionUtils;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private CommonAdapter<MainListItem> commonAdapter;
    private List<MainListItem> mainListItemList = new ArrayList<>();


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
        homePresenter.getListData("123");
    }

    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        titleTv.setText("坚果手记");
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new CommonAdapter<MainListItem>(this, R.layout.main_list_item, mainListItemList) {
            @Override
            protected void convert(ViewHolder holder, MainListItem mainListItem, int position) {
                holder.setText(R.id.content_tv, mainListItem.getContent());
                holder.setText(R.id.date_tv, mainListItem.getDateStr());
                holder.setText(R.id.weather_tv, mainListItem.getWeather());
            }
        };
        recyclerView.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homePresenter.getFirstListData("123");

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                homePresenter.getListData("123");
            }
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddDiaryActivity.class));
            }
        });
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
    public void addList(List<MainListItem> mainListItems) {

        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        mainListItemList.addAll(mainListItems);
        commonAdapter.notifyItemRangeInserted(mainListItemList.size(),mainListItems.size());

    }

    @Override
    public void firstList(List<MainListItem> mainListItems) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        mainListItemList.clear();
        mainListItemList.addAll(mainListItems);
        commonAdapter.notifyDataSetChanged();
    }
}
