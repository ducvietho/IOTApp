package com.example.ducvietho.iotapp.screen.login;

import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.data.model.LoginResponse;

import java.util.List;

/**
 * Created by ducvietho on 1/28/2018.
 */

public interface LoginContract {
    interface Presenter{
        void loginUserLan(String username,String pass);
        void loginUserInternet(String username,String pass);
        void loginUserDomain(String username,String pass);
        void downloadImageLan();
        void downloadImageInternet();
    }
    interface View{
        void loginSuccess(LoginResponse login);
        void loginFailureLan(String username,String pass);
        void loginFailureInternet(String username,String pass);
        void loginFailure(String message);
        void downloadSuccess(List<Image> list);
        void downloadFailLan();
    }
}
