package com.nutdiary.diary.contract

import com.nutdiary.diary.base.BaseView
import com.nutdiary.diary.base.ResultBean
import com.nutdiary.diary.bean.DiaryBean
import com.nutdiary.diary.bean.LoginResultBean
import com.nutdiary.diary.bean.MainListBean

import io.reactivex.Observable

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
class LoginContract {
    interface LoginView : BaseView {
        fun jumpMain()
    }

    interface LoginModel {
        fun userLogin(phone: String, pass: String): Observable<LoginResultBean>
    }

}
