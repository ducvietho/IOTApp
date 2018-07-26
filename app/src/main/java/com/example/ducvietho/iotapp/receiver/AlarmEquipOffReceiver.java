package com.example.ducvietho.iotapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.util.Constant;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class AlarmEquipOffReceiver extends BroadcastReceiver {
    private Socket mSocket;
    @Override
    public void onReceive(final Context context, Intent intent) {

        SharedPreferences preferencesPortSocket = context.getSharedPreferences(Constant.PREFS_PORT_SOCKET,
                MODE_PRIVATE);
        String portSocket = preferencesPortSocket.getString(Constant.EXTRA_PORT_SOCKET,"");
        SharedPreferences sharedPreferencesLan = context.getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = Constant.HTTP+sharedPreferencesLan.getString(Constant.EXTRA_LAN, null)+":"+portSocket;
        lan = lan.replaceAll(" ","");
        SharedPreferences sharedPreferencesInternet = context.getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)+":"+portSocket;
        internet = internet.replaceAll(" ","");
        SharedPreferences sharedPreferencesDomain = context.getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = Constant.HTTP+sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null)+":"+portSocket;
        domain = domain.replaceAll(" ","");
        if (sharedPreferencesLan.getString(Constant.EXTRA_LAN, null) != null) {
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
                    if(sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)!=null){
                        try {
                            mSocket = IO.socket(internet);

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            mSocket.connect();
            if (!mSocket.connected()) {
                if (sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null) != null) {
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
        } else {
            {
                if(sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)!=null){
                    try {
                        mSocket = IO.socket(internet);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
            mSocket.connect();
            if (!mSocket.connected()) {
                if (sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null) != null) {
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
        //Toast.makeText(context,"Connect socket:"+String.valueOf(mSocket.connected()),Toast.LENGTH_LONG).show();
        Bundle extra = intent.getExtras();
        final String message = extra.getString(Constant.EXTRA_ID_EQUIP);
        Equipment equipment = new Gson().fromJson(message,Equipment.class);
        equipment.setState(0);

        mSocket.emit("request",new Gson().toJson(equipment));
        final int idFloor = extra.getInt(Constant.EXTRA_ID_FLOOR);
        String preSettingOn = Constant.PRE_STATE + String.valueOf(equipment.getId());
        SharedPreferences.Editor editorStateOn = context.getSharedPreferences(preSettingOn, Context
                .MODE_PRIVATE).edit();
        editorStateOn.putBoolean(Constant.EXTRA_STATE,false);
        editorStateOn.apply();
       // Toast.makeText(context, "Turn OFF Equipment", Toast.LENGTH_LONG).show();
        String preAlarmEqui = Constant.PRES_ALARM_EQUI + String.valueOf(equipment.getId());
        SharedPreferences.Editor editor = context.getSharedPreferences(preAlarmEqui,MODE_PRIVATE).edit();
        editor.putBoolean(Constant.EXTRA_ALARM_OFF_EQUI,true);

    }
}
