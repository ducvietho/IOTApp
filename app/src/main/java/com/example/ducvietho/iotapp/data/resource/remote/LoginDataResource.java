package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.data.model.LoginResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 1/21/2018.
 */

public interface LoginDataResource {
    Observable<LoginResponse> login(String username, String pass);
}
