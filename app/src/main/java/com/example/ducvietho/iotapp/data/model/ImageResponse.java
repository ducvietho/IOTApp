package com.example.ducvietho.iotapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 3/8/2018.
 */

public class ImageResponse {
    @SerializedName("data")
    List<Image> mImages;

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }
}
