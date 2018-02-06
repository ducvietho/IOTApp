package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Floor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 1/22/2018.
 */

public class FloorDataRepository implements FloorDataResource {
    private FloorDataResource mDataResource;

    public FloorDataRepository(FloorDataResource dataResource) {
        mDataResource = dataResource;
    }


    @Override
    public Observable<List<Floor>> getAllFloor() {
        return mDataResource.getAllFloor();
    }
}
