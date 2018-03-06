package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ducvietho.iotapp.util.Constant.EXTRA_STATE;
import static com.example.ducvietho.iotapp.util.Constant.EXTRA_STATE_GROUP;
import static com.example.ducvietho.iotapp.util.Constant.PRE_OFF;
import static com.example.ducvietho.iotapp.util.Constant.PRE_ON;
import static com.example.ducvietho.iotapp.util.Constant.PRE_STATE;
import static com.example.ducvietho.iotapp.util.Constant.PRE_STATE_GROUP;

/**
 * Created by ducvietho on 2/28/2018.
 */

public class DialogSettingAlarm {


    @BindView(R.id.tv_turn_alarm)
    TextView mTextView;
    @BindView(R.id.tv_alarm_setting)
    TextView mSetting;
    @BindView(R.id.switch_state)
    Switch mSwitch;
    private Context mContext;

    public DialogSettingAlarm(Context context) {
        mContext = context;
    }

    public void showDialogAlarmSettingEquipment(final Equipment equipment) {
        String preSetting = PRE_STATE + String.valueOf(equipment.getId());
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_alarm);
        ButterKnife.bind(this, dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        mTextView.setTypeface(tf);
        mSetting.setTypeface(tf);
        final SharedPreferences sharedPreferences = mContext.getSharedPreferences(preSetting, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean state = sharedPreferences.getBoolean(EXTRA_STATE, false);
        if (state) {
            mSwitch.setChecked(true);
            final String pre = Constant.PRE_ALARM + String.valueOf(equipment.getId());
            SharedPreferences preferences = mContext.getSharedPreferences(pre, Context.MODE_PRIVATE);
            String alarmOnTime = preferences.getString(PRE_ON, null);
            String alarmOffTime = preferences.getString(PRE_OFF, null);
            if (alarmOnTime!=null){
                new AlarmEquip(mContext).alarmOnEquip(equipment,alarmOnTime);
            }
            if(alarmOffTime!=null){
                new AlarmEquip(mContext).alarmOffEquip(equipment,alarmOffTime);
            }
        }
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmEquiment(equipment);
                dialog.dismiss();
            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(EXTRA_STATE, true);
                    final String pre = Constant.PRE_ALARM + String.valueOf(equipment.getId());
                    SharedPreferences preferences = mContext.getSharedPreferences(pre, Context.MODE_PRIVATE);
                    String alarmOnTime = preferences.getString(PRE_ON, null);
                    String alarmOffTime = preferences.getString(PRE_OFF, null);
                    if (alarmOnTime!=null){
                        new AlarmEquip(mContext).alarmOnEquip(equipment,alarmOnTime);
                    }
                    if(alarmOffTime!=null){
                        new AlarmEquip(mContext).alarmOffEquip(equipment,alarmOffTime);
                    }

                    editor.commit();
                } else {
                    editor.putBoolean(EXTRA_STATE, false);
                    editor.commit();
                }
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public void showDialogAlarmSettingGroup(final Group group) {
        String preSetting = PRE_STATE_GROUP + String.valueOf(group.getId());
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_alarm);
        ButterKnife.bind(this, dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        mTextView.setTypeface(tf);
        mSetting.setTypeface(tf);
        final SharedPreferences sharedPreferences = mContext.getSharedPreferences(preSetting, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean state = sharedPreferences.getBoolean(EXTRA_STATE_GROUP, false);
        if (state) {
            mSwitch.setChecked(true);
        }
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmGroup(group);
                dialog.dismiss();
            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(EXTRA_STATE_GROUP, true);
                    editor.commit();
                } else {
                    editor.putBoolean(EXTRA_STATE_GROUP, false);
                    editor.commit();
                }
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
