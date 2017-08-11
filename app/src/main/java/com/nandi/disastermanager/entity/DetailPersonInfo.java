package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/10.
 * 人员信息
 */

public class DetailPersonInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"areaAdmin":[{"lon":106.849522,"admin_id":399,"admin_pic":"wansheng/zhanghuaxian.jpg","admin_name":"张华先","admin_phone":"13658382122","lat":28.867838}],"areaProfessor":[{"professor_phone":"15702331022","professor_id":186,"lng":106.9700751,"dispicture":"wansheng/sujianqiang.jpg","disname":"苏建强","lat":28.9431577}],"dihuanzhan":[{"dhz_phone":"15213062108","area_code":500110,"name":"牟联清","lng":"106.927437","lat":"28.96346","url":"wansheng/mulianqing.jpg"}],"human":[{"head_url":"wansheng/qingnianzhen/zhushaobi.jpg","human_phone":"","dis_lon":106.8088,"human_name":"朱绍碧","dis_id":11100,"dis_lat":28.8683}]}
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
        private List<AreaAdminBean> areaAdmin;
        private List<AreaProfessorBean> areaProfessor;
        private List<DihuanzhanBean> dihuanzhan;
        private List<HumanBean> human;

        public List<AreaAdminBean> getAreaAdmin() {
            return areaAdmin;
        }

        public void setAreaAdmin(List<AreaAdminBean> areaAdmin) {
            this.areaAdmin = areaAdmin;
        }

        public List<AreaProfessorBean> getAreaProfessor() {
            return areaProfessor;
        }

        public void setAreaProfessor(List<AreaProfessorBean> areaProfessor) {
            this.areaProfessor = areaProfessor;
        }

        public List<DihuanzhanBean> getDihuanzhan() {
            return dihuanzhan;
        }

        public void setDihuanzhan(List<DihuanzhanBean> dihuanzhan) {
            this.dihuanzhan = dihuanzhan;
        }

        public List<HumanBean> getHuman() {
            return human;
        }

        public void setHuman(List<HumanBean> human) {
            this.human = human;
        }


        public static class AreaAdminBean {
            /**
             * 片区负责人
             * lon : 106.849522
             * admin_id : 399
             * admin_pic : wansheng/zhanghuaxian.jpg
             * admin_name : 张华先
             * admin_phone : 13658382122
             * lat : 28.867838
             */

            private double lon;
            private int admin_id;
            private String admin_pic;
            private String admin_name;
            private String admin_phone;
            private double lat;

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public int getAdmin_id() {
                return admin_id;
            }

            public void setAdmin_id(int admin_id) {
                this.admin_id = admin_id;
            }

            public String getAdmin_pic() {
                return admin_pic;
            }

            public void setAdmin_pic(String admin_pic) {
                this.admin_pic = admin_pic;
            }

            public String getAdmin_name() {
                return admin_name;
            }

            public void setAdmin_name(String admin_name) {
                this.admin_name = admin_name;
            }

            public String getAdmin_phone() {
                return admin_phone;
            }

            public void setAdmin_phone(String admin_phone) {
                this.admin_phone = admin_phone;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class AreaProfessorBean {
            /**
             * 地质驻守人员
             * professor_phone : 15702331022
             * professor_id : 186
             * lng : 106.9700751
             * dispicture : wansheng/sujianqiang.jpg
             * disname : 苏建强
             * lat : 28.9431577
             */

            private String professor_phone;
            private int professor_id;
            private double lng;
            private String dispicture;
            private String disname;
            private double lat;

            public String getProfessor_phone() {
                return professor_phone;
            }

            public void setProfessor_phone(String professor_phone) {
                this.professor_phone = professor_phone;
            }

            public int getProfessor_id() {
                return professor_id;
            }

            public void setProfessor_id(int professor_id) {
                this.professor_id = professor_id;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getDispicture() {
                return dispicture;
            }

            public void setDispicture(String dispicture) {
                this.dispicture = dispicture;
            }

            public String getDisname() {
                return disname;
            }

            public void setDisname(String disname) {
                this.disname = disname;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class DihuanzhanBean {
            /**
             * 地环站人员
             * dhz_phone : 15213062108
             * area_code : 500110
             * name : 牟联清
             * lng : 106.927437
             * lat : 28.96346
             * url : wansheng/mulianqing.jpg
             */

            private String dhz_phone;
            private int area_code;
            private String name;
            private String lng;
            private String lat;
            private String url;

            public String getDhz_phone() {
                return dhz_phone;
            }

            public void setDhz_phone(String dhz_phone) {
                this.dhz_phone = dhz_phone;
            }

            public int getArea_code() {
                return area_code;
            }

            public void setArea_code(int area_code) {
                this.area_code = area_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class HumanBean {
            /**
             * 群测群防人员
             * head_url : wansheng/qingnianzhen/zhushaobi.jpg
             * human_phone :
             * dis_lon : 106.8088
             * human_name : 朱绍碧
             * dis_id : 11100
             * dis_lat : 28.8683
             */

            private String head_url;
            private String human_phone;
            private double dis_lon;
            private String human_name;
            private int dis_id;
            private double dis_lat;

            public String getHead_url() {
                return head_url;
            }

            public void setHead_url(String head_url) {
                this.head_url = head_url;
            }

            public String getHuman_phone() {
                return human_phone;
            }

            public void setHuman_phone(String human_phone) {
                this.human_phone = human_phone;
            }

            public double getDis_lon() {
                return dis_lon;
            }

            public void setDis_lon(double dis_lon) {
                this.dis_lon = dis_lon;
            }

            public String getHuman_name() {
                return human_name;
            }

            public void setHuman_name(String human_name) {
                this.human_name = human_name;
            }

            public int getDis_id() {
                return dis_id;
            }

            public void setDis_id(int dis_id) {
                this.dis_id = dis_id;
            }

            public double getDis_lat() {
                return dis_lat;
            }

            public void setDis_lat(double dis_lat) {
                this.dis_lat = dis_lat;
            }
        }
    }
}
