package com.example.ducvietho.iotapp.screen.floor;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.resource.remote.EquipmentDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.FloorDataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ducvietho on 2/3/2018.
 */

public class FloorPresenter implements FloorContract.Presenter {
    private EquipmentDataRepository mRepository;
    private FloorContract.View mView;
    private CompositeDisposable mDisposable;

    public FloorPresenter(EquipmentDataRepository repository, FloorContract.View view) {
        mRepository = repository;
        mView = view;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void getAllEquipByFloor(int id) {
        mDisposable.add(mRepository.getAllEquipmentByFloor(id).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                mView.getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllEquipByFloorFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
