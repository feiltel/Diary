package com.nutdiary.diary.model;

import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.contract.MainContract;
import com.nutdiary.diary.network.RetrofitHelper;
import com.nutdiary.diary.network.RetrofitService;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
    public Observable<MainListBean> getMainListData(String token) {
        return retrofitService.getMainListData(token);
    }

}
