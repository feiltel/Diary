package com.nutdiary.diary.plan.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nutdiary.diary.baselibrary.component.BaseRecyclerView.CommonAdapter;
import com.nutdiary.diary.baselibrary.component.BaseRecyclerView.baseIn.ViewHolder;
import com.nutdiary.diary.plan.R;
import com.nutdiary.diary.plan.bean.PlanItemBean;

import java.util.List;

@Route(path = "/plan/PlanActivity")
public class PlanActivity extends AppCompatActivity {

    private RecyclerView planlistrv;
    private CommonAdapter adapter;
    private List<PlanItemBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
    }

    private void initView() {
        this.planlistrv = findViewById(R.id.plan_list_rv);
        adapter = new CommonAdapter<PlanItemBean>(this,R.layout.plan_time_item_layout,dataList) {

            @Override
            protected void convert(ViewHolder holder, PlanItemBean planItemBean, int position) {

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
