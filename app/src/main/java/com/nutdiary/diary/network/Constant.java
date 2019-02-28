package com.nutdiary.diary.network;

/**
 * 常量
 * Created by yangle on 2017/6/26.
 */

public class Constant {

    /**
     * 服务器地址
     */
    //public static final String SERVER_URL = "http://nutstudio.net:8083/diary/";
   public static final String SERVER_URL = "http://192.168.31.196:8083/diary/";

    /**
     * 接口请求地址
     */
    public static class UrlOrigin {
        public static final String get_main_list_data = "getListData";
        public static final String delete_item = "delete";
        public static final String save_item_data = "saveItemData";
        public static final String upload_pic = "upload_pic";
        public static final String user_login = "user_login";

    }

    public static String getAddressUrl(double lng, double lat) {
        return "http://api.map.baidu.com/geocoder?output=json&location=" +
                lng +
                "," +
                lat +
                "&ak=esNPFDwwsXWtsQfw4NMNmur1";
    }
}
