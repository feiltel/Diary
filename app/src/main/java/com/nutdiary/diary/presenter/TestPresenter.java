package com.nutdiary.diary.presenter;

/*
public class TestPresenter extends BasePresenter {
    private AddDiaryContract.AddDiaryView mainView;
    private AddDiaryContract.AddDiaryModel mainModel;

    public TestPresenter(AddDiaryContract.AddDiaryView mainView, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.mainView = mainView;
        mainModel = new com.nutdiary.diary.model.AddDiaryModel();
    }


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
      *//* RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);*//*

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
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(UploadBean uploadBean) {
                        //更新总进度
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
    //上传单个文件
    private Observable<UploadBean> getUploadBeanObservable(File file) {

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(),
                        new UploadFileRequestBody(file, new UploadFileRequestBody.UploadFileCall() {
                            @Override
                            public void onProgress(int progress) {
                                //更新单个进度
                                mainView.showProgress(progress);
                            }
                        }));
        return mainModel.uploadFile(body);
    }


}*/
