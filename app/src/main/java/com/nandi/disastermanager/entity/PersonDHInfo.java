package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/14.
 */

public class PersonDHInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"id":87,"area_id":275,"zhibantel":"48291469","location":"万盛区国土资源和房屋管理局","area_name":"万盛区","tel":"48293862","name":"牟联清","job":"地环站站长","lng":"106.927437","iphone":"15213062108","url":"wansheng/mulianqing.jpg","lat":"28.96346"},{"id":88,"area_id":275,"zhibantel":"48291469","location":"万盛区国土资源和房屋管理局","area_name":"万盛区","tel":"48293061","name":"罗玉鹏","job":"地环站副站长","lng":"106.927437","iphone":"18983883805","url":"wansheng/luoyupeng.jpg","lat":"28.96346"},{"id":89,"area_id":275,"zhibantel":"48291469","location":"万盛区国土资源和房屋管理局","area_name":"万盛区","tel":"48284001","name":"蔡云锋","job":"地环站九级职员","lng":"106.927437","iphone":"13657607982","url":"wansheng/caiyunfeng.jpg","lat":"28.96346"},{"id":90,"area_id":275,"zhibantel":"48291469","location":"万盛区国土资源和房屋管理局","area_name":"万盛区","tel":"48291770","name":"杜在敏","job":"地环站九级职员","lng":"106.927437","iphone":"18225219717","url":"wansheng/duzaimin.jpg","lat":"28.96346"},{"id":91,"area_id":275,"zhibantel":"48293061","location":"万盛区国土资源和房屋管理局","area_name":"万盛区","tel":"48291770","name":"谢同芳","job":"文秘","lng":"106.927437","iphone":"13243522421","url":"wansheng/xietongfang.jpg","lat":"28.96346"}]
     */

    private MetaBean meta;
    private List<DataBean> data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
         * id : 87
         * area_id : 275
         * zhibantel : 48291469
         * location : 万盛区国土资源和房屋管理局
         * area_name : 万盛区
         * tel : 48293862
         * name : 牟联清
         * job : 地环站站长
         * lng : 106.927437
         * iphone : 15213062108
         * url : wansheng/mulianqing.jpg
         * lat : 28.96346
         */

        private int id;
        private int area_id;
        private String zhibantel;
        private String location;
        private String area_name;
        private String tel;
        private String name;
        private String job;
        private String lng;
        private String iphone;
        private String url;
        private String lat;
        private String onlineStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getZhibantel() {
            return zhibantel;
        }

        public void setZhibantel(String zhibantel) {
            this.zhibantel = zhibantel;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getIphone() {
            return iphone;
        }

        public void setIphone(String iphone) {
            this.iphone = iphone;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }
    }
}
