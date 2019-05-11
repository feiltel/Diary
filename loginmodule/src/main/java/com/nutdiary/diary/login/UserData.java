package com.nutdiary.diary.login;

import android.content.Context;

import com.nutdiary.diary.baselibrary.utils.PreferencesUtils;

public class UserData {
    private static String USER_DATA_FILE_NAME = "UserData";

    public static void saveUserUUID(Context context, String uuid) {
        PreferencesUtils.setString(context, USER_DATA_FILE_NAME, "UserUUID", uuid);
    }

    public static String getUserUUID(Context context) {
        return PreferencesUtils.getString(context, USER_DATA_FILE_NAME, "UserUUID");
    }
}
