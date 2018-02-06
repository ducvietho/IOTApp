package com.example.ducvietho.iotapp.data.resource.remote.api;

import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTApi;

/**
 * Created by ducvietho on 1/21/2018.
 */

public abstract class BaseRemoteDataResource {
    public IOTApi mIOTApi;

    public BaseRemoteDataResource(IOTApi IOTApi) {
        mIOTApi = IOTApi;
    }
}
