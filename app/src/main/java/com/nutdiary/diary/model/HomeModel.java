package com.nutdiary.diary.model;

import com.nutdiary.diary.baselibrary.base.ResultBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.baselibrary.network.RetrofitHelper;
import com.nutdiary.diary.network.RetrofitService;

import io.reactivex.Observable;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class HomeModel implements HomeContract.HomeModel {
    private RetrofitService retrofitService;

    public HomeModel() {
        retrofitService = RetrofitHelper.getInstance().getApiService(RetrofitService.class);
    }

    @Override
    public Observable<MainListBean> getMainListData(Integer page,Integer limit) {
        return retrofitService.getMainListData(page,limit);
    }

    @Override
    public Observable<ResultBean> delete(Integer id) {
        return retrofitService.delete(id);
    }

}
