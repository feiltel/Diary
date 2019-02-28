package com.nutdiary.diary.localData;

import com.nutdiary.diary.app.MyApp;
import com.nutdiary.diary.utils.PreferencesUtils;

public class UserData {
    private static String USER_DATA_FILE_NAME="UserData";
    public static void saveUserUUID(String uuid){
        PreferencesUtils.setString(MyApp.getContext(),USER_DATA_FILE_NAME,"UserUUID",uuid);
    }
    public static String getUserUUID(){
      return   PreferencesUtils.getString(MyApp.getContext(),USER_DATA_FILE_NAME,"UserUUID");
    }
}
