package com.example.ducvietho.iotapp.screen.floor;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.EquipmentDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogAlarm;
import com.example.ducvietho.iotapp.util.OnCLickItem;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.DatePickerPopUpWindow;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FloorFragment extends Fragment implements FloorContract.View, OnCLickItem {

    public static final String EXTRA_POS = "position";
    @BindView(R.id.rec_equip)
    RecyclerView mRecyclerView;
    @BindView(R.id.pro_load)
    ProgressBar mProgressBar;
    private View v;
    private int idFloor;
    private FloorContract.Presenter mPresenter;
    private EquipmentDataRepository mRepository;

    public static FloorFragment newInstance(int postion) {
        FloorFragment fragment = new FloorFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_POS, postion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_floor, container, false);
        ButterKnife.bind(this, v);
        idFloor = getArguments().getInt(EXTRA_POS, 0);
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance(lan)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.getAllEquipByFloorLAN(idFloor);
        return v;
    }

    @Override
    public void getAllEquipByFloorSuccess(List<Equipment> equipments) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        EquipmentAdapter adapter = new EquipmentAdapter(equipments, FloorFragment.this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getAllEquipByFloorFailureLAN() {
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesLan.getString(Constant.EXTRA_INTERNET,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance(internet)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.getAllEquipByFloorInternet(idFloor);
    }

    @Override
    public void getAllEquipByFloorFailureInternet() {
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesLan.getString(Constant.EXTRA_DOMAIN,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance(domain)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.getAllEquipByFloorDomain(idFloor);
    }

    @Override
    public void getAllEquipByFloorFailureDomain(String message) {
        Toast.makeText(v.getContext(),"Error :"+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOnEquipSuccess(Equipment equipment, Response response, ImageView imageView, TextView textView) {

        if (response.getStatus() == 200) {
            File file = new File(Environment.getExternalStorageDirectory().toString()+ "/iot/"+equipment
                    .getIconOn().replaceAll("/","") );
            Picasso.with(v.getContext()).load(file).into(imageView);
            //imageView.setImageResource(R.drawable.ic_ac);
            Toast.makeText(v.getContext(), "Đã bật thiết bị :" + equipment.getName(), Toast.LENGTH_LONG).show();
            equipment.setState(1);
        } else {
            Toast.makeText(v.getContext(), "Bật thiết bị " + equipment.getName() + " thất bại", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void turnOnquipFailureLAN(Equipment equipment, ImageView imageView, TextView textView) {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance
                (internet)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.turnOnEquipInternet(equipment, imageView, textView);
    }

    @Override
    public void turnOnquipFailureInternet(Equipment equipment, ImageView imageView, TextView textView) {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance
                (domain)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.turnOnEquipDomain(equipment, imageView, textView);
    }

    @Override
    public void turnOnquipFailureDomain(String message) {
        Toast.makeText(v.getContext(), "Error:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOffEquipSuccess(Equipment equipment, Response response, ImageView imageView, TextView textView) {

        if (response.getStatus() == 200) {
            File file = new File(Environment.getExternalStorageDirectory().toString()+ "/iot/"+equipment
                    .getIconOff().replaceAll("/","") );
            Picasso.with(v.getContext()).load(file).into(imageView);
            //imageView.setImageResource(R.drawable.ic_ac_off);
            equipment.setState(0);
            Toast.makeText(v.getContext(), "Đã tắt thiết bị :" + equipment.getName(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(v.getContext(), "Tắt thiết bị " + equipment.getName() + " thất bại", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void turnOffEquipFailureLAN(Equipment equipment, ImageView imageView, TextView textView) {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance
                (internet)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.turnOffEquipInternet(equipment, imageView, textView);
    }

    @Override
    public void turnOffEquipFailureInternet(Equipment equipment, ImageView imageView, TextView textView) {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN,null);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance
                (domain)));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.turnOffEquipDomain(equipment, imageView, textView);
    }

    @Override
    public void turnOffEquipFailureDomain(String message) {
        Toast.makeText(v.getContext(), "Error:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(Equipment equipment, ImageView imageView, TextView textView) {
        if (equipment.getState() == 0) {
            SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                    MODE_PRIVATE);
            String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
            mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance(lan)));
            mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
            mPresenter.turnOnEquipLAN(equipment, imageView, textView);
        } else {
            SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                    MODE_PRIVATE);
            String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
            mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance(lan)));
            mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
            mPresenter.turnOffEquipLAN(equipment, imageView, textView);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
