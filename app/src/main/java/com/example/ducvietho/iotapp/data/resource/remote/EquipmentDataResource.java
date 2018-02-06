package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Equipment;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 1/25/2018.
 */

public interface EquipmentDataResource {
    Observable<List<Equipment>> getAllEquipmentByFloor(int idFloor);
}
