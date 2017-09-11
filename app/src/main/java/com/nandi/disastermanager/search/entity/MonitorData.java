package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/8.
 */

public class MonitorData {


    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"pageNo":1,"pageSize":10000,"totalCount":1,"totalPages":1,"result":[{"PHONE":"13885272488","GATHER":"蒋方明","MONITORDATA":200,"time":"2017-09-11 11:15:07","lon":106,"NAME":"轿子山镇厦耳村前寨滑坡_02","WEATHER":"晴天","IS_DANGER":0,"ID":"520402010003_02","lat":28}],"offset":0,"limit":10000,"length":8,"funcName":"page","prev":1,"next":1}
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
         * pageNo : 1
         * pageSize : 10000
         * totalCount : 1
         * totalPages : 1
         * result : [{"PHONE":"13885272488","GATHER":"蒋方明","MONITORDATA":200,"time":"2017-09-11 11:15:07","lon":106,"NAME":"轿子山镇厦耳村前寨滑坡_02","WEATHER":"晴天","IS_DANGER":0,"ID":"520402010003_02","lat":28}]
         * offset : 0
         * limit : 10000
         * length : 8
         * funcName : page
         * prev : 1
         * next : 1
         */

        private int pageNo;
        private int pageSize;
        private int totalCount;
        private int totalPages;
        private int offset;
        private int limit;
        private int length;
        private String funcName;
        private int prev;
        private int next;
        private List<ResultBean> result;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getFuncName() {
            return funcName;
        }

        public void setFuncName(String funcName) {
            this.funcName = funcName;
        }

        public int getPrev() {
            return prev;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * PHONE : 13885272488
             * GATHER : 蒋方明
             * MONITORDATA : 200.0
             * time : 2017-09-11 11:15:07
             * lon : 106.0
             * NAME : 轿子山镇厦耳村前寨滑坡_02
             * WEATHER : 晴天
             * IS_DANGER : 0
             * ID : 520402010003_02
             * lat : 28.0
             */

            private String PHONE;
            private String GATHER;
            private double MONITORDATA;
            private String time;
            private double lon;
            private String NAME;
            private String WEATHER;
            private int IS_DANGER;
            private String ID;
            private double lat;

            public String getPHONE() {
                return PHONE;
            }

            public void setPHONE(String PHONE) {
                this.PHONE = PHONE;
            }

            public String getGATHER() {
                return GATHER;
            }

            public void setGATHER(String GATHER) {
                this.GATHER = GATHER;
            }

            public double getMONITORDATA() {
                return MONITORDATA;
            }

            public void setMONITORDATA(double MONITORDATA) {
                this.MONITORDATA = MONITORDATA;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getWEATHER() {
                return WEATHER;
            }

            public void setWEATHER(String WEATHER) {
                this.WEATHER = WEATHER;
            }

            public int getIS_DANGER() {
                return IS_DANGER;
            }

            public void setIS_DANGER(int IS_DANGER) {
                this.IS_DANGER = IS_DANGER;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }
    }
}
