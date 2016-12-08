package com.wodm.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class ColumnBean implements Parcelable {


        private int id;
        private String name;

    protected ColumnBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<ColumnBean> CREATOR = new Creator<ColumnBean>() {
        @Override
        public ColumnBean createFromParcel(Parcel in) {
            return new ColumnBean(in);
        }

        @Override
        public ColumnBean[] newArray(int size) {
            return new ColumnBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
