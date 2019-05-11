package com.nutdiary.diary.login.network;

import com.nutdiary.diary.login.login.LoginResultBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 请求参数接口
 * Created by tangfei
 */

public interface RetrofitService {


    @POST(Constant.UrlOrigin.user_login)
    Observable<LoginResultBean> userLogin(@Query("phone") String phone, @Query("pass") String pass);

}
