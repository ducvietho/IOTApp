package com.example.ducvietho.iotapp.screen.home;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Floor;
import com.example.ducvietho.iotapp.data.resource.remote.EquipmentDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.FloorDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.EquipmentRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.FloorRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.screen.floor.FloorFragment;
import com.example.ducvietho.iotapp.screen.group.GroupFragment;
import com.example.ducvietho.iotapp.util.Constant;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

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
public class HomeFragment extends Fragment implements HomeContract.View{
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.viewpagertab)
    TabLayout mTabLayout;
    @BindView(R.id.pro_load)
    ProgressBar mProgressBar;
    ViewPagerAdapter viewPagerAdapter;
    private CompositeDisposable mDisposable;
    private View v;
    public HomeFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,v);
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        SharedPreferences sharedPreferencesLan = v.getContext().getSharedPreferences(Constant.PREFS_LAN,
                MODE_PRIVATE);
        String lan = sharedPreferencesLan.getString(Constant.EXTRA_LAN,null);
        FloorDataRepository repository = new FloorDataRepository(new FloorRemoteDataResource(IOTServiceClient
                .getInstance(lan)));
        HomeContract.Presenter presenter = new HomePresenter(HomeFragment.this,repository);
        presenter.getAllFloorLAN();
        return v;
    }


    @Override
    public void getAllFloorSuccess(List<Floor> floors) {
        mProgressBar.setVisibility(View.GONE);
        for (int i=0;i<floors.size();i++){
            viewPagerAdapter.addFragment(FloorFragment.newInstance(floors.get(i).getIdFloor()),floors.get(i).getName());
        }
        viewPagerAdapter.addFragment(new GroupFragment(),"Group");
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < floors.size()+1; i++) {
            //noinspection ConstantConditions
            TextView tv=(TextView)LayoutInflater.from(v.getContext()).inflate(R.layout.custom_tab,null);
            Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(),"fonts/UTM Penumbra.ttf");
            tv.setTypeface(tf);
            mTabLayout.getTabAt(i).setCustomView(tv);

        }

    }

    @Override
    public void getAllFloorFailureLAN() {
        SharedPreferences sharedPreferencesInternet = v.getContext().getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        FloorDataRepository repository = new FloorDataRepository(new FloorRemoteDataResource(IOTServiceClient
                .getInstance(internet)));
        HomeContract.Presenter presenter = new HomePresenter(HomeFragment.this,repository);
        presenter.getAllFloorInternet();
    }

    @Override
    public void getAllFloorFailureInternet() {
        SharedPreferences sharedPreferencesDomain = v.getContext().getSharedPreferences(Constant.PREFS_DOMAIN,
                MODE_PRIVATE);
        String domain = sharedPreferencesDomain.getString(Constant.EXTRA_INTERNET,null);
        FloorDataRepository repository = new FloorDataRepository(new FloorRemoteDataResource(IOTServiceClient
                .getInstance(domain)));
        HomeContract.Presenter presenter = new HomePresenter(HomeFragment.this,repository);
        presenter.getAllFloorDomain();
    }

    @Override
    public void getAllFloorFailureDomain(String message) {
        Toast.makeText(v.getContext(),"Error:"+message,Toast.LENGTH_LONG).show();
    }
}
