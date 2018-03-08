package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 3/8/2018.
 */

public class Image {
    @SerializedName("icon_off")
    private String mIconOff;
    @SerializedName("icon_on")
    private String mIconOn;

    public String getIconOff() {
        return mIconOff;
    }

    public void setIconOff(String iconOff) {
        mIconOff = iconOff;
    }

    public String getIconOn() {
        return mIconOn;
    }

    public void setIconOn(String iconOn) {
        mIconOn = iconOn;
    }
}
