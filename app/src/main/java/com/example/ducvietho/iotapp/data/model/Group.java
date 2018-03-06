package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 1/30/2018.
 */

public class Group {
    @SerializedName("id_group")
    private int mId;
    @SerializedName("name_group")
    private String mName;
    @SerializedName("state")
    private int mState;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
