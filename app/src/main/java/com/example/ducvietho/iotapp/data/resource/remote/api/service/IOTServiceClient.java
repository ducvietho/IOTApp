package com.example.ducvietho.iotapp.data.resource.remote.api.service;

import android.app.Service;

import com.example.ducvietho.iotapp.util.Constant;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class IOTServiceClient extends ServiceClient {
    private static IOTApi mIOTAPi;
    public static IOTApi getInstance(String server){
        if(mIOTAPi==null){
            mIOTAPi = ServiceClient.createService(server,IOTApi.class);
        }
        return mIOTAPi;
    }
}
