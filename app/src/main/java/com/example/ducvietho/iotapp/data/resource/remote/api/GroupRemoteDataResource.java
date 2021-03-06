package com.example.ducvietho.iotapp.data.resource.remote.api;

import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.model.GroupResponse;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 2/1/2018.
 */

public class GroupRemoteDataResource extends BaseRemoteDataResource  {
    public GroupRemoteDataResource(IOTApi IOTApi) {
        super(IOTApi);
    }


    public Observable<List<Group>> getAllGroup() {
        return mIOTApi.getAllGroup().map(new Function<GroupResponse, List<Group>>() {
            @Override
            public List<Group> apply(GroupResponse groupResponse) throws Exception {
                return groupResponse.getList();
            }
        });
    }


    public Observable<Response> turnOnGroup(int idGroup) {
        return mIOTApi.turnOnGroup(idGroup);
    }


    public Observable<Response> turnOffGroup(int idGroup) {
        return mIOTApi.turnOnGroup(idGroup);
    }
}
