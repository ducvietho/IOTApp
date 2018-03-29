package com.example.ducvietho.iotapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;

import java.net.URISyntaxException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.IO;
import io.socket.client.Socket;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 3/4/2018.
 */

public class AlarmEquipOnReceiver extends BroadcastReceiver {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPreferencesLan = context.getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
        Bundle extra = intent.getExtras();
        final int idEquip = extra.getInt(Constant.EXTRA_ID_EQUIP);
        final int idFloor = extra.getInt(Constant.EXTRA_ID_FLOOR);
        Toast.makeText(context, "Turn On Equipment", Toast.LENGTH_LONG).show();
        String preSettingOn = Constant.PRE_STATE + String.valueOf(idEquip);
        SharedPreferences.Editor editorState = context.getSharedPreferences(preSettingOn, Context
                .MODE_PRIVATE).edit();
        editorState.putBoolean(Constant.EXTRA_STATE,false);
        editorState.apply();



    }

}
