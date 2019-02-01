package com.nutdiary.diary.network;

import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.MainListItem;
import com.nutdiary.diary.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 请求参数接口
 * Created by yangle on 2017/6/26.
 */

public interface RetrofitService {


    /**
     *
     * @param token token
     * @return
     */
    @GET(Constant.UrlOrigin.get_main_list_data)
    Observable<MainListBean> getMainListData(@Query("token") String token);


    /**
     * POST
     * @param user
     * @return
     */
    @POST(Constant.UrlOrigin.save_item_data)
    Observable<MainListBean> saveItemData(@Body  MainListItem mainListItem);



    @Multipart
    @POST("uploads")
    Observable<UploadBean> uploadFile(@Part MultipartBody.Part file);
}
