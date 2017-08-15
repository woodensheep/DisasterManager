package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/14.
 */

public class PersonZSInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"id":55,"areaId":864,"disname":"曹军","gender":null,"age":null,"dispicture":"qijiang/caojun.jpg","disarea":"重庆市綦江区新盛镇","telephone":"15702331037","lat":29.049722,"lng":106.736111,"unitName":null,"manageArea":"新盛镇、三角镇","manageDispiont":null,"phone":"13594083018","areaParent":115,"imei":null,"locationTime":null,"onlineStatus":null}
     */

    private MetaBean meta;
    private DataBean data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * success : true
         * message : ok
         */

        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DataBean {
        /**
         * id : 55
         * areaId : 864
         * disname : 曹军
         * gender : null
         * age : null
         * dispicture : qijiang/caojun.jpg
         * disarea : 重庆市綦江区新盛镇
         * telephone : 15702331037
         * lat : 29.049722
         * lng : 106.736111
         * unitName : null
         * manageArea : 新盛镇、三角镇
         * manageDispiont : null
         * phone : 13594083018
         * areaParent : 115
         * imei : null
         * locationTime : null
         * onlineStatus : null
         */

        private int id;
        private int areaId;
        private String disname;
        private Object gender;
        private Object age;
        private String dispicture;
        private String disarea;
        private String telephone;
        private double lat;
        private double lng;
        private Object unitName;
        private String manageArea;
        private Object manageDispiont;
        private String phone;
        private int areaParent;
        private Object imei;
        private Object locationTime;
        private Object onlineStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getDisname() {
            return disname;
        }

        public void setDisname(String disname) {
            this.disname = disname;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getDispicture() {
            return dispicture;
        }

        public void setDispicture(String dispicture) {
            this.dispicture = dispicture;
        }

        public String getDisarea() {
            return disarea;
        }

        public void setDisarea(String disarea) {
            this.disarea = disarea;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public Object getUnitName() {
            return unitName;
        }

        public void setUnitName(Object unitName) {
            this.unitName = unitName;
        }

        public String getManageArea() {
            return manageArea;
        }

        public void setManageArea(String manageArea) {
            this.manageArea = manageArea;
        }

        public Object getManageDispiont() {
            return manageDispiont;
        }

        public void setManageDispiont(Object manageDispiont) {
            this.manageDispiont = manageDispiont;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getAreaParent() {
            return areaParent;
        }

        public void setAreaParent(int areaParent) {
            this.areaParent = areaParent;
        }

        public Object getImei() {
            return imei;
        }

        public void setImei(Object imei) {
            this.imei = imei;
        }

        public Object getLocationTime() {
            return locationTime;
        }

        public void setLocationTime(Object locationTime) {
            this.locationTime = locationTime;
        }

        public Object getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(Object onlineStatus) {
            this.onlineStatus = onlineStatus;
        }
    }
}
