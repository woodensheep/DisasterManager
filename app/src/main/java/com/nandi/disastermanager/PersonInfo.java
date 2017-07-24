package com.nandi.disastermanager;

/**
 * Created by ChenPeng on 2017/7/21.
 */

public class PersonInfo {

    /**
     * work : 监测人
     * head_url : tabFile/HeadPic/1463995203691.png
     * Operation : 0
     * area_id : 95
     * polics : 1
     * state : 1
     * password : 123456
     * nation : 汉
     * id : 189
     * village : 吊龙村4组
     * address : 重庆万州区钟鼓楼街道吊龙村4组
     * real_mobile : 13452630503
     * name : 向中华
     * brithday : 1959-07-15
     * imsi : 862659023935190
     * types : 0
     * worker_id : 1
     * is_monitor : 0
     * culture : 1
     * mobile : 15702310956
     */

    private String work;
    private String head_url;
    private int Operation;
    private int area_id;
    private String polics;
    private String state;
    private String password;
    private String nation;
    private int id;
    private String village;
    private String address;
    private String real_mobile;
    private String name;
    private String brithday;
    private String imsi;
    private int types;
    private int worker_id;
    private int is_monitor;
    private String culture;
    private String mobile;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
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

    public String getPolics() {
        return polics;
    }

    public void setPolics(String polics) {
        this.polics = polics;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReal_mobile() {
        return real_mobile;
    }

    public void setReal_mobile(String real_mobile) {
        this.real_mobile = real_mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public int getIs_monitor() {
        return is_monitor;
    }

    public void setIs_monitor(int is_monitor) {
        this.is_monitor = is_monitor;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "work='" + work + '\'' +
                ", head_url='" + head_url + '\'' +
                ", Operation=" + Operation +
                ", area_id=" + area_id +
                ", polics='" + polics + '\'' +
                ", state='" + state + '\'' +
                ", password='" + password + '\'' +
                ", nation='" + nation + '\'' +
                ", id=" + id +
                ", village='" + village + '\'' +
                ", address='" + address + '\'' +
                ", real_mobile='" + real_mobile + '\'' +
                ", name='" + name + '\'' +
                ", brithday='" + brithday + '\'' +
                ", imsi='" + imsi + '\'' +
                ", types=" + types +
                ", worker_id=" + worker_id +
                ", is_monitor=" + is_monitor +
                ", culture='" + culture + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
