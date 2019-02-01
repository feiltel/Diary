package com.nutdiary.diary.network;

import io.reactivex.observers.DefaultObserver;

/**
 * 上传文件的RxJava2回调
 */

public abstract class UploadObserver<T> extends DefaultObserver<T> {

    @Override
    public void onNext(T t) {
        onUpLoadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onUpLoadFail(e);
    }

    @Override
    public void onComplete() {

    }


    //上传成功的回调
    public abstract void onUpLoadSuccess(T t);

    //上传失败回调
    public abstract void onUpLoadFail(Throwable e);


}
