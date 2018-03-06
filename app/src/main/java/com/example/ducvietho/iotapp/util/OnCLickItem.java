package com.example.ducvietho.iotapp.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.data.model.Equipment;

/**
 * Created by ducvietho on 1/21/2018.
 */

public interface OnCLickItem {
    void onClick(Equipment equipment, ImageView imageView, TextView textView);
}
