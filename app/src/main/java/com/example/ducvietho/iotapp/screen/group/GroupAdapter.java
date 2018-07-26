package com.example.ducvietho.iotapp.screen.group;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.util.CacheImage;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.OnClickItemGroup;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ducvietho.iotapp.util.Constant.EXTRA_STATE_GROUP;
import static com.example.ducvietho.iotapp.util.Constant.PRE_OFF_GROUP;
import static com.example.ducvietho.iotapp.util.Constant.PRE_ON_GROUP;
import static com.example.ducvietho.iotapp.util.Constant.PRE_STATE_GROUP;

/**
 * Created by ducvietho on 2/3/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<Group> mList;
    private OnLongClickItem<Group> mLongClickItem;
    private OnClickItemGroup mOnClickItemGroup;
    private Context mContext;
    public GroupAdapter(Context context,List<Group> list, OnLongClickItem<Group> longClickItem, OnClickItemGroup
            onClickItemGroup) {
        mContext = context;
        mList = list;
        mLongClickItem = longClickItem;
        mOnClickItemGroup = onClickItemGroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
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
        @BindView(R.id.img_group)
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

        public void bind(final Group group) {
            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/UTM Avo.ttf");
            mTextView.setText(group.getName());
            mTextView.setTypeface(font);
            final String preStringAlarm =  Constant.PRE_ALARM_GROUP + String.valueOf(group.getId());
            SharedPreferences sharedPreferencesAlarm = mContext.getSharedPreferences(preStringAlarm, Context.MODE_PRIVATE);
            String alarmOnTime = sharedPreferencesAlarm.getString(PRE_ON_GROUP, null);
            String alarmOffTime = sharedPreferencesAlarm.getString(PRE_OFF_GROUP, null);
            String preSetting = PRE_STATE_GROUP + String.valueOf(group.getId());
            final SharedPreferences sharedPreferences = mContext.getSharedPreferences(preSetting, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean state = sharedPreferences.getBoolean(EXTRA_STATE_GROUP, false);
            String preGroupRepeat = Constant.PRE_REPEAT_GROUP+String.valueOf(group.getId());
            final String extraEquip = Constant.EXTRA_GROUP_REPEAT+String.valueOf(group.getId());
            SharedPreferences sharedPreferencesRepeat = mContext.getSharedPreferences(preGroupRepeat,Context
                    .MODE_PRIVATE);
            Set<String> setDay = sharedPreferencesRepeat.getStringSet(extraEquip,null);
            if(state){
                    mAlarmOn.setVisibility(View.VISIBLE);
                mAlarmOff.setVisibility(View.GONE);
            }else {
                if(alarmOffTime!=null || alarmOnTime!=null){
                    mAlarmOff.setVisibility(View.VISIBLE);
                    mAlarmOn.setVisibility(View.GONE);
                }else {
                    mAlarmOff.setVisibility(View.GONE);
                    mAlarmOn.setVisibility(View.GONE);
                }
            }
            if(group.getIconOff()!=null&&group.getIconOn()!=null){
                if(group.getState()==0){
                    File file = new File(mContext.getCacheDir() + "/iot/"+group.getIconOff().replaceAll("/","") );
                    if(file.exists()){
                        Picasso.with(itemView.getContext()).load(file).into(mImageView);
                    }else {
                        Picasso.with(itemView.getContext()).load(group.getIconOff()).into(mImageView);
                        new CacheImage(mContext).imageDownload(group.getIconOff());
                    }
                }
                else {
                    File file = new File(mContext.getCacheDir()+ "/iot/"+group.getIconOn().replaceAll("/","") );
                    if(file.exists()){
                        Picasso.with(itemView.getContext()).load(file).into(mImageView);
                    }else {
                        Picasso.with(itemView.getContext()).load(group.getIconOn()).into(mImageView);
                        new CacheImage(mContext).imageDownload(group.getIconOn());
                    }
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItemGroup.onClick(group,mImageView,mTextView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickItem.onLongClick(group);
                    return true;
                }
            });
        }
    }
}
