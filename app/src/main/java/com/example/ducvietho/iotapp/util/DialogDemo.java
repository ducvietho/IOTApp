package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.WheelView;

/**
 * Created by ducvietho on 3/2/2018.
 */

public class DialogDemo {
    @BindView(R.id.wheel_hour_on)
    WheelView mWheelHourOn;
    @BindView(R.id.wheel_minute_on)
    WheelView mWheelMinuteOn;
    @BindView(R.id.wheel_hour_off)
    WheelView mWheelHourOff;
    @BindView(R.id.wheel_minute_off)
    WheelView mWheelMinuteOff;
    @BindView(R.id.wheel_on)
    WheelView mOn;
    @BindView(R.id.wheel_off)
    WheelView mOff;
    @BindView(R.id.tv_alarm)
    TextView mAlarm;
    @BindView(R.id.tv_delete_alarm)
    TextView mDelete;
    @BindView(R.id.tv_save)
    TextView mOk;
    @BindView(R.id.tv_cancel)
    TextView mCancel;
    @BindView(R.id.tv_on_hour)
    TextView mOnHour;
    @BindView(R.id.tv_off_hour)
    TextView mOffHour;

    private Context mContext;
    private String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
    private String[] minutes = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private String[] day = new String[]{"AM","PM"};

    public DialogDemo(Context context) {
        mContext = context;
    }
    public void showDialodAlarmEquiment(final Equipment equipment) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alarm);
        dialog.setCancelable(false);
        ButterKnife.bind(this,dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM PenumbraBold.ttf");
        mOk.setTypeface(tf);
        mCancel.setTypeface(tf);
        mAlarm.setTypeface(tf1);
        mDelete.setTypeface(tf1);
        mOnHour.setTypeface(tf);
        mOffHour.setTypeface(tf);
        mWheelHourOn.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelMinuteOn.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mWheelHourOff.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelMinuteOff.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mOn.setItems(new ArrayList<String>(Arrays.asList(day)));
        mOff.setItems(new ArrayList<String>(Arrays.asList(day)));
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }
}
