package com.nutdiary.diary.contract;

import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class MainContract {
    public interface MainView {
        void showToast(String msg);
        void showDialog();
        void dismissDialog();
        void showProgress(int progress);
        void setDialogMsg(String msg);
    }

    public interface MainModel {
        Observable<MainListBean> getMainListData(String token);

        Observable<MainListBean> saveItemData(MainListItem mainListItem);


        Observable<UploadBean> uploadFile(MultipartBody.Part part);
    }

}
