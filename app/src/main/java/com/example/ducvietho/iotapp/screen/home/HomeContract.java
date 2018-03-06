package com.example.ducvietho.iotapp.screen.home;

import com.example.ducvietho.iotapp.data.model.Floor;

import java.util.List;

/**
 * Created by ducvietho on 2/1/2018.
 */

public interface HomeContract {
    interface Presenter{
        void getAllFloorLAN();
        void getAllFloorInternet();
        void getAllFloorDomain();
    }
    interface View{
        void getAllFloorSuccess(List<Floor> floors);
        void getAllFloorFailureLAN();
        void getAllFloorFailureInternet();
        void getAllFloorFailureDomain(String message);
    }
}
