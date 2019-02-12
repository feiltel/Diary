package com.nutdiary.diary.model;

import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.network.RetrofitHelper;
import com.nutdiary.diary.network.RetrofitService;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class AddDiaryModel implements AddDiaryContract.AddDiaryModel {
    private RetrofitService retrofitService;

    public AddDiaryModel() {
        retrofitService = RetrofitHelper.getInstance().getRetrofitService();
    }


    @Override
    public Observable<MainListBean> saveItemData(MainListItem mainListItem) {
        return retrofitService.saveItemData(mainListItem);
    }


    @Override
    public Observable<UploadBean> uploadFile(MultipartBody.Part file) {

        return retrofitService.uploadFile(file);
    }
}
