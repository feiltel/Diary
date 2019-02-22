package com.nutdiary.diary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyPermissionUtils {
    public static final int ACCESS_COARSE_LOCATION_REQUEST_CODE=13;




    private static boolean hasPermission(Activity context, String permissionStr) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context,
                permissionStr);
    }


    public static boolean checkAndRequest(Activity context, String permissionStr, int requestCode) {
        Log.d("myper", "权限：" + permissionStr + " 请求码" + requestCode);
        //是否拥有这个权限
        boolean hasPermission=hasPermission(context, permissionStr);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    permissionStr)) {
                //权限被拒绝过 是否再次请求
                ActivityCompat.requestPermissions(context,
                        new String[]{permissionStr},
                        requestCode);
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{permissionStr},
                        requestCode);
                //首次请求权限
            }
        }
        return hasPermission;
    }


    public static void onRequestPermissionsResult(Activity context, int requestCode, String[] permissions, int[] grantResults, int nowRequestCode) {
        if (requestCode == nowRequestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i]);
                    Log.d("myper", "权限：" + permissions[i] + " 请求码" + requestCode + " 状态：" + showRequestPermission);
                    //false 代表权限勾选禁止后不再询问
                    if (showRequestPermission) {
                        //代表权限被拒绝
                    } else {
                        //代表权限勾选禁止后不再询问
                    }

                }
            }
        }
    }

    //判断某个权限的用户是否允许 1允许 0拒绝 -1拒绝不在询问
    public static int getRequestPermissionsResult(Activity context, int requestCode, String[] permissions, int[] grantResults, int nowRequestCode, String permissionStr) {
        if (requestCode == nowRequestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (permissionStr.equals(permissions[i])) {
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i]);
                        Log.d("myper", "权限：" + permissions[i] + " 请求码" + requestCode + " 状态：" + showRequestPermission);
                        //false 代表权限勾选禁止后不再询问
                        if (showRequestPermission) {
                            //代表权限被拒绝
                            return 0;
                        } else {
                            //代表勾选禁止后不再询问
                            return -1;
                        }
                    }
                }
            }
        }
        return 1;
    }

    //统一请求所有权限
    public static void checkAndRequests(Activity context, int requestCode) {
        String[] permissionStr = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        Log.d("myper", "权限：" + permissionStr + " 请求码" + requestCode);
        List<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < permissionStr.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissionStr[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissionStr[i]);
            }
        }
        if (!mPermissionList.isEmpty()) {
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(context, permissions, requestCode);
        }
    }
}
