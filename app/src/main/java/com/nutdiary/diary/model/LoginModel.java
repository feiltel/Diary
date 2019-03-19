package com.nutdiary.diary.model;

import com.nutdiary.diary.bean.LoginResultBean;
import com.nutdiary.diary.contract.LoginContract;
import com.nutdiary.diary.baselibrary.network.RetrofitHelper;
import com.nutdiary.diary.network.RetrofitService;

import io.reactivex.Observable;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class LoginModel implements LoginContract.LoginModel {
    private RetrofitService retrofitService;

    public LoginModel() {
        retrofitService = RetrofitHelper.getInstance().getApiService(RetrofitService.class);
    }


    @Override
    public Observable<LoginResultBean> userLogin(String phone, String pass) {
        return retrofitService.userLogin(phone,pass);
    }
}
