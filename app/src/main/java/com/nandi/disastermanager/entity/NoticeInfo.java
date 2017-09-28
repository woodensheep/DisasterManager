package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/27.
 */

public class NoticeInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"content":"合法性：gps定位正常且在隐患点指定范围内（目前要求500米，实际参数1000-2000米）。有效性：按时上报数据每周两次。两次内有效，超出无效。     绿色代表有效，红色代表无效。请各位乡镇管理员悉知。\r\n","id":48,"user_name":"wushan","title":"关于合法性和有效性定义说明","level":1,"ann_time":"2016-11-23"},{"content":"各乡镇群测群防管理员：请注意年后手机终端上报率情况，对未按时上报的群测人进行督查跟踪及时督促，对手机无法上报的进行整改指导，确保信息上报达到100%。本年度将加大力度对上报率进行考核，请各管理做好群测群防相关指导工作，以确保地方平安。\r\n","id":49,"user_name":"wushan","title":"巫山县国土局关于加强群测群防上报率督查的通知","level":1,"ann_time":"2017-02-06"}]
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
         * content : 合法性：gps定位正常且在隐患点指定范围内（目前要求500米，实际参数1000-2000米）。有效性：按时上报数据每周两次。两次内有效，超出无效。     绿色代表有效，红色代表无效。请各位乡镇管理员悉知。

         * id : 48
         * user_name : wushan
         * title : 关于合法性和有效性定义说明
         * level : 1
         * ann_time : 2016-11-23
         */

        private String content;
        private int id;
        private String user_name;
        private String title;
        private int level;
        private String ann_time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getAnn_time() {
            return ann_time;
        }

        public void setAnn_time(String ann_time) {
            this.ann_time = ann_time;
        }
    }
}
