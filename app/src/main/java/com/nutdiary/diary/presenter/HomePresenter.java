package com.nutdiary.diary.presenter;

import android.support.annotation.NonNull;

import com.nutdiary.diary.base.BasePresenter;
import com.nutdiary.diary.base.ResultBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.contract.HomeContract;
import com.nutdiary.diary.model.HomeModel;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter {
    private HomeContract.HomeView homeView;
    private HomeContract.HomeModel homeModel;

    public HomePresenter(HomeContract.HomeView homeView, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.homeView = homeView;
        homeModel = new HomeModel();
    }

    public void getListData(Integer page) {

        homeModel.getMainListData(page, 10)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<MainListBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull MainListBean mainListBean) {
                        homeView.finishLoadMore();
                        if (mainListBean.getCode()==0){
                            homeView.addDataAndRefresh(mainListBean.getList());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeView.finishLoadMore();
                        homeView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteItem(Integer id, int listPos) {

        homeModel.delete(id)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<ResultBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull ResultBean resultBean) {
                        if (resultBean.getCode() == 0) {
                            homeView.removeItem(listPos);
                        } else {
                            homeView.showToast(resultBean.getMsg());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeView.finishLoadMore();
                        homeView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getFirstListData(Integer page) {

        homeModel.getMainListData(page, 10)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<MainListBean>bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<MainListBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull MainListBean mainListBean) {
                        homeView.finishRefresh();
                        homeView.clearDataAndRefresh();
                        homeView.addDataAndRefresh(mainListBean.getList());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeView.finishRefresh();
                        homeView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
