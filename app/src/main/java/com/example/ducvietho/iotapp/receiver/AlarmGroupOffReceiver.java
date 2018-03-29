package com.example.ducvietho.iotapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.GroupRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.example.ducvietho.iotapp.util.Constant.PRE_STATE_GROUP;

/**
 * Created by ducvietho on 3/9/2018.
 */

public class AlarmGroupOffReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPreferencesLan = context.getSharedPreferences(Constant.PREFS_LAN, MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN, null);
        Bundle extra = intent.getExtras();
        final int idGroup = extra.getInt(Constant.EXTRA_ID_GROUP);
        String preSetting = PRE_STATE_GROUP + String.valueOf(idGroup);
        final SharedPreferences sharedPreferences = context.getSharedPreferences(preSetting, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(context, "Turn Off Group", Toast.LENGTH_LONG).show();

    }
}
