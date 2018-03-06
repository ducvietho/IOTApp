package com.example.ducvietho.iotapp.data.model;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class DayRepeat {
    private String mDay;
    private int mId;

    public DayRepeat(String day, int id) {
        mDay = day;
        mId = id;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
