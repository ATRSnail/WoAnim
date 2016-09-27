package com.wodm.android.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moon1 on 2016/4/27.
 */
public class TypeBean implements Serializable {
    private String name;
    private String parameter;
    private List<TabItemBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public List<TabItemBean> getList() {
        return list;
    }

    public void setList(List<TabItemBean> list) {
        this.list = list;
    }
}
