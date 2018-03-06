package com.example.ducvietho.iotapp.screen.group;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.model.Response;
import com.example.ducvietho.iotapp.data.resource.remote.GroupDataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ducvietho on 2/3/2018.
 */

public class GroupPresenter implements GroupContract.Presenter {
    private GroupDataRepository mRepository;
    private GroupContract.View mView;
    private CompositeDisposable mDisposable;

    public GroupPresenter(GroupDataRepository repository, GroupContract.View view) {
        mRepository = repository;
        mView = view;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void getAllGroupLAN() {
        mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
            @Override
            public void onNext(List<Group> value) {
                mView.getAllGroupSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllGroupFailureLAN();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getAllGroupInternet() {
        mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
            @Override
            public void onNext(List<Group> value) {
                mView.getAllGroupSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllGroupFailureInternet();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getAllGroupDomain() {
        mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
            @Override
            public void onNext(List<Group> value) {
                mView.getAllGroupSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllGroupFailureDoamin(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOnGroupLAN(final Group group, final ImageView imageView) {
        mDisposable.add(mRepository.turnOnGroup(group.getId()).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
               mView.turnOnGroupSuccess(group,value,imageView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOnGroupFailureLAN(group, imageView);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOnGroupInternet(final Group group, final ImageView imageView) {
        mDisposable.add(mRepository.turnOnGroup(group.getId()).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOnGroupSuccess(group,value,imageView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOnGroupFailureInternet(group, imageView);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOnGroupDomain(final Group group, final ImageView imageView) {
        mDisposable.add(mRepository.turnOnGroup(group.getId()).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOnGroupSuccess(group,value,imageView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOnGroupFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOffGroupLan(final Group group, final ImageView imageView) {
        mDisposable.add(mRepository.turnOffGroup(group.getId()).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOffGroupSuccess(group,value,imageView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOffGroupFailureLAN(group, imageView);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOffGroupInternet(final Group group, final ImageView imageView) {
        mDisposable.add(mRepository.turnOffGroup(group.getId()).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOffGroupSuccess(group,value,imageView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOffGroupFailureInternet(group, imageView);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void turnOffGroupDomain(final Group group, final ImageView imageView) {
        mDisposable.add(mRepository.turnOffGroup(group.getId()).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Response>() {
            @Override
            public void onNext(Response value) {
                mView.turnOffGroupSuccess(group,value,imageView);
            }

            @Override
            public void onError(Throwable e) {
                mView.turnOffGroupFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
