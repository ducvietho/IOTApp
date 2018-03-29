package com.example.ducvietho.iotapp.data.resource.remote.api;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.EquipmentResponse;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 1/25/2018.
 */

public class EquipmentRemoteDataResource extends BaseRemoteDataResource  {
    public EquipmentRemoteDataResource(IOTApi IOTApi) {
        super(IOTApi);
    }



    public Observable<List<Equipment>> getAllEquipmentByFloor(int idFloor) {
        return mIOTApi.getAllEquipmentByFloor(idFloor).map(new Function<EquipmentResponse, List<Equipment>>() {
            @Override
            public List<Equipment> apply(EquipmentResponse equipmentResponse) throws Exception {
                return equipmentResponse.getList();
            }
        });
    }

    public Observable<Response> turnOnEquiment(int idEquip, int idFloor) {
        return mIOTApi.turnOnEquipment(idEquip,idFloor);
    }


    public Observable<Response> turnOffEquiment(int idEquip, int idFloor) {
        return mIOTApi.turnOffEquipment(idEquip,idFloor);
    }


    public Observable<Response> turnAlarmEquip(int idEquip, int idFloor, String time, int alarmState) {
        return mIOTApi.turnOnAlarm(idEquip,idFloor,time,alarmState);
    }
}
