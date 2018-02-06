package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 1/30/2018.
 */

public class Response {
    @SerializedName("status_code")
    private int mStatus;

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }
}
