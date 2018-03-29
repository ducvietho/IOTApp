package com.example.ducvietho.iotapp.data.resource.remote.api;

import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.data.model.ImageResponse;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 3/8/2018.
 */

public class ImageRemoteDataResource extends BaseRemoteDataResource  {
    public ImageRemoteDataResource(IOTApi IOTApi) {
        super(IOTApi);
    }


    public Observable<List<Image>> getAllImage() {
        return mIOTApi.getAllImage().map(new Function<ImageResponse, List<Image>>() {
            @Override
            public List<Image> apply(ImageResponse imageResponse) throws Exception {
                return imageResponse.getImages();
            }
        });
    }
}
