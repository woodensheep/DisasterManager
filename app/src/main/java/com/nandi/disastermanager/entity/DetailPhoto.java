package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/10.
 * 图片信息
 */

public class DetailPhoto {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"id":35320,"dis_no":"5001101020010101","dis_id":35320,"f_remark":"","type":"1","url":"5001101020010101-q.png"},{"id":115406,"dis_no":"5001101020010101","dis_id":35320,"f_remark":"","type":"2","url":"5001101020010101-t.png"},{"id":135405,"dis_no":"5001101020010101","dis_id":35320,"f_remark":"","type":"3","url":"5001101020010101-p.png,5001101020010101-pp.png,5001101020010101-1.jpg"},{"id":155405,"dis_no":"5001101020010101","dis_id":35320,"f_remark":"","type":"4","url":"5001101020010101-2.jpg"}]
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
         * http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=10&path=5001101060500101-q.jpg
         * http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=10&path=5001101060500101-t.gif
         * http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=10&path=5001101060500101-p.jpg
         * http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=10&path=5001101060500101-m.jpg
         * id : 35320
         * dis_no : 5001101020010101
         * dis_id : 35320
         * f_remark :
         * type : 1
         * url : 5001101020010101-q.png
         */

        private int id;
        private String dis_no;
        private int dis_id;
        private String f_remark;
        private String type;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDis_no() {
            return dis_no;
        }

        public void setDis_no(String dis_no) {
            this.dis_no = dis_no;
        }

        public int getDis_id() {
            return dis_id;
        }

        public void setDis_id(int dis_id) {
            this.dis_id = dis_id;
        }

        public String getF_remark() {
            return f_remark;
        }

        public void setF_remark(String f_remark) {
            this.f_remark = f_remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
