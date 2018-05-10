package com.example.ducvietho.iotapp.screen.floor;


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
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.OnCLickItem;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FloorFragment extends Fragment implements OnCLickItem {

    public static final String EXTRA_POS = "position";
    @BindView(R.id.rec_equip)
    RecyclerView mRecyclerView;
    @BindView(R.id.pro_load)
    ProgressBar mProgressBar;
    private View v;
    private int idFloor;
    private CompositeDisposable mDisposable;
    private EquipmentRemoteDataResource mRepository;
    private Socket mSocket;

    private List<Equipment> mList = new ArrayList<>();
    EquipmentAdapter adapter;

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
        SharedPreferences preferencesPortSocket = v.getContext().getSharedPreferences(Constant.PREFS_PORT_SOCKET,
                MODE_PRIVATE);
        String portSocket = preferencesPortSocket.getString(Constant.EXTRA_PORT_SOCKET,"");
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = Constant.HTTP+sharedPreferencesLan.getString(Constant.EXTRA_LAN, null)+":"+portSocket;
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)+":"+portSocket;

        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = Constant.HTTP+sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null)+":"+portSocket;
        if (lan != null) {
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
                if (domain != null) {
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
                try {
                    mSocket = IO.socket(internet);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            mSocket.connect();
            if (!mSocket.connected()) {
                if (domain != null) {
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
        Log.i("Connection status:", String.valueOf(mSocket.connected()));
        idFloor = getArguments().getInt(EXTRA_POS, 0);
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesLanInternet = v.getContext().getSharedPreferences(Constant.PREFS_LAN, MODE_PRIVATE);
        String lan1 = Constant.HTTP+sharedPreferencesLanInternet.getString(Constant.EXTRA_LAN, null)+":"+port;
        mRepository = new EquipmentRemoteDataResource(IOTServiceClient.getInstance(lan1));
        mDisposable = new CompositeDisposable();
        mDisposable.add(mRepository.getAllEquipmentByFloor(idFloor).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                getAllEquipByFloorFailureLan();
            }

            @Override
            public void onComplete() {

            }
        }));
        mSocket.on("response", onTurnEquip);
        return v;
    }

    private Emitter.Listener onTurnEquip = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String infor;

                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        infor = jsonObject.getString("object");
                        Equipment equip = new Gson().fromJson(infor, Equipment.class);
                        if (equip.getIdFloor() == idFloor) {
                            int position = -1;
                            int size = mList.size();
                            Log.i("size " + String.valueOf(idFloor) + ":", String.valueOf(size));
                            for (int i=0;i<mList.size();i++){
                                if(mList.get(i).getId()==equip.getId()){
                                    position =i;
                                }
                            }
                            if(position>-1){
                                Log.i("equip infor", new Gson().toJson(equip));
                                mList.set(position, equip);
                                adapter.notifyItemChanged(position,mList);
                            }

                        }
                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                        return;
                    }


                }
            });
        }
    };

    @Override
    public void onClick(final Equipment equipment, final ImageView imageView, final TextView textView) {

        try {
            attemptSend(equipment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void getAllEquipByFloorSuccess(List<Equipment> equipments) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        mList = equipments;
        adapter = new EquipmentAdapter(v.getContext(), mList, FloorFragment.this);
        mRecyclerView.setAdapter(adapter);
    }

    private void attemptSend(Equipment equipment) throws JSONException {
        Equipment equip = equipment;
        if (equip.getState() == 0) {
            equip.setState(1);
        } else {
            equip.setState(0);
        }
        Log.i("Socket :", new Gson().toJson(equip));
        mSocket.emit("request", new Gson().toJson(equipment));

    }

    public void getAllEquipByFloorFailureLan() {
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null)+":"+port;
        mRepository = new EquipmentRemoteDataResource(IOTServiceClient.getInstance(internet));
        mDisposable.add(mRepository.getAllEquipmentByFloor(idFloor).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                getAllEquipByFloorFailureInternet();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    public void getAllEquipByFloorFailureInternet() {
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN, MODE_PRIVATE);
        String domain = Constant.HTTP+sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, null)+":"+port;
        mRepository = new EquipmentRemoteDataResource(IOTServiceClient.getInstance(domain));
        mDisposable.add(mRepository.getAllEquipmentByFloor(idFloor).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {

                getAllEquipByFloorFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    public void getAllEquipByFloorFailure(String message) {
        mProgressBar.setVisibility(View.GONE);
        // Toast.makeText(v.getContext(), "Error :" + message, Toast.LENGTH_LONG).show();
    }

}
