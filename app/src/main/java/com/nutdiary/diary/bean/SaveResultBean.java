package com.nutdiary.diary.bean;

import com.nutdiary.diary.base.ResultBean;

import java.util.List;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class SaveResultBean extends ResultBean {
    private DiaryBean data;

    public DiaryBean getData() {
        return data;
    }

    public void setData(DiaryBean data) {
        this.data = data;
    }
}
