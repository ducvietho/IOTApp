package com.example.ducvietho.iotapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.receiver.AlarmEquipOffReceiver;
import com.example.ducvietho.iotapp.receiver.AlarmEquipOnReceiver;
import com.example.ducvietho.iotapp.receiver.AlarmGroupOffReceiver;
import com.example.ducvietho.iotapp.receiver.AlarmGroupOnReceiver;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

import static com.example.ducvietho.iotapp.R.string.group;

/**
 * Created by ducvietho on 3/9/2018.
 */

public class AlarmGroup {
    private Context mContext;

    public AlarmGroup(Context context) {
        mContext = context;
    }
    public void alarmOnGroup(Group group, String time) {
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
        Intent myIntent = new Intent(mContext, AlarmGroupOnReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_GROUP, group.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);


    }

    public void alarmOffGroup(Group group, String time) {
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
        Intent myIntent = new Intent(mContext, AlarmGroupOffReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, new Gson().toJson(group));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);


    }

    public void alarmRepeatOn(Group group, String time, int dayWeek) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayWeek);
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayWeek);
        Intent myIntent = new Intent(mContext, AlarmGroupOnReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, new Gson().toJson(group));
        myIntent.putExtra(Constant.EXTRA_TIME, time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),1*60*60*100, pendingIntent);
    }

    public void alarmRepeatOff(Group group, String time, int dayWeek) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayWeek);
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayWeek);
        Intent myIntent = new Intent(mContext, AlarmGroupOffReceiver.class);
        myIntent.putExtra(Constant.EXTRA_ID_EQUIP, group.getId());
        myIntent.putExtra(Constant.EXTRA_TIME, time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),1*60*60*100, pendingIntent);

    }
}
