package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/10.
 * 防灾预案
 */

public class DetailPnInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"id":11222,"pDisNo":null,"pMonitorMan":"朱绍碧","pMonitorPhone":"15702327885","pVillageHead":"郭召平","pVillagePhone":"15702327889","pGroupHead":"郭召平","pGroupPhone":"15702327889","pAlarmCall":"15702327889","pNo":"5001101020010101","pDisName":"关山沟滑坡","pECity":"","pEDistrict":"","pETown":"","pEVillage":"","pEGroup":"","pSurveyWay":"村级监测，加强巡查工作，做好监测记录及时向上级反映监测情况","pSurveyCycle":"雨季：每周一次，   变形剧烈：每日一次或数次。","pSurveyMeans":"巡视、测量、记录，注意有无新的变形情况","pWallPart":"滑坡整体及边缘，地裂","pType":"呼喊、敲锣","pEMeasure":"组织撤离、报告上级、进行抢险、设立警戒、安置灾民","pOutLine":"垂直主滑方向，沿滑坡两侧撤离","pEOrder":"优先老人、儿童和妇女","pLinePic":"5001101020010101.jpg","pWeaveUnit":"重庆地质矿产研究院","pApproveUnit":"重庆市万盛区国土房管局"}
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
         * id : 11222
         * pDisNo : null
         * pMonitorMan : 朱绍碧
         * pMonitorPhone : 15702327885
         * pVillageHead : 郭召平
         * pVillagePhone : 15702327889
         * pGroupHead : 郭召平
         * pGroupPhone : 15702327889
         * pAlarmCall : 15702327889
         * pNo : 5001101020010101
         * pDisName : 关山沟滑坡
         * pECity :
         * pEDistrict :
         * pETown :
         * pEVillage :
         * pEGroup :
         * pSurveyWay : 村级监测，加强巡查工作，做好监测记录及时向上级反映监测情况
         * pSurveyCycle : 雨季：每周一次，   变形剧烈：每日一次或数次。
         * pSurveyMeans : 巡视、测量、记录，注意有无新的变形情况
         * pWallPart : 滑坡整体及边缘，地裂
         * pType : 呼喊、敲锣
         * pEMeasure : 组织撤离、报告上级、进行抢险、设立警戒、安置灾民
         * pOutLine : 垂直主滑方向，沿滑坡两侧撤离
         * pEOrder : 优先老人、儿童和妇女
         * pLinePic : 5001101020010101.jpg
         * pWeaveUnit : 重庆地质矿产研究院
         * pApproveUnit : 重庆市万盛区国土房管局
         */

        private int id;
        private String pDisNo;
        private String pMonitorMan;
        private String pMonitorPhone;
        private String pVillageHead;
        private String pVillagePhone;
        private String pGroupHead;
        private String pGroupPhone;
        private String pAlarmCall;
        private String pNo;
        private String pDisName;
        private String pECity;
        private String pEDistrict;
        private String pETown;
        private String pEVillage;
        private String pEGroup;
        private String pSurveyWay;
        private String pSurveyCycle;
        private String pSurveyMeans;
        private String pWallPart;
        private String pType;
        private String pEMeasure;
        private String pOutLine;
        private String pEOrder;
        private String pLinePic;
        private String pWeaveUnit;
        private String pApproveUnit;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPDisNo() {
            return pDisNo;
        }

        public void setPDisNo(String pDisNo) {
            this.pDisNo = pDisNo;
        }

        public String getPMonitorMan() {
            return pMonitorMan;
        }

        public void setPMonitorMan(String pMonitorMan) {
            this.pMonitorMan = pMonitorMan;
        }

        public String getPMonitorPhone() {
            return pMonitorPhone;
        }

        public void setPMonitorPhone(String pMonitorPhone) {
            this.pMonitorPhone = pMonitorPhone;
        }

        public String getPVillageHead() {
            return pVillageHead;
        }

        public void setPVillageHead(String pVillageHead) {
            this.pVillageHead = pVillageHead;
        }

        public String getPVillagePhone() {
            return pVillagePhone;
        }

        public void setPVillagePhone(String pVillagePhone) {
            this.pVillagePhone = pVillagePhone;
        }

        public String getPGroupHead() {
            return pGroupHead;
        }

        public void setPGroupHead(String pGroupHead) {
            this.pGroupHead = pGroupHead;
        }

        public String getPGroupPhone() {
            return pGroupPhone;
        }

        public void setPGroupPhone(String pGroupPhone) {
            this.pGroupPhone = pGroupPhone;
        }

        public String getPAlarmCall() {
            return pAlarmCall;
        }

        public void setPAlarmCall(String pAlarmCall) {
            this.pAlarmCall = pAlarmCall;
        }

        public String getPNo() {
            return pNo;
        }

        public void setPNo(String pNo) {
            this.pNo = pNo;
        }

        public String getPDisName() {
            return pDisName;
        }

        public void setPDisName(String pDisName) {
            this.pDisName = pDisName;
        }

        public String getPECity() {
            return pECity;
        }

        public void setPECity(String pECity) {
            this.pECity = pECity;
        }

        public String getPEDistrict() {
            return pEDistrict;
        }

        public void setPEDistrict(String pEDistrict) {
            this.pEDistrict = pEDistrict;
        }

        public String getPETown() {
            return pETown;
        }

        public void setPETown(String pETown) {
            this.pETown = pETown;
        }

        public String getPEVillage() {
            return pEVillage;
        }

        public void setPEVillage(String pEVillage) {
            this.pEVillage = pEVillage;
        }

        public String getPEGroup() {
            return pEGroup;
        }

        public void setPEGroup(String pEGroup) {
            this.pEGroup = pEGroup;
        }

        public String getPSurveyWay() {
            return pSurveyWay;
        }

        public void setPSurveyWay(String pSurveyWay) {
            this.pSurveyWay = pSurveyWay;
        }

        public String getPSurveyCycle() {
            return pSurveyCycle;
        }

        public void setPSurveyCycle(String pSurveyCycle) {
            this.pSurveyCycle = pSurveyCycle;
        }

        public String getPSurveyMeans() {
            return pSurveyMeans;
        }

        public void setPSurveyMeans(String pSurveyMeans) {
            this.pSurveyMeans = pSurveyMeans;
        }

        public String getPWallPart() {
            return pWallPart;
        }

        public void setPWallPart(String pWallPart) {
            this.pWallPart = pWallPart;
        }

        public String getPType() {
            return pType;
        }

        public void setPType(String pType) {
            this.pType = pType;
        }

        public String getPEMeasure() {
            return pEMeasure;
        }

        public void setPEMeasure(String pEMeasure) {
            this.pEMeasure = pEMeasure;
        }

        public String getPOutLine() {
            return pOutLine;
        }

        public void setPOutLine(String pOutLine) {
            this.pOutLine = pOutLine;
        }

        public String getPEOrder() {
            return pEOrder;
        }

        public void setPEOrder(String pEOrder) {
            this.pEOrder = pEOrder;
        }

        public String getPLinePic() {
            return pLinePic;
        }

        public void setPLinePic(String pLinePic) {
            this.pLinePic = pLinePic;
        }

        public String getPWeaveUnit() {
            return pWeaveUnit;
        }

        public void setPWeaveUnit(String pWeaveUnit) {
            this.pWeaveUnit = pWeaveUnit;
        }

        public String getPApproveUnit() {
            return pApproveUnit;
        }

        public void setPApproveUnit(String pApproveUnit) {
            this.pApproveUnit = pApproveUnit;
        }
    }
}
