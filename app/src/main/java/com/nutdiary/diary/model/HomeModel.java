package com.nutdiary.diary.model;

import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.network.RetrofitHelper;
import com.nutdiary.diary.network.RetrofitService;

import io.reactivex.Observable;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class HomeModel implements HomeContract.HomeModel {
    private RetrofitService retrofitService;

    public HomeModel() {
        retrofitService = RetrofitHelper.getInstance().getRetrofitService();
    }

    @Override
    public Observable<MainListBean> getMainListData(Integer page,Integer limit) {
        return retrofitService.getMainListData(page,limit);
    }

}
