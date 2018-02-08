package com.example.ducvietho.iotapp.screen.floor;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;

import java.util.List;

/**
 * Created by ducvietho on 2/3/2018.
 */

public interface FloorContract {
    interface Presenter{
        void getAllEquipByFloor(int id);
        void turnOnEquip(int idEquip,int idFloor);
        void turnOffEquip(int idEquip,int idFloor);
        void clear();
    }
    interface View{
        void getAllEquipByFloorSuccess(List<Equipment> equipments);
        void getAllEquipByFloorFailure(String message);
        void turnOnEquipSuccess(Response response);
        void turnOnquipFailure(String message);
        void turnOffEquipSuccess(Response response);
        void turnOffEquipFailure(String message);
    }
}
