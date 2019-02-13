package com.nutdiary.diary.contract;

import com.nutdiary.diary.base.BaseView;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class AddDiaryContract {
    public interface AddDiaryView extends BaseView {

    }

    public interface AddDiaryModel {


        Observable<MainListBean> saveItemData(MainListItem mainListItem);


        Observable<UploadBean> uploadFile(MultipartBody.Part part);
    }

}