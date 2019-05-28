package com.nutdiary.diary.login.login;

import android.support.annotation.NonNull;

import com.nutdiary.diary.baselibrary.base.BasePresenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {
    private LoginContract.LoginView homeView;
    private LoginContract.LoginModel homeModel;

    public LoginPresenter(LoginContract.LoginView homeView, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.homeView = homeView;
        homeModel = new LoginModel();
    }

    public void userLogin1(String phone, String pass) {
        homeModel.userLogin(phone, pass)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<LoginResultBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new DefaultObserver<LoginResultBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull LoginResultBean resultBean) {
                        if (resultBean.getCode() == 0) {
                            homeView.jumpMain(resultBean);
                        }
                        homeView.showToast(resultBean.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
