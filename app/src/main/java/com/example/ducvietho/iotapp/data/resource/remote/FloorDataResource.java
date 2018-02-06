package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Floor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 1/22/2018.
 */

public interface FloorDataResource {
    Observable<List<Floor>> getAllFloor();
}
