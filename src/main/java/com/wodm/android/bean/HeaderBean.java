package com.wodm.android.bean;

import java.util.ArrayList;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/8
 */
public class HeaderBean {
    private ColumnBean columnBean;
    private ArrayList<MallGuaJianBean> GuaJianList;

    public HeaderBean(ArrayList<MallGuaJianBean> guaJianList, ColumnBean columnBean) {
        GuaJianList = guaJianList;
        this.columnBean = columnBean;
    }

    public ColumnBean getColumnBean() {
        return columnBean;
    }

    public void setColumnBean(ColumnBean columnBean) {
        this.columnBean = columnBean;
    }

    public ArrayList<MallGuaJianBean> getGuaJianList() {
        return GuaJianList;
    }

    public void setGuaJianList(ArrayList<MallGuaJianBean> guaJianList) {
        GuaJianList = guaJianList;
    }
}
