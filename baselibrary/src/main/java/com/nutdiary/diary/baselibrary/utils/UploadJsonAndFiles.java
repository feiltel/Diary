package com.nutdiary.diary.baselibrary.utils;

import java.util.List;

public class UploadJsonAndFiles {
    String jsonItemStr;
    String jsonDotStr;
    List<UploadFileInfo> uploadFileInfoList;

    public String getJsonItemStr() {
        return jsonItemStr;
    }

    public void setJsonItemStr(String jsonItemStr) {
        this.jsonItemStr = jsonItemStr;
    }

    public String getJsonDotStr() {
        return jsonDotStr;
    }

    public void setJsonDotStr(String jsonDotStr) {
        this.jsonDotStr = jsonDotStr;
    }

    public List<UploadFileInfo> getUploadFileInfoList() {
        return uploadFileInfoList;
    }

    public void setUploadFileInfoList(List<UploadFileInfo> uploadFileInfoList) {
        this.uploadFileInfoList = uploadFileInfoList;
    }
}
