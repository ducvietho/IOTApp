package com.example.ducvietho.iotapp.data.resource.remote;

import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.data.model.ImageResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 3/8/2018.
 */

public class ImageDataRepository implements ImageDataResource {
    private ImageDataResource mDataResource;

    public ImageDataRepository(ImageDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<List<Image>> getAllImage() {
        return mDataResource.getAllImage();
    }
}
