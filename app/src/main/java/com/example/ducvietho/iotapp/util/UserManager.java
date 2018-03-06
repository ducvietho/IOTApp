package com.example.ducvietho.iotapp.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.screen.login.LoginActivity;
import com.example.ducvietho.iotapp.screen.main.MainActivity;

/**
 * Created by ducvietho on 1/30/2018.
 */

public class UserManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "UserSession";
    private static final String IS_USER_LOGIN = "isUserLogin";
    private static final String USER_NAME = "user";
    private static final String KEY_TOKEN = "toke";

    public UserManager(Context context) {
        this.mContext = context;
        this.sharedPreferences = mContext.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createUserLoginSession(Login login) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_TOKEN, login.getToken());
        editor.putString(USER_NAME, login.getName());
        editor.commit();
    }

    public void checkUserLogin() {
        if (sharedPreferences.getBoolean(IS_USER_LOGIN, false)) {
            mContext.startActivity(new MainActivity().getIntent(mContext));

        }

    }

    public Login getUserDetail() {
        Login login = new Login();
        String name = sharedPreferences.getString(USER_NAME, null);
        login.setName(name);
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        login.setToken(token);
        return login;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
