package com.nutdiary.diary.presenter;

import android.support.annotation.NonNull;

import com.nutdiary.diary.base.BasePresenter;
import com.nutdiary.diary.bean.AddressBean;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.SaveResultBean;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class AddDiaryPresenter extends BasePresenter {
    private AddDiaryContract.AddDiaryView addDiaryView;
    private AddDiaryContract.AddDiaryModel mainModel;

    public AddDiaryPresenter(AddDiaryContract.AddDiaryView addDiaryView, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.addDiaryView = addDiaryView;
        mainModel = new com.nutdiary.diary.model.AddDiaryModel();
    }


    public void saveItemData(DiaryBean mainListItem) {

        mainModel.saveItemData(mainListItem)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<SaveResultBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull SaveResultBean saveResultBean) {
                        addDiaryView.showToast(saveResultBean.getMsg());
                        if (saveResultBean.getCode() == 1) {
                            addDiaryView.finishSave();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addDiaryView.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        addDiaryView.showToast("onComplete");
                    }
                });


    }

    public void getAddress() {
        double lng = 108.94160443;
        double lat = 34.3303392;
        mainModel.getAddress(lat, lng)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<AddressBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull AddressBean addressBean) {
                        addDiaryView.setAddress(addressBean.getResult().getAddressComponent().getCity());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

}
