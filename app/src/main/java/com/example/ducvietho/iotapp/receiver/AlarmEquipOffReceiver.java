package com.example.ducvietho.iotapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.EquipmentDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class AlarmEquipOffReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPreferencesLan = context.getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
        final EquipmentDataRepository mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource
                (IOTServiceClient.getInstance(lan)));
        final CompositeDisposable mDisposable = new CompositeDisposable();
        Bundle extra = intent.getExtras();
        final int idEquip = extra.getInt(Constant.EXTRA_ID_EQUIP);
        final int idFloor = extra.getInt(Constant.EXTRA_ID_FLOOR);
        Toast.makeText(context, "Turn OFF", Toast.LENGTH_LONG).show();

        mDisposable.add(mRepository.turnOffEquiment(idEquip, idFloor).subscribeOn(Schedulers.newThread()).subscribeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                Toast.makeText(context, "Turn Successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                SharedPreferences sharedPreferencesInternet = context.getSharedPreferences(Constant.PREFS_INTERNET,
                        MODE_PRIVATE);
                String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
                EquipmentDataRepository mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource
                        (IOTServiceClient.getInstance(internet)));
                mDisposable.add(mRepository.turnOffEquiment(idEquip, idFloor).subscribeOn(Schedulers.newThread()).subscribeOn
                        (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response value) {
                        Toast.makeText(context, "Turn Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFS_DOMAIN,
                                MODE_PRIVATE);
                        String domain = sharedPreferences.getString(Constant.EXTRA_DOMAIN,null);
                        EquipmentDataRepository mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource
                                (IOTServiceClient.getInstance(domain)));
                        mDisposable.add(mRepository.turnOffEquiment(idEquip, idFloor).subscribeOn(Schedulers.newThread()).subscribeOn
                                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
                            @Override
                            public void onNext(Response value) {
                                Toast.makeText(context, "Turn Successful", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
            }

            @Override
            public void onComplete() {

            }
        }));


    }
}
