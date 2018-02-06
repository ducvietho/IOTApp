package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 1/25/2018.
 */

public class FloorResponse {
    @SerializedName("data")
    private List<Floor> mList;

    public List<Floor> getList() {
        return mList;
    }

    public void setList(List<Floor> list) {
        mList = list;
    }
}
