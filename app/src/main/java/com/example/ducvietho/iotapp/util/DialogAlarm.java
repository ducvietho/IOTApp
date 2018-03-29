package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.WheelView;

import static com.example.ducvietho.iotapp.util.Constant.PRE_OFF;
import static com.example.ducvietho.iotapp.util.Constant.PRE_OFF_GROUP;
import static com.example.ducvietho.iotapp.util.Constant.PRE_ON;
import static com.example.ducvietho.iotapp.util.Constant.PRE_ON_GROUP;

/**
 * Created by ducvietho on 2/7/2018.
 */

public class DialogAlarm {

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
    @BindView(R.id.switch_on)
    SwitchCompat mSwitchON;
    @BindView(R.id.switch_off)
    SwitchCompat mSwitchOff;
    @BindView(R.id.tv_repeat)
    TextView mRepeat;
    @BindView(R.id.layout_repeat)
    LinearLayout mLayout;
    @BindView(R.id.tv_t2)
    TextView mT2;
    @BindView(R.id.tv_t3)
    TextView mT3;
    @BindView(R.id.tv_t4)
    TextView mT4;
    @BindView(R.id.tv_t5)
    TextView mT5;
    @BindView(R.id.tv_t6)
    TextView mT6;
    @BindView(R.id.tv_t7)
    TextView mT7;
    @BindView(R.id.tv_cn)
    TextView mCN;
    private Context mContext;
    private String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
    private String[] minutes = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private String[] day = new String[]{"AM", "PM", "", ""};


    public DialogAlarm(Context context) {
        mContext = context;
    }

    public void showDialodAlarmEquiment(final Equipment equipment) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alarm);
        dialog.setCancelable(false);
        ButterKnife.bind(this, dialog);
        final Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM PenumbraBold.ttf");
        mT2.setTypeface(tf);
        mT3.setTypeface(tf);
        mT4.setTypeface(tf);
        mT5.setTypeface(tf);
        mT6.setTypeface(tf);
        mT7.setTypeface(tf);
        mCN.setTypeface(tf);
        mOk.setTypeface(tf);
        mCancel.setTypeface(tf);
        mAlarm.setTypeface(tf1);
        mDelete.setTypeface(tf1);
        mOnHour.setTypeface(tf);
        mOffHour.setTypeface(tf);
        mRepeat.setTypeface(tf);
        mWheelHourOn.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelMinuteOn.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mWheelHourOff.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelMinuteOff.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mOn.setItems(new ArrayList<String>(Arrays.asList(day)));
        mOff.setItems(new ArrayList<String>(Arrays.asList(day)));
        String preEquip = Constant.PRE_REPEAT_EQUIP+String.valueOf(equipment.getId());
        final String extraEquip = Constant.EXTRA_EQUIP_REPEAT+String.valueOf(equipment.getId());
        SharedPreferences sharedPreferencesRepeat = mContext.getSharedPreferences(preEquip,Context.MODE_PRIVATE);
        Set<String> setDay = sharedPreferencesRepeat.getStringSet(extraEquip,null);
        if(setDay!=null){
            List<String> strings = new ArrayList<>(setDay);
            for (int i= 0;i<strings.size();i++){
                int day = Integer.parseInt(strings.get(i));
                switch (day){
                    case 1:
                        mCN.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mT2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mT3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        mT4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        mT5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        mT6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        mT7.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

        final String preString = Constant.PRE_ALARM + String.valueOf(equipment.getId());
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(preString, Context.MODE_PRIVATE);
        String alarmOnTime = sharedPreferences.getString(PRE_ON, null);
        String alarmOffTime = sharedPreferences.getString(PRE_OFF, null);
        if (alarmOnTime == null) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
                mOn.setInitPosition(1);
            } else {
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
                mOn.setInitPosition(0);

            }

        } else {
            mSwitchON.setChecked(true);
            String[] times = alarmOnTime.split(":");
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
                mOn.setInitPosition(1);
            } else {
                mOn.setInitPosition(0);
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
            }

        }
        if (alarmOffTime == null) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
                mOff.setInitPosition(1);
            } else {
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
                mOff.setInitPosition(0);

            }

        } else {
            String[] times = alarmOffTime.split(":");
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            mSwitchOff.setChecked(true);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
                mOff.setInitPosition(1);
            } else {
                mOff.setInitPosition(0);
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
            }

        }
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String onAlarm = null;
                String offAlarm = null;
                if (mSwitchON.isChecked() == true) {
                    if (mOn.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOn.getSelectedItem()]);
                        hour = hour + 12;
                        onAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                    else {
                        onAlarm = hours[mWheelHourOn.getSelectedItem()]+":"+minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                }
                if (mSwitchOff.isChecked() == true) {
                    if (mOff.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOff.getSelectedItem()]);
                        hour = hour + 12;
                        offAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOff.getSelectedItem()];
                    }
                    else {
                        offAlarm = hours[mWheelHourOff.getSelectedItem()]+":"+minutes[mWheelMinuteOff.getSelectedItem
                                ()];
                    }
                }
                SharedPreferences.Editor editor = mContext.getSharedPreferences(preString, Context.MODE_PRIVATE).edit();
                editor.putString(PRE_ON, onAlarm);
                editor.putString(PRE_OFF, offAlarm);
                editor.commit();
                dialog.dismiss();

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogRepeatEquip(mContext).showDialog(equipment);
                String onAlarm = null;
                String offAlarm = null;
                if (mSwitchON.isChecked() == true) {
                    if (mOn.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOn.getSelectedItem()]);
                        hour = hour + 12;
                        onAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                    else {
                        onAlarm = hours[mWheelHourOn.getSelectedItem()]+":"+minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                }
                if (mSwitchOff.isChecked() == true) {
                    if (mOff.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOff.getSelectedItem()]);
                        hour = hour + 12;
                        offAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOff.getSelectedItem()];
                    }
                    else {
                        offAlarm = hours[mWheelHourOff.getSelectedItem()]+":"+minutes[mWheelMinuteOff.getSelectedItem
                                ()];
                    }
                }
                SharedPreferences.Editor editor = mContext.getSharedPreferences(preString, Context.MODE_PRIVATE).edit();
                editor.putString(PRE_ON, onAlarm);
                editor.putString(PRE_OFF, offAlarm);
                editor.commit();
                dialog.dismiss();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor edit = mContext.getSharedPreferences(preString,Context.MODE_PRIVATE).edit();
                edit.clear();
                edit.commit();
                Toast.makeText(mContext, "Đã xóa hẹn giờ !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }

    public void showDialodAlarmGroup(final Group group) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alarm);
        dialog.setCancelable(false);
        ButterKnife.bind(this, dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM PenumbraBold.ttf");
        mT2.setTypeface(tf);
        mT3.setTypeface(tf);
        mT4.setTypeface(tf);
        mT5.setTypeface(tf);
        mT6.setTypeface(tf);
        mT7.setTypeface(tf);
        mCN.setTypeface(tf);
        mOk.setTypeface(tf);
        mCancel.setTypeface(tf);
        mAlarm.setTypeface(tf1);
        mDelete.setTypeface(tf1);
        mOnHour.setTypeface(tf);
        mOffHour.setTypeface(tf);
        mRepeat.setTypeface(tf);
        mWheelHourOn.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelMinuteOn.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mWheelHourOff.setItems(new ArrayList<String>(Arrays.asList(hours)));
        mWheelMinuteOff.setItems(new ArrayList<String>(Arrays.asList(minutes)));
        mOn.setItems(new ArrayList<String>(Arrays.asList(day)));
        mOff.setItems(new ArrayList<String>(Arrays.asList(day)));
        String preEquip = Constant.PRE_REPEAT_GROUP+String.valueOf(group.getId());
        final String extraEquip = Constant.EXTRA_GROUP_REPEAT+String.valueOf(group.getId());
        SharedPreferences sharedPreferencesRepeat = mContext.getSharedPreferences(preEquip,Context.MODE_PRIVATE);
        Set<String> setDay = sharedPreferencesRepeat.getStringSet(extraEquip,null);
        if(setDay!=null){
            List<String> strings = new ArrayList<>(setDay);
            for (int i= 0;i<strings.size();i++){
                int day = Integer.parseInt(strings.get(i));
                switch (day){
                    case 1:
                        mCN.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mT2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mT3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        mT4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        mT5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        mT6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        mT7.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        final String preString =  Constant.PRE_ALARM_GROUP + String.valueOf(group.getId());
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(preString, Context.MODE_PRIVATE);
        String alarmOnTime = sharedPreferences.getString(PRE_ON_GROUP, null);
        String alarmOffTime = sharedPreferences.getString(PRE_OFF_GROUP, null);
        if (alarmOnTime == null) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
                mOn.setInitPosition(1);
            } else {
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
                mOn.setInitPosition(0);

            }

        } else {
            mSwitchON.setChecked(true);
            String[] times = alarmOnTime.split(":");
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
                mOn.setInitPosition(1);
            } else {
                mOn.setInitPosition(0);
                mWheelHourOn.setInitPosition(hour);
                mWheelMinuteOn.setInitPosition(minute);
            }

        }
        if (alarmOffTime == null) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
                mOff.setInitPosition(1);
            } else {
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
                mOff.setInitPosition(0);

            }

        } else {
            String[] times = alarmOffTime.split(":");
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            mSwitchOff.setChecked(true);
            if (hour >= 12) {
                hour = hour - 12;
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
                mOff.setInitPosition(1);
            } else {
                mOff.setInitPosition(0);
                mWheelHourOff.setInitPosition(hour);
                mWheelMinuteOff.setInitPosition(minute);
            }

        }
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String onAlarm = null;
                String offAlarm = null;
                if (mSwitchON.isChecked() == true) {
                    if (mOn.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOn.getSelectedItem()]);
                        hour = hour + 12;
                        onAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                    else {
                        onAlarm = hours[mWheelHourOn.getSelectedItem()]+":"+minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                }
                if (mSwitchOff.isChecked() == true) {
                    if (mOff.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOff.getSelectedItem()]);
                        hour = hour + 12;
                        offAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOff.getSelectedItem()];
                    }
                    else {
                        offAlarm = hours[mWheelHourOff.getSelectedItem()]+":"+minutes[mWheelMinuteOff.getSelectedItem
                                ()];
                    }
                }
                SharedPreferences.Editor editor = mContext.getSharedPreferences(preString, Context.MODE_PRIVATE).edit();
                editor.putString(PRE_ON_GROUP, onAlarm);
                editor.putString(PRE_OFF_GROUP, offAlarm);
                editor.commit();
                dialog.dismiss();

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogRepeatGroup(mContext).showDialog(group);
                String onAlarm = null;
                String offAlarm = null;
                if (mSwitchON.isChecked() == true) {
                    if (mOn.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOn.getSelectedItem()]);
                        hour = hour + 12;
                        onAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                    else {
                        onAlarm = hours[mWheelHourOn.getSelectedItem()]+":"+minutes[mWheelMinuteOn.getSelectedItem()];
                    }
                }
                if (mSwitchOff.isChecked() == true) {
                    if (mOff.getSelectedItem() == 1) {
                        int hour = Integer.parseInt(hours[mWheelHourOff.getSelectedItem()]);
                        hour = hour + 12;
                        offAlarm = String.valueOf(hour) + ":" + minutes[mWheelMinuteOff.getSelectedItem()];
                    }
                    else {
                        offAlarm = hours[mWheelHourOff.getSelectedItem()]+":"+minutes[mWheelMinuteOff.getSelectedItem
                                ()];
                    }
                }
                SharedPreferences.Editor editor = mContext.getSharedPreferences(preString, Context.MODE_PRIVATE).edit();
                editor.putString(PRE_ON_GROUP, onAlarm);
                editor.putString(PRE_OFF_GROUP, offAlarm);
                editor.commit();

                dialog.dismiss();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = mContext.getSharedPreferences(preString,Context.MODE_PRIVATE).edit();
                edit.clear();
                edit.commit();
                Toast.makeText(mContext, "Đã xóa hẹn giờ !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            dialog.show();
        } catch (Exception e) {

        }

    }
}
