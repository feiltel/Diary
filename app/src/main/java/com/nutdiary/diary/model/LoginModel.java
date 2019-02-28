package com.nutdiary.diary.model;

import com.nutdiary.diary.base.ResultBean;
import com.nutdiary.diary.bean.LoginResultBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.contract.LoginContract;
import com.nutdiary.diary.network.RetrofitHelper;
import com.nutdiary.diary.network.RetrofitService;

import io.reactivex.Observable;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class LoginModel implements LoginContract.LoginModel {
    private RetrofitService retrofitService;

    public LoginModel() {
        retrofitService = RetrofitHelper.getInstance().getRetrofitService();
    }


    @Override
    public Observable<LoginResultBean> userLogin(String phone, String pass) {
        return retrofitService.userLogin(phone,pass);
    }
}
