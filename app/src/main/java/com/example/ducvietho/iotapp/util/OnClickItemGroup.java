package com.example.ducvietho.iotapp.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;

/**
 * Created by ducvietho on 2/26/2018.
 */

public interface OnClickItemGroup {
    void onClick(Group group, ImageView imageView, TextView textView);
}
