/**
 * Copyright 2019 bejson.com
 */
package com.nutdiary.diary.bean;

/**
 * Auto-generated: 2019-02-19 17:19:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AddressBean {

    private String status;
    private Result result;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public Result getResult() {
        return result;
    }

    public class Location {

        private double lng;
        private double lat;
        public void setLng(double lng) {
            this.lng = lng;
        }
        public double getLng() {
            return lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
        public double getLat() {
            return lat;
        }

    }


    public class AddressComponent {

        private String city;
        private String direction;
        private String distance;
        private String district;
        private String province;
        private String street;
        private String street_number;
        public void setCity(String city) {
            this.city = city;
        }
        public String getCity() {
            return city;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
        public String getDirection() {
            return direction;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
        public String getDistance() {
            return distance;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
        public String getDistrict() {
            return district;
        }

        public void setProvince(String province) {
            this.province = province;
        }
        public String getProvince() {
            return province;
        }

        public void setStreet(String street) {
            this.street = street;
        }
        public String getStreet() {
            return street;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }
        public String getStreet_number() {
            return street_number;
        }

    }
    public class Result {

        private Location location;
        private String formatted_address;
        private String business;
        private AddressComponent addressComponent;
        private int cityCode;
        public void setLocation(Location location) {
            this.location = location;
        }
        public Location getLocation() {
            return location;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }
        public String getFormatted_address() {
            return formatted_address;
        }

        public void setBusiness(String business) {
            this.business = business;
        }
        public String getBusiness() {
            return business;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }
        public AddressComponent getAddressComponent() {
            return addressComponent;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }
        public int getCityCode() {
            return cityCode;
        }

    }

}