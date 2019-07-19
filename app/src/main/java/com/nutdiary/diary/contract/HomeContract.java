package com.nutdiary.diary.contract;

import com.nutdiary.diary.baselibrary.base.BaseView;
import com.nutdiary.diary.baselibrary.base.ResultBean;
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
        void loadMoreComplete();
        void loadMoreFail();
        void loadMoreEnd();

        void finishRefresh();
        void clearDataAndRefresh();
        void addDataAndRefresh(List<DiaryBean> mainListItems);
        void removeItem(int pos);
    }

    public interface HomeModel {
        Observable<MainListBean> getMainListData(Integer page,Integer limit);
        Observable<ResultBean> delete(Integer id);
    }

}
