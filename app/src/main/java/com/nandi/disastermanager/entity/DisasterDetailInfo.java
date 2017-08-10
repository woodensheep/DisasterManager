package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/9.
 */

public class DisasterDetailInfo {

    private List<DisPlanBean> disPlan;
    private List<HedgeCardBean> HedgeCard;
    private List<PersonsMessageBean> personsMessage;
    private List<DisBasicInfoBean> disBasicInfo;
    private List<FCardBean> fCard;
    private List<PicUrlsBean> picUrls;
    private List<Family0Bean> family0;

    public List<DisPlanBean> getDisPlan() {
        return disPlan;
    }

    public void setDisPlan(List<DisPlanBean> disPlan) {
        this.disPlan = disPlan;
    }

    public List<HedgeCardBean> getHedgeCard() {
        return HedgeCard;
    }

    public void setHedgeCard(List<HedgeCardBean> HedgeCard) {
        this.HedgeCard = HedgeCard;
    }

    public List<PersonsMessageBean> getPersonsMessage() {
        return personsMessage;
    }

    public void setPersonsMessage(List<PersonsMessageBean> personsMessage) {
        this.personsMessage = personsMessage;
    }

    public List<DisBasicInfoBean> getDisBasicInfo() {
        return disBasicInfo;
    }

    public void setDisBasicInfo(List<DisBasicInfoBean> disBasicInfo) {
        this.disBasicInfo = disBasicInfo;
    }

    public List<FCardBean> getFCard() {
        return fCard;
    }

    public void setFCard(List<FCardBean> fCard) {
        this.fCard = fCard;
    }

    public List<PicUrlsBean> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<PicUrlsBean> picUrls) {
        this.picUrls = picUrls;
    }

    public List<Family0Bean> getFamily0() {
        return family0;
    }

    public void setFamily0(List<Family0Bean> family0) {
        this.family0 = family0;
    }

    public static class DisPlanBean {
        /**
         * p_group_phone :
         * p_e_city : 重庆
         * p_e_order : 8
         * p_weave_unit :
         * p_village_head :
         * p_approve_unit :
         * p_survey_way : 1ggg
         * p_line_pic : 1469786307990.png
         * p_monitor_man :
         * p_group_head :
         * p_alarm_call :
         * p_dis_no : 0
         * p_wall_part : 4
         * p_out_line : 7
         * p_village_phone :
         * p_survey_means : 3
         * p_e_district : 万州
         * p_type : 5
         * p_e_measure : 6
         * p_monitor_phone :
         * p_survey_cycle : 2
         * id : 37
         */

        private String p_group_phone;
        private String p_e_city;
        private String p_e_order;
        private String p_weave_unit;
        private String p_village_head;
        private String p_approve_unit;
        private String p_survey_way;
        private String p_line_pic;
        private String p_monitor_man;
        private String p_group_head;
        private String p_alarm_call;
        private int p_dis_no;
        private String p_wall_part;
        private String p_out_line;
        private String p_village_phone;
        private String p_survey_means;
        private String p_e_district;
        private String p_type;
        private String p_e_measure;
        private String p_monitor_phone;
        private String p_survey_cycle;
        private int id;

        public String getP_group_phone() {
            return p_group_phone;
        }

        public void setP_group_phone(String p_group_phone) {
            this.p_group_phone = p_group_phone;
        }

        public String getP_e_city() {
            return p_e_city;
        }

        public void setP_e_city(String p_e_city) {
            this.p_e_city = p_e_city;
        }

        public String getP_e_order() {
            return p_e_order;
        }

        public void setP_e_order(String p_e_order) {
            this.p_e_order = p_e_order;
        }

        public String getP_weave_unit() {
            return p_weave_unit;
        }

        public void setP_weave_unit(String p_weave_unit) {
            this.p_weave_unit = p_weave_unit;
        }

        public String getP_village_head() {
            return p_village_head;
        }

        public void setP_village_head(String p_village_head) {
            this.p_village_head = p_village_head;
        }

        public String getP_approve_unit() {
            return p_approve_unit;
        }

        public void setP_approve_unit(String p_approve_unit) {
            this.p_approve_unit = p_approve_unit;
        }

        public String getP_survey_way() {
            return p_survey_way;
        }

        public void setP_survey_way(String p_survey_way) {
            this.p_survey_way = p_survey_way;
        }

        public String getP_line_pic() {
            return p_line_pic;
        }

        public void setP_line_pic(String p_line_pic) {
            this.p_line_pic = p_line_pic;
        }

        public String getP_monitor_man() {
            return p_monitor_man;
        }

        public void setP_monitor_man(String p_monitor_man) {
            this.p_monitor_man = p_monitor_man;
        }

        public String getP_group_head() {
            return p_group_head;
        }

        public void setP_group_head(String p_group_head) {
            this.p_group_head = p_group_head;
        }

        public String getP_alarm_call() {
            return p_alarm_call;
        }

        public void setP_alarm_call(String p_alarm_call) {
            this.p_alarm_call = p_alarm_call;
        }

        public int getP_dis_no() {
            return p_dis_no;
        }

        public void setP_dis_no(int p_dis_no) {
            this.p_dis_no = p_dis_no;
        }

        public String getP_wall_part() {
            return p_wall_part;
        }

        public void setP_wall_part(String p_wall_part) {
            this.p_wall_part = p_wall_part;
        }

        public String getP_out_line() {
            return p_out_line;
        }

        public void setP_out_line(String p_out_line) {
            this.p_out_line = p_out_line;
        }

        public String getP_village_phone() {
            return p_village_phone;
        }

        public void setP_village_phone(String p_village_phone) {
            this.p_village_phone = p_village_phone;
        }

        public String getP_survey_means() {
            return p_survey_means;
        }

        public void setP_survey_means(String p_survey_means) {
            this.p_survey_means = p_survey_means;
        }

        public String getP_e_district() {
            return p_e_district;
        }

        public void setP_e_district(String p_e_district) {
            this.p_e_district = p_e_district;
        }

        public String getP_type() {
            return p_type;
        }

        public void setP_type(String p_type) {
            this.p_type = p_type;
        }

        public String getP_e_measure() {
            return p_e_measure;
        }

        public void setP_e_measure(String p_e_measure) {
            this.p_e_measure = p_e_measure;
        }

        public String getP_monitor_phone() {
            return p_monitor_phone;
        }

        public void setP_monitor_phone(String p_monitor_phone) {
            this.p_monitor_phone = p_monitor_phone;
        }

        public String getP_survey_cycle() {
            return p_survey_cycle;
        }

        public void setP_survey_cycle(String p_survey_cycle) {
            this.p_survey_cycle = p_survey_cycle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class HedgeCardBean {
        /**
         * h_ambulance_man : 123
         * h_grant_date : 2017-08-10
         * h_dis_matters :
         * h_family_num : 123
         * h_holder_date : 2017-08-10
         * h_holder_phone : 123
         * h_grant_unit : 123
         * h_family_name : 123
         * dis_id : 37
         * h_dis_scale : 123
         * h_dis_type : 123
         * h_holder : 123
         * h_ambulance_phone : 123
         * h_eva_placement : 123
         * h_placement_man : 123
         * h_pre_phone :
         * id : 37
         * h_pre_signal : 123
         * h_house_type : 123
         * h_eva_line : 123
         * h_dis_factor :
         * h_address : 123
         * h_dis_relationship :
         * h_placement_phone : 123
         * h_grant_phone : 123
         * h_ambulance_unit : 123
         * h_pre_man :
         * h_signal_phone : 123
         * h_signal_man : 123
         */

        private String h_ambulance_man;
        private String h_grant_date;
        private String h_dis_matters;
        private String h_family_num;
        private String h_holder_date;
        private String h_holder_phone;
        private String h_grant_unit;
        private String h_family_name;
        private int dis_id;
        private String h_dis_scale;
        private String h_dis_type;
        private String h_holder;
        private String h_ambulance_phone;
        private String h_eva_placement;
        private String h_placement_man;
        private String h_pre_phone;
        private int id;
        private String h_pre_signal;
        private String h_house_type;
        private String h_eva_line;
        private String h_dis_factor;
        private String h_address;
        private String h_dis_relationship;
        private String h_placement_phone;
        private String h_grant_phone;
        private String h_ambulance_unit;
        private String h_pre_man;
        private String h_signal_phone;
        private String h_signal_man;


        public String getH_ambulance_man() {
            return h_ambulance_man;
        }

        public void setH_ambulance_man(String h_ambulance_man) {
            this.h_ambulance_man = h_ambulance_man;
        }

        public String getH_grant_date() {
            return h_grant_date;
        }

        public void setH_grant_date(String h_grant_date) {
            this.h_grant_date = h_grant_date;
        }

        public String getH_dis_matters() {
            return h_dis_matters;
        }

        public void setH_dis_matters(String h_dis_matters) {
            this.h_dis_matters = h_dis_matters;
        }

        public String getH_family_num() {
            return h_family_num;
        }

        public void setH_family_num(String h_family_num) {
            this.h_family_num = h_family_num;
        }

        public String getH_holder_date() {
            return h_holder_date;
        }

        public void setH_holder_date(String h_holder_date) {
            this.h_holder_date = h_holder_date;
        }

        public String getH_holder_phone() {
            return h_holder_phone;
        }

        public void setH_holder_phone(String h_holder_phone) {
            this.h_holder_phone = h_holder_phone;
        }

        public String getH_grant_unit() {
            return h_grant_unit;
        }

        public void setH_grant_unit(String h_grant_unit) {
            this.h_grant_unit = h_grant_unit;
        }

        public String getH_family_name() {
            return h_family_name;
        }

        public void setH_family_name(String h_family_name) {
            this.h_family_name = h_family_name;
        }

        public int getDis_id() {
            return dis_id;
        }

        public void setDis_id(int dis_id) {
            this.dis_id = dis_id;
        }

        public String getH_dis_scale() {
            return h_dis_scale;
        }

        public void setH_dis_scale(String h_dis_scale) {
            this.h_dis_scale = h_dis_scale;
        }

        public String getH_dis_type() {
            return h_dis_type;
        }

        public void setH_dis_type(String h_dis_type) {
            this.h_dis_type = h_dis_type;
        }

        public String getH_holder() {
            return h_holder;
        }

        public void setH_holder(String h_holder) {
            this.h_holder = h_holder;
        }

        public String getH_ambulance_phone() {
            return h_ambulance_phone;
        }

        public void setH_ambulance_phone(String h_ambulance_phone) {
            this.h_ambulance_phone = h_ambulance_phone;
        }

        public String getH_eva_placement() {
            return h_eva_placement;
        }

        public void setH_eva_placement(String h_eva_placement) {
            this.h_eva_placement = h_eva_placement;
        }

        public String getH_placement_man() {
            return h_placement_man;
        }

        public void setH_placement_man(String h_placement_man) {
            this.h_placement_man = h_placement_man;
        }

        public String getH_pre_phone() {
            return h_pre_phone;
        }

        public void setH_pre_phone(String h_pre_phone) {
            this.h_pre_phone = h_pre_phone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getH_pre_signal() {
            return h_pre_signal;
        }

        public void setH_pre_signal(String h_pre_signal) {
            this.h_pre_signal = h_pre_signal;
        }

        public String getH_house_type() {
            return h_house_type;
        }

        public void setH_house_type(String h_house_type) {
            this.h_house_type = h_house_type;
        }

        public String getH_eva_line() {
            return h_eva_line;
        }

        public void setH_eva_line(String h_eva_line) {
            this.h_eva_line = h_eva_line;
        }

        public String getH_dis_factor() {
            return h_dis_factor;
        }

        public void setH_dis_factor(String h_dis_factor) {
            this.h_dis_factor = h_dis_factor;
        }

        public String getH_address() {
            return h_address;
        }

        public void setH_address(String h_address) {
            this.h_address = h_address;
        }

        public String getH_dis_relationship() {
            return h_dis_relationship;
        }

        public void setH_dis_relationship(String h_dis_relationship) {
            this.h_dis_relationship = h_dis_relationship;
        }

        public String getH_placement_phone() {
            return h_placement_phone;
        }

        public void setH_placement_phone(String h_placement_phone) {
            this.h_placement_phone = h_placement_phone;
        }

        public String getH_grant_phone() {
            return h_grant_phone;
        }

        public void setH_grant_phone(String h_grant_phone) {
            this.h_grant_phone = h_grant_phone;
        }

        public String getH_ambulance_unit() {
            return h_ambulance_unit;
        }

        public void setH_ambulance_unit(String h_ambulance_unit) {
            this.h_ambulance_unit = h_ambulance_unit;
        }

        public String getH_pre_man() {
            return h_pre_man;
        }

        public void setH_pre_man(String h_pre_man) {
            this.h_pre_man = h_pre_man;
        }

        public String getH_signal_phone() {
            return h_signal_phone;
        }

        public void setH_signal_phone(String h_signal_phone) {
            this.h_signal_phone = h_signal_phone;
        }

        public String getH_signal_man() {
            return h_signal_man;
        }

        public void setH_signal_man(String h_signal_man) {
            this.h_signal_man = h_signal_man;
        }
    }

    public static class PersonsMessageBean {
        /**
         * human_name : 魏佐明
         * zhushouid : 394
         *   : 13896028589
         * qcqfid : 895
         * dispicture : upload/jiancetixi/4103e9f7e4e14fb5b09b5e8d9d20cdb6.png
         * human_mobile : 15702310784
         * human_head_url : tabFile/HeadPic/1462953533089.png
         * disname : 林尤跃
         */

        private String human_name;
        private int zhushouid;
        private String p_telephone;
        private int qcqfid;
        private String dispicture;
        private String human_mobile;
        private String human_head_url;
        private String disname;

        public String getHuman_name() {
            return human_name;
        }

        public void setHuman_name(String human_name) {
            this.human_name = human_name;
        }

        public int getZhushouid() {
            return zhushouid;
        }

        public void setZhushouid(int zhushouid) {
            this.zhushouid = zhushouid;
        }

        public String getP_telephone() {
            return p_telephone;
        }

        public void setP_telephone(String p_telephone) {
            this.p_telephone = p_telephone;
        }

        public int getQcqfid() {
            return qcqfid;
        }

        public void setQcqfid(int qcqfid) {
            this.qcqfid = qcqfid;
        }

        public String getDispicture() {
            return dispicture;
        }

        public void setDispicture(String dispicture) {
            this.dispicture = dispicture;
        }

        public String getHuman_mobile() {
            return human_mobile;
        }

        public void setHuman_mobile(String human_mobile) {
            this.human_mobile = human_mobile;
        }

        public String getHuman_head_url() {
            return human_head_url;
        }

        public void setHuman_head_url(String human_head_url) {
            this.human_head_url = human_head_url;
        }

        public String getDisname() {
            return disname;
        }

        public void setDisname(String disname) {
            this.disname = disname;
        }
    }

    public static class DisBasicInfoBean {
        /**
         * area_name : 陈家坝街道
         * has_mobile : 1
         * dis_before : 120
         * come_time : 2010-05-01
         * dis_radius : 2
         * stable_level : 31
         * dis_lon : 108.2708659
         * scale : 17
         * dis_state : 1
         * Operation : 1
         * area_id : 104
         * dis_after : 325
         * dis_name : 塘角村1号滑坡
         * state_time : 2018-08-08
         * imperil_area : 0
         * dis_area : 0
         * bz : (Binary/Image)
         * dis_type : 1
         * id : 38
         * dis_slope : 25
         * dis_location : 万州区陈家坝街道塘角村、晒网村
         * defenseName : 三级
         * warn_mobile : 13896944880
         * imperilName : 小型
         * dis_volume : 0
         * statbleName : 欠稳定
         * deal_idea : null
         * disType : 滑坡
         * main_object : 村民
         * imperil_money : 0
         * dis_no : 500101000020 02 01
         * dis_lat : 30.49346567
         * dis_cause : 暴雨,库水位
         * defense_level : 39
         * qcqfry_id : 895
         * imperil_level : 19
         */

        private String area_name;
        private int has_mobile;
        private String dis_before;
        private String come_time;
        private int dis_radius;
        private int stable_level;
        private String dis_lon;
        private int scale;
        private int dis_state;
        private int Operation;
        private int area_id;
        private String dis_after;
        private String dis_name;
        private String state_time;
        private String imperil_man;
        private String imperil_families;
        private String imperil_area;
        private String dis_area;
        private String bz;
        private int dis_type;
        private int id;
        private String dis_slope;
        private String dis_location;
        private String defenseName;
        private String warn_mobile;
        private String imperilName;
        private String dis_volume;
        private String statbleName;
        private String deal_idea;
        private String disType;
        private String main_object;
        private String imperil_money;
        private String dis_no;
        private String dis_lat;
        private String dis_cause;
        private int defense_level;
        private int qcqfry_id;
        private int imperil_level;

        public String getImperil_man() {
            return imperil_man;
        }

        public void setImperil_man(String area_name) {
            this.imperil_man = imperil_man;
        }
        public String getImperil_families() {
            return imperil_families;
        }

        public void setImperil_families(String area_name) {
            this.imperil_families = imperil_families;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getHas_mobile() {
            return has_mobile;
        }

        public void setHas_mobile(int has_mobile) {
            this.has_mobile = has_mobile;
        }

        public String getDis_before() {
            return dis_before;
        }

        public void setDis_before(String dis_before) {
            this.dis_before = dis_before;
        }

        public String getCome_time() {
            return come_time;
        }

        public void setCome_time(String come_time) {
            this.come_time = come_time;
        }

        public int getDis_radius() {
            return dis_radius;
        }

        public void setDis_radius(int dis_radius) {
            this.dis_radius = dis_radius;
        }

        public int getStable_level() {
            return stable_level;
        }

        public void setStable_level(int stable_level) {
            this.stable_level = stable_level;
        }

        public String getDis_lon() {
            return dis_lon;
        }

        public void setDis_lon(String dis_lon) {
            this.dis_lon = dis_lon;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public int getDis_state() {
            return dis_state;
        }

        public void setDis_state(int dis_state) {
            this.dis_state = dis_state;
        }

        public int getOperation() {
            return Operation;
        }

        public void setOperation(int Operation) {
            this.Operation = Operation;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getDis_after() {
            return dis_after;
        }

        public void setDis_after(String dis_after) {
            this.dis_after = dis_after;
        }

        public String getDis_name() {
            return dis_name;
        }

        public void setDis_name(String dis_name) {
            this.dis_name = dis_name;
        }

        public String getState_time() {
            return state_time;
        }

        public void setState_time(String state_time) {
            this.state_time = state_time;
        }

        public String getImperil_area() {
            return imperil_area;
        }

        public void setImperil_area(String imperil_area) {
            this.imperil_area = imperil_area;
        }

        public String getDis_area() {
            return dis_area;
        }

        public void setDis_area(String dis_area) {
            this.dis_area = dis_area;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public int getDis_type() {
            return dis_type;
        }

        public void setDis_type(int dis_type) {
            this.dis_type = dis_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDis_slope() {
            return dis_slope;
        }

        public void setDis_slope(String dis_slope) {
            this.dis_slope = dis_slope;
        }

        public String getDis_location() {
            return dis_location;
        }

        public void setDis_location(String dis_location) {
            this.dis_location = dis_location;
        }

        public String getDefenseName() {
            return defenseName;
        }

        public void setDefenseName(String defenseName) {
            this.defenseName = defenseName;
        }

        public String getWarn_mobile() {
            return warn_mobile;
        }

        public void setWarn_mobile(String warn_mobile) {
            this.warn_mobile = warn_mobile;
        }

        public String getImperilName() {
            return imperilName;
        }

        public void setImperilName(String imperilName) {
            this.imperilName = imperilName;
        }

        public String getDis_volume() {
            return dis_volume;
        }

        public void setDis_volume(String dis_volume) {
            this.dis_volume = dis_volume;
        }

        public String getStatbleName() {
            return statbleName;
        }

        public void setStatbleName(String statbleName) {
            this.statbleName = statbleName;
        }

        public String getDeal_idea() {
            return deal_idea;
        }

        public void setDeal_idea(String deal_idea) {
            this.deal_idea = deal_idea;
        }

        public String getDisType() {
            return disType;
        }

        public void setDisType(String disType) {
            this.disType = disType;
        }

        public String getMain_object() {
            return main_object;
        }

        public void setMain_object(String main_object) {
            this.main_object = main_object;
        }

        public String getImperil_money() {
            return imperil_money;
        }

        public void setImperil_money(String imperil_money) {
            this.imperil_money = imperil_money;
        }

        public String getDis_no() {
            return dis_no;
        }

        public void setDis_no(String dis_no) {
            this.dis_no = dis_no;
        }

        public String getDis_lat() {
            return dis_lat;
        }

        public void setDis_lat(String dis_lat) {
            this.dis_lat = dis_lat;
        }

        public String getDis_cause() {
            return dis_cause;
        }

        public void setDis_cause(String dis_cause) {
            this.dis_cause = dis_cause;
        }

        public int getDefense_level() {
            return defense_level;
        }

        public void setDefense_level(int defense_level) {
            this.defense_level = defense_level;
        }

        public int getQcqfry_id() {
            return qcqfry_id;
        }

        public void setQcqfry_id(int qcqfry_id) {
            this.qcqfry_id = qcqfry_id;
        }

        public int getImperil_level() {
            return imperil_level;
        }

        public void setImperil_level(int imperil_level) {
            this.imperil_level = imperil_level;
        }
    }

    public static class FCardBean {
        /**
         * d_position : 滑坡位于陈家坝街道办事处晒网村5、6、7、9组
         * d_hold_phone : 123
         * d_evacuate_man : 人员
         * d_exclude_man : 123
         * d_grant_date : 2016-11-04
         * d_e_place : 地点
         * d_threat : 滑坡所在范围内人类工程活动主要为交通小路、房屋修建开挖切坡、居民耕种生活等活动对坡体形态进行改变和加载影响。自然陡坎造成前缘坡体应力释放
         * d_hold_unit : 123
         * id : 37
         * d_evacuate_phone : 电话
         * d_monitor_man : 魏佐明
         * d_alarm_type : 请不要输入超过100个字
         * d_monitor_judge : 请不要输入超过300个字
         * d_exclude_phone : 123
         * d_type : 地处长江南岸175米沿线，前缘高程150米，后缘高程253米。滑坡区地形为斜坡，地面坡度为40度，滑坡上靠陈太公路，下临长江库岸，为库岸滑坡。该滑坡上层系黄泥粘性土，并夹有孤石、碎石。下层为页岩，东至塘角村村界（河沟），西至塘角村五组（闺妹坟）。长度约1000米，宽约150米，厚度10米，面积约50万平方米，体积约500万立方米。
         * d_security_phone : 123
         * d_e_line : 线
         * d_security_man : 123
         * d_hold_date : 2016-11-04
         * d_e_signal : 信息号

         * d_grant_unit : 123
         * d_monitor_phone : 15702310784
         * d_induce_factor :  自然因素主要有降雨、地表水入渗等；人为因素主要有农业生产活动。在自然因素和人为因素的综合作用条件下，滑坡体上的崩积、残积含碎块石粉质粘土在重力作用的趋势沿岩土界面向下滑移，由于滑坡前缘临空，使之应力释放属牵引式滑坡。
         * d_monitor_sign : 请不要输入超过100个字
         * d_doc_phone : 123
         * d_doc_man : 123
         * d_grant_phone : 123
         */

        private String d_position;
        private String d_hold_phone;
        private String d_evacuate_man;
        private String d_exclude_man;
        private String d_grant_date;
        private String d_e_place;
        private String d_threat;
        private String d_hold_unit;
        private int id;
        private String d_evacuate_phone;
        private String d_monitor_man;
        private String d_alarm_type;
        private String d_monitor_judge;
        private String d_exclude_phone;
        private String d_type;
        private String d_security_phone;
        private String d_e_line;
        private String d_security_man;
        private String d_hold_date;
        private String d_e_signal;
        private String d_grant_unit;
        private String d_monitor_phone;
        private String d_induce_factor;
        private String d_monitor_sign;
        private String d_doc_phone;
        private String d_doc_man;
        private String d_grant_phone;

        public String getD_position() {
            return d_position;
        }

        public void setD_position(String d_position) {
            this.d_position = d_position;
        }

        public String getD_hold_phone() {
            return d_hold_phone;
        }

        public void setD_hold_phone(String d_hold_phone) {
            this.d_hold_phone = d_hold_phone;
        }

        public String getD_evacuate_man() {
            return d_evacuate_man;
        }

        public void setD_evacuate_man(String d_evacuate_man) {
            this.d_evacuate_man = d_evacuate_man;
        }

        public String getD_exclude_man() {
            return d_exclude_man;
        }

        public void setD_exclude_man(String d_exclude_man) {
            this.d_exclude_man = d_exclude_man;
        }

        public String getD_grant_date() {
            return d_grant_date;
        }

        public void setD_grant_date(String d_grant_date) {
            this.d_grant_date = d_grant_date;
        }

        public String getD_e_place() {
            return d_e_place;
        }

        public void setD_e_place(String d_e_place) {
            this.d_e_place = d_e_place;
        }

        public String getD_threat() {
            return d_threat;
        }

        public void setD_threat(String d_threat) {
            this.d_threat = d_threat;
        }

        public String getD_hold_unit() {
            return d_hold_unit;
        }

        public void setD_hold_unit(String d_hold_unit) {
            this.d_hold_unit = d_hold_unit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getD_evacuate_phone() {
            return d_evacuate_phone;
        }

        public void setD_evacuate_phone(String d_evacuate_phone) {
            this.d_evacuate_phone = d_evacuate_phone;
        }

        public String getD_monitor_man() {
            return d_monitor_man;
        }

        public void setD_monitor_man(String d_monitor_man) {
            this.d_monitor_man = d_monitor_man;
        }

        public String getD_alarm_type() {
            return d_alarm_type;
        }

        public void setD_alarm_type(String d_alarm_type) {
            this.d_alarm_type = d_alarm_type;
        }

        public String getD_monitor_judge() {
            return d_monitor_judge;
        }

        public void setD_monitor_judge(String d_monitor_judge) {
            this.d_monitor_judge = d_monitor_judge;
        }

        public String getD_exclude_phone() {
            return d_exclude_phone;
        }

        public void setD_exclude_phone(String d_exclude_phone) {
            this.d_exclude_phone = d_exclude_phone;
        }

        public String getD_type() {
            return d_type;
        }

        public void setD_type(String d_type) {
            this.d_type = d_type;
        }

        public String getD_security_phone() {
            return d_security_phone;
        }

        public void setD_security_phone(String d_security_phone) {
            this.d_security_phone = d_security_phone;
        }

        public String getD_e_line() {
            return d_e_line;
        }

        public void setD_e_line(String d_e_line) {
            this.d_e_line = d_e_line;
        }

        public String getD_security_man() {
            return d_security_man;
        }

        public void setD_security_man(String d_security_man) {
            this.d_security_man = d_security_man;
        }

        public String getD_hold_date() {
            return d_hold_date;
        }

        public void setD_hold_date(String d_hold_date) {
            this.d_hold_date = d_hold_date;
        }

        public String getD_e_signal() {
            return d_e_signal;
        }

        public void setD_e_signal(String d_e_signal) {
            this.d_e_signal = d_e_signal;
        }

        public String getD_grant_unit() {
            return d_grant_unit;
        }

        public void setD_grant_unit(String d_grant_unit) {
            this.d_grant_unit = d_grant_unit;
        }

        public String getD_monitor_phone() {
            return d_monitor_phone;
        }

        public void setD_monitor_phone(String d_monitor_phone) {
            this.d_monitor_phone = d_monitor_phone;
        }

        public String getD_induce_factor() {
            return d_induce_factor;
        }

        public void setD_induce_factor(String d_induce_factor) {
            this.d_induce_factor = d_induce_factor;
        }

        public String getD_monitor_sign() {
            return d_monitor_sign;
        }

        public void setD_monitor_sign(String d_monitor_sign) {
            this.d_monitor_sign = d_monitor_sign;
        }

        public String getD_doc_phone() {
            return d_doc_phone;
        }

        public void setD_doc_phone(String d_doc_phone) {
            this.d_doc_phone = d_doc_phone;
        }

        public String getD_doc_man() {
            return d_doc_man;
        }

        public void setD_doc_man(String d_doc_man) {
            this.d_doc_man = d_doc_man;
        }

        public String getD_grant_phone() {
            return d_grant_phone;
        }

        public void setD_grant_phone(String d_grant_phone) {
            this.d_grant_phone = d_grant_phone;
        }
    }

    public static class PicUrlsBean {
        /**
         * url :
         * type :
         */

        private String url;
        private String type;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Family0Bean {
        /**
         * m_name_one : 董远路
         * m_age_one : 14
         * hedge_id : 62767
         * id : 4794
         * m_sex_one : 1
         */

        private String m_name_one;
        private String m_age_one;
        private int hedge_id;
        private int id;
        private String m_sex_one;

        public String getM_name_one() {
            return m_name_one;
        }

        public void setM_name_one(String m_name_one) {
            this.m_name_one = m_name_one;
        }

        public String getM_age_one() {
            return m_age_one;
        }

        public void setM_age_one(String m_age_one) {
            this.m_age_one = m_age_one;
        }

        public int getHedge_id() {
            return hedge_id;
        }

        public void setHedge_id(int hedge_id) {
            this.hedge_id = hedge_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getM_sex_one() {
            return m_sex_one;
        }

        public void setM_sex_one(String m_sex_one) {
            this.m_sex_one = m_sex_one;
        }
    }
}
