package com.nutdiary.diary.contract;

import com.nutdiary.diary.base.BaseView;
import com.nutdiary.diary.bean.AddressBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.bean.SaveResultBean;
import com.nutdiary.diary.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class AddDiaryContract {
    public interface AddDiaryView extends BaseView {
        void setAddress(String address,double lat,double lng);
        void finishSave();

    }

    public interface AddDiaryModel {


        Observable<SaveResultBean> saveItemData(DiaryBean mainListItem);
        Observable<AddressBean> getAddress(double lng, double lat);


        Observable<UploadBean> uploadFile(MultipartBody.Part part);
    }

}
