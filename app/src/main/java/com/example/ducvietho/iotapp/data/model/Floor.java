package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class Floor {

    @SerializedName("id_floor")
    private int mIdFloor;
    @SerializedName("name_floor")
    private String mName;

    public Floor() {
    }

    public int getIdFloor() {
        return mIdFloor;
    }

    public void setIdFloor(int idFloor) {
        mIdFloor = idFloor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
