package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ChenPeng on 2017/9/28.
 */
@Entity
public class PhotoPath {
    @Id(autoincrement = true)
    private Long id;
    private String Path;
    @Generated(hash = 1973115512)
    public PhotoPath(Long id, String Path) {
        this.id = id;
        this.Path = Path;
    }
    @Generated(hash = 1895854510)
    public PhotoPath() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPath() {
        return this.Path;
    }
    public void setPath(String Path) {
        this.Path = Path;
    }
}
