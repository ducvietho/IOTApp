package com.example.ducvietho.iotapp.data.resource.remote.api.service;

import android.app.Service;

import com.example.ducvietho.iotapp.util.Constant;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class IOTServiceClient extends ServiceClient {
    private static IOTApi mIOTAPi;
    public static IOTApi getInstance(){
        if(mIOTAPi==null){
            mIOTAPi = ServiceClient.createService(Constant.IOT_API,IOTApi.class);
        }
        return mIOTAPi;
    }
}
