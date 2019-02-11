package com.nutdiary.diary.contract;

import com.nutdiary.diary.base.BaseView;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class HomeContract {
    public interface HomeView extends BaseView{
        void changeList(List<MainListItem> mainListItems);
    }

    public interface HomeModel {
        Observable<MainListBean> getMainListData(String token);
    }

}
