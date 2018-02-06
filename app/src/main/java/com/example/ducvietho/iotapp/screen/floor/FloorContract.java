package com.example.ducvietho.iotapp.screen.floor;

import com.example.ducvietho.iotapp.data.model.Equipment;

import java.util.List;

/**
 * Created by ducvietho on 2/3/2018.
 */

public interface FloorContract {
    interface Presenter{
        void getAllEquipByFloor(int id);
    }
    interface View{
        void getAllEquipByFloorSuccess(List<Equipment> equipments);
        void getAllEquipByFloorFailure(String message);
    }
}
