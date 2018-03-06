package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.model.Response;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 2/1/2018.
 */

public class GroupDataRepository implements GroupDataResource {
    private GroupDataResource mGroupDataResource;

    public GroupDataRepository(GroupDataResource groupDataResource) {
        mGroupDataResource = groupDataResource;
    }

    @Override
    public Observable<List<Group>> getAllGroup() {
        return mGroupDataResource.getAllGroup();
    }

    @Override
    public Observable<Response> turnOnGroup(int idGroup) {
        return mGroupDataResource.turnOnGroup(idGroup);
    }

    @Override
    public Observable<Response> turnOffGroup(int idGroup) {
        return mGroupDataResource.turnOffGroup(idGroup);
    }
}
