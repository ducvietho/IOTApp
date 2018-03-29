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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class DialogRepeatGroup  {

    @BindView(R.id.img_back)
    ImageView mView;
    @BindView(R.id.tv_repeat)
    TextView mTextView;
    @BindView(R.id.tv_complete)
    TextView mComplete;
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
    @BindView(R.id.cb_t2)
    ImageView mCBT2;
    @BindView(R.id.cb_t3)
    ImageView mCBT3;
    @BindView(R.id.cb_t4)
    ImageView mCBT4;
    @BindView(R.id.cb_t5)
    ImageView mCBT5;
    @BindView(R.id.cb_t6)
    ImageView mCBT6;
    @BindView(R.id.cb_t7)
    ImageView mCBT7;
    @BindView(R.id.cb_cn)
    ImageView mCBCN;
    @BindView(R.id.layout_back)
    RelativeLayout mLayout;
    private Context mContext;
    public DialogRepeatGroup(Context context) {
        mContext = context;
    }
    public void showDialog(final Group group) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_repeat);
        dialog.setCancelable(false);
        ButterKnife.bind(this, dialog);
        String preEquip = Constant.PRE_REPEAT_GROUP+String.valueOf(group.getId());
        final String extraEquip = Constant.EXTRA_GROUP_REPEAT+String.valueOf(group.getId());
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(preEquip,Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> setDay = sharedPreferences.getStringSet(extraEquip,null);
        if(setDay!=null){
            List<String> strings = new ArrayList<>(setDay);
            for (int i= 0;i<strings.size();i++){
                int day = Integer.parseInt(strings.get(i));
                switch (day){
                    case 1:
                        mCBCN.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mCBT2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mCBT3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        mCBT4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        mCBT5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        mCBT6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        mCBT7.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Penumbra.ttf");
        mTextView.setTypeface(tf);
        mComplete.setTypeface(tf);
        mT2.setTypeface(tf);
        mT3.setTypeface(tf);
        mT4.setTypeface(tf);
        mT5.setTypeface(tf);
        mT6.setTypeface(tf);
        mT7.setTypeface(tf);
        mCN.setTypeface(tf);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmGroup(group);
                dialog.dismiss();
            }
        });
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmGroup(group);
                List<String> list = new ArrayList<String>();
                if(mCBT2.getVisibility()==View.VISIBLE){
                    list.add("2");
                }
                if(mCBT3.getVisibility()==View.VISIBLE){
                    list.add("3");
                }
                if(mCBT4.getVisibility()==View.VISIBLE){
                    list.add("4");
                }
                if(mCBT5.getVisibility()==View.VISIBLE){
                    list.add("5");
                }
                if(mCBT6.getVisibility()==View.VISIBLE){
                    list.add("6");
                }
                if(mCBT7.getVisibility()==View.VISIBLE){
                    list.add("7");
                }
                if(mCBCN.getVisibility()==View.VISIBLE){
                    list.add("1");
                }
                Set<String> hashSet = new HashSet<String>();
                hashSet.addAll(list);
                editor.putStringSet(extraEquip,hashSet);
                editor.commit();
                dialog.dismiss();
            }
        });
        mT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBT2.getVisibility()==View.VISIBLE){
                    mCBT2.setVisibility(View.GONE);
                }else {
                    mCBT2.setVisibility(View.VISIBLE);
                }
            }
        });
        mT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBT3.getVisibility()==View.VISIBLE){
                    mCBT3.setVisibility(View.GONE);
                }else {
                    mCBT3.setVisibility(View.VISIBLE);
                }
            }
        });
        mT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBT4.getVisibility()==View.VISIBLE){
                    mCBT4.setVisibility(View.GONE);
                }else {
                    mCBT4.setVisibility(View.VISIBLE);
                }
            }
        });
        mT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBT5.getVisibility()==View.VISIBLE){
                    mCBT5.setVisibility(View.GONE);
                }else {
                    mCBT5.setVisibility(View.VISIBLE);
                }
            }
        });
        mT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBT6.getVisibility()==View.VISIBLE){
                    mCBT6.setVisibility(View.GONE);
                }else {
                    mCBT6.setVisibility(View.VISIBLE);
                }
            }
        });
        mT7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBT7.getVisibility()==View.VISIBLE){
                    mCBT7.setVisibility(View.GONE);
                }else {
                    mCBT7.setVisibility(View.VISIBLE);
                }
            }
        });
        mCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCBCN.getVisibility()==View.VISIBLE){
                    mCBCN.setVisibility(View.GONE);
                }else {
                    mCBCN.setVisibility(View.VISIBLE);
                }
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
