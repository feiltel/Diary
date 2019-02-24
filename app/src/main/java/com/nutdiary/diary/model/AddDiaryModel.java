package com.nutdiary.diary.model;

import com.nutdiary.diary.bean.AddressBean;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.bean.SaveResultBean;
import com.nutdiary.diary.bean.UploadBean;
import com.nutdiary.diary.contract.AddDiaryContract;
import com.nutdiary.diary.network.Constant;
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
    public Observable<SaveResultBean> saveItemData(DiaryBean mainListItem) {
        return retrofitService.saveItemData(mainListItem);
    }

    @Override
    public Observable<AddressBean> getAddress(double lng, double lat) {
        return retrofitService.getAddress(Constant.getAddressUrl(lng, lat));
    }


    @Override
    public Observable<UploadBean> uploadFile(MultipartBody.Part file) {
        return retrofitService.uploadFile(file);
    }
}
