package com.example.ducvietho.iotapp.screen.group;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.GroupRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogSettingAlarm;
import com.example.ducvietho.iotapp.util.OnClickItemGroup;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


import static android.content.Context.MODE_PRIVATE;

public class GroupFragment extends Fragment implements OnLongClickItem<Group>, OnClickItemGroup {
    @BindView(R.id.pro_load)
    ProgressBar mProgressBar;
    @BindView(R.id.rec_group)
    RecyclerView mRecyclerView;
    View v;
    CompositeDisposable mDisposable;
    GroupRemoteDataResource mRepository;
    Socket mSocket;
    List<Group> mGroups = new ArrayList<>();
    GroupAdapter adapter;
    public GroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_group,container,false);
        ButterKnife.bind(GroupFragment.this,v);

        SharedPreferences preferencesPortSocket = v.getContext().getSharedPreferences(Constant.PREFS_PORT_SOCKET,
                MODE_PRIVATE);
        String portSocket = preferencesPortSocket.getString(Constant.EXTRA_PORT_SOCKET,"");
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = Constant.HTTP+sharedPreferencesLan.getString(Constant.EXTRA_LAN, null)+":"+portSocket;
        lan = lan.replaceAll(" ","");
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)+":"+portSocket;
        internet = internet.replaceAll(" ","");
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
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
        Log.i("Connection status:",String.valueOf(mSocket.connected()));
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesLanInternet = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan1 = Constant.HTTP+sharedPreferencesLanInternet.getString(Constant.EXTRA_LAN, null)+":"+port;
        lan1 = lan1.replaceAll(" ","");
        mDisposable = new CompositeDisposable();
        if(sharedPreferencesLanInternet.getString(Constant.EXTRA_LAN,"").equals("")){
            getAllGroupFailureLan();
        }else {

            mRepository = (new GroupRemoteDataResource(IOTServiceClient.getInstance(lan1)));
            mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
                @Override
                public void onNext(List<Group> value) {
                    getAllGroupSuccess(value);
                }

                @Override
                public void onError(Throwable e) {
                    getAllGroupFailureLan();
                }

                @Override
                public void onComplete() {

                }
            }));
        }

        mSocket.on("response_group",onTurnGroup);
        return v;
    }
    private Emitter.Listener onTurnGroup = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            GroupFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Group group = new Gson().fromJson(args[0].toString(),Group.class);
                    int position = mGroups.indexOf(group);
                    mGroups.set(position,group);
                    adapter.notifyItemChanged(position);

                }
            });
        }
    };
    @Override
    public void onLongClick(Group object) {
        new DialogSettingAlarm(v.getContext()).showDialogAlarmSettingGroup(object,mGroups,adapter);
    }

    @Override
    public void onClick(final Group group, final ImageView imageView, TextView textView) {
        try {
            attemptSend(group);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void attemptSend(Group group) throws JSONException {
        if(group.getState()==0){
            group.setState(1);
        }else {
            group.setState(0);
        }
        String groupMessage = new Gson().toJson(group);
        Log.i("Socket :" ,new Gson().toJson(group));
        mSocket.emit("request", groupMessage);


    }
    public void getAllGroupSuccess(List<Group> groups) {
        mProgressBar.setVisibility(View.GONE);
        mGroups = groups;
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
         adapter = new GroupAdapter(v.getContext(),mGroups, this, this);
        mRecyclerView.setAdapter(adapter);
    }


    public void getAllGroupFailureLan() {
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)+":"+port;
        internet = internet.replaceAll(" ","");
        if(sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,"").equals("")){
            getAllGroupFailureInternet();
        }else {
            mRepository = (new GroupRemoteDataResource(IOTServiceClient.getInstance(internet)));
            mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
                @Override
                public void onNext(List<Group> value) {
                    getAllGroupSuccess(value);
                }

                @Override
                public void onError(Throwable e) {
                    getAllGroupFailureInternet();
                }

                @Override
                public void onComplete() {

                }
            }));
        }

    }
    public void getAllGroupFailureInternet() {
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = Constant.HTTP+sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null)+":"+port;
        domain = domain.replaceAll(" ","");
        mRepository = new GroupRemoteDataResource(IOTServiceClient.getInstance(domain));
        mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
            @Override
            public void onNext(List<Group> value) {
                getAllGroupSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                getAllGroupFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }


    public void getAllGroupFailure(String message) {
        Toast.makeText(v.getContext(), "Error" + message, Toast.LENGTH_LONG).show();
    }

}
