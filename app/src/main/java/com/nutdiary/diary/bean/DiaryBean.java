package com.nutdiary.diary.bean;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class DiaryBean {
    public DiaryBean(Integer id, String content, Integer userId, String locationName, long lat, long lng, String mood) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
        this.mood = mood;
    }

    public DiaryBean(String content, Integer userId, String locationName, double lat, double lng, String mood) {
        this.content = content;
        this.userId = userId;
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
        this.mood = mood;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    private Integer id;

    private String content;
    //用户ID
    private Integer userId;

    //位置名称
    private String locationName;
    //经纬度
    private double lat;
    private double lng;
    //心情
    private String mood;
}
