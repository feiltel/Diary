package com.nutdiary.diary.network;

import com.nutdiary.diary.base.ResultBean;
import com.nutdiary.diary.bean.AddressBean;
import com.nutdiary.diary.bean.DiaryBean;
import com.nutdiary.diary.bean.MainListBean;
import com.nutdiary.diary.bean.SaveResultBean;
import com.nutdiary.diary.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 请求参数接口
 * Created by tangfei
 */

public interface RetrofitService {

    /**
     * 获取主页数据 分页
     *
     * @param page  页码
     * @param limit 每页个数
     * @return Observable
     */
    @GET(Constant.UrlOrigin.get_main_list_data)
    Observable<MainListBean> getMainListData(@Query("page") Integer page, @Query("limit") Integer limit);

    @GET(Constant.UrlOrigin.delete_item)
    Observable<ResultBean> delete(@Query("id") Integer id);
    /**
     * 保存日记
     * @param mainListItem 保存的数据集合
     * @return Observable
     */
    @POST(Constant.UrlOrigin.save_item_data)
    Observable<SaveResultBean> saveItemData(@Body DiaryBean mainListItem);

    /**
     * 根据经纬度获取地址信息
     * @param url 请求地址
     * @return Observable
     */
    @GET
    Observable<AddressBean> getAddress(@Url String url);

    /**
     * 上传图片
     * @param file 上传的文件
     * @return Observable
     */
    @Multipart
    @POST(Constant.UrlOrigin.upload_pic)
    Observable<UploadBean> uploadFile(@Part MultipartBody.Part file);



    /**
     * 测试用
     * @param file 上传的文件
     * @return Observable
     */
    @Multipart
    @POST("uploads")
    Observable<String> uploadFiles(@Part MultipartBody.Part file);

}
