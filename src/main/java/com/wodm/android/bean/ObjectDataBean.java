package com.wodm.android.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/4/27.
 */
public class ObjectDataBean {

    private int id;
    private String name;
    private String icon;
    private List<ObjectBean> resources = new ArrayList<>();


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ObjectBean> getResources() {
        return resources;
    }

    public void setResources(List<ObjectBean> resources) {
        this.resources = resources;
    }
}
