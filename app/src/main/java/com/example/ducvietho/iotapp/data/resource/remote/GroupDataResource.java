package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Group;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 2/1/2018.
 */

public interface GroupDataResource {
    Observable<List<Group>> getAllGroup();
}
