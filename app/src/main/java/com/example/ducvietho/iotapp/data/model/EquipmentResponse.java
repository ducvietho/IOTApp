package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 1/25/2018.
 */

public class EquipmentResponse {
    @SerializedName("data")
    private List<Equipment> mList;

    public List<Equipment> getList() {
        return mList;
    }

    public void setList(List<Equipment> list) {
        mList = list;
    }
}
