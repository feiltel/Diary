package com.nutdiary.diary.plan.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nutdiary.diary.plan.PlanAdapter;
import com.nutdiary.diary.plan.R;
import com.nutdiary.diary.plan.bean.PlanItemBean;

import java.util.List;

@Route(path = "/plan/PlanActivity")
public class PlanActivity extends AppCompatActivity {

    private RecyclerView planlistrv;
    private PlanAdapter adapter;
    private List<PlanItemBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_activity_main);
        initView();
    }

    private void initView() {
        this.planlistrv = findViewById(R.id.plan_list_rv);
        adapter = new PlanAdapter(R.layout.plan_time_item_layout, dataList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
