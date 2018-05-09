package com.example.ducvietho.iotapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 3/4/2018.
 */

public class AlarmEquipOnReceiver extends BroadcastReceiver {
    private Socket mSocket;


    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPreferencesLan = context.getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = Constant.HTTP+sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
        SharedPreferences sharedPreferencesInternet = context.getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null);
        SharedPreferences sharedPreferencesDomain = context.getSharedPreferences(Constant.PREFS_DOMAIN, MODE_PRIVATE);
        String domain = Constant.HTTP+sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null);
        if(lan!=null){
            {
                try {
                    mSocket = IO.socket(lan);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            mSocket.connect();
            if (!mSocket.connected()) {
                {
                    try {
                        mSocket = IO.socket(internet);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
            mSocket.connect();
            if (!mSocket.connected()) {
                if(domain!=null){
                    {
                        try {
                            mSocket = IO.socket(domain);

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            mSocket.connect();
        }
        else {
            {
                try {
                    mSocket = IO.socket(internet);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            mSocket.connect();
            if (!mSocket.connected()) {
                if(domain!=null){
                    {
                        try {
                            mSocket = IO.socket(domain);

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            mSocket.connect();
        }
        Bundle extra = intent.getExtras();
        final String message = extra.getString(Constant.EXTRA_ID_EQUIP);
        Equipment equipment = new Gson().fromJson(message,Equipment.class);
        equipment.setState(1);

        mSocket.emit("request",new Gson().toJson(equipment));
        final int idFloor = extra.getInt(Constant.EXTRA_ID_FLOOR);
        Toast.makeText(context, "Turn On Equipment", Toast.LENGTH_LONG).show();
        String preSettingOn = Constant.PRE_STATE + String.valueOf(equipment.getId());
        SharedPreferences.Editor editorState = context.getSharedPreferences(preSettingOn, Context
                .MODE_PRIVATE).edit();
        editorState.putBoolean(Constant.EXTRA_STATE,false);
        editorState.apply();



    }

}
