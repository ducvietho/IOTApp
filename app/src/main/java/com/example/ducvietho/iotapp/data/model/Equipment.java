package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 1/25/2018.
 */

public class Equipment {
    @SerializedName("id_equip")
    private int mId;
    @SerializedName("id_floor")
    private int mIdFloor;
    @SerializedName("name_equip")
    private String mName;
    @SerializedName("state")
    private int mState;
    @SerializedName("icon_on")
    private String mIconOn;
    @SerializedName("icon_off")
    private String mIconOff;
    @SerializedName("iotdevice_id")
    private String mIOTDeviceId;
    @SerializedName("stt")
    private int mSTT;

    public Equipment() {
    }



    public String getIOTDeviceId() {
        return mIOTDeviceId;
    }

    public void setIOTDeviceId(String IOTDeviceId) {
        mIOTDeviceId = IOTDeviceId;
    }

    public int getSTT() {
        return mSTT;
    }

    public void setSTT(int STT) {
        mSTT = STT;
    }



    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public String getIconOn() {
        return mIconOn;
    }

    public void setIconOn(String iconOn) {
        mIconOn = iconOn;
    }

    public String getIconOff() {
        return mIconOff;
    }

    public void setIconOff(String iconOff) {
        mIconOff = iconOff;
    }
}
