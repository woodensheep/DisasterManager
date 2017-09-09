package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/9.
 */

public class MonitorListData {
    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"pageNo":1,"pageSize":10,"totalCount":1,"totalPages":1,"result":[{"time":"2017-09-08 19:55:34","lon":107,"NAME":"轿子山镇厦耳村前寨滑坡_02","disName":"嘎哩滑坡","disnNum":"520402010001","ID":"520402010003_02","lat":27,"REGIONID":1136}],"offset":0,"limit":10,"length":8,"funcName":"page","prev":1,"next":1}
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
         * pageSize : 10
         * totalCount : 1
         * totalPages : 1
         * result : [{"time":"2017-09-08 19:55:34","lon":107,"NAME":"轿子山镇厦耳村前寨滑坡_02","disName":"嘎哩滑坡","disnNum":"520402010001","ID":"520402010003_02","lat":27,"REGIONID":1136}]
         * offset : 0
         * limit : 10
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
             * time : 2017-09-08 19:55:34
             * lon : 107
             * NAME : 轿子山镇厦耳村前寨滑坡_02
             * disName : 嘎哩滑坡
             * disnNum : 520402010001
             * ID : 520402010003_02
             * lat : 27
             * REGIONID : 1136
             */

            private String time;
            private int lon;
            private String NAME;
            private String disName;
            private String disnNum;
            private String ID;
            private int lat;
            private int REGIONID;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getLon() {
                return lon;
            }

            public void setLon(int lon) {
                this.lon = lon;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getDisName() {
                return disName;
            }

            public void setDisName(String disName) {
                this.disName = disName;
            }

            public String getDisnNum() {
                return disnNum;
            }

            public void setDisnNum(String disnNum) {
                this.disnNum = disnNum;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public int getLat() {
                return lat;
            }

            public void setLat(int lat) {
                this.lat = lat;
            }

            public int getREGIONID() {
                return REGIONID;
            }

            public void setREGIONID(int REGIONID) {
                this.REGIONID = REGIONID;
            }
        }
    }

}
