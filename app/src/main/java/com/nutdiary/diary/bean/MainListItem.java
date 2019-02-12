package com.nutdiary.diary.bean;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 */
public class MainListItem {
    private String dateStr;

    public MainListItem(String dateStr, String content, int type, String weather) {
        this.dateStr = dateStr;
        this.content = content;
        this.type = type;
        this.weather = weather;
    }

    private String content;
    private int type;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    private String weather;
}
