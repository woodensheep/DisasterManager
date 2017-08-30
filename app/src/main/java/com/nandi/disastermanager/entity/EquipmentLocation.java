package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/18.
 */

public class EquipmentLocation {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"result":[{"id":1,"monitor_type":11,"project_name":"羊角场镇危岩专业预警监测-高新院","device_name":"大湾北危岩监控","result_pic":"img/monitorSPJK/dawan.jpg","install_pic":"img/monitorSPJK/dawanInstall1.jpg","longitude":107.594094,"latitude":29.392719,"explain":"大湾北危岩由位于2级陡崖上，顶面高程690～743m，底面高程为577～622m，危岩体\r\r\n总长148m，高72～146m，厚50～93m，体积69.07×104m3，大致呈楔状。危岩体东侧临空，南侧为凹沟，全部临空。"},{"id":2,"device_name":"大湾小湾危岩监控","result_pic":"img/monitorSPJK/dawanxiaowan1.jpg","install_pic":"img/monitorSPJK/dawanxiaowanInstall1.jpg","longitude":107.594919,"latitude":29.390856,"explain":"小湾危岩由位于2级陡崖上，顶面高程783～843m，底面高程为670～704m，危岩体总\r\r\n长165m，高79～203m，厚48～77m，体积85.04×104m3，大致呈方体状。危岩体东侧临空，南北侧为凹沟，大部分临空。大湾危岩由位于2级陡崖\r\r\n上，顶面高程770～796m，底面高程为636～642m，危岩体总长185m，高85～208m，厚46～102m，体积138.75×104m3，大致呈方体状。危岩体东\r\r\n侧临空，南北侧为凹沟，大部分临空。"},{"id":3,"device_name":"观音洞危岩监控","result_pic":"img/monitorSPJK/guanyindong1.jpg","install_pic":"img/monitorSPJK/guanyindongInstall1.jpg","longitude":107.593719,"latitude":29.392056,"explain":"观音洞危岩由位于1级陡崖顶部，顶面高程950～979m，底面高程为923～930m，危岩体\r\r\n总长132m，高21～35m，厚24～89m，体积36.2×104m3，大致呈块状。危岩体东侧、北侧临空。"}],"type":2}]
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
         * result : [{"id":1,"monitor_type":11,"project_name":"羊角场镇危岩专业预警监测-高新院","device_name":"大湾北危岩监控","result_pic":"img/monitorSPJK/dawan.jpg","install_pic":"img/monitorSPJK/dawanInstall1.jpg","longitude":107.594094,"latitude":29.392719,"explain":"大湾北危岩由位于2级陡崖上，顶面高程690～743m，底面高程为577～622m，危岩体\r\r\n总长148m，高72～146m，厚50～93m，体积69.07×104m3，大致呈楔状。危岩体东侧临空，南侧为凹沟，全部临空。"},{"id":2,"device_name":"大湾小湾危岩监控","result_pic":"img/monitorSPJK/dawanxiaowan1.jpg","install_pic":"img/monitorSPJK/dawanxiaowanInstall1.jpg","longitude":107.594919,"latitude":29.390856,"explain":"小湾危岩由位于2级陡崖上，顶面高程783～843m，底面高程为670～704m，危岩体总\r\r\n长165m，高79～203m，厚48～77m，体积85.04×104m3，大致呈方体状。危岩体东侧临空，南北侧为凹沟，大部分临空。大湾危岩由位于2级陡崖\r\r\n上，顶面高程770～796m，底面高程为636～642m，危岩体总长185m，高85～208m，厚46～102m，体积138.75×104m3，大致呈方体状。危岩体东\r\r\n侧临空，南北侧为凹沟，大部分临空。"},{"id":3,"device_name":"观音洞危岩监控","result_pic":"img/monitorSPJK/guanyindong1.jpg","install_pic":"img/monitorSPJK/guanyindongInstall1.jpg","longitude":107.593719,"latitude":29.392056,"explain":"观音洞危岩由位于1级陡崖顶部，顶面高程950～979m，底面高程为923～930m，危岩体\r\r\n总长132m，高21～35m，厚24～89m，体积36.2×104m3，大致呈块状。危岩体东侧、北侧临空。"}]
         * type : 2
         */

        private int type;
        private List<ResultBean> result;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * id : 1
             * monitor_type : 11
             * project_name : 羊角场镇危岩专业预警监测-高新院
             * device_name : 大湾北危岩监控
             * result_pic : img/monitorSPJK/dawan.jpg
             * install_pic : img/monitorSPJK/dawanInstall1.jpg
             * longitude : 107.594094
             * latitude : 29.392719
             * explain : 大湾北危岩由位于2级陡崖上，顶面高程690～743m，底面高程为577～622m，危岩体
             总长148m，高72～146m，厚50～93m，体积69.07×104m3，大致呈楔状。危岩体东侧临空，南侧为凹沟，全部临空。
             */

            private int id;
            private int monitor_type;
            private String project_name;
            private String device_name;
            private String result_pic;
            private String install_pic;
            private double longitude;
            private double latitude;
            private String explain;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMonitor_type() {
                return monitor_type;
            }

            public void setMonitor_type(int monitor_type) {
                this.monitor_type = monitor_type;
            }

            public String getProject_name() {
                return project_name;
            }

            public void setProject_name(String project_name) {
                this.project_name = project_name;
            }

            public String getDevice_name() {
                return device_name;
            }

            public void setDevice_name(String device_name) {
                this.device_name = device_name;
            }

            public String getResult_pic() {
                return result_pic;
            }

            public void setResult_pic(String result_pic) {
                this.result_pic = result_pic;
            }

            public String getInstall_pic() {
                return install_pic;
            }

            public void setInstall_pic(String install_pic) {
                this.install_pic = install_pic;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }
        }
    }
}
