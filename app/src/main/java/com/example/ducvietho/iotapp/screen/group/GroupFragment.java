package com.example.ducvietho.iotapp.screen.group;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.GroupDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.GroupRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogSettingAlarm;
import com.example.ducvietho.iotapp.util.OnClickItemGroup;
import com.example.ducvietho.iotapp.util.OnLongClickItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.IO;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements GroupContract.View,OnLongClickItem<Group>,OnClickItemGroup{
    @BindView(R.id.pro_load)
    ProgressBar mProgressBar;
    @BindView(R.id.rec_group)
    RecyclerView mRecyclerView;
    GroupContract.Presenter presenter;
    GroupDataRepository groupDataRepository;
    private  View v;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_group,container,false);
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
        ButterKnife.bind(this,v);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(lan)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.getAllGroupLAN();
        return v;
    }

    @Override
    public void getAllGroupSuccess(List<Group> groups) {
        mProgressBar.setVisibility(View.GONE);
        GridLayoutManager manager = new GridLayoutManager(v.getContext(),3);
        mRecyclerView.setLayoutManager(manager);
        GroupAdapter adapter = new GroupAdapter(groups,this,this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getAllGroupFailureLAN() {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(internet)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.getAllGroupInternet();
    }

    @Override
    public void getAllGroupFailureInternet() {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN,null);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(domain)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.getAllGroupDomain();
    }

    @Override
    public void getAllGroupFailureDoamin(String message) {
       Toast.makeText(v.getContext(),"Error"+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOnGroupSuccess(Group group, Response response, ImageView imageView) {
        if(response.getStatus()==200){
            imageView.setImageResource(R.drawable.ic_ac);
            group.setState(1);
            Toast.makeText(v.getContext(),"Bật "+group.getName()+" thành công",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(v.getContext(),"Bật "+group.getName()+" thất bại",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void turnOnGroupFailureLAN(Group group, ImageView imageView) {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(internet)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.turnOnGroupInternet(group,imageView);
    }

    @Override
    public void turnOnGroupFailureInternet(Group group, ImageView imageView) {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN,null);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(domain)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.turnOnGroupDomain(group,imageView);
    }

    @Override
    public void turnOnGroupFailure(String message) {
        Toast.makeText(v.getContext(),"Error :"+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void turnOffGroupSuccess(Group group, Response response, ImageView imageView) {
        if(response.getStatus()==200){
            group.setState(0);
            imageView.setImageResource(R.drawable.ic_ac_off);
            Toast.makeText(v.getContext(),"Tắt "+group.getName()+" thành công",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(v.getContext(),"Tắt "+group.getName()+" thất bại",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void turnOffGroupFailureLAN(Group group, ImageView imageView) {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(internet)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.turnOffGroupInternet(group,imageView);
    }

    @Override
    public void turnOffGroupFailureInternet(Group group, ImageView imageView) {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN,null);
        groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                (IOTServiceClient.getInstance(domain)));
        presenter = new GroupPresenter(groupDataRepository,this);
        presenter.turnOffGroupDomain(group,imageView);
    }

    @Override
    public void turnOffGroupFailure(String message) {
        Toast.makeText(v.getContext(),"Error :"+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLongClick(Group object) {
        new DialogSettingAlarm(v.getContext()).showDialogAlarmSettingGroup(object);
    }

    @Override
    public void onClick(Group group, ImageView imageView, TextView textView) {
        if(group.getState()==0){
            SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                    MODE_PRIVATE);
            String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
            groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                    (IOTServiceClient.getInstance(lan)));
            presenter = new GroupPresenter(groupDataRepository,this);
            presenter.turnOnGroupLAN(group,imageView);

        }
        else {
            SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                    MODE_PRIVATE);
            String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
            groupDataRepository = new GroupDataRepository(new GroupRemoteDataResource
                    (IOTServiceClient.getInstance(lan)));
            presenter = new GroupPresenter(groupDataRepository,this);

            presenter.turnOffGroupLan(group,imageView);
        }

    }
}
