package com.example.ducvietho.iotapp.data.resource.remote.api;

import com.example.ducvietho.iotapp.data.model.Floor;
import com.example.ducvietho.iotapp.data.model.FloorResponse;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.FloorDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 1/22/2018.
 */

public class FloorRemoteDataResource extends BaseRemoteDataResource implements FloorDataResource {
    public FloorRemoteDataResource(IOTApi IOTApi) {
        super(IOTApi);
    }


    @Override
    public Observable<List<Floor>> getAllFloor() {
        return mIOTApi.getAllFloor().map(new Function<FloorResponse, List<Floor>>() {
            @Override
            public List<Floor> apply(FloorResponse floorResponse) throws Exception {
                return floorResponse.getList();
            }
        });
    }


}
