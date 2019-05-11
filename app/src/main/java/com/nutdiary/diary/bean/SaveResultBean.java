package com.nutdiary.diary.bean;

import com.nutdiary.diary.baselibrary.base.ResultBean;

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
