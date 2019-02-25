package com.nutdiary.diary.contract;

import com.nutdiary.diary.base.BaseView;
import com.nutdiary.diary.base.ResultBean;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.bean.MainListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class LoginContract {
    public interface LoginView extends BaseView{

    }

    public interface LoginModel {
        Observable<ResultBean> userLogin(String phone,String pass);
    }

}
