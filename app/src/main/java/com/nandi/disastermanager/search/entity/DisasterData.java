package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by lemon on 2017/9/9.
 * 网络请求的数据
 */

public class DisasterData {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"dzbh":"522327010005","jd":105.923889,"wd":24.863056,"xqdj":"小型","dzmc":"弼佑四组","zhzl":"01","yfys":"自然","yhdbh":"369","id":889,"level":4,"province":"贵州省","city":"黔西南布依族苗族自治州","county":"册亨县","town":"弼佑镇","LATITUDE":27,"LONGITUDE":107}]
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
         * dzbh : 522327010005
         * jd : 105.923889
         * wd : 24.863056
         * xqdj : 小型
         * dzmc : 弼佑四组
         * zhzl : 01
         * yfys : 自然
         * yhdbh : 369
         * id : 889
         * level : 4
         * province : 贵州省
         * city : 黔西南布依族苗族自治州
         * county : 册亨县
         * town : 弼佑镇
         * LATITUDE : 27
         * LONGITUDE : 107
         */

        private String dzbh;
        private double jd;
        private double wd;
        private String xqdj;
        private String dzmc;
        private String zhzl;
        private String yfys;
        private String yhdbh;
        private int id;
        private int level;
        private String province;
        private String city;
        private String county;
        private String town;

        public String getDzbh() {
            return dzbh;
        }

        public void setDzbh(String dzbh) {
            this.dzbh = dzbh;
        }

        public double getJd() {
            return jd;
        }

        public void setJd(double jd) {
            this.jd = jd;
        }

        public double getWd() {
            return wd;
        }

        public void setWd(double wd) {
            this.wd = wd;
        }

        public String getXqdj() {
            return xqdj;
        }

        public void setXqdj(String xqdj) {
            this.xqdj = xqdj;
        }

        public String getDzmc() {
            return dzmc;
        }

        public void setDzmc(String dzmc) {
            this.dzmc = dzmc;
        }

        public String getZhzl() {
            return zhzl;
        }

        public void setZhzl(String zhzl) {
            this.zhzl = zhzl;
        }

        public String getYfys() {
            return yfys;
        }

        public void setYfys(String yfys) {
            this.yfys = yfys;
        }

        public String getYhdbh() {
            return yhdbh;
        }

        public void setYhdbh(String yhdbh) {
            this.yhdbh = yhdbh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "dzbh='" + dzbh + '\'' +
                    ", jd=" + jd +
                    ", wd=" + wd +
                    ", xqdj='" + xqdj + '\'' +
                    ", dzmc='" + dzmc + '\'' +
                    ", zhzl='" + zhzl + '\'' +
                    ", yfys='" + yfys + '\'' +
                    ", yhdbh='" + yhdbh + '\'' +
                    ", id=" + id +
                    ", level=" + level +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", county='" + county + '\'' +
                    ", town='" + town + '\'' +
                    '}';
        }
    }
}
