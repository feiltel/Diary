package com.nutdiary.diary.view;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nutdiary.diary.R;
import com.nutdiary.diary.bean.DiaryBean;

import java.util.List;

import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

public class MainListAdapter extends BaseQuickAdapter<DiaryBean, BaseViewHolder> {
    public MainListAdapter(int layoutResId, @Nullable List<DiaryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiaryBean mainListItem) {
        BGASwipeItemLayout layout = helper.getView(R.id.swipe_bga);
        layout.close();
        helper.setText(R.id.content_tv, mainListItem.getContent());
        helper.setText(R.id.date_tv, mainListItem.getLocationName());
        helper.setText(R.id.weather_tv, mainListItem.getMood());
        helper.addOnClickListener(R.id.delete_tv);
    }
}
