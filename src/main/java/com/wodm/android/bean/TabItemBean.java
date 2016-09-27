package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by moon1 on 2016/4/27.
 */
public class TabItemBean implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
