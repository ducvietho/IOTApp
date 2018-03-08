package com.example.ducvietho.iotapp.data.resource.remote.api.service;

import com.example.ducvietho.iotapp.data.model.EquipmentResponse;
import com.example.ducvietho.iotapp.data.model.FloorResponse;
import com.example.ducvietho.iotapp.data.model.GroupResponse;
import com.example.ducvietho.iotapp.data.model.ImageResponse;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.model.Response;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ducvietho on 1/21/2018.
 */

public interface IOTApi {
    @FormUrlEncoded
    @POST("/volumn/api/login")
    Observable<LoginResponse> login(@Field("username") String username,@Field("pass")String pass);
    @POST("/volumn/api/floors")
    Observable<FloorResponse> getAllFloor();
    @FormUrlEncoded
    @POST("/volumn/api/floor/equipment")
    Observable<EquipmentResponse> getAllEquipmentByFloor(@Field("id_floor") int id);
    @FormUrlEncoded
    @POST("/volumn/api/turn_on")
    Observable<Response> turnOnEquipment(@Field("id_equip")int idEquip,@Field("id_floor")int idFloor);
    @FormUrlEncoded
    @POST("/volumn/api/turn_off")
    Observable<Response> turnOffEquipment(@Field("id_equip")int idEquip,@Field("id_floor")int idFloor);
    @POST("/volumn/api/groups")
    Observable<GroupResponse> getAllGroup();
    @FormUrlEncoded
    @POST("/volumn/api/turn_on/group")
    Observable<Response> turnOnGroup(@Field("id_group") int idGroup);
    @FormUrlEncoded
    @POST("/volumn/api/turn_off/group")
    Observable<Response> turnOffGroup(@Field("id_group") int idGroup);
    @FormUrlEncoded
    @POST("/volumn/api/alarm/equip")
    Observable<Response> turnOnAlarm(@Field("id_equip") int idEquip,@Field("id_floor") int idFloor,@Field("time")
                                     String time,@Field("alarm_state") int alarmState);
    @POST("/volumn/api/all-images")
    Observable<ImageResponse> getAllImage();
}
