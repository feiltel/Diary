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

    private int courrentIndex = 1;

    public void uploadJsonAndFiles(List<UploadJsonAndFiles> uploadJsonAndFilesList) {
        Observable.just(uploadJsonAndFilesList)
                .flatMap(new Function<List<UploadJsonAndFiles>, ObservableSource<UploadJsonAndFiles>>() {
                    @Override
                    public ObservableSource<UploadJsonAndFiles> apply(List<UploadJsonAndFiles> uploadJsonAndFilesList) throws Exception {
                        return Observable.fromIterable(uploadJsonAndFilesList);
                    }
                })
                .flatMap(new Function<UploadJsonAndFiles, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(UploadJsonAndFiles uploadJsonAndFiles) throws Exception {
                        return createJsonAndFileObservable(uploadJsonAndFiles);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("allprogress", courrentIndex + "完成");
                        courrentIndex++;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private int fileIndex = 1;

    private Observable<Integer> createJsonAndFileObservable(UploadJsonAndFiles uploadJsonAndFiles) {
        fileIndex = 1;
        return Observable.just(uploadJsonAndFiles).flatMap(new Function<UploadJsonAndFiles, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(UploadJsonAndFiles uploadJsonAndFiles) throws Exception {
                Log.d("progress", "上传第一个JSON");
                //第一个json
                return Observable.just(uploadJsonAndFiles.getJsonItemStr()).flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        //第二个
                        Log.d("progress", "上传第2个JSON");
                        return Observable.just(uploadJsonAndFiles.getJsonDotStr()).flatMap(new Function<String, ObservableSource<Integer>>() {
                            @Override
                            public ObservableSource<Integer> apply(String integer) throws Exception {
                                return Observable.just(uploadJsonAndFiles.getUploadFileInfoList()).flatMap(new Function<List<UploadFileInfo>, ObservableSource<UploadFileInfo>>() {
                                    @Override
                                    public ObservableSource<UploadFileInfo> apply(List<UploadFileInfo> uploadFileInfos) throws Exception {
                                        return Observable.fromIterable(uploadFileInfos);
                                    }
                                }).flatMap(new Function<UploadFileInfo, ObservableSource<Integer>>() {
                                    @Override
                                    public ObservableSource<Integer> apply(UploadFileInfo uploadFileInfo) throws Exception {
                                        Log.d("filprogress", fileIndex + "完成");
                                        fileIndex++;
                                        return Observable.just(uploadFileInfo).flatMap(new Function<UploadFileInfo, ObservableSource<Integer>>() {
                                            @Override
                                            public ObservableSource<Integer> apply(UploadFileInfo uploadFileInfo) throws Exception {
                                                return null;
                                            }
                                        }).onErrorReturn(new Function<Throwable, Integer>() {
                                            @Override
                                            public Integer apply(Throwable throwable) throws Exception {
                                                return 2;
                                            }
                                        });
                                    }
                                }).onErrorReturn(new Function<Throwable, Integer>() {
                                    @Override
                                    public Integer apply(Throwable throwable) throws Exception {
                                        return 1;
                                    }
                                });

                            }
                        });

                    }
                });
            }
        });
    }


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
                        if (nowUploadIndex > countSum) {
                            Log.d("allProgress", "finish");
                        } else {
                            Log.d("allProgress", nowUploadIndex + "/" + countSum);
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
                                StringBuffer stringBuffer = new StringBuffer();
                                for (int i = 0; i < progress; i++) {
                                    stringBuffer.append("-");
                                }
                                //更新单个进度
                                Log.d("Progress", stringBuffer.toString());
                            }
                        }));
        return RetrofitHelper.getInstance().getRetrofitService().uploadFiles(body).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return null;
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return "-1";
            }
        });
    }
}
