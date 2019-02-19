package com.nutdiary.diary.network;

/**
 * 常量
 * Created by yangle on 2017/6/26.
 */

public class Constant {

    /**
     * 服务器地址
     */
    public static final String SERVER_URL = "http://192.168.31.196:8085/diary/";

    /**
     * 接口请求地址
     */
    public static class UrlOrigin {
        /**
         * 测试GET请求
         */
        public static final String get_main_list_data = "getListData";
        public static final String save_item_data = "saveItemData";
        public static final String upload_pic = "upload_pic";
    }
}
