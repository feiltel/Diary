package com.nutdiary.diary.bean;

import com.nutdiary.diary.baselibrary.base.ResultBean;

import java.util.List;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class MainListBean extends ResultBean {
    private List<DiaryBean> data;

    public List<DiaryBean> getList() {
        return data;
    }

    public void setList(List<DiaryBean> list) {
        this.data = list;
    }
}
