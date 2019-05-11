package com.nutdiary.diary.login.login;

import com.nutdiary.diary.baselibrary.base.BaseView;

import io.reactivex.Observable;

public class LoginContract {
    public interface LoginView  extends BaseView {
        void jumpMain(LoginResultBean bean);
    }
    public interface LoginModel{
        Observable<LoginResultBean> userLogin(String phone, String pass);
    }
}
