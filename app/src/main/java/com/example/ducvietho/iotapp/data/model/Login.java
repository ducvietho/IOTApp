package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class Login {
    @SerializedName("name_user")
    private String mName;
    @SerializedName("token_user")
    private String mToken;
    @SerializedName("max")
    private String mMac;
    public Login() {

    }

    public String getMac() {
        return mMac;
    }

    public void setMac(String mac) {
        mMac = mac;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
