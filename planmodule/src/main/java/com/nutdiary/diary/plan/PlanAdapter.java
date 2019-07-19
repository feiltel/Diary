package com.nutdiary.diary.plan;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nutdiary.diary.plan.bean.PlanItemBean;

import java.util.List;

public class PlanAdapter extends BaseQuickAdapter<PlanItemBean,BaseViewHolder> {
    public PlanAdapter(int layoutResId, @Nullable List<PlanItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanItemBean item) {

    }
}
