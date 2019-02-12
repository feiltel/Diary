package com.nutdiary.diary.presenter;

import android.support.annotation.NonNull;

import com.nutdiary.diary.base.BasePresenter;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.network.UploadFileRequestBody;
import com.nutdiary.diary.network.UploadObserver;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class AddDiaryPresenter extends BasePresenter {
    private AddDiaryContract.AddDiaryView mainView;
    private AddDiaryContract.AddDiaryModel mainModel;

    public AddDiaryPresenter(AddDiaryContract.AddDiaryView mainView, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.mainView = mainView;
        mainModel = new com.nutdiary.diary.model.AddDiaryModel();
    }


    public void saveItemData(MainListItem mainListItem) {
        mainModel.saveItemData(mainListItem)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<MainListBean>bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<MainListBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull MainListBean mainListBean) {
                        mainView.showToast(mainListBean.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        mainView.showToast("onComplete");
                    }
                });
    }







}
