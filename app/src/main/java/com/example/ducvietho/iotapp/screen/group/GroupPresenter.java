package com.example.ducvietho.iotapp.screen.group;

import com.example.ducvietho.iotapp.data.model.Group;
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
    public void getAllGroup() {
        mDisposable.add(mRepository.getAllGroup().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Group>>() {
            @Override
            public void onNext(List<Group> value) {
                mView.getAllGroupSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.getAllGroupFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
