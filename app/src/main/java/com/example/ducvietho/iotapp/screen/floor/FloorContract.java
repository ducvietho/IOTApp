package com.example.ducvietho.iotapp.screen.floor;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;

import java.util.List;

/**
 * Created by ducvietho on 2/3/2018.
 */

public interface FloorContract {
    interface Presenter{
        void getAllEquipByFloorLAN(int id);
        void getAllEquipByFloorInternet(int id);
        void getAllEquipByFloorDomain(int id);
        void turnOnEquipLAN(Equipment equipment, ImageView imageView, TextView textView);
        void turnOnEquipInternet(Equipment equipment, ImageView imageView, TextView textView);
        void turnOnEquipDomain(Equipment equipment, ImageView imageView, TextView textView);
        void turnOffEquipLAN(Equipment equipment, ImageView imageView,TextView textView);
        void turnOffEquipInternet(Equipment equipment, ImageView imageView,TextView textView);
        void turnOffEquipDomain(Equipment equipment, ImageView imageView,TextView textView);
        void clear();
    }
    interface View{
        void getAllEquipByFloorSuccess(List<Equipment> equipments);
        void getAllEquipByFloorFailureLAN();
        void getAllEquipByFloorFailureInternet();
        void getAllEquipByFloorFailureDomain(String message);
        void turnOnEquipSuccess(Equipment equipment,Response response,ImageView imageView,TextView textView);
        void turnOnquipFailureLAN(Equipment equipment, ImageView imageView, TextView textView);
        void turnOnquipFailureInternet(Equipment equipment, ImageView imageView, TextView textView);
        void turnOnquipFailureDomain(String message);
        void turnOffEquipSuccess(Equipment equipment,Response response,ImageView imageView,TextView textView);
        void turnOffEquipFailureLAN(Equipment equipment, ImageView imageView, TextView textView);
        void turnOffEquipFailureInternet(Equipment equipment, ImageView imageView, TextView textView);
        void turnOffEquipFailureDomain(String message);
    }
}
