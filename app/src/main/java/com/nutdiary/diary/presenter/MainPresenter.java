package com.nutdiary.diary.presenter;

import android.support.annotation.NonNull;

import com.nutdiary.diary.base.BasePresenter;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;
import com.nutdiary.diary.contract.MainContract;
import com.nutdiary.diary.model.MainModel;
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

public class MainPresenter extends BasePresenter {
    private MainContract.MainView mainView;
    private MainContract.MainModel mainModel;

    public MainPresenter(MainContract.MainView mainView, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.mainView = mainView;
        mainModel = new MainModel();
    }

    /**
     */
    public void getListData(String token) {
        mainView.showDialog();
        mainModel.getMainListData(token)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<MainListBean>bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<MainListBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull MainListBean mainListBean) {
                        mainView.showToast(mainListBean.getMsg());
                        mainView.dismissDialog();
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

    public void uploadFile(File file) {

        mainView.showDialog();
        mainView.showProgress(0);
        /*RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);*/

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(),
                        new UploadFileRequestBody(file, new UploadFileRequestBody.UploadFileCall() {
                            @Override
                            public void onProgress(int progress) {
                                mainView.showProgress(progress);
                            }
                        }));

        mainModel.uploadFile(body)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().<UploadBean>bindUntilEvent(ActivityEvent.DESTROY)) // onDestroy取消订阅
                .subscribe(new UploadObserver<UploadBean>() {

                    @Override
                    public void onUpLoadSuccess(UploadBean uploadBean) {
                        mainView.dismissDialog();
                    }

                    @Override
                    public void onUpLoadFail(Throwable e) {
                        mainView.dismissDialog();
                    }
                });
    }



    private int nowUploadIndex = 1;
    private int countSum = 0;

    public void uploadFiles(List<File> files) {
        nowUploadIndex = 1;
        countSum = files.size();
        mainView.showDialog();
        mainView.showProgress(0);
        mainView.setDialogMsg(1 + "/" + countSum);
        Observable.just(files)
                .flatMap(new Function<List<File>, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(List<File> fileList) throws Exception {
                        return Observable.fromIterable(fileList);
                    }
                })
                .flatMap(new Function<File, ObservableSource<UploadBean>>() {
                    @Override
                    public ObservableSource<UploadBean> apply(File file) throws Exception {
                        return getUploadBeanObservable(file);
                    }
                })
                /* .onErrorReturnItem(new UploadBean())*/
                //处理文件上传出错
                .onErrorResumeNext(new ObservableSource<UploadBean>() {
                    @Override
                    public void subscribe(Observer<? super UploadBean> observer) {
                        observer.onNext(new UploadBean());
                    }
                })
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
                        //
                        if (uploadBean != null) {
                            mainView.showToast(uploadBean.getMsg());
                        }
                        mainView.setDialogMsg(nowUploadIndex + 1 + "/" + countSum);
                        mainView.showProgress(0);
                        nowUploadIndex++;

                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.showToast(e.toString());
                        mainView.setDialogMsg(nowUploadIndex + 1 + "/" + countSum);
                        mainView.showProgress(0);
                        nowUploadIndex++;
                    }

                    @Override
                    public void onComplete() {
                        mainView.dismissDialog();
                    }
                });
    }

    private Observable<UploadBean> getUploadBeanObservable(File file) {

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(),
                        new UploadFileRequestBody(file, new UploadFileRequestBody.UploadFileCall() {
                            @Override
                            public void onProgress(int progress) {
                                mainView.showProgress(progress);
                            }
                        }));
        return mainModel.uploadFile(body);
    }


}
