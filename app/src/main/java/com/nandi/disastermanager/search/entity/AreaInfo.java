package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lemon on 2017/9/9.
 */
@Entity
public class AreaInfo {
    @Id
    private Long id;
    private int level;
    private String name;
    private int area_code;
    private int area_parent;
    private int area_id;
    @Generated(hash = 451257477)
    public AreaInfo(Long id, int level, String name, int area_code, int area_parent,
            int area_id) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.area_code = area_code;
        this.area_parent = area_parent;
        this.area_id = area_id;
    }
    @Generated(hash = 177146206)
    public AreaInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getArea_code() {
        return this.area_code;
    }
    public void setArea_code(int area_code) {
        this.area_code = area_code;
    }
    public int getArea_parent() {
        return this.area_parent;
    }
    public void setArea_parent(int area_parent) {
        this.area_parent = area_parent;
    }
    public int getArea_id() {
        return this.area_id;
    }
    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }
}
