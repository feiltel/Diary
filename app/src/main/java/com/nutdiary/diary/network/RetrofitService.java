package com.nutdiary.diary.network;

import com.nutdiary.diary.bean.AddressBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.bean.SaveResultBean;
import com.nutdiary.diary.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
    Observable<MainListBean> getMainListData(@Query("page") Integer page,@Query("limit") Integer limit);


    /**
     * POST
     * @param user
     * @return
     */
    @POST(Constant.UrlOrigin.save_item_data)
    Observable<SaveResultBean> saveItemData(@Body DiaryBean mainListItem);


    @GET
    Observable<AddressBean> getAddress(@Url String url);

    @Multipart
    @POST(Constant.UrlOrigin.upload_pic)
    Observable<UploadBean> uploadFile(@Part MultipartBody.Part file);


    //测试用
    @Multipart
    @POST("uploads")
    Observable<String> uploadFiles(@Part MultipartBody.Part file);

    @GET(Constant.UrlOrigin.get_main_list_data)
    Observable<String> getMainListDatas(@Query("token") String token);
}
