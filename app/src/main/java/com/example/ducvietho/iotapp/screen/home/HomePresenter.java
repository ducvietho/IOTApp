package com.example.ducvietho.iotapp.screen.home;

import com.example.ducvietho.iotapp.data.model.Floor;
import com.example.ducvietho.iotapp.data.resource.remote.FloorDataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ducvietho on 2/1/2018.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private FloorDataRepository mRepository;
    private CompositeDisposable mDisposable;

    public HomePresenter(HomeContract.View view, FloorDataRepository repository) {
        mView = view;
        mRepository = repository;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void getAllFloorLAN() {
        mDisposable.add(mRepository.getAllFloor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Floor>>() {
            @Override
            public void onNext(List<Floor> value) {
                mView.getAllFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllFloorFailureLAN();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getAllFloorInternet() {
        mDisposable.add(mRepository.getAllFloor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Floor>>() {
            @Override
            public void onNext(List<Floor> value) {
                mView.getAllFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllFloorFailureInternet();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getAllFloorDomain() {
        mDisposable.add(mRepository.getAllFloor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Floor>>() {
            @Override
            public void onNext(List<Floor> value) {
                mView.getAllFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllFloorFailureDomain(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
