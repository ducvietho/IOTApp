package com.example.ducvietho.iotapp.screen.floor;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.EquipmentDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.DialogAlarm;
import com.example.ducvietho.iotapp.util.OnCLickItem;
import com.example.ducvietho.iotapp.util.OnLongClickItem;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.DatePickerPopUpWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class FloorFragment extends Fragment implements FloorContract.View, OnCLickItem<Equipment>, OnLongClickItem<Equipment> {

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
        idFloor = getArguments().getInt(EXTRA_POS,0);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance()));
        mPresenter = new FloorPresenter(mRepository, FloorFragment.this);
        mPresenter.getAllEquipByFloor(idFloor);
        return v;
    }

    @Override
    public void getAllEquipByFloorSuccess(List<Equipment> equipments) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        EquipmentAdapter adapter = new EquipmentAdapter(equipments, FloorFragment.this, FloorFragment.this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getAllEquipByFloorFailure(String message) {
        Toast.makeText(v.getContext(), "Error:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOnEquipSuccess(Response response) {
        Toast.makeText(v.getContext(), "Đã bật thiết bị", Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOnquipFailure(String message) {
        Toast.makeText(v.getContext(), "Error:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOffEquipSuccess(Response response) {
        Toast.makeText(v.getContext(), "Đã tắt thiết bị :", Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOffEquipFailure(String message) {
        Toast.makeText(v.getContext(), "Error:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(Equipment equipment) {
        if (equipment.getState() == 0) {
            mPresenter.turnOnEquip(equipment.getId(), equipment.getIdFloor());
        } else {
            mPresenter.turnOffEquip(equipment.getId(), equipment.getIdFloor());
        }
    }

    @Override
    public void onLongClick(Equipment equipment) {
        new DialogAlarm(v.getContext()).showDialodAlarmEquiment(equipment);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
