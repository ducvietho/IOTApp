package com.example.ducvietho.iotapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.receiver.AlarmEquipOffReceiver;
import com.example.ducvietho.iotapp.receiver.AlarmEquipOnReceiver;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class AlarmEquip {

    private Context mContext;

    public AlarmEquip(Context context) {
        mContext = context;
    }

    public void alarmOnEquip(Equipment equipment, String time) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        Date dat = new Date();

        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
        }
        Intent myIntent = new Intent(mContext, AlarmEquipOnReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, new Gson().toJson(equipment));
        myIntent.putExtra(Constant.EXTRA_ID_FLOOR, equipment.getIdFloor());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);


    }

    public void alarmOffEquip(Equipment equipment, String time) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);

        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
        }
        Intent myIntent = new Intent(mContext, AlarmEquipOffReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, new Gson().toJson(equipment));
        myIntent.putExtra(Constant.EXTRA_ID_FLOOR, equipment.getIdFloor());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);


    }

    public void alarmRepeatOn(Equipment equipment, String time, int dayWeek) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayWeek);
        Intent myIntent = new Intent(mContext, AlarmEquipOnReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, equipment.getId());
        myIntent.putExtra(Constant.EXTRA_ID_FLOOR, equipment.getIdFloor());
        myIntent.putExtra(Constant.EXTRA_TIME, time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),7*24*60*60*1000, pendingIntent);

    }

    public void alarmRepeatOff(Equipment equipment, String time, int dayWeek) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayWeek);
        Intent myIntent = new Intent(mContext, AlarmEquipOffReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, equipment.getId());
        myIntent.putExtra(Constant.EXTRA_ID_FLOOR, equipment.getIdFloor());
        myIntent.putExtra(Constant.EXTRA_TIME, time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),7*24*60*60*1000, pendingIntent);

    }
}
