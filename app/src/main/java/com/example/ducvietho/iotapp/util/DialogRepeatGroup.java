package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.DayRepeat;
import com.example.ducvietho.iotapp.data.model.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class DialogRepeatGroup implements SaveDayRepeat<Group> {
    public static final String PRE_REPEAT_GROUP = "repeat_group_";
    @BindView(R.id.img_back)
    ImageView mView;
    @BindView(R.id.tv_repeat)
    TextView mTextView;
    @BindView(R.id.rec_repeat)
    RecyclerView mRecyclerView;
    private Context mContext;

    public DialogRepeatGroup(Context context) {
        mContext = context;
    }

    public void showDialog(final Group group) {
        String prefString  = PRE_REPEAT_GROUP+String.valueOf(group.getId());
        SharedPreferences.Editor editor = mContext.getSharedPreferences(prefString,Context.MODE_PRIVATE).edit();
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_repeat);
        ButterKnife.bind(this, dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        mTextView.setTypeface(tf);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmGroup(group);
                dialog.dismiss();
            }
        });
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        mRecyclerView.setLayoutManager(manager);
        RecyclerAdapterRepeatGroup adapterRepeat = new RecyclerAdapterRepeatGroup(addDDay(), this, group);
        mRecyclerView.setAdapter(adapterRepeat);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void saveDayRepeat(Group object, int id) {

    }

    public List<DayRepeat> addDDay(){
        List<DayRepeat> dayRepeats =new ArrayList<>();
        dayRepeats.add(new DayRepeat("Thứ hai",2));
        dayRepeats.add(new DayRepeat("Thứ ba",3));
        dayRepeats.add(new DayRepeat("Thứ tư",4));
        dayRepeats.add(new DayRepeat("Thứ năm",5));
        dayRepeats.add(new DayRepeat("Thứ sáu",6));
        dayRepeats.add(new DayRepeat("Thứ bảy",7));
        dayRepeats.add(new DayRepeat("Chủ nhật",1));
        return dayRepeats;
    }

}
