package com.example.ducvietho.iotapp.screen.login;

import android.support.annotation.MainThread;

import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.resource.remote.ImageDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.LoginDataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ducvietho on 1/28/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginDataRepository mRepository;
    private LoginContract.View mView;
    private CompositeDisposable mDisposable;
    private ImageDataRepository mImageDataRepository;

    public LoginPresenter(ImageDataRepository imageDataRepository, LoginDataRepository repository, LoginContract.View view) {
        mImageDataRepository = imageDataRepository;
        mRepository = repository;
        mView = view;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void loginUser(String username, String pass) {
        mDisposable.add(mRepository.login(username, pass).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse value) {
                mView.loginSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.loginFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void downloadImage() {
        mDisposable.add(mImageDataRepository.getAllImage().subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Image>>() {
            @Override
            public void onNext(List<Image> value) {
                mView.downloadSuccess(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
