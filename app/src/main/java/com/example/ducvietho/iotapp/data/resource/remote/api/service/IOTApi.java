package com.example.ducvietho.iotapp.data.resource.remote.api.service;

import com.example.ducvietho.iotapp.data.model.EquipmentResponse;
import com.example.ducvietho.iotapp.data.model.FloorResponse;
import com.example.ducvietho.iotapp.data.model.GroupResponse;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.model.Response;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ducvietho on 1/21/2018.
 */

public interface IOTApi {
    @FormUrlEncoded
    @POST("login")
    Observable<LoginResponse> login(@Field("username") String username,@Field("pass")String pass);
    @POST("floors")
    Observable<FloorResponse> getAllFloor();
    @FormUrlEncoded
    @POST("floor/equipment")
    Observable<EquipmentResponse> getAllEquipmentByFloor(@Field("id_floor") int id);
    @FormUrlEncoded
    @POST("turn_on")
    Observable<Response> turnOnEquipment(@Field("id_equip")int idEquip,@Field("id_floor")int idFloor);
    @FormUrlEncoded
    @POST("turn_off")
    Observable<Response> turnOffEquipment(@Field("id_equip")int idEquip,@Field("id_floor")int idFloor);
    @POST("groups")
    Observable<GroupResponse> getAllGroup();
}
