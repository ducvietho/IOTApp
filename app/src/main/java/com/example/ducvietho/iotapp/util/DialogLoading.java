package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.ducvietho.iotapp.R;

/**
 * Created by ducvietho on 2/8/2018.
 */

public class DialogLoading {
    private Context mContext;

    public DialogLoading(Context context) {
        mContext = context;
    }
    public void showDialog(){
        Dialog dialog = new Dialog(mContext);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);

        dialog.show();
    }
}
