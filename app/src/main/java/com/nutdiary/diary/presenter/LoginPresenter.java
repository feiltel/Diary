package com.nutdiary.diary.presenter;

import android.support.annotation.NonNull;

import com.nutdiary.diary.base.BasePresenter;
import com.nutdiary.diary.base.ResultBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.contract.LoginContract;
import com.nutdiary.diary.model.HomeModel;
import com.nutdiary.diary.model.LoginModel;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter{
    private LoginContract.LoginModel loginModel;
    private LoginContract.LoginView loginView;
    public LoginPresenter(LoginContract.LoginView loginView,LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.loginView = loginView;
        loginModel = new LoginModel();
    }
    public void userLogin(String phone,String pass){
        loginModel.userLogin(phone,pass)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<ResultBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull ResultBean resultBean) {
                        if (resultBean.getCode()==1){
                            //jump

                        }
                        loginView.showToast(resultBean.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loginView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
