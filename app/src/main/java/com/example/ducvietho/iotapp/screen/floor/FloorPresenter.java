package com.example.ducvietho.iotapp.screen.floor;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Response;
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
    public void getAllEquipByFloorLAN(int id) {
        mDisposable.add(mRepository.getAllEquipmentByFloor(id).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                mView.getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllEquipByFloorFailureLAN();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getAllEquipByFloorInternet(int id) {
        mDisposable.add(mRepository.getAllEquipmentByFloor(id).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                mView.getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllEquipByFloorFailureInternet();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getAllEquipByFloorDomain(int id) {
        mDisposable.add(mRepository.getAllEquipmentByFloor(id).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Equipment>>() {
            @Override
            public void onNext(List<Equipment> value) {
                mView.getAllEquipByFloorSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllEquipByFloorFailureDomain(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOnEquipLAN(final Equipment equipment, final ImageView imageView, final TextView textView) {
        mDisposable.add(mRepository.turnOnEquiment(equipment.getId(),equipment.getIdFloor()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOnEquipSuccess(equipment,value,imageView,textView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOnquipFailureLAN(equipment, imageView, textView);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOnEquipInternet(final Equipment equipment, final ImageView imageView, final TextView textView) {
        mDisposable.add(mRepository.turnOnEquiment(equipment.getId(),equipment.getIdFloor()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response value) {
                        mView.turnOnEquipSuccess(equipment,value,imageView,textView);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.turnOnquipFailureInternet(equipment, imageView, textView);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void turnOnEquipDomain(final Equipment equipment, final ImageView imageView, final TextView textView) {
        mDisposable.add(mRepository.turnOnEquiment(equipment.getId(),equipment.getIdFloor()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response value) {
                        mView.turnOnEquipSuccess(equipment,value,imageView,textView);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.turnOnquipFailureDomain(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void turnOffEquipLAN(final Equipment equipment, final ImageView imageView, final TextView textView) {
        mDisposable.add(mRepository.turnOffEquiment(equipment.getId(),equipment.getIdFloor()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOffEquipSuccess(equipment,value,imageView,textView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOffEquipFailureLAN(equipment, imageView, textView);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOffEquipInternet(final Equipment equipment, final ImageView imageView, final TextView textView) {
        mDisposable.add(mRepository.turnOffEquiment(equipment.getId(),equipment.getIdFloor()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response value) {
                        mView.turnOffEquipSuccess(equipment,value,imageView,textView);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.turnOffEquipFailureInternet(equipment, imageView, textView);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void turnOffEquipDomain(final Equipment equipment, final ImageView imageView, final TextView textView) {
        mDisposable.add(mRepository.turnOffEquiment(equipment.getId(),equipment.getIdFloor()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response value) {
                        mView.turnOffEquipSuccess(equipment,value,imageView,textView);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.turnOffEquipFailureDomain(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void clear() {
        mDisposable.clear();
    }

}
