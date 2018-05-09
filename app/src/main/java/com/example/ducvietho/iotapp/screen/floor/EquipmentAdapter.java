package com.example.ducvietho.iotapp.screen.floor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.util.AlarmEquip;
import com.example.ducvietho.iotapp.util.CacheImage;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogAlarm;
import com.example.ducvietho.iotapp.util.DialogSettingAlarm;
import com.example.ducvietho.iotapp.util.OnCLickItem;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ducvietho.iotapp.util.Constant.EXTRA_STATE;
import static com.example.ducvietho.iotapp.util.Constant.PRE_OFF;
import static com.example.ducvietho.iotapp.util.Constant.PRE_ON;
import static com.example.ducvietho.iotapp.util.Constant.PRE_STATE;

/**
 * Created by ducvietho on 2/3/2018.
 */

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private List<Equipment> mList;
    private OnCLickItem mOnCLick;
    private Context mContext;
    public EquipmentAdapter(Context context,List<Equipment> list, OnCLickItem onCLick) {
        mContext = context;
        mList = list;
        mOnCLick = onCLick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equip, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_equip)
        ImageView mImageView;
        @BindView(R.id.tv_name)
        TextView mTextView;
        @BindView(R.id.img_alarm_off)
        ImageView mAlarmOff;
        @BindView(R.id.img_alarm_on)
        ImageView mAlarmOn;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Equipment equipment) {
            Typeface tf = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/UTM Penumbra.ttf");
            mTextView.setText(equipment.getName());
            mTextView.setTypeface(tf);
            if (equipment.getState() == 0) {

                File file = new File(mContext.getCacheDir()+ "/iot/"+equipment.getIconOff().replaceAll("/","") );
                if(file.exists()){
                    Picasso.with(itemView.getContext()).load(file).into(mImageView);
                }else{
                    Picasso.with(itemView.getContext()).load(equipment.getIconOff()).into(mImageView);
                    new CacheImage(mContext).imageDownload(equipment.getIconOff());
                }

            } else {
                File file = new File(mContext.getCacheDir() + "/iot/"+equipment.getIconOn().replaceAll("/","") );
                if(file.exists()){
                    Picasso.with(itemView.getContext()).load(file).into(mImageView);
                }else {
                    Picasso.with(itemView.getContext()).load(equipment.getIconOn()).into(mImageView);
                    new CacheImage(mContext).imageDownload(equipment.getIconOn());
                }

            }
            String preSetting = PRE_STATE + String.valueOf(equipment.getId());
            final SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences(preSetting, Context
                    .MODE_PRIVATE);
            boolean state = sharedPreferences.getBoolean(EXTRA_STATE, false);
            final String pre = Constant.PRE_ALARM + String.valueOf(equipment.getId());
            SharedPreferences preferences = mContext.getSharedPreferences(pre, Context.MODE_PRIVATE);
            String alarmOnTime = preferences.getString(PRE_ON, null);
            String alarmOffTime = preferences.getString(PRE_OFF, null);
            if(state){
                mAlarmOn.setVisibility(View.VISIBLE);
            }else {
                if(alarmOffTime!=null||alarmOnTime !=null){
                    mAlarmOff.setVisibility(View.VISIBLE);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnCLick.onClick(equipment,mImageView,mTextView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   new DialogSettingAlarm(itemView.getContext()).showDialogAlarmSettingEquipment(equipment,mList,
                           EquipmentAdapter.this);
                    return true;
                }
            });
        }
    }
}
