package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by ChenPeng on 2017/8/15.
 */

public class personLocationInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"id":1,"time":"2017-07-17 10:07:27","lon":108.708114,"area_id":136,"head_pic":"tannian.png","lon_lat_id":16647726,"imei":"18210129411","tel":"18210129411","name":"谭念","lat":30.932543},{"id":2,"time":"2017-08-14 09:46:40","lon":108.669726,"area_id":132,"head_pic":"wenyouqing.png","lon_lat_id":17008738,"imei":"13635346839","tel":"13635346839","name":"文尤庆","lat":31.955113},{"id":3,"time":"2017-07-04 09:33:48","lon":108.4039,"area_id":108,"head_pic":"chendailu.png","lon_lat_id":16397295,"imei":"15736339179","tel":"15736339179","name":"陈代路","lat":31.17011},{"id":6,"time":"2017-08-07 09:47:09","lon":109.399391,"area_id":137,"head_pic":"yangwurui.png","lon_lat_id":16952512,"imei":"13668447525","tel":"13668447525","name":"杨武锐","lat":31.024101},{"id":8,"time":"2017-08-15 09:35:35","lon":108.045934,"area_id":135,"head_pic":"huangyalin.png","lon_lat_id":17018104,"imei":"18223847610","tel":"18223847610","name":"黄亚林","lat":30.298762},{"id":11,"time":"2017-07-06 15:34:47","lon":106.927207,"area_id":275,"head_pic":"xiaohong.png","lon_lat_id":16488707,"imei":"15730370186","tel":"15730370186","name":"肖宏","lat":28.963431},{"id":13,"time":"2017-08-14 17:43:50","lon":106.259735,"area_id":122,"head_pic":"chenhongrui.png","lon_lat_id":17016795,"imei":"18883155400","tel":"18883155400","name":"陈洪睿","lat":29.287218},{"id":14,"time":"2017-08-12 03:22:05","lon":105.775805,"area_id":124,"head_pic":"jiangyue.png","lon_lat_id":17004494,"imei":"15086874418","tel":"15086874418","name":"蒋跃","lat":29.085383},{"id":17,"time":"2017-08-07 09:59:52","lon":106.064512,"area_id":127,"head_pic":"wujiuchuan.png","lon_lat_id":16952519,"imei":"18623593350","tel":"18623593350","name":"吴久川","lat":29.850256},{"id":23,"time":"2017-08-11 08:25:14","lon":107.71661,"area_id":133,"head_pic":"sunhao.png","lon_lat_id":16958382,"imei":"15334699906","tel":"15334699906","name":"孙豪","lat":29.859798},{"id":26,"time":"2017-08-14 12:42:06","lon":108.168278,"area_id":142,"head_pic":"houzhangwang.png","lon_lat_id":17008820,"imei":"18523860520","tel":"18523860520","name":"侯章旺","lat":29.290194},{"id":27,"time":"2017-08-03 11:07:06","lon":108.776586,"area_id":120,"head_pic":"wuqilong.png","lon_lat_id":16891870,"imei":"15703061546","tel":"15703061546","name":"吴奇龙","lat":29.531805},{"id":28,"time":"2017-07-24 12:49:36","lon":108.776456,"area_id":141,"head_pic":"xiangsipeng.png","lon_lat_id":16773270,"imei":"15111895051","tel":"15111895051","name":"向思鹏","lat":28.854362},{"id":29,"time":"2017-08-15 09:36:20","lon":109.01514,"area_id":140,"head_pic":"suhai.png","lon_lat_id":17018141,"imei":"15023620135","tel":"15023620135","name":"粟海","lat":28.451523},{"id":32,"time":"2017-08-10 20:53:04","lon":106.546002,"area_id":110,"head_pic":"suweiling.png","lon_lat_id":16958202,"imei":"15223862035","tel":"15223862035","name":"苏卫凌","lat":29.415658},{"id":33,"time":"2017-07-12 09:02:07","lon":106.483918,"area_id":111,"head_pic":"wangbing.png","lon_lat_id":16586600,"imei":"18302344750","tel":"18302344750","name":"王斌","lat":29.485298},{"id":34,"time":"2017-08-15 09:34:05","lon":106.545789,"area_id":112,"head_pic":"gaoxin.png","lon_lat_id":17018051,"imei":"13996324657","tel":"13996324657","name":"高鑫","lat":29.585806},{"id":36,"time":"2017-08-04 10:26:32","lon":106.525932,"area_id":114,"head_pic":"liwei.png","lon_lat_id":16914700,"imei":"18883182097","tel":"18883182097","name":"李威","lat":29.508567},{"id":37,"time":"2017-07-26 13:00:38","lon":106.572996,"area_id":106,"head_pic":"wangjunfeng.png","lon_lat_id":16841202,"imei":"13883935226","tel":"13883935226","name":"王俊峰","lat":29.528546},{"id":38,"time":"2017-07-19 18:19:56","lon":106.407925,"area_id":109,"head_pic":"yanhan.png","lon_lat_id":16702131,"imei":"13647660076","tel":"13647660076","name":"严涵","lat":29.803687},{"id":40,"time":"2017-08-14 10:55:02","lon":106.566579,"area_id":337,"head_pic":"wushifeng.png","lon_lat_id":17008769,"imei":"18202341678","tel":"18202341678","name":"吴仕峰","lat":29.640336},{"id":43,"time":"2017-07-21 00:19:40","lon":106.53672,"area_id":117,"head_pic":"denghuan.png","lon_lat_id":16723492,"imei":"18926070915","tel":"18926070915","name":"黄麒麟","lat":29.56834},{"id":44,"time":"2017-07-26 07:04:55","lon":106.534162,"area_id":117,"head_pic":"hexuan.png","lon_lat_id":16839594,"imei":"13637879268","tel":"13637879268","name":"何璇","lat":29.591522},{"id":48,"time":"2017-07-25 11:15:27","lon":106.557813,"area_id":117,"head_pic":"default.png","lon_lat_id":16822592,"imei":"18696639683","tel":"18696639683","name":"陈霞","lat":29.46559},{"id":49,"time":"2017-07-07 12:09:34","lon":114.171611,"area_id":117,"head_pic":"liuyongjia.png","lon_lat_id":16493033,"imei":"15023688170","tel":"15023688170","name":"刘永佳","lat":22.564087},{"id":54,"time":"2017-08-10 19:28:23","lon":108.874712,"area_id":138,"head_pic":"liujingkai.png","lon_lat_id":16958183,"imei":"15923805910","tel":"15923805910","name":"刘敬凯","lat":31.403435},{"id":55,"time":"2017-08-14 23:13:44","lon":106.564639,"area_id":117,"head_pic":"default.png","lon_lat_id":17016899,"imei":"18883596666","tel":"18883596666","name":"熊总","lat":29.560524},{"id":56,"time":"2017-08-01 12:03:50","lon":106.510198,"area_id":105,"head_pic":"default.png","lon_lat_id":16884328,"imei":"15730386281","tel":"15730386281","name":"张建伦","lat":29.610816},{"id":57,"time":"2017-08-03 09:07:40","lon":106.510022,"area_id":115,"head_pic":"default.png","lon_lat_id":16886917,"imei":"13350348410","tel":"13350348410","name":"姚鑫","lat":29.610566},{"id":58,"time":"2017-08-07 09:34:10","lon":106.509489,"area_id":106,"head_pic":"default.png","lon_lat_id":16952504,"imei":"15025451647","tel":"15025451647","name":"陈燕","lat":29.608321}]
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
         * id : 1
         * time : 2017-07-17 10:07:27
         * lon : 108.708114
         * area_id : 136
         * head_pic : tannian.png
         * lon_lat_id : 16647726
         * imei : 18210129411
         * tel : 18210129411
         * name : 谭念
         * lat : 30.932543
         */

        private int id;
        private String time;
        private double lon;
        private int area_id;
        private String head_pic;
        private int lon_lat_id;
        private String imei;
        private String tel;
        private String name;
        private double lat;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getLon_lat_id() {
            return lon_lat_id;
        }

        public void setLon_lat_id(int lon_lat_id) {
            this.lon_lat_id = lon_lat_id;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
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

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", time='" + time + '\'' +
                    ", lon=" + lon +
                    ", area_id=" + area_id +
                    ", head_pic='" + head_pic + '\'' +
                    ", lon_lat_id=" + lon_lat_id +
                    ", imei='" + imei + '\'' +
                    ", tel='" + tel + '\'' +
                    ", name='" + name + '\'' +
                    ", lat=" + lat +
                    '}';
        }
    }
}
