package com.example.ducvietho.iotapp.screen.group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.resource.remote.GroupDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.GroupRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements GroupContract.View {
    @BindView(R.id.rec_group)
    RecyclerView mRecyclerView;
    private View v;
    private GroupDataRepository mRepository;
    private GroupContract.Presenter presenter;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, v);
        mRepository = new GroupDataRepository(new GroupRemoteDataResource(IOTServiceClient.getInstance()));
        presenter = new GroupPresenter(mRepository,GroupFragment.this);
        presenter.getAllGroup();
        return v;
    }

    @Override
    public void getAllGroupSuccess(List<Group> groups) {
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 1);
        mRecyclerView.setLayoutManager(manager);
        GroupAdapter adapter = new GroupAdapter(groups);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getAllGroupFailure(String message) {
        Toast.makeText(v.getContext(),"Error:"+message,Toast.LENGTH_LONG).show();
    }
}
