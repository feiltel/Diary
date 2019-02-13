package com.nutdiary.diary.presenter;

import android.support.annotation.NonNull;

import com.nutdiary.diary.base.BasePresenter;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.UploadBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.model.HomeModel;
import com.nutdiary.diary.network.UploadFileRequestBody;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.List;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class HomePresenter extends BasePresenter {
    private HomeContract.HomeView homeView;
    private HomeContract.HomeModel homeModel;

    public HomePresenter(HomeContract.HomeView homeView,LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.homeView=homeView;
        homeModel=new HomeModel();
    }
    public void getListData(String token) {

        homeModel.getMainListData(token)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<MainListBean>bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<MainListBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull MainListBean mainListBean) {
                        homeView.addList(mainListBean.getList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeView.addFail();
                        homeView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getFirstListData(String token) {

        homeModel.getMainListData(token)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<MainListBean>bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<MainListBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull MainListBean mainListBean) {
                        homeView.firstList(mainListBean.getList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeView.firstFail();
                        homeView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
