package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by ChenPeng on 2017/9/20.
 */

public class StaticsInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"zs":[{"count":8816,"xqdj":"灾害点总数","type":"point"}],"zhzl":[{"count":4620,"xqdj":"01","type":"zhzl"},{"count":538,"xqdj":"02","type":"zhzl"},{"count":138,"xqdj":"03","type":"zhzl"},{"count":241,"xqdj":"05","type":"zhzl"},{"count":1029,"xqdj":"06","type":"zhzl"},{"count":2250,"xqdj":"07","type":"zhzl"}],"dj":[{"count":24,"xqdj":"","type":"dj"},{"count":1,"xqdj":"  特大","type":"dj"},{"count":1,"xqdj":"一般","type":"dj"},{"count":21,"xqdj":"一般级","type":"dj"},{"count":171,"xqdj":"中","type":"dj"},{"count":1980,"xqdj":"中型","type":"dj"},{"count":39,"xqdj":"大","type":"dj"},{"count":237,"xqdj":"大型","type":"dj"},{"count":195,"xqdj":"小","type":"dj"},{"count":2,"xqdj":"小 型","type":"dj"},{"count":5934,"xqdj":"小型","type":"dj"},{"count":11,"xqdj":"特大","type":"dj"},{"count":176,"xqdj":"特大型","type":"dj"},{"count":23,"xqdj":"较大级","type":"dj"},{"count":1,"xqdj":"重大级","type":"dj"}]}
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
        private List<ZsBean> zs;
        private List<ZhzlBean> zhzl;
        private List<DjBean> dj;

        public List<ZsBean> getZs() {
            return zs;
        }

        public void setZs(List<ZsBean> zs) {
            this.zs = zs;
        }

        public List<ZhzlBean> getZhzl() {
            return zhzl;
        }

        public void setZhzl(List<ZhzlBean> zhzl) {
            this.zhzl = zhzl;
        }

        public List<DjBean> getDj() {
            return dj;
        }

        public void setDj(List<DjBean> dj) {
            this.dj = dj;
        }

        public static class ZsBean {
            /**
             * count : 8816
             * xqdj : 灾害点总数
             * type : point
             */

            private int count;
            private String xqdj;
            private String type;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getXqdj() {
                return xqdj;
            }

            public void setXqdj(String xqdj) {
                this.xqdj = xqdj;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ZhzlBean {
            /**
             * count : 4620
             * xqdj : 01
             * type : zhzl
             */

            private int count;
            private String xqdj;
            private String type;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getXqdj() {
                return xqdj;
            }

            public void setXqdj(String xqdj) {
                this.xqdj = xqdj;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class DjBean {
            /**
             * count : 24
             * xqdj :
             * type : dj
             */

            private int count;
            private String xqdj;
            private String type;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getXqdj() {
                return xqdj;
            }

            public void setXqdj(String xqdj) {
                this.xqdj = xqdj;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
