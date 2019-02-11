package com.nutdiary.diary.bean;

import com.nutdiary.diary.base.ResultBean;

import java.util.List;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class MainListBean extends ResultBean {
    private List<MainListItem> data;

    public List<MainListItem> getList() {
        return data;
    }

    public void setList(List<MainListItem> list) {
        this.data = list;
    }
}
