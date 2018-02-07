package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.WheelView;

/**
 * Created by ducvietho on 2/7/2018.
 */

public class DialogAlarm {
    @BindView(R.id.wheel_hour)
    WheelView mWheelHour;
    @BindView(R.id.wheel_minute)
    WheelView mWheelMinute;
    private Context mContext;
    private String[] hours = new String[]{"00","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
            "17",
            "18","19","20","21","22","23"};
    private String[] minutes = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14",
            "15","16","17", "18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35",
            "36","37","38", "39", "40", "41", "42", "43","44","45","46","47","48","49","50","51","52","53","54","55",
            "56","57","58","59"};
    private int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private int minute = Calendar.getInstance().get(Calendar.MINUTE);
    public DialogAlarm(Context context) {
        mContext = context;
    }
    public  void showDialodAlarmEquiment(Equipment equipment){
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alarm);
        ButterKnife.bind(this,dialog);
        mWheelHour.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelHour.setInitPosition(hour);
        mWheelMinute.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mWheelMinute.setInitPosition(minute);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public  void showDialodAlarmGroup(Group group){
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alarm);
        ButterKnife.bind(this,dialog);
        mWheelHour.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelHour.setInitPosition(hour);
        mWheelMinute.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mWheelMinute.setInitPosition(minute);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
