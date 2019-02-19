package com.nutdiary.diary.contract;

import com.nutdiary.diary.base.BaseView;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.DiaryBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class HomeContract {
    public interface HomeView extends BaseView{
        void addList(List<DiaryBean> mainListItems);
        void addFail();
        void firstList(List<DiaryBean> mainListItems);
        void firstFail();

    }

    public interface HomeModel {
        Observable<MainListBean> getMainListData(Integer page,Integer limit);
    }

}
