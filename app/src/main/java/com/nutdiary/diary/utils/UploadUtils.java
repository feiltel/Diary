package com.nutdiary.diary.utils;

import android.util.Log;

import com.nutdiary.diary.network.RetrofitHelper;
import com.nutdiary.diary.network.UploadFileRequestBody;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class UploadUtils {
    private int nowUploadIndex = 1;
    private int countSum = 0;

    public void uploadFiles(List<File> files) {
        countSum = 0;
        nowUploadIndex = 1;
        countSum = files.size();
        Log.d("allProgress", 1 + "/" + countSum);
        Observable.just(files)
                .flatMap(new Function<List<File>, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(List<File> fileList) throws Exception {
                        return Observable.fromIterable(fileList);
                    }
                })
                .flatMap(new Function<File, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(File file) throws Exception {
                        return getUploadBeanObservable(file);
                    }
                })
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String uploadBean) {
                        //更新总进度
                        nowUploadIndex++;
                        if (nowUploadIndex>countSum){
                            Log.d("allProgress", "finish");
                        }else {
                            Log.d("allProgress", nowUploadIndex  + "/" + countSum);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //上传单个文件
    private Observable<String> getUploadBeanObservable(File file) {

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(),
                        new UploadFileRequestBody(file, new UploadFileRequestBody.UploadFileCall() {
                            @Override
                            public void onProgress(int progress) {
                                StringBuffer stringBuffer=new StringBuffer();
                                for (int i=0;i<progress;i++){
                                    stringBuffer.append("-");
                                }
                                //更新单个进度
                                Log.d("Progress", stringBuffer.toString());
                            }
                        }));
        return RetrofitHelper.getInstance().getRetrofitService().uploadFiles(body).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return "-1";
            }
        });
    }
}
