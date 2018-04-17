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
import com.example.ducvietho.iotapp.data.resource.remote.api.GroupRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogSettingAlarm;
import com.example.ducvietho.iotapp.util.OnClickItemGroup;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.google.gson.Gson;

import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

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
    public GroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_group,container,false);
        ButterKnife.bind(GroupFragment.this,v);

        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN, MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN, null);
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null);
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN, MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null);
        if(lan!=null){
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
                    try {
                        mSocket = IO.socket(internet);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
            mSocket.connect();

            if (!mSocket.connected()) {
                if(domain!=null){
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
        else {
            {
                try {
                    mSocket = IO.socket(internet);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            mSocket.connect();
            if (!mSocket.connected()) {
                if(domain!=null){
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
        mDisposable = new CompositeDisposable();
        mRepository = (new GroupRemoteDataResource(IOTServiceClient.getInstance(lan)));
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
        mSocket.on("response",onTurnGroup);
        return v;
    }
    private Emitter.Listener onTurnGroup = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(v.getContext(),"Turn Success",Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    @Override
    public void onLongClick(Group object) {
        new DialogSettingAlarm(v.getContext()).showDialogAlarmSettingGroup(object);
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
        group.setType(1);
        if(group.getState()==0){
            group.setState(1);
        }else {
            group.setState(0);
        }
        String groupMessage = new Gson().toJson(group);
        mSocket.emit("request", groupMessage);
    }
    public void getAllGroupSuccess(List<Group> groups) {
        mProgressBar.setVisibility(View.GONE);
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        GroupAdapter adapter = new GroupAdapter(v.getContext(),groups, this, this);
        mRecyclerView.setAdapter(adapter);
    }


    public void getAllGroupFailureLan() {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null);
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
    public void getAllGroupFailureInternet() {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_INTERNET, null);
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
