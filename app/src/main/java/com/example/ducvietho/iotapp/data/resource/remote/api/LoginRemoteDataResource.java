package com.example.ducvietho.iotapp.data.resource.remote.api;

import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.data.model.EquipmentResponse;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.resource.remote.LoginDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class LoginRemoteDataResource extends BaseRemoteDataResource implements LoginDataResource {
    public LoginRemoteDataResource(IOTApi IOTApi) {
        super(IOTApi);
    }


    @Override
    public Observable<LoginResponse> login(String username, String pass) {
        return mIOTApi.login(username,pass);
    }
}
