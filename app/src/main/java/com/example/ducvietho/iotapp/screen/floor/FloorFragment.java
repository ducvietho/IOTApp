package com.example.ducvietho.iotapp.screen.floor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.resource.remote.EquipmentDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FloorFragment extends Fragment implements FloorContract.View {

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
        idFloor = getArguments().getInt(EXTRA_POS);
        mRepository = new EquipmentDataRepository(new EquipmentRemoteDataResource(IOTServiceClient.getInstance()));
        mPresenter = new FloorPresenter(mRepository,FloorFragment.this);
        mPresenter.getAllEquipByFloor(idFloor);
        return v;
    }

    @Override
    public void getAllEquipByFloorSuccess(List<Equipment> equipments) {
        mProgressBar.setVisibility(View.GONE);
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        EquipmentAdapter adapter = new EquipmentAdapter(equipments);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getAllEquipByFloorFailure(String message) {
        Toast.makeText(v.getContext(),"Error:"+message,Toast.LENGTH_LONG).show();
    }
}
