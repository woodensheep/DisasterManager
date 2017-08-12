package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/12.
 */

public class SearchPlace {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"lon":106.8088,"area_parent":275,"name":"关山沟滑坡","type_name":"地质灾害点","number":"5001101020010101","lat":28.8683},{"lon":106.8808,"area_parent":275,"name":"千丘滑坡","type_name":"地质灾害点","number":"5001101020020101","lat":28.8091},{"lon":106.8677,"area_parent":275,"name":"大坪滑坡","type_name":"地质灾害点","number":"5001101020030101","lat":28.8188},{"lon":106.873,"area_parent":275,"name":"燕石滑坡","type_name":"地质灾害点","number":"5001101020040101","lat":28.8202},{"lon":106.8688,"area_parent":275,"name":"桃子坪滑坡","type_name":"地质灾害点","number":"5001101020080101","lat":28.8625},{"lon":106.8102,"area_parent":275,"name":"清水湾滑坡","type_name":"地质灾害点","number":"5001101020090101","lat":28.8488},{"lon":106.9647,"area_parent":275,"name":"廖家湾响水河滑坡","type_name":"地质灾害点","number":"5001101070140101","lat":28.9005},{"lon":106.9577,"area_parent":275,"name":"高墙院滑坡","type_name":"地质灾害点","number":"5001101070150101","lat":28.92},{"lon":106.9022,"area_parent":275,"name":"棕树碥滑坡","type_name":"地质灾害点","number":"5001101060260101","lat":29.0741},{"lon":106.883545,"area_parent":275,"name":"黄龙湾滑坡","type_name":"地质灾害点","number":"5001101060250101","lat":29.076501},{"lon":106.903,"area_parent":275,"name":"张三塘滑坡","type_name":"地质灾害点","number":"5001101060440101","lat":29.0341},{"lon":106.8588,"area_parent":275,"name":"碾盆滑坡","type_name":"地质灾害点","number":"5001101060610101","lat":29.0586},{"lon":106.8513,"area_parent":275,"name":"杉树湾滑坡","type_name":"地质灾害点","number":"5001101060620101","lat":29.068},{"lon":106.8975,"area_parent":275,"name":"肖家沟滑坡","type_name":"地质灾害点","number":"5001101060340101","lat":29.048},{"lon":106.899311,"area_parent":275,"name":"松林岗滑坡","type_name":"地质灾害点","number":"5001101060350101","lat":29.0269},{"lon":106.8844,"area_parent":275,"name":"天坪岗滑坡","type_name":"地质灾害点","number":"5001101060430101","lat":29.0336},{"lon":106.8969114904,"area_parent":275,"name":"单项滑坡","type_name":"地质灾害点","number":"5001101060390101","lat":29.1083623688},{"lon":106.8572,"area_parent":275,"name":"龙井湾滑坡","type_name":"地质灾害点","number":"5001101060670101","lat":29.1113},{"lon":106.8463,"area_parent":275,"name":"生基湾滑坡","type_name":"地质灾害点","number":"5001101060680101","lat":29.0955},{"lon":106.9319,"area_parent":275,"name":"鱼秋田滑坡","type_name":"地质灾害点","number":"5001101060500101","lat":29.0347},{"lon":106.9238,"area_parent":275,"name":"红生基滑坡","type_name":"地质灾害点","number":"5001101060480101","lat":29.0338},{"lon":106.8894,"area_parent":275,"name":"联丰社后头湾滑坡","type_name":"地质灾害点","number":"5001101060310101","lat":29.0355},{"lon":106.9213,"area_parent":275,"name":"石窑滑坡","type_name":"地质灾害点","number":"5001101060410101","lat":29.0058},{"lon":106.9002,"area_parent":275,"name":"中湾滑坡","type_name":"地质灾害点","number":"5001101060370101","lat":29.0344},{"lon":106.8872,"area_parent":275,"name":"幸福社滑坡","type_name":"地质灾害点","number":"5001101060360101","lat":29.0544},{"lon":106.9258,"area_parent":275,"name":"坟湾滑坡","type_name":"地质灾害点","number":"5001101060510101","lat":29.0286},{"lon":106.9286,"area_parent":275,"name":"蚕房滑坡","type_name":"地质灾害点","number":"5001101060520101","lat":29.0413},{"lon":106.9283,"area_parent":275,"name":"绿寸垭口滑坡","type_name":"地质灾害点","number":"5001101060540101","lat":29.0258},{"lon":106.9272,"area_parent":275,"name":"新田湾滑坡","type_name":"地质灾害点","number":"5001101060530101","lat":29.0336},{"lon":106.868,"area_parent":275,"name":"焱林湾滑坡","type_name":"地质灾害点","number":"5001101060600101","lat":29.043},{"lon":106.8852,"area_parent":275,"name":"杨垭口滑坡","type_name":"地质灾害点","number":"5001101060240101","lat":29.0616},{"lon":106.8977,"area_parent":275,"name":"岗上滑坡","type_name":"地质灾害点","number":"5001101060330101","lat":29.0372},{"lon":106.903,"area_parent":275,"name":"邓家岩滑坡","type_name":"地质灾害点","number":"5001101060033001","lat":29.0208},{"lon":106.8702,"area_parent":275,"name":"石梯子滑坡","type_name":"地质灾害点","number":"5001101060700101","lat":29.1069},{"lon":106.9127,"area_parent":275,"name":"太平榜滑坡","type_name":"地质灾害点","number":"5001101060490101","lat":29.0402},{"lon":106.893,"area_parent":275,"name":"哑口滑坡","type_name":"地质灾害点","number":"5001101060400101","lat":29.0005},{"lon":106.8627,"area_parent":275,"name":"丁家坪滑坡","type_name":"地质灾害点","number":"5001101060560101","lat":29.0561},{"lon":106.9236,"area_parent":275,"name":"石馆子滑坡","type_name":"地质灾害点","number":"5001101060270101","lat":29.0141},{"lon":106.8738,"area_parent":275,"name":"土生基滑坡","type_name":"地质灾害点","number":"5001101060280101","lat":29.0177},{"lon":106.8397,"area_parent":275,"name":"玉家岩滑坡","type_name":"地质灾害点","number":"5001101060710101","lat":29.0886},{"lon":106.9008,"area_parent":275,"name":"七一滑坡","type_name":"地质灾害点","number":"5001101060010101","lat":29.0338},{"lon":106.8738,"area_parent":275,"name":"土家湾滑坡","type_name":"地质灾害点","number":"5001101060200101","lat":29.0148},{"lon":106.9402,"area_parent":275,"name":"烂心子滑坡","type_name":"地质灾害点","number":"5001101060420101","lat":29.0069},{"lon":106.9,"area_parent":275,"name":"何家湾滑坡","type_name":"地质灾害点","number":"5001101060300101","lat":29.0547},{"lon":106.8675,"area_parent":275,"name":"下榜滑坡","type_name":"地质灾害点","number":"5001101060180101","lat":29.1041},{"lon":106.9016,"area_parent":275,"name":"花果园滑坡","type_name":"地质灾害点","number":"5001101060002101","lat":29.0466},{"lon":106.8913,"area_parent":275,"name":"沙田滑坡","type_name":"地质灾害点","number":"5001101000720101","lat":28.9605},{"lon":106.8838,"area_parent":275,"name":"垭口滑坡","type_name":"地质灾害点","number":"5001101000730101","lat":28.9761},{"lon":106.935,"area_parent":275,"name":"皂角滑坡","type_name":"地质灾害点","number":"5001101000040301","lat":28.9875},{"lon":106.9475,"area_parent":275,"name":"大院子滑坡","type_name":"地质灾害点","number":"5001101000040401","lat":28.9855},{"lon":106.9627,"area_parent":275,"name":"梨子榜滑坡","type_name":"地质灾害点","number":"5001101000040501","lat":28.9761},{"lon":106.9452,"area_parent":275,"name":"杨湾滑坡","type_name":"地质灾害点","number":"5001101000770101","lat":28.9719},{"lon":106.9097,"area_parent":275,"name":"卷子堡滑坡","type_name":"地质灾害点","number":"5001101000840101","lat":28.94},{"lon":106.9547,"area_parent":275,"name":"二层岩滑坡","type_name":"地质灾害点","number":"5001101000850101","lat":28.9797},{"lon":106.9475,"area_parent":275,"name":"韩家湾滑坡","type_name":"地质灾害点","number":"5001101000870101","lat":28.9836},{"lon":106.928,"area_parent":275,"name":"大土湾滑坡","type_name":"地质灾害点","number":"5001101000880101","lat":28.998},{"lon":106.8888,"area_parent":275,"name":"石桥沟滑坡","type_name":"地质灾害点","number":"5001101000070101","lat":28.9638},{"lon":106.9302,"area_parent":275,"name":"凤凰滑坡","type_name":"地质灾害点","number":"5001101000900101","lat":28.9144},{"lon":106.9255,"area_parent":275,"name":"会厂湾滑坡","type_name":"地质灾害点","number":"5001101000090101","lat":28.9969},{"lon":106.9366,"area_parent":275,"name":"干孔石厂滑坡","type_name":"地质灾害点","number":"5001101000110101","lat":28.9627},{"lon":106.7947,"area_parent":275,"name":"南天门滑坡","type_name":"地质灾害点","number":"5001101030920101","lat":28.8458},{"lon":106.7827,"area_parent":275,"name":"阴半岩滑坡","type_name":"地质灾害点","number":"5001101030020101","lat":28.8233},{"lon":106.788,"area_parent":275,"name":"半坡社滑坡","type_name":"地质灾害点","number":"5001101030970101","lat":28.8644},{"lon":106.8372,"area_parent":275,"name":"兴隆湾滑坡","type_name":"地质灾害点","number":"5001101030990101","lat":28.808},{"lon":106.7866,"area_parent":275,"name":"大榜社滑坡","type_name":"地质灾害点","number":"5001101030040101","lat":28.783},{"lon":106.7969,"area_parent":275,"name":"杨柳沟滑坡","type_name":"地质灾害点","number":"5001101030050101","lat":28.8305},{"lon":106.995,"area_parent":275,"name":"松涛湾滑坡","type_name":"地质灾害点","number":"5001101041020101","lat":29.0161},{"lon":106.9772,"area_parent":275,"name":"叶家沟滑坡","type_name":"地质灾害点","number":"5001101041060101","lat":28.9822},{"lon":106.8708,"area_parent":275,"name":"上田垭口滑坡","type_name":"地质灾害点","number":"5001101011070101","lat":29.0133},{"lon":106.8675,"area_parent":275,"name":"干丘堡滑坡","type_name":"地质灾害点","number":"5001101011080101","lat":29.0091},{"lon":106.9005,"area_parent":275,"name":"红星滑坡","type_name":"地质灾害点","number":"5001101051130101","lat":28.8627},{"lon":106.9358,"area_parent":275,"name":"范家坡滑坡","type_name":"地质灾害点","number":"5001101051140101","lat":28.8933},{"lon":106.9022,"area_parent":275,"name":"朱行滑坡","type_name":"地质灾害点","number":"5001101051170101","lat":28.8297},{"lon":106.978,"area_parent":275,"name":"大田滑坡","type_name":"地质灾害点","number":"5001101051180101","lat":28.8297},{"lon":106.965,"area_parent":275,"name":"千丘滑坡","type_name":"地质灾害点","number":"5001101051190101","lat":28.7936},{"lon":106.9463,"area_parent":275,"name":"河坝滑坡","type_name":"地质灾害点","number":"5001101051200101","lat":28.8002},{"lon":106.9127,"area_parent":275,"name":"石人足滑坡","type_name":"地质灾害点","number":"5001101051210101","lat":28.8641},{"lon":106.9008,"area_parent":275,"name":"蒙溪沟滑坡","type_name":"地质灾害点","number":"5001101051220101","lat":28.8838},{"lon":106.9227,"area_parent":275,"name":"瓦房坝滑坡","type_name":"地质灾害点","number":"5001101050081201","lat":28.8688},{"lon":106.9197,"area_parent":275,"name":"松树坡滑坡","type_name":"地质灾害点","number":"5001101050010101","lat":28.8508},{"lon":106.9363,"area_parent":275,"name":"大屋基滑坡","type_name":"地质灾害点","number":"5001101051250101","lat":28.8402},{"lon":106.9069,"area_parent":275,"name":"石板岩滑坡","type_name":"地质灾害点","number":"5001101051260101","lat":28.8836},{"lon":106.91805,"area_parent":275,"name":"大槽滑坡","type_name":"地质灾害点","number":"5001101051280101","lat":28.8266},{"lon":106.9108,"area_parent":275,"name":"大院子滑坡","type_name":"地质灾害点","number":"5001101051290101","lat":28.8902},{"lon":106.9238,"area_parent":275,"name":"新田滑坡","type_name":"地质灾害点","number":"5001101051300101","lat":28.893},{"lon":106.9794,"area_parent":275,"name":"小里村滑坡","type_name":"地质灾害点","number":"5001101051310101","lat":28.785},{"lon":106.9608,"area_parent":275,"name":"老房子滑坡","type_name":"地质灾害点","number":"5001101050020101","lat":28.8247},{"lon":106.89,"area_parent":275,"name":"通湾社滑坡","type_name":"地质灾害点","number":"5001101050030101","lat":28.8641},{"lon":106.9857542616,"area_parent":275,"name":"丛林黑公路滑坡","type_name":"地质灾害点","number":"5001101","lat":28.9645944713},{"lon":106.916,"area_parent":275,"name":"建设村石桥沟滑坡","type_name":"地质灾害点","number":"5001102","lat":28.978},{"lon":106.9209061737,"area_parent":275,"name":"农林村龙洞坪社山角箐滑坡","type_name":"地质灾害点","number":"5001100706","lat":28.8899814038}]
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
         * lon : 106.8088
         * area_parent : 275
         * name : 关山沟滑坡
         * type_name : 地质灾害点
         * number : 5001101020010101
         * lat : 28.8683
         */

        private double lon;
        private int area_parent;
        private String name;
        private String type_name;
        private String number;
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public int getArea_parent() {
            return area_parent;
        }

        public void setArea_parent(int area_parent) {
            this.area_parent = area_parent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
