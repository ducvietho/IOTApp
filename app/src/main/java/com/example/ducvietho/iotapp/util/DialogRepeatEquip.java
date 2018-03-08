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
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class DialogRepeatEquip  {
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
    CheckBox mCBT2;
    @BindView(R.id.cb_t3)
    CheckBox mCBT3;
    @BindView(R.id.cb_t4)
    CheckBox mCBT4;
    @BindView(R.id.cb_t5)
    CheckBox mCBT5;
    @BindView(R.id.cb_t6)
    CheckBox mCBT6;
    @BindView(R.id.cb_t7)
    CheckBox mCBT7;
    @BindView(R.id.cb_cn)
    CheckBox mCBCN;
    private Context mContext;

    public DialogRepeatEquip(Context context) {
        mContext = context;
    }
    public void showDialog(final Equipment equipment){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_repeat);
        ButterKnife.bind(this,dialog);
        String preEquip = Constant.PRE_REPEAT_EQUIP+String.valueOf(equipment.getId());
        final String extraEquip = Constant.EXTRA_EQUIP_REPEAT+String.valueOf(equipment.getId());
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(preEquip,Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> setDay = sharedPreferences.getStringSet(extraEquip,null);
        if(setDay!=null){
            List<String> strings = new ArrayList<>(setDay);
            for (int i= 0;i<strings.size();i++){
                int day = Integer.parseInt(strings.get(i));
                switch (day){
                    case 1:
                        mCBCN.setChecked(true);
                        break;
                    case 2:
                        mCBT2.setChecked(true);
                        break;
                    case 3:
                        mCBT3.setChecked(true);
                        break;
                    case 4:
                        mCBT4.setChecked(true);
                        break;
                    case 5:
                        mCBT5.setChecked(true);
                        break;
                    case 6:
                        mCBT6.setChecked(true);
                        break;
                    case 7:
                        mCBT7.setChecked(true);
                        break;
                }
            }
        }
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM Penumbra.ttf");
        mTextView.setTypeface(tf);
        mComplete.setTypeface(tf);
        mT2.setTypeface(tf);
        mT3.setTypeface(tf);
        mT4.setTypeface(tf);
        mT5.setTypeface(tf);
        mT6.setTypeface(tf);
        mT7.setTypeface(tf);
        mCN.setTypeface(tf);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmEquiment(equipment);
                dialog.dismiss();
            }
        });
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlarm(mContext).showDialodAlarmEquiment(equipment);
                List<String> list = new ArrayList<String>();
                if(mCBT2.isChecked()==true){
                    list.add("2");
                }
                if(mCBT3.isChecked()==true){
                    list.add("3");
                }
                if(mCBT4.isChecked()==true){
                    list.add("4");
                }
                if(mCBT5.isChecked()==true){
                    list.add("5");
                }
                if(mCBT6.isChecked()==true){
                    list.add("6");
                }
                if(mCBT7.isChecked()==true){
                    list.add("7");
                }
                if(mCBCN.isChecked()==true){
                    list.add("1");
                }
                Set<String> hashSet = new HashSet<String>();
                hashSet.addAll(list);
                editor.putStringSet(extraEquip,hashSet);
                editor.commit();
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
