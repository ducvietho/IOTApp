package com.example.ducvietho.iotapp.screen.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Floor;
import com.example.ducvietho.iotapp.data.resource.remote.api.FloorRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.screen.floor.FloorFragment;
import com.example.ducvietho.iotapp.util.Constant;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

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
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.viewpagertab)
    RecyclerTabLayout mTabLayout;
    @BindView(R.id.pro_load)
    ProgressBar mProgressBar;

    private CompositeDisposable mDisposable;
    private View v;
    public HomeFragment() {
        // Required empty public constructor
    }






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,v);
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,MODE_PRIVATE);
        String lan = Constant.HTTP+sharedPreferencesLan.getString(Constant.EXTRA_LAN,"")+":"+port;
        lan = lan.replaceAll(" ","");
        mDisposable = new CompositeDisposable();
        if(sharedPreferencesLan.getString(Constant.EXTRA_LAN,"").equals("")){
            getAllFloorFailureLan();
        }else {
            FloorRemoteDataResource repository = (new FloorRemoteDataResource(IOTServiceClient
                    .getInstance(lan)));

            mDisposable.add(repository.getAllFloor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                    .mainThread()).subscribeWith(new DisposableObserver<List<Floor>>() {
                @Override
                public void onNext(List<Floor> value) {
                    getAllFloorSuccess(value);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(v.getContext(),"Connect fail Lan !",Toast.LENGTH_LONG).show();
                    getAllFloorFailureLan();
                }

                @Override
                public void onComplete() {

                }
            }));
        }

        return v;
    }



    public void getAllFloorSuccess(List<Floor> floors) {
        mProgressBar.setVisibility(View.GONE);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        for (int i=0;i<floors.size();i++){
            viewPagerAdapter.addFragment(FloorFragment.newInstance(floors.get(i).getIdFloor()),floors.get(i).getName());
        }
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setUpWithViewPager(mViewPager);
//        for (int i = 0; i < floors.size(); i++) {
//            //noinspection ConstantConditions
//            TextView tv=(TextView)LayoutInflater.from(v.getContext()).inflate(R.layout.custom_tab,null);
//            Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(),"fonts/UTM Penumbra.ttf");
//            tv.setTypeface(tf);
//            mTabLayout.getTabAt(i).setCustomView(tv);
//        }


    }
    public void getAllFloorFailureLan(){
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = Constant.HTTP+sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,"")+":"+port;
        internet = internet.replaceAll(" ","");
        if(sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,"").equals("")){
            getAllFloorFailureInternet();
        }else {
            FloorRemoteDataResource repository = (new FloorRemoteDataResource(IOTServiceClient
                    .getInstance(internet)));
            mDisposable.add(repository.getAllFloor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                    .mainThread()).subscribeWith(new DisposableObserver<List<Floor>>() {
                @Override
                public void onNext(List<Floor> value) {
                    getAllFloorSuccess(value);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(v.getContext(),"Connect fail Internet !",Toast.LENGTH_LONG).show();
                    getAllFloorFailureInternet();
                }

                @Override
                public void onComplete() {

                }
            }));
        }

    }
    public void getAllFloorFailureInternet(){
        SharedPreferences preferencesPort = v.getContext().getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB,"");
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = Constant.HTTP+sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN,"")+":"+port;
        domain = domain.replaceAll(" ","");
        FloorRemoteDataResource repository = (new FloorRemoteDataResource(IOTServiceClient
                .getInstance(domain)));
        mDisposable.add(repository.getAllFloor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Floor>>() {
            @Override
            public void onNext(List<Floor> value) {
                getAllFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                getAllFloorFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }
    public void getAllFloorFailure(String message) {
        Toast.makeText(v.getContext(),"Lỗi kết nối !",Toast.LENGTH_LONG).show();
    }
}
