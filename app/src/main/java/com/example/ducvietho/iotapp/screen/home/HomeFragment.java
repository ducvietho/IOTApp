package com.example.ducvietho.iotapp.screen.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Floor;
import com.example.ducvietho.iotapp.data.resource.remote.FloorDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.FloorRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.screen.floor.FloorFragment;
import com.example.ducvietho.iotapp.screen.group.GroupFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HomeContract.View{
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_floor)
    TabLayout mTabLayout;

    private View v;
    public HomeFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,v);
        FloorDataRepository repository = new FloorDataRepository(new FloorRemoteDataResource(IOTServiceClient
                .getInstance()));
        HomeContract.Presenter presenter = new HomePresenter(HomeFragment.this,repository);
        presenter.getAllFloor();
        return v;
    }


    @Override
    public void getAllFloorSuccess(List<Floor> floors) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        for (int i=0;i<floors.size();i++){
            viewPagerAdapter.addFragment(FloorFragment.newInstance(floors.get(i).getIdFloor()),floors.get(i).getName());
        }
        viewPagerAdapter.addFragment(new GroupFragment(),"Groups");
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void getAllFloorFailure(String message) {
        Toast.makeText(v.getContext(),"Error:"+message,Toast.LENGTH_LONG).show();
    }
}
