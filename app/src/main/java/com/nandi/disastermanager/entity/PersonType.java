package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/15.
 *
 * id与type对应
 */
public class PersonType {
    private int id;
    private String type;

    public PersonType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
