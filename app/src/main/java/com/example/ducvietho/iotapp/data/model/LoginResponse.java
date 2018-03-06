package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 1/25/2018.
 */

public class LoginResponse {
    @SerializedName("data")
    private Login mLogin;
    @SerializedName("code")
    private int mStatus;

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public Login getLogin() {
        return mLogin;
    }

    public void setLogin(Login login) {
        mLogin = login;
    }
}
