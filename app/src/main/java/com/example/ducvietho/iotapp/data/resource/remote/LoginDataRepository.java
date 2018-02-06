package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.data.model.LoginResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class LoginDataRepository implements LoginDataResource {
    private LoginDataResource mResource;

    public LoginDataRepository(LoginDataResource resource) {
        mResource = resource;
    }


    @Override
    public Observable<LoginResponse> login(String username, String pass) {
        return mResource.login(username,pass);
    }
}
