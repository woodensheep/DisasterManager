package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingsong on 2017/9/29.
 */
@Entity
public class GTSLocationPoint {
    @Id
    private Long id;
    private double jd;
    private String adminname;
    private String town;
    private double wd;
    @Generated(hash = 781771423)
    public GTSLocationPoint(Long id, double jd, String adminname, String town,
            double wd) {
        this.id = id;
        this.jd = jd;
        this.adminname = adminname;
        this.town = town;
        this.wd = wd;
    }
    @Generated(hash = 1348630621)
    public GTSLocationPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getJd() {
        return this.jd;
    }
    public void setJd(double jd) {
        this.jd = jd;
    }
    public String getAdminname() {
        return this.adminname;
    }
    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }
    public String getTown() {
        return this.town;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public double getWd() {
        return this.wd;
    }
    public void setWd(double wd) {
        this.wd = wd;
    }
}
