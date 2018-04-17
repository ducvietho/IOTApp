package com.example.ducvietho.iotapp.data.resource.remote.api.service;

/**
 * Created by ducvietho on 1/21/2018.
 */

public class IOTServiceClient extends ServiceClient {
    private static IOTApi mIOTAPi;
    public static IOTApi getInstance(String urlEndcode){
        if(mIOTAPi==null){
            mIOTAPi = ServiceClient.createService(urlEndcode,IOTApi.class);
        }
        return mIOTAPi;
    }
    public static void clear(){
        mIOTAPi = null;
    }
}
