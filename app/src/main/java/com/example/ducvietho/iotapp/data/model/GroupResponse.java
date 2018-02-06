package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 1/30/2018.
 */

public class GroupResponse {
    @SerializedName("data")
    private List<Group> mList;

    public List<Group> getList() {
        return mList;
    }

    public void setList(List<Group> list) {
        mList = list;
    }
}
