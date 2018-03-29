package com.example.ducvietho.iotapp.screen.floor;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.OnCLickItem;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN, MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN, null);
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, null);
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
        }


        idFloor = getArguments().getInt(EXTRA_POS, 0);
        mRepository = new EquipmentRemoteDataResource(IOTServiceClient.getInstance());
        mDisposable = new CompositeDisposable();
        mDisposable.add(mRepository.getAllEquipmentByFloor(idFloor).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
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
        mSocket.on("response",onTurnEquip);
        return v;
    }
    private Emitter.Listener onTurnEquip = new Emitter.Listener() {
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
    public void onClick(final Equipment equipment, final ImageView imageView, final TextView textView) {
        if (equipment.getState() == 0) {

            try {
                attemptSend(equipment.getId(), 2, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            try {
                attemptSend(equipment.getId(), 2, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        EquipmentAdapter adapter = new EquipmentAdapter(v.getContext(),mList, FloorFragment.this);
        mRecyclerView.setAdapter(adapter);
    }

    private void attemptSend(int id, int serial, int state) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("idFloor", id);
        object.put("serial", serial);
        object.put("state", state);
        String message = object.toString().substring(1, object.toString().length() - 1);
        mSocket.emit("request", message);
    }


    public void getAllEquipByFloorFailure(String message) {
        Toast.makeText(v.getContext(), "Error :" + message, Toast.LENGTH_LONG).show();
    }


    public void turnOnEquipSuccess(Equipment equipment, Response response, ImageView imageView, TextView textView) {

        if (response.getStatus() == 200) {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/iot/" + equipment.getIconOn().replaceAll("/", ""));
            Picasso.with(v.getContext()).load(file).into(imageView);
            //imageView.setImageResource(R.drawable.ic_ac);
            Toast.makeText(v.getContext(), "Đã bật thiết bị :" + equipment.getName(), Toast.LENGTH_LONG).show();
            equipment.setState(1);
        } else {
            Toast.makeText(v.getContext(), "Bật thiết bị " + equipment.getName() + " thất bại", Toast.LENGTH_LONG).show();
        }

    }

    public void turnOffEquipSuccess(Equipment equipment, Response response, ImageView imageView, TextView textView) {

        if (response.getStatus() == 200) {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/iot/" + equipment.getIconOff().replaceAll("/", ""));
            Picasso.with(v.getContext()).load(file).into(imageView);
            //imageView.setImageResource(R.drawable.ic_ac_off);
            equipment.setState(0);
            Toast.makeText(v.getContext(), "Đã tắt thiết bị :" + equipment.getName(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(v.getContext(), "Tắt thiết bị " + equipment.getName() + " thất bại", Toast.LENGTH_LONG).show();
        }
    }


}
