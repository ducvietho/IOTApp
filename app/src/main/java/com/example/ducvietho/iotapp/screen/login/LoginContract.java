package com.example.ducvietho.iotapp.screen.login;

import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.data.model.LoginResponse;

import java.util.List;

/**
 * Created by ducvietho on 1/28/2018.
 */

public interface LoginContract {
    interface Presenter{
        void loginUser(String username,String pass);
        void downloadImage();
    }
    interface View{
        void loginSuccess(LoginResponse login);
        void loginFailure(String message);
        void downloadSuccess(List<Image> list);
    }
}
